package com.amazon.checkerframework.compliance.kms.inference;

import checkers.inference.InferenceChecker;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import checkers.inference.InferenceVisitor;
import checkers.inference.InferenceAnnotatedTypeFactory;
import checkers.inference.InferenceChecker;
import checkers.inference.InferenceMain;
import checkers.inference.InferenceVisitor;
import checkers.inference.SlotManager;
import checkers.inference.VariableAnnotator;
import checkers.inference.model.ArithmeticConstraint.ArithmeticOperationKind;
import checkers.inference.model.ArithmeticVariableSlot;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.ConstraintManager;
import checkers.inference.model.Slot;
import checkers.inference.model.VariableSlot;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.UnaryTree;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.TreeUtils;

public class ComplianceInferenceVisitor extends InferenceVisitor<ComplianceInferenceChecker, BaseAnnotatedTypeFactory> {
    public ComplianceInferenceVisitor(ComplianceInferenceChecker checker,
                                      InferenceChecker ichecker,
                                      BaseAnnotatedTypeFactory factory,
                                      boolean infer) {
        super(checker, ichecker, factory, infer);
    }

    @Override
    public Void visitBinary(BinaryTree binaryTree, Void p) {
        // infer mode, adds constraints for binary operations
        if (infer) {
            SlotManager slotManager = InferenceMain.getInstance().getSlotManager();
            ConstraintManager constraintManager =
                    InferenceMain.getInstance().getConstraintManager();

            // Candidate Fix 1:
            InferenceAnnotatedTypeFactory iatf = (InferenceAnnotatedTypeFactory) atypeFactory;

            AnnotatedTypeMirror lhsATM = iatf.getAnnotatedType(binaryTree.getLeftOperand());
            AnnotatedTypeMirror rhsATM = iatf.getAnnotatedType(binaryTree.getRightOperand());
            // For types such as T extends @VarAnnot() Class, use @VarAnnot(), by grabbing
            // the effective annotation in varannot hierarchy
            AnnotationMirror lhsEAM = lhsATM.getEffectiveAnnotationInHierarchy(iatf.getVarAnnot());
            AnnotationMirror rhsEAM = rhsATM.getEffectiveAnnotationInHierarchy(iatf.getVarAnnot());
            Slot lhs = slotManager.getSlot(lhsEAM);
            Slot rhs = slotManager.getSlot(rhsEAM);

            Kind kind = binaryTree.getKind();
            switch (binaryTree.getKind()) {
                case PLUS:
                    if (TreeUtils.isStringConcatenation(binaryTree)) {
                        break;
                    }
                default:
                    // TODO: replace with LUBSlot pending mier's PR
                    VariableSlot lubSlot =
                            slotManager.getVariableSlot(atypeFactory.getAnnotatedType(binaryTree));
                    // Create LUB constraint by default
                    constraintManager.addSubtypeConstraint(lhs, lubSlot);
                    constraintManager.addSubtypeConstraint(rhs, lubSlot);
                    break;
            }
        }

        return super.visitBinary(binaryTree, p);
    }
}
