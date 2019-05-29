package com.amazon.checkerframework.compliance.kms.inference;

import checkers.inference.BaseInferrableChecker;
import checkers.inference.InferenceAnnotatedTypeFactory;
import checkers.inference.InferenceChecker;
import checkers.inference.InferenceVisitor;
import checkers.inference.InferrableChecker;
import checkers.inference.SlotManager;
import checkers.inference.model.ConstraintManager;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.framework.source.SupportedOptions;
import checkers.inference.dataflow.InferenceAnalysis;
import checkers.inference.dataflow.InferenceTransfer;
import org.checkerframework.framework.flow.CFTransfer;

/**
 * Main entry class
 */
@SupportedOptions({"upcast", "anycast", "comparablecast", "optimalSolution"})
public class ComplianceInferenceChecker extends BaseInferrableChecker {

    @Override
    public void initChecker() {
        super.initChecker();
    }

    @Override
    public CFTransfer createInferenceTransferFunction(InferenceAnalysis analysis) {
        return new InferenceTransfer(analysis);
    }

    @Override
    public BaseAnnotatedTypeFactory createRealTypeFactory() {
        return new ComplianceSimpleAnnotatedTypeFactory(this);
    }

    @Override
    public InferenceAnnotatedTypeFactory createInferenceATF(InferenceChecker inferenceChecker,
                                                            InferrableChecker realChecker,
                                                            BaseAnnotatedTypeFactory realTypeFactory,
                                                            SlotManager slotManager,
                                                            ConstraintManager constraintManager) {
        return new ComplianceInferenceAnnotatedTypeFactory(inferenceChecker,
                realChecker.withCombineConstraints(),
                realTypeFactory,
                realChecker,
                slotManager,
                constraintManager);
    }

    @Override
    public InferenceVisitor<?, ?> createVisitor(InferenceChecker ichecker, BaseAnnotatedTypeFactory factory, boolean infer) {
        return new ComplianceInferenceVisitor(this, ichecker, factory, infer);
    }

    @Override
    public boolean withCombineConstraints() {
        return false;
    }

    @Override
    public boolean isInsertMainModOfLocalVar() {
        return false;
    }
}