package com.amazon.checkerframework.compliance.kms.inference;

import checkers.inference.InferenceAnnotatedTypeFactory;
import checkers.inference.InferenceChecker;
import checkers.inference.InferrableChecker;
import checkers.inference.SlotManager;
import checkers.inference.model.ConstraintManager;
import com.amazon.checkerframework.compliance.kms.inference.qual.*;
import com.sun.source.tree.*;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.javacutil.AnnotationUtils;
import checkers.inference.InferenceAnnotatedTypeFactory;
import checkers.inference.InferenceChecker;
import checkers.inference.InferenceTreeAnnotator;
import checkers.inference.InferrableChecker;
import checkers.inference.SlotManager;
import checkers.inference.VariableAnnotator;
import checkers.inference.model.ConstraintManager;
import checkers.inference.util.InferenceViewpointAdapter;
import com.sun.source.util.TreePath;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.AnnotatedTypeMirror.AnnotatedDeclaredType;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.framework.type.typeannotator.ListTypeAnnotator;
import org.checkerframework.framework.type.typeannotator.TypeAnnotator;
import org.checkerframework.framework.type.typeannotator.ImplicitsTypeAnnotator;
import org.checkerframework.javacutil.AnnotationBuilder;
import org.checkerframework.javacutil.Pair;
import org.checkerframework.javacutil.TreeUtils;
import checkers.inference.model.VariableSlot;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;

public class ComplianceInferenceAnnotatedTypeFactory extends InferenceAnnotatedTypeFactory {

    protected final AnnotationMirror UNKNOWNVAL;

    protected final AnnotationMirror BOTTOMVAL;

    public ComplianceInferenceAnnotatedTypeFactory(
            InferenceChecker inferenceChecker,
            boolean withCombineConstraints,
            BaseAnnotatedTypeFactory realTypeFactory,
            InferrableChecker realChecker,
            SlotManager slotManager,
            ConstraintManager constraintManager) {
        super(
                inferenceChecker,
                withCombineConstraints,
                realTypeFactory,
                realChecker,
                slotManager,
                constraintManager);
        BOTTOMVAL = AnnotationBuilder.fromClass(elements, BottomAES.class);
        UNKNOWNVAL = AnnotationBuilder.fromClass(elements, UnknownVal.class);
        postInit();
    }

    @Override
    public TypeAnnotator createTypeAnnotator() {
        return new ListTypeAnnotator(new ComplianceInferenceImplicitsTypeAnnotator(this));
    }

    @Override
    public TreeAnnotator createTreeAnnotator() {
        return new ListTreeAnnotator(new ComplianceInferencePropagationTreeAnnotator(this),
                new ComplianceInferenceImplicitsTreeAnnotator(this),
                new CompilanceInferenceTreeAnnotator(this, realChecker, realTypeFactory, variableAnnotator, slotManager));
    }

    protected class CompilanceInferenceTreeAnnotator extends InferenceTreeAnnotator {
        public CompilanceInferenceTreeAnnotator(InferenceAnnotatedTypeFactory atypeFactory, InferrableChecker realChecker, AnnotatedTypeFactory realAnnotatedTypeFactory, VariableAnnotator variableAnnotator, SlotManager slotManager) {
            super(atypeFactory, realChecker, realAnnotatedTypeFactory, variableAnnotator, slotManager);
        }

