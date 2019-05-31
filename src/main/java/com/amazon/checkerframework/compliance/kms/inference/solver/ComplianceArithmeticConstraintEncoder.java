package com.amazon.checkerframework.compliance.kms.inference.solver;

import checkers.inference.model.ArithmeticConstraint;
import checkers.inference.model.ArithmeticVariableSlot;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.VariableSlot;
import checkers.inference.solver.backend.encoder.ArithmeticConstraintEncoder;
import checkers.inference.solver.backend.maxsat.MathUtils;
import checkers.inference.solver.backend.maxsat.VectorUtils;
import checkers.inference.solver.backend.maxsat.encoder.MaxSATAbstractConstraintEncoder;
import checkers.inference.solver.frontend.Lattice;
import org.checkerframework.javacutil.AnnotationUtils;
import org.sat4j.core.VecInt;

import javax.lang.model.element.AnnotationMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplianceArithmeticConstraintEncoder extends MaxSATAbstractConstraintEncoder implements ArithmeticConstraintEncoder<VecInt[]> {

    public ComplianceArithmeticConstraintEncoder(Lattice lattice, Map<AnnotationMirror, Integer> typeToInt) {
        super(lattice, typeToInt);
    }

    @Override
    public VecInt[] encodeVariable_Variable(ArithmeticConstraint.ArithmeticOperationKind arithmeticOperationKind, VariableSlot variableSlot, VariableSlot variableSlot1, ArithmeticVariableSlot arithmeticVariableSlot) {
        System.out.println("HEEEEEELLLLLLLOOOOOOO11111");

        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeVariable_Constant(ArithmeticConstraint.ArithmeticOperationKind arithmeticOperationKind, VariableSlot variableSlot, ConstantSlot constantSlot, ArithmeticVariableSlot arithmeticVariableSlot) {
        System.out.println("HEEEEEELLLLLLLOOOOOOO22222");

        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeConstant_Variable(ArithmeticConstraint.ArithmeticOperationKind arithmeticOperationKind, ConstantSlot constantSlot, VariableSlot variableSlot, ArithmeticVariableSlot arithmeticVariableSlot) {
        System.out.println("HEEEEEELLLLLLLOOOOOOO33333");

        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeConstant_Constant(ArithmeticConstraint.ArithmeticOperationKind arithmeticOperationKind, ConstantSlot constantSlot, ConstantSlot constantSlot1, ArithmeticVariableSlot arithmeticVariableSlot) {
        System.out.println("HEEEEEELLLLLLLOOOOOOO44444");

        return new VecInt[0];
    }
}
