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

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;

public class ComplianceInferenceAnnotatedTypeFactory extends InferenceAnnotatedTypeFactory {
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
        postInit();
    }

    @Override
    public TypeAnnotator createTypeAnnotator() {
        return new ListTypeAnnotator(new ComplianceInferenceImplicitsTypeAnnotator(this));
    }

    @Override
    public TreeAnnotator createTreeAnnotator() {
        return new ListTreeAnnotator(new ComplianceInferenceImplicitsTreeAnnotator(this),
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
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, AES256.class);
                        AnnotationMirror numberAnno = builder.build();
                        type.replaceAnnotation(numberAnno);
                    } else if (data_string.equals("AES_128")) {
                        AnnotationBuilder builder = new AnnotationBuilder(processingEnv, AES128.class);
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
}
