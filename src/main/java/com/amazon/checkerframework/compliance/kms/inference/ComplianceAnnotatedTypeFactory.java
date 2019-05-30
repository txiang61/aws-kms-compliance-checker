package com.amazon.checkerframework.compliance.kms.inference;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.LiteralTree;
import org.checkerframework.framework.qual.LiteralKind;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.util.TreePath;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.dataflow.analysis.FlowExpressions;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFTransfer;
import org.checkerframework.framework.flow.CFValue;
import org.checkerframework.framework.qual.PolyAll;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.AnnotatedTypeMirror.AnnotatedArrayType;
import org.checkerframework.framework.type.DefaultTypeHierarchy;
import org.checkerframework.framework.type.QualifierHierarchy;
import org.checkerframework.framework.type.StructuralEqualityComparer;
import org.checkerframework.framework.type.TypeHierarchy;
import org.checkerframework.framework.type.treeannotator.ImplicitsTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.ListTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.PropagationTreeAnnotator;
import org.checkerframework.framework.type.treeannotator.TreeAnnotator;
import org.checkerframework.framework.type.typeannotator.ImplicitsTypeAnnotator;
import org.checkerframework.framework.type.typeannotator.ListTypeAnnotator;
import org.checkerframework.framework.type.typeannotator.TypeAnnotator;
import org.checkerframework.framework.util.FieldInvariants;
import org.checkerframework.framework.util.FlowExpressionParseUtil.FlowExpressionParseException;
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy;
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy.MultiGraphFactory;
import org.checkerframework.javacutil.AnnotationBuilder;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.ElementUtils;
import org.checkerframework.javacutil.TreeUtils;
import org.checkerframework.javacutil.TypesUtils;
import com.amazon.checkerframework.compliance.kms.inference.qual.*;

public class ComplianceAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {

    protected final AnnotationMirror UNKNOWNAES;

    protected final AnnotationMirror BOTTOMAES;

    public ComplianceAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);

        BOTTOMAES = AnnotationBuilder.fromClass(elements, BottomAES.class);
        UNKNOWNAES = AnnotationBuilder.fromClass(elements, UnknownVal.class);

        postInit();
    }

    @Override
    public TypeAnnotator createTypeAnnotator() {
        return new ListTypeAnnotator(new ComplianceImplicitsTypeAnnotator(this));
    }


    @Override
    protected TreeAnnotator createTreeAnnotator() {
        return new ListTreeAnnotator(
                new ComplianceTreeAnnotator(this), new ComplianceImplicitsTreeAnnotator(this));
    }

    /** The TreeAnnotator for this AnnotatedTypeFactory. It adds/replaces annotations. */
    protected class ComplianceTreeAnnotator extends TreeAnnotator {
        public ComplianceTreeAnnotator(ComplianceAnnotatedTypeFactory factory) {
            super(factory);
        }

        @Override
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

    public class ComplianceImplicitsTreeAnnotator extends ImplicitsTreeAnnotator {

        public ComplianceImplicitsTreeAnnotator(AnnotatedTypeFactory atypeFactory) {
            super(atypeFactory);
            addLiteralKind(LiteralKind.NULL, BOTTOMAES);
            addLiteralKind(LiteralKind.STRING, AnnotationBuilder.fromClass(elements, StringVal.class));
            addLiteralKind(LiteralKind.CHAR, AnnotationBuilder.fromClass(elements, IntVal.class));
            addLiteralKind(LiteralKind.INT, AnnotationBuilder.fromClass(elements, IntVal.class));
            addLiteralKind(LiteralKind.LONG, AnnotationBuilder.fromClass(elements, IntVal.class));
            addLiteralKind(LiteralKind.BOOLEAN, UNKNOWNAES);
        }
    }

    public class ComplianceImplicitsTypeAnnotator extends ImplicitsTypeAnnotator {

        public ComplianceImplicitsTypeAnnotator(AnnotatedTypeFactory atypeFactory) {
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
