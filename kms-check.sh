#!/bin/bash

WORKING_DIR=$(pwd)
ROOT=$(cd $(dirname "$0")/.. && pwd)
JAVAC=$ROOT/checker-framework/checker/bin-devel/javac

KMS_CHECKER=$ROOT/cast-checker

cd $WORKING_DIR

files=$1
java_files=""
shift
while [ $# -gt 0 ]
do
    files="$files $1"
    shift
done

for entry in $(find $files -name '*.java' -or -name '*.doc')
do
    java_files="$java_files $entry"
done

echo $java_files

# no cast checker invocation:
# $JAVAC $java_files

# has cast checker invocation:
# -Acfgviz=org.checkerframework.dataflow.cfg.DOTCFGVisualizer,verbose,outdir=dotfile
$JAVAC -AuseDefaultsForUncheckedCode=bytecode -processor com.amazon.checkerframework.compliance.kms.ComplianceChecker -cp $KMS_CHECKER/bin:$KMS_CHECKER/lib $java_files 
