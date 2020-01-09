import argparse
import glob
import json
import os
import re
from collections import defaultdict

COLOR_RED = '\033[1;31m'
COLOR_YELLOW = '\033[1;33m'
COLOR_BLUE = '\033[1;34m'
COLOR_GREEN = '\033[92m'
COLOR_CYAN = '\033[1;36m'
END_COLOR = '\033[1;m'


def parse_json_file(distribution_json_file):
    """
    Parse a json distribution file for a distribution id.
    Look for node of type 'dynamicPluginConfiguration' that contains the attribute id
    Note: The file should contains only one distribution.

    :param distribution_json_file: filepath
    :return: the distribution id found or None if not found
    """
    with open(distribution_json_file, 'r') as json_file:
        content = json.load(json_file)
        distributions = [node['id'] for node in content['childNodes'] if
                         node['tagName'] == 'dynamicPluginConfiguration']
        return distributions[0] if len(distributions) > 0 else None


def look_for_builtin_distribution_id(distribution_plugin, root_directory):
    """
    look for distribution id in builtin json files

    For now, org.talend.hadoop.distribution.cdh5x  and org.talend.hadoop.distribution.hdpx ONLY
    :param distribution_plugin: name of the plugin
    :return: list of distribution ID found.
    """
    result = []
    json_files = glob.glob('%s/%s/resources/builtin/**/*.json' % (root_directory, distribution_plugin), recursive=True)
    for json_file in json_files:
        distribution = parse_json_file(json_file)
        if distribution is not None:
            result.append(distribution)
    return result


def parse_java_source_file(distribution_java_class):
    """
    Parse a java source file and look for a VERSION constant.

    :param distribution_java_class: path of the java source file.
    :return: the version or None if no version is found
    """
    result = None
    with open(distribution_java_class, 'r') as source_file:
        for line in source_file:
            if "String VERSION" in line:
                parsing_result = re.match(
                    '(?:.*)public (?:final|static)? (?:final|static)? String VERSION(?:_56)? = "(.*)"', line)
                #     if re.search(r'public final static String VERSION = ".*";', line) is not None:
                result = None if parsing_result is None else parsing_result.group(1)
                if result is not None:
                    break
    return result


def look_for_static_distribution_id(distribution_plugin, root_directory):
    """
    look for distribution id in java files

    :param distribution_plugin: name of the plugin
    :return: list of distribution ID found.
    """
    result = []
    classes = glob.glob('%s/%s/src/**/*Distribution.java' % (root_directory, distribution_plugin), recursive=True)
    for current_class in classes:
        distribution = parse_java_source_file(current_class)
        if distribution is not None:
            result.append(distribution)
    return result


def look_for_distribution_id(distribution_plugin, plugins_directory):
    """
    Look for a distribution Id in a plugin.
    The distribution could be found in a Java source file (old static distribution)
    or a Json (builtin distribution)

    :param distribution_plugin: name of the plugin
    :return: list of distribution ids found. could be empty list if none found
    """
    distribution_ids = look_for_static_distribution_id(distribution_plugin, plugins_directory)
    if distribution_ids is None or len(distribution_ids) == 0:
        distribution_ids = look_for_builtin_distribution_id(distribution_plugin, plugins_directory)
    return distribution_ids


def find_distribution_plugins(plugin_directory="../main/plugins"):
    """
    Look for plugin that contains distribution in the name of the plugin

    :param plugin_directory: where the plugins are located
    :return: list of plugin names
    """
    main_plugins_directories = os.listdir(plugin_directory)
    result = [plugin for plugin in main_plugins_directories if 'distribution.' in plugin]
    return result


def display_result(result, grouped=False):
    """
    display distributions ids by plugins or by distributions
    :param result: map of distributions ids by plugins
    :param grouped: if we display grouped by distributions or by plugins
    """
    GROUPS = ['Cloudera', 'MAPR', 'EMR', 'PIVOTAL_HD', 'HDP', 'MICROSOFT_HD_INSIGHT', 'Databricks',
              'DATAPROC']

    if not grouped:
        for plugin, distribution_ids in result.items():
            distribution_color = COLOR_GREEN if len(distribution_ids) > 0 else COLOR_RED
            print("%s%-50s%s: %s%s%s" % (
                COLOR_BLUE, plugin, END_COLOR, distribution_color, distribution_ids, END_COLOR))
    else:
        groups = defaultdict(list)
        for plugin, distribution_ids in result.items():
            for distribution_id in distribution_ids:
                for group in GROUPS:
                    if group in distribution_id:
                        # workaround on bad naming convention
                        groups[group].append(distribution_id)
        for group, distribution_ids in groups.items():
            distribution_color = COLOR_GREEN if len(distribution_ids) > 0 else COLOR_RED
            print("%s%-20s%s: %s%s%s" % (COLOR_BLUE, group, END_COLOR, distribution_color, distribution_ids, END_COLOR))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='list distribution ids of supported distributions')
    parser.add_argument('-d', '--plugin-directory', dest='directory', type=str,
                        help='where plugins are located', default='../main/plugins')
    parser.add_argument('-g', '--grouped', help='display result grouped by distributions', action='store_true')
    args = parser.parse_args()
    plugin_directory = args.directory

    supported_distributions = dict()
    distributions_plugins_directories = find_distribution_plugins(plugin_directory)
    for distribution_plugin in distributions_plugins_directories:
        distribution_ids = look_for_distribution_id(distribution_plugin, plugin_directory)
        supported_distributions[distribution_plugin] = distribution_ids
    display_result(supported_distributions, args.grouped)
