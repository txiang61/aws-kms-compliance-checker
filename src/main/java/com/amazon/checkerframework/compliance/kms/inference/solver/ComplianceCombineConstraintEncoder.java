package com.amazon.checkerframework.compliance.kms.inference.solver;

import checkers.inference.model.ConstantSlot;
import checkers.inference.model.VariableSlot;
import checkers.inference.solver.backend.encoder.combine.CombineConstraintEncoder;
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

/**
 * Contains viewpoint adaptation encoding logic for PICOInfer. Specifies how qualifiers are
 * combined and what result the combination yields.
 */
public class ComplianceCombineConstraintEncoder extends MaxSATAbstractConstraintEncoder implements CombineConstraintEncoder<VecInt[]> {

    public ComplianceCombineConstraintEncoder(Lattice lattice, Map<AnnotationMirror, Integer> typeToInt) {
        super(lattice, typeToInt);
    }

    @Override
    public VecInt[] encodeVariable_Variable(VariableSlot variableSlot, VariableSlot variableSlot1, VariableSlot variableSlot2) {
        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeVariable_Constant(VariableSlot variableSlot, ConstantSlot constantSlot, VariableSlot variableSlot1) {
        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeConstant_Variable(ConstantSlot constantSlot, VariableSlot variableSlot, VariableSlot variableSlot1) {
        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeConstant_Constant(ConstantSlot constantSlot, ConstantSlot constantSlot1, VariableSlot variableSlot) {
        return new VecInt[0];
    }
}
