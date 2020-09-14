# coding=utf8

import os
import argparse
import glob
import sys

from xml.etree import ElementTree

COLOR_RED = '\033[1;31m'
COLOR_YELLOW = '\033[1;33m'
COLOR_BLUE = '\033[1;34m'
COLOR_GREEN = '\033[92m'
COLOR_CYAN = '\033[1;36m'
END_COLOR = '\033[1;m'

MAVEN_NAMESPACE = '{http://maven.apache.org/POM/4.0.0}'

errors = {}


def check_pom_version(pom_path, expected_version):
    project_version = read_version(pom_path)
    if project_version != expected_version:
        errors[pom_path] = project_version
        raise ValueError(
            "the pom %s%s%s is not consistent with this branch. expected version %s" % (
            COLOR_RED, pom_path, END_COLOR, expected_version))


def check_manifest_version(manifest_path, expected_version):
    properties = {}
    project_version = None
    with open(manifest_path, 'r') as file:
        for line in file:
            if ':' in line and 'Bundle-Version' in line:
                key, value = line.split(':', 1)
                project_version = value.strip()
    if project_version is None:
        print('WARNING: the Bundle-Version is missing in file', COLOR_CYAN, manifest_path, END_COLOR)
    if project_version is not None and project_version != expected_version:
        errors[manifest_path] = project_version
        raise ValueError("the manifest %s%s%s is not consistent with this branch. expected version %s%s%s" % (
            COLOR_RED, manifest_path, END_COLOR, COLOR_BLUE, expected_version, END_COLOR))


def log(verbose, message):
    if verbose:
        print(message)


def version_check(directory, pom_version, manifest_version, verbose):
    poms = glob.glob(directory + "/**/pom.xml", recursive=True)
    poms = [pom for pom in poms if 'components_lib' not in pom]
    log(verbose, "found %s%d%s pom.xml files" % (COLOR_BLUE, len(poms), END_COLOR))
    manifests = glob.glob(directory + "/**/MANIFEST.MF", recursive=True)
    manifests = [manifest for manifest in manifests if
                 # 'test%splugins' % os.sep not in manifest and
                 'target' not in manifest]
    log(verbose, "found %s%d%s MANIFEST.MF files" % (COLOR_BLUE, len(manifests), END_COLOR))
    for pom_path in poms:
        try:
            check_pom_version(pom_path, pom_version)
        except ValueError as e:
            print(e)
    for manifest in manifests:
        try:
            check_manifest_version(manifest, manifest_version)
        except ValueError as e:
            print(e)
    return 0


def read_version(pom_path):
    document = ElementTree.parse(pom_path)
    parent_version_node = document.find(MAVEN_NAMESPACE + "parent/" + MAVEN_NAMESPACE + "version")
    project_version_node = document.find(MAVEN_NAMESPACE + "version")
    parent_version = None if parent_version_node is None else parent_version_node.text.strip()
    project_version = None if project_version_node is None else project_version_node.text.strip()
    if project_version is not None and parent_version != project_version:
        raise ValueError("parent version and project version must be the same in pom", pom_path)
    return parent_version


def calculate_manifest_version(pom_version):
    return pom_version.split('-')[0] + ".qualifier"


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='check consistency between versions in pom and manifest')
    parser.add_argument('-d', '--directory', dest='directory', type=str,
                        help='directory where project is located',
                        default='.')
    parser.add_argument('-v', '--verbose', action='store_true', help='some more messages')
    args = parser.parse_args()
    pom_version = read_version(args.directory + "/pom.xml")
    manifest_version = calculate_manifest_version(pom_version)
    print("pom version for the branch must be", COLOR_BLUE, pom_version, END_COLOR)
    print("manifest version for the branch must be", COLOR_BLUE, manifest_version, END_COLOR)
    version_check(args.directory, pom_version, manifest_version, args.verbose)
    nb_errors = len(errors)
    print('')
    if nb_errors > 0:
        print("%sProject is not correctly configured (%s %s found).%s" %
              (COLOR_RED, nb_errors, 'error' if nb_errors == 1 else 'errors', END_COLOR))
    else:
        print("%sProject is correctly configured.%s" % (COLOR_GREEN, END_COLOR))
    sys.exit(nb_errors)
