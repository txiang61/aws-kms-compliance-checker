package com.amazon.checkerframework.compliance.kms.inference.solver;

import checkers.inference.solver.backend.encoder.ArithmeticConstraintEncoder;
import checkers.inference.solver.backend.encoder.ConstraintEncoderFactory;
import checkers.inference.solver.backend.maxsat.MaxSatFormatTranslator;
import checkers.inference.solver.backend.maxsat.encoder.MaxSATConstraintEncoderFactory;
import checkers.inference.solver.frontend.Lattice;
import org.sat4j.core.VecInt;

/**
 * {@link checkers.inference.solver.backend.FormatTranslator} that encodes constraints to format that
 * underlying solver can understand. Difference from super class is this class also encodes viewpoint
 * adaptation logic by delegating to {@link ComplianceArithmeticConstraintEncoder}
 */
public class ComplianceFormatTranslator extends MaxSatFormatTranslator{

    public ComplianceFormatTranslator(Lattice lattice) {
        super(lattice);

    }

    @Override
    protected ConstraintEncoderFactory<VecInt[]> createConstraintEncoderFactory() {
        return new MaxSATConstraintEncoderFactory(lattice, typeToInt, this){
            @Override
            public ArithmeticConstraintEncoder<VecInt[]> createArithmeticConstraintEncoder() {
                return new ComplianceArithmeticConstraintEncoder(lattice, typeToInt);
            }
        };
    }
}