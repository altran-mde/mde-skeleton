#!/usr/bin/env bash

# Ignore for acidcli, only used by script
SCRIPT_DIR=$(dirname $0)

# ACIDCLI variables should be configurable via .acidcli.yml
ACIDCLI_MAVEN_POM="${SCRIPT_DIR}/../pom.xml"
ACIDCLI_MAVEN_SETTINGS="${SCRIPT_DIR}/../settings.xml"

# Start the build.
mvn package -s ${ACIDCLI_MAVEN_SETTINGS} -f ${ACIDCLI_MAVEN_POM} -DskipTests=true -e -U -B
