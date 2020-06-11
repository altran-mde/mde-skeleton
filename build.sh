#!/usr/bin/env bash

SCRIPT_DIR=$(dirname $0)
XVFB_PID=""

if [ ! -z "$CI_COMMIT_REF_NAME" ]; then
    echo "$CI_JOB_NAME from branch $CI_COMMIT_REF_NAME"
elif [ ! -z "$CI_COMMIT_TAG" ]; then
    echo "$CI_JOB_NAME from tag $CI_COMMIT_TAG"
else
    echo "Developer build"
fi

if which Xvfb > /dev/null 2>&1; then
    export DISPLAY=:99
    Xvfb $DISPLAY -screen 0 1280x1024x16 -nolisten tcp &
    XVFB_PID=$!
    echo "Started X virtual framebuffer (Xvfb $DISPLAY) => PID $XVFB_PID"
else
    echo "X virtual framebuffer (Xvfb) not installed, please ensure that a display is available."
fi

# Start the build.
mvn clean verify -s $SCRIPT_DIR/settings.xml -f $SCRIPT_DIR/pom.xml -e -U

# Get build status.
build_success=$?

if [ ! -z "$XVFB_PID" ]; then
    echo "Terminate X virtual framebuffer (Xvfb)."
    kill $XVFB_PID
fi

exit $build_success
