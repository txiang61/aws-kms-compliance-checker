#!/bin/bash

# Environment
export JSR308=/Users/ttx/Documents/jsr308_inference
export CF=$JSR308/checker-framework
export CFI=$JSR308/checker-framework-inference
export AWSKMS=$JSR308/aws-sdk-java/aws-java-sdk-kms/target

export KMS=$JSR308/aws-kms-compliance-checker

# Dependencies
export CLASSPATH=$KMS/build/classes/java/main:$CFI/dist/checker-framework-inference.jar:$AWSKMS/aws-java-sdk-kms-1.11.562-SNAPSHOT.jar:$KMS/aws-java-sdk-core-1.11.561.jar
export external_checker_classpath=$KMS/build/classes/java/main:$KMS/build/resources/main

export AFU=$JSR308/annotation-tools/annotation-file-utilities
export PATH=$AFU/scripts:$PATH
export JDK_JAR=$CF/checker/dist/jdk8.jar

Z3=$JSR308/z3/bin
export PATH=$Z3:$PATH
export DYLD_LIBRARY_PATH = $Z3

CHECKER=com.amazon.checkerframework.compliance.kms.inference.ComplianceInferenceChecker

SOLVER=com.amazon.checkerframework.compliance.kms.inference.solver.ComplianceSolverEngine

declare -a ARGS
for i in "$@" ; do
    if [[ $i == "-ds" ]] ; then
        echo "Configured to use debug solver"
        SOLVER="checkers.inference.solver.DebugSolver"
        continue
    fi
    ARGS[${#ARGS[@]}]="$i"
done

SOLVER_ARGS=useGraph=false,collectStatistic=true

IS_HACK=true

# echo "${ARGS[@]}"

# Start the inference
$CFI/scripts/inference-dev -m ROUNDTRIP --checker "$CHECKER" --solver "$SOLVER" \
    --solverArgs="useGraph=false,collectStatistic=true" --hacks="$IS_HACK" \
    -afud ./annotated "${ARGS[@]}"
