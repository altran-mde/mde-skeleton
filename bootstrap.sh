#!/bin/bash

BASE_DIR=$(pwd $0)
PROJECT_NAME=$1
BUNDLE_SYMBOLICNAME_PREFIX=$2
OUTPUT_DIR=$3

usage() {
	echo "Usage $0 <project-name> <bundle-prefix> <path>"
	echo
	echo "This script will bootstrap a git repository with an"
	echo "EMF based Eclipse RCP application."
	echo
	echo "project-name  - A short descriptive name, e.g., MyEMFApp,"
	echo "                only alphanumeric characters are allowed."
	echo "bundle-prefix - A bundle prefix, e.g., nl.cge.myemfapp,"
	echo "                a lowercase [a-z], dot-separated prefix."
	echo "path          - Points to a local ACIDSpace gitlab clone."
	exit -1
}

if [[ $# -ne 3 ]]; then
	echo "ERROR: expected 3 arguments"
	usage
elif [[ ! "$PROJECT_NAME" =~ ^[a-zA-Z0-9_]{1,8}$ ]]; then
	echo "ERROR: <project-name> '$PROJECT_NAME' should match [a-zA-Z0-9_]{1,8}"
	usage
elif [[ ! "$BUNDLE_SYMBOLICNAME_PREFIX" =~ ^[a-z]+(\.[a-z]+)*$ ]]; then
	echo "ERROR: <bundle-prefix> '$BUNDLE_SYMBOLICNAME_PREFIX' should match [:lower:]+(\.[:lower:]+)*"
	usage
elif [[ ! -d "$OUTPUT_DIR/.git" ]]; then
	echo "ERROR: <path> '$OUTPUT_DIR' does not exist or is not a git repository."
	usage
fi

cd $OUTPUT_DIR
OUTPUT_DIR=$(pwd)

echo "Bootstrapping the $PROJECT_NAME project with prefix $BUNDLE_SYMBOLICNAME_PREFIX in: $OUTPUT_DIR"
echo

cd $BASE_DIR
if ! git diff-index --quiet HEAD --; then
  git status --porcelain
  echo
  echo "Your bootstrap repository contains local changes, please commit these before running this script!"
  exit -1
fi

cd $OUTPUT_DIR
OUTPUT_DIR=$(pwd)

GIT_URL=$(git remote get-url origin)
GIT_URL_REGEX="^([^@]+)@([^:]+):(.+)/([^/]+)\.git$"
[[ $GIT_URL =~ $GIT_URL_REGEX ]]
GIT_PROTOCOL="${BASH_REMATCH[1]}"
GIT_HOST="${BASH_REMATCH[2]}"
GIT_GROUP="${BASH_REMATCH[3]}"
GIT_PROJECT="${BASH_REMATCH[4]}"

if [[ "$GIT_HOST" != "gitlab.acidspace.nl" ]]; then
	echo "Expected output directory '$OUTPUT_DIR' to be an ACIDSpace gitlab clone."
	exit -1
fi

if [[ -f "README.md" ]]; then
	git mv README.md README.adoc
fi

cd $BASE_DIR
IFS=$'\n'
for INPUT_FILE in $(git ls-tree --full-tree --name-only -r HEAD | grep -v '^bootstrap' ); do
	OUTPUT_FILE="${INPUT_FILE//com.altran.ec.mde.skeleton/$BUNDLE_SYMBOLICNAME_PREFIX}"
	OUTPUT_FILE="${OUTPUT_FILE//mde-skeleton/$GIT_PROJECT}"
	OUTPUT_FILE="${OUTPUT_FILE//MDESkeleton/$PROJECT_NAME}"
	OUTPUT_FILE="$OUTPUT_DIR/$OUTPUT_FILE"
	
	echo "Processing file $OUTPUT_FILE"
	mkdir -p "$(dirname $OUTPUT_FILE)" && cp -f "$INPUT_FILE" "$OUTPUT_FILE"
	
	if file "$OUTPUT_FILE" | grep -iq ASCII; then
		# The copied file is a text file, applying substitutions
		sed -i \
			-e "s#com.altran.ec.mde.skeleton#$BUNDLE_SYMBOLICNAME_PREFIX#g" \
			-e "s#EMF Reference Project#$PROJECT_NAME#g" \
			-e "s#MDE Assets - MDE Skeleton#$PROJECT_NAME#g" \
			-e "s#MDESkeleton#$PROJECT_NAME#g" \
			-e "s#ec-mde/mde-skeleton#$GIT_GROUP/$GIT_PROJECT#g" \
			-e "s#mde-skeleton#$GIT_PROJECT#g" \
			"$OUTPUT_FILE"
	fi
done

echo
echo "Committing changes"
cd $OUTPUT_DIR
git commit -a -m "Bootstrapped the $PROJECT_NAME project, using the skeleton as provided by Capgemini Engineering."

echo
echo "Done, use the next commands to build your application:"
echo "> cd $OUTPUT_DIR"
echo "> mvn clean verify -s settings.xml"
echo
echo "Also review the last commit and if all is ok, push it to ACIDSpace."