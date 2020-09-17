#!/usr/bin/env bash

# Ignore for acidcli, only used by script
SCRIPT_DIR=$(dirname $0)

# ACIDCLI variables should be configurable via .acidcli.yml
ACIDCLI_MDE_REPOSITORY_REMOTE_NAME="MDEAssets/mde-skeleton"
ACIDCLI_MDE_REPOSITORY_PROJECT="${SCRIPT_DIR}/../products/com.altran.ec.mde.skeleton.package.product"

REMOTE_REPOSITORY_URL="https://nexus.acidspace.nl/repository/${ACIDCLI_MDE_REPOSITORY_REMOTE_NAME}"
# TODO: Do we only want to support specific branch names for URL translation?
if [ ! -z "${CI_COMMIT_REF_NAME}" ]; then
    echo "${CI_JOB_NAME} from branch ${CI_COMMIT_REF_NAME}"
    REMOTE_REPOSITORY_URL="${REMOTE_REPOSITORY_URL}/${CI_COMMIT_REF_NAME}"
    echo "P2 repository location: ${REMOTE_REPOSITORY_URL}"
elif [ ! -z "${CI_COMMIT_TAG}" ]; then
    echo "${CI_JOB_NAME} from tag ${CI_COMMIT_TAG}"
    DOTS=${CI_COMMIT_TAG//[^.]}
    if [ ${#DOTS} == 2 ]; then
        REMOTE_REPOSITORY_URL="${REMOTE_REPOSITORY_URL}/${CI_COMMIT_TAG%.*}"
    else
        REMOTE_REPOSITORY_URL="${REMOTE_REPOSITORY_URL}/${CI_COMMIT_TAG}"
    fi
    echo "P2 repository location: ${REMOTE_REPOSITORY_URL}"
else
    exit 1
fi

# Maven requires a full-path, hence realpath
LOCAL_REPOSITORY_DIR=$(realpath ${ACIDCLI_MDE_REPOSITORY_PROJECT}/target)
MAVEN_ARGS="-Dacidcli.repository.remoteUri=${REMOTE_REPOSITORY_URL} -Dacidcli.repository.localDir=${LOCAL_REPOSITORY_DIR}"

# TODO: If a P2 repository is already available at ${REMOTE_REPOSITORY_URL}/repository,
#       it should be downloaded to a temp directory and the location of this directory 
#       should be provided as acidcli.p2.mergeDir property
# TODO: Should we add a flag to acidcli to configure merge/overwrite?
# MAVEN_ARGS="${MAVEN_ARGS} -Dacidcli.repository.localUri=<<LOCAL_REPOSITORY_DIR as URI>> -Dacidcli.p2.mergeDir=<<temp dir>>"

# Start the build.
mvn deploy -s ${SCRIPT_DIR}/mde-release-settings.xml -f ${SCRIPT_DIR}/mde-release-pom.xml ${MAVEN_ARGS} -e -U -B
