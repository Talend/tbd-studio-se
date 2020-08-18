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
            "the pom %s is not consistent with this branch. expected version %s" % (pom_path, expected_version))


def check_manifest_version(manifest_path, expected_version):
    properties = {}
    project_version = None
    if 'test%splugins' % os.sep not in manifest_path and 'target' not in manifest_path:
        with open(manifest_path, 'r') as file:
            for line in file:
                if ':' in line and 'Bundle-Version' in line:
                    key, value = line.split(':', 1)
                    project_version = value.strip()
        if project_version is None:
            print('WARNING: the Bundle-Version is missing in file', manifest_path)
        if project_version is not None and project_version != expected_version:
            errors[manifest_path] = project_version
            raise ValueError("the manifest %s is not consistent with this branch. expected version %s" % (
                manifest_path, expected_version))


def version_check(directory, pom_version, manifest_version):
    poms = glob.glob(directory + "/**/pom.xml", recursive=True)
    manifests = glob.glob(directory + "/**/MANIFEST.MF", recursive=True)
    for pom_path in poms:
        if 'components_lib' not in pom_path:
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
    args = parser.parse_args()
    pom_version = read_version(args.directory + "/pom.xml")
    manifest_version = calculate_manifest_version(pom_version)
    print("pom version for the branch must be", pom_version)
    print("manifest version for the branch must be", manifest_version)
    version_check(args.directory, pom_version, manifest_version)
    nb_errors = len(errors)
    if nb_errors > 0:
        print("%sProject is not correctly configured (%s %s found).%s" % (
            COLOR_RED, nb_errors, 'error' if nb_errors == 1 else 'errors', END_COLOR))
    sys.exit(nb_errors)
