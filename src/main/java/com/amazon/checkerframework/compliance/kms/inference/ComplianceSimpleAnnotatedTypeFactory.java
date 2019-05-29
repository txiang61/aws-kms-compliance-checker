package com.amazon.checkerframework.compliance.kms.inference;

import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;

public class ComplianceSimpleAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {
    public ComplianceSimpleAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        postInit();
    }
}
