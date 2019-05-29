package com.amazon.checkerframework.compliance.kms.inference;

import checkers.inference.InferenceAnnotatedTypeFactory;
import checkers.inference.InferenceChecker;
import checkers.inference.InferrableChecker;
import checkers.inference.SlotManager;
import checkers.inference.model.ConstraintManager;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;

public class ComplianceInferenceAnnotatedTypeFactory extends InferenceAnnotatedTypeFactory {

    public ComplianceInferenceAnnotatedTypeFactory(InferenceChecker inferenceChecker,
                                                   boolean withCombineConstraints,
                                                   BaseAnnotatedTypeFactory realTypeFactory,
                                                   InferrableChecker realChecker, SlotManager slotManager,
                                                   ConstraintManager constraintManager) {
        super(inferenceChecker, withCombineConstraints, realTypeFactory, realChecker, slotManager, constraintManager);
        postInit();
    }
}