        public Void visitLiteral(LiteralTree tree, AnnotatedTypeMirror type) {
            Object value = tree.getValue();
            switch (tree.getKind()) {
                case CHAR_LITERAL:
                case LONG_LITERAL:
                case INT_LITERAL:
                    Number data_num = (Number) value;
                    if (data_num.equals(256)) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, IntVal256.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else if (data_num.equals(128)) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, IntVal128.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, IntVal.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    }
                    return null;
                case STRING_LITERAL:
                    String data_string= (String) value;
                    if (data_string.equals("AES_256")) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, AES_256.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else if (data_string.equals("AES_128")) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, AES_128.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else if (data_string.equals("AES")) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, AES.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else if (data_string.equals("_")) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, Underline.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else if (data_string.equals("256")) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, IntVal256.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else if (data_string.equals("128")) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, IntVal128.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, StringVal.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    }
                    return null;
                default:
                    return null;
            }
        }
    }

    public class ComplianceInferenceImplicitsTreeAnnotator extends ImplicitsTreeAnnotator {

        public ComplianceInferenceImplicitsTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
            super(atypeFactory);

            // Add literal kinds
            addLiteralKind(LiteralKind.NULL, AnnotationBuilder.fromClass(elements, BottomAES.class));
            addLiteralKind(LiteralKind.STRING, AnnotationBuilder.fromClass(elements, StringVal.class));
            addLiteralKind(LiteralKind.CHAR, AnnotationBuilder.fromClass(elements, IntVal.class));
            addLiteralKind(LiteralKind.INT, AnnotationBuilder.fromClass(elements, IntVal.class));
            addLiteralKind(LiteralKind.LONG, AnnotationBuilder.fromClass(elements, IntVal.class));
            addLiteralKind(LiteralKind.BOOLEAN, AnnotationBuilder.fromClass(elements, UnknownVal.class));
        }
    }

    public class ComplianceInferenceImplicitsTypeAnnotator extends ImplicitsTypeAnnotator {

        public ComplianceInferenceImplicitsTypeAnnotator(AnnotatedTypeFactory atypeFactory) {
            super(atypeFactory);

            // Add type kinds
            addTypeKind(TypeKind.BYTE, AnnotationBuilder.fromClass(elements, IntVal.class));
            addTypeKind(TypeKind.SHORT, AnnotationBuilder.fromClass(elements, IntVal.class));
            addTypeKind(TypeKind.CHAR, AnnotationBuilder.fromClass(elements, IntVal.class));
            addTypeKind(TypeKind.INT, AnnotationBuilder.fromClass(elements, IntVal.class));
            addTypeKind(TypeKind.LONG, AnnotationBuilder.fromClass(elements, IntVal.class));
            addTypeKind(TypeKind.NULL, AnnotationBuilder.fromClass(elements, BottomAES.class));
            addTypeKind(TypeKind.DECLARED, AnnotationBuilder.fromClass(elements, StringVal.class));

            // Add type class name
            addTypeName(String.class, AnnotationBuilder.fromClass(elements, StringVal.class));
            addTypeName(Integer.class, AnnotationBuilder.fromClass(elements, IntVal.class));
        }
    }

    private final class ComplianceInferencePropagationTreeAnnotator extends PropagationTreeAnnotator {
        public ComplianceInferencePropagationTreeAnnotator(AnnotatedTypeFactory factory) {
            super(factory);
        }

        @Override
        public Void visitBinary(BinaryTree binaryTree, AnnotatedTypeMirror type) {
            Tree.Kind kind = binaryTree.getKind();
            AnnotatedTypeMirror lhsATM = atypeFactory.getAnnotatedType(binaryTree.getLeftOperand());
            AnnotatedTypeMirror rhsATM = atypeFactory.getAnnotatedType(binaryTree.getRightOperand());
            AnnotationMirror lhsAM = lhsATM.getEffectiveAnnotationInHierarchy(UNKNOWNVAL);
            AnnotationMirror rhsAM = rhsATM.getEffectiveAnnotationInHierarchy(UNKNOWNVAL);

            switch (kind) {
                case PLUS:
                    if (lhsAM == null || rhsAM == null) {
                        return super.visitBinary(binaryTree, type);
                    }
                    if (AnnotationUtils.areSameByClass(rhsAM, AES.class)
                            && AnnotationUtils.areSameByClass(lhsAM, Underline.class)) {
                        type.replaceAnnotation(AnnotationBuilder.fromClass(elements, AES_.class));
                    } else if (AnnotationUtils.areSameByClass(rhsAM, AES_.class)
                            && AnnotationUtils.areSameByClass(lhsAM, IntVal256.class)) {
                        type.replaceAnnotation(AnnotationBuilder.fromClass(elements, AES_256.class));
                    } else if (AnnotationUtils.areSameByClass(rhsAM, AES_.class)
                            && AnnotationUtils.areSameByClass(lhsAM, IntVal128.class)) {
                        type.replaceAnnotation(AnnotationBuilder.fromClass(elements, AES_128.class));
                    } else {
                        return super.visitBinary(binaryTree, type);
                    }
                    break;
                default:
                    // Check LUB by default
                    return super.visitBinary(binaryTree, type);
            }

            return null;
        }
    }

    @Override
    public VariableAnnotator createVariableAnnotator() {
        return new ComplianceVariableAnnotator(
                this, realTypeFactory, realChecker, slotManager, constraintManager);
    }

    private final class ComplianceVariableAnnotator extends VariableAnnotator {
        public ComplianceVariableAnnotator(
                InferenceAnnotatedTypeFactory typeFactory,
                AnnotatedTypeFactory realTypeFactory,
                InferrableChecker realChecker,
                SlotManager slotManager,
                ConstraintManager constraintManager) {
            super(typeFactory, realTypeFactory, realChecker, slotManager, constraintManager);
        }
//
//        @Override
//        public void handleBinaryTree(AnnotatedTypeMirror atm, BinaryTree binaryTree) {
//            // Super creates an LUB constraint by default, we create an VariableSlot here
//            // instead for the result of the binary op and create LUB constraint in units
//            // visitor.
//            if (treeToVarAnnoPair.containsKey(binaryTree)) {
//                atm.replaceAnnotations(treeToVarAnnoPair.get(binaryTree).second);
//            } else {
//                // grab slots for the component (only for lub slot)
//                AnnotatedTypeMirror lhsATM =
//                        inferenceTypeFactory.getAnnotatedType(binaryTree.getLeftOperand());
//                AnnotatedTypeMirror rhsATM =
//                        inferenceTypeFactory.getAnnotatedType(binaryTree.getRightOperand());
//                AnnotationMirror lhsAM = lhsATM.getEffectiveAnnotationInHierarchy(UNKNOWNVAL);
//                AnnotationMirror rhsAM = rhsATM.getEffectiveAnnotationInHierarchy(UNKNOWNVAL);
//                VariableSlot lhs = slotManager.getVariableSlot(lhsATM);
//                VariableSlot rhs = slotManager.getVariableSlot(rhsATM);
//
//                // create varslot for the result of the binary tree computation
//                // note: constraints for binary ops are added in UnitsVisitor
//                VariableSlot result;
//                switch (binaryTree.getKind()) {
//                    case PLUS:
//                        if (lhsAM == null || rhsAM == null) {
//                            return super.visitBinary(binaryTree, type);
//                        }
//                        if (AnnotationUtils.areSameByClass(rhsAM, AES.class)
//                                && AnnotationUtils.areSameByClass(lhsAM, Underline.class)) {
//                            type.replaceAnnotation(AnnotationBuilder.fromClass(elements, AES_.class));
//                        } else if (AnnotationUtils.areSameByClass(rhsAM, AES_.class)
//                                && AnnotationUtils.areSameByClass(lhsAM, IntVal256.class)) {
//                            type.replaceAnnotation(AnnotationBuilder.fromClass(elements, AES_256.class));
//                        } else if (AnnotationUtils.areSameByClass(rhsAM, AES_.class)
//                                && AnnotationUtils.areSameByClass(lhsAM, IntVal128.class)) {
//                            type.replaceAnnotation(AnnotationBuilder.fromClass(elements, AES_128.class));
//                        } else {
//                            return super.visitBinary(binaryTree, type);
//                        }
//                        slotManager.createArithmeticVariableSlot()
//
//                        // if it is a string concatenation, result is dimensionless
//                        if (TreeUtils.isStringConcatenation(binaryTree)) {
//                            result = slotManager.createConstantSlot(unitsRepUtils.DIMENSIONLESS);
//                            break;
//                        } // else create arithmetic slot
//                    default:
//                        result = slotManager.createLubVariableSlot(lhs, rhs);
//                        break;
//                }
//
//                // insert varAnnot of the slot into the ATM
//                AnnotationMirror resultAM = slotManager.getAnnotation(result);
//                atm.clearAnnotations();
//                atm.replaceAnnotation(resultAM);
//
//                // add to cache
//                Set<AnnotationMirror> resultSet = AnnotationUtils.createAnnotationSet();
//                resultSet.add(resultAM);
//                final Pair<VariableSlot, Set<? extends AnnotationMirror>> varATMPair =
//                        Pair.of(slotManager.getVariableSlot(atm), resultSet);
//                treeToVarAnnoPair.put(binaryTree, varATMPair);
//            }
//        }
    }
}
