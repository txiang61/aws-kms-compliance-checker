#!/bin/bash
set -e

# Environment
export JSR308=/Users/ttx/Documents/jsr308_inference
export CF=$JSR308/checker-framework
export JAVAC=$CF/checker/bin/javac
export AWSKMS=$JSR308/aws-sdk-java/aws-java-sdk-kms/target

export KMS=$JSR308/aws-kms-compliance-checker

# Dependencies
export CLASSPATH=$KMS/build/classes/java/main:$AWSKMS/aws-java-sdk-kms-1.11.562-SNAPSHOT.jar:$KMS/aws-java-sdk-core-1.11.561.jar

# Command
DEBUG=""
CHECKER="com.amazon.checkerframework.compliance.kms.typecheck.ComplianceChecker"

declare -a ARGS
for i in "$@" ; do
    if [[ $i == "-d" ]] ; then
        echo "Typecheck using debug mode. Listening at port 5050. Waiting for connection...."
        DEBUG="-J-Xdebug -J-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5050"
        continue
    fi

    if [[ $i == "-i" ]] ; then
        echo "Typecheck using aws-kms-compliance-checker typechecking mode"
        CHECKER="com.amazon.checkerframework.compliance.kms.ComplianceChecker"
        continue
    fi
    ARGS[${#ARGS[@]}]="$i"
done

cmd=""

if [ "$DEBUG" == "" ]; then
	cmd="$JAVAC -cp "${CLASSPATH}" -processor "${CHECKER}" "${ARGS[@]}""
else
	cmd="$JAVAC "$DEBUG" -cp "${CLASSPATH}" -processor "${CHECKER}" -AatfDoNotCache "${ARGS[@]}""
fi

eval "$cmd"