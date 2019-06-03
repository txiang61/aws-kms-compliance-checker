package com.amazon.checkerframework.compliance.kms.inference;

import javax.lang.model.element.AnnotationMirror;

import com.amazon.checkerframework.compliance.kms.inference.qual.*;
import org.checkerframework.framework.source.SourceChecker;
import org.checkerframework.javacutil.AnnotationBuilder;
import javax.lang.model.util.Elements;

/**
 * A holder class that holds AnnotationMirrors
 */
public class ComplianceAnnotationMirrorHolder {

    public static AnnotationMirror UNKNOWNVAL;
    public static AnnotationMirror STRINGVAL;
    public static AnnotationMirror INTVAL;
    public static AnnotationMirror INTVAL128;
    public static AnnotationMirror INTVAL256;
    public static AnnotationMirror AES_128;
    public static AnnotationMirror AES_256;
    public static AnnotationMirror AES;
    public static AnnotationMirror AES_;
    public static AnnotationMirror UNDERLINE;
    public static AnnotationMirror BOTTOMVAL;

    public static void init(SourceChecker checker) {
        Elements elements = checker.getElementUtils();

        UNKNOWNVAL = AnnotationBuilder.fromClass(elements, UnknownVal.class);
        STRINGVAL = AnnotationBuilder.fromClass(elements, StringVal.class);
        INTVAL = AnnotationBuilder.fromClass(elements, IntVal.class);
        INTVAL256 = AnnotationBuilder.fromClass(elements, IntVal256.class);
        INTVAL128 = AnnotationBuilder.fromClass(elements, IntVal128.class);
        AES_128 = AnnotationBuilder.fromClass(elements, AES_128.class);
        AES_256 = AnnotationBuilder.fromClass(elements, AES_256.class);
        AES = AnnotationBuilder.fromClass(elements, AES.class);
        AES_ = AnnotationBuilder.fromClass(elements, AES_.class);
        UNDERLINE = AnnotationBuilder.fromClass(elements, Underline.class);
        BOTTOMVAL = AnnotationBuilder.fromClass(elements, BottomAES.class);
    }
}
