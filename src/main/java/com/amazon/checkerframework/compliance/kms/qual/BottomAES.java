package com.amazon.checkerframework.compliance.kms.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.SubtypeOf;

@Documented
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf({IntVal128.class, IntVal256.class, AES.class, AES128.class, AES256.class})
public @interface BottomAES {
}