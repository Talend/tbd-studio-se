import argparse
import javaproperties
import os
import xml.etree.ElementTree as xml
import sys

COLOR_RED = '\033[1;31m'
COLOR_YELLOW = '\033[1;33m'
COLOR_BLUE = '\033[1;34m'
COLOR_GREEN = '\033[92m'
COLOR_CYAN = '\033[1;36m'
END_COLOR = '\033[1;m'

MAVEN_NAMESPACE = '{http://maven.apache.org/POM/4.0.0}'
SOURCE_DIRECTORY_NODE = MAVEN_NAMESPACE + 'build/' + MAVEN_NAMESPACE + 'sourceDirectory'
OUTPUT_DIRECTORY_NODE = MAVEN_NAMESPACE + 'build/' + MAVEN_NAMESPACE + 'outputDirectory'
DEFAULT_MAVEN_SOURCE_DIR = 'src/main/java'
DEFAULT_MAVEN_OUTPUT_DIR = 'target/classes'
DEFAULT_ECLIPSE_PLUGIN_SOURCE_DIR = 'src'
DEFAULT_ECLIPSE_PLUGIN_OUTPUT_DIR = 'bin'


class InvalidPluginException(Exception):
    """Base class for exceptions in this module."""
    pass


def yellow(text):
    return '%s%s%s' % (COLOR_YELLOW, str(text), END_COLOR)


def red(text):
    return '%s%s%s' % (COLOR_RED, str(text), END_COLOR)


def green(text):
    return '%s%s%s' % (COLOR_GREEN, str(text), END_COLOR)


def plugin_name(plugin_path):
    return plugin_path.split(os.sep)[-1]


def get_directories_in_pom(plugin_path):
    """
    retreive source and output directories define in pom
    or default values of maven if not defined
    :param pom_path:
    :return:
    """
    pom_path = os.path.join(plugin_path, 'pom.xml')
    if not os.path.isfile(pom_path):
        raise InvalidPluginException('missing pom.xml in the plugin %s' % red(plugin_name(plugin_path)))
    pom_file = xml.parse(pom_path)
    source_directory_node = pom_file.find(SOURCE_DIRECTORY_NODE)
    output_directory_node = pom_file.find(OUTPUT_DIRECTORY_NODE)
    source_directory_in_pom = source_directory_node.text if source_directory_node is not None else 'src/main/java'
    output_directory_in_pom = output_directory_node.text if output_directory_node is not None else 'target/classes'
    # output_directory_in_pom = output_directory_in_pom.replace('/classes', '')
    return source_directory_in_pom, output_directory_in_pom


def get_directories_in_properties(plugin_path):
    build_properties_path = os.path.join(plugin_path, 'build.properties')
    if not os.path.isfile(build_properties_path):
        raise InvalidPluginException('missing build.properties in the plugin %s' % red(plugin_name(plugin_path)))
    build_properties = javaproperties.load(open(build_properties_path, 'r'))
    source_directory_in_properties = build_properties['source..'] if 'source..' in build_properties else None
    output_directory_in_properties = build_properties['output..'] if 'output..' in build_properties else None
    if source_directory_in_properties is not None and source_directory_in_properties.endswith('/'):
        source_directory_in_properties = source_directory_in_properties[:-1]
    if output_directory_in_properties is not None and output_directory_in_properties.endswith('/'):
        output_directory_in_properties = output_directory_in_properties[:-1]
    return source_directory_in_properties, output_directory_in_properties


def get_directories_on_filesystem(plugin_path, source_directory_in_pom, output_directory_in_pom):
    source_directory_on_filesystem = source_directory_in_pom if os.path.isdir(
        os.path.join(plugin_path, source_directory_in_pom)) else DEFAULT_ECLIPSE_PLUGIN_SOURCE_DIR if os.path.isdir(
        os.path.join(plugin_path, DEFAULT_ECLIPSE_PLUGIN_SOURCE_DIR)) else None
    output_directory_on_filesystem = output_directory_in_pom if os.path.isdir(
        os.path.join(plugin_path, output_directory_in_pom)) else DEFAULT_ECLIPSE_PLUGIN_OUTPUT_DIR if os.path.isdir(
        os.path.join(plugin_path, DEFAULT_ECLIPSE_PLUGIN_OUTPUT_DIR)) else None
    return source_directory_on_filesystem, output_directory_on_filesystem


def check_consistency(plugin_path):
    result = 0
    source_directory_in_pom, output_directory_in_pom = get_directories_in_pom(plugin_path)
    source_directory_in_properties, output_directory_in_properties = get_directories_in_properties(plugin_path)
    source_directory_on_filesystem, output_directory_on_filesystem = get_directories_on_filesystem(plugin_path,
                                                                                                   source_directory_in_pom,
                                                                                                   output_directory_in_pom)

    # case of standard plugin with a source directory
    if source_directory_in_pom == source_directory_in_properties and \
            source_directory_in_pom == source_directory_on_filesystem and \
            output_directory_in_pom == output_directory_in_properties and \
            (output_directory_in_pom == output_directory_on_filesystem or output_directory_on_filesystem is None):
        print("INFO : %s is %s (%s)" % (
            yellow(plugin_name(plugin_path)), green('consistent'), yellow(source_directory_in_pom)))
    # case of libraries plugin without any java source directory (just lib import)
    elif source_directory_on_filesystem is None and \
            source_directory_in_properties is None and \
            source_directory_in_pom == DEFAULT_MAVEN_SOURCE_DIR and \
            output_directory_in_pom == DEFAULT_MAVEN_OUTPUT_DIR and \
            (output_directory_in_pom == output_directory_in_properties or output_directory_in_properties is None) and \
            (output_directory_in_pom == output_directory_on_filesystem or output_directory_on_filesystem is None):
        print("INFO : %s is %s (library plugin, no source directory)" % (
            yellow(plugin_name(plugin_path)), green('consistent')))
    else:
        print("ERROR : %s is %s" % (yellow(plugin_name(plugin_path)), red('misconfigured')))
        print("ERROR : sources : system: %6s, pom: %6s, properties: %6s" % (
            source_directory_on_filesystem, source_directory_in_pom, source_directory_in_properties))
        print("ERROR : output  : system: %6s, pom: %6s, properties: %6s" % (
            output_directory_on_filesystem, output_directory_in_pom, output_directory_in_properties))
        result = 1
    return result


def sanity_check(directories):
    plugins = []
    for directory in directories:
        print("looking for plugins in %s" % yellow(directory))
        items = os.listdir(directory)
        for item in items:
            item_path = os.path.join(directory, item)
            if os.path.isdir(item_path) and item.startswith('org.talend'):
                plugins.append(item_path)
    nb_plugins = len(plugins)
    if nb_plugins > 0:
        print("found %s plugins" % yellow(nb_plugins))
    else:
        print("found %s plugins" % red(nb_plugins))
    errors = 0
    for plugin_path in plugins:
        errors += check_consistency(plugin_path)
    return errors


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='check consistency between directory structure, pom.xml and build.properties')
    parser.add_argument('-d', '--directories', dest='directories', type=str,
                        help='list of directory where plugins are located',
                        default='main/plugins:test/plugins')
    args = parser.parse_args()
    plugin_directories = args.directories.split(':')
    nb_errors = sanity_check(plugin_directories)
    if nb_errors != 0:
        print("%sProject is not correctly configured (%s %s found).%s" % (
            COLOR_RED, nb_errors, 'error' if nb_errors == 1 else 'errors', END_COLOR))
    sys.exit(nb_errors)
