package com.amazon.checkerframework.compliance.kms.inference.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;

@Documented
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf({IntVal128.class, IntVal256.class, Underline.class, AES.class, AES_128.class, AES_256.class, AES_.class})
public @interface BottomAES {
}