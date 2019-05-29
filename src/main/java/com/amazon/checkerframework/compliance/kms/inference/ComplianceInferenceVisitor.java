package com.amazon.checkerframework.compliance.kms.inference;

import checkers.inference.InferenceChecker;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import checkers.inference.InferenceVisitor;

public class ComplianceInferenceVisitor extends InferenceVisitor<ComplianceInferenceChecker, BaseAnnotatedTypeFactory> {
    public ComplianceInferenceVisitor(ComplianceInferenceChecker checker,
                                      InferenceChecker ichecker,
                                      BaseAnnotatedTypeFactory factory,
                                      boolean infer) {
        super(checker, ichecker, factory, infer);
    }
}
