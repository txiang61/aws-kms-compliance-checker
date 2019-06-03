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
import com.amazon.checkerframework.compliance.kms.inference.ComplianceAnnotationMirrorHolder;
import org.checkerframework.javacutil.AnnotationUtils;
import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;

import javax.lang.model.element.AnnotationMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplianceArithmeticConstraintEncoder extends MaxSATAbstractConstraintEncoder implements ArithmeticConstraintEncoder<VecInt[]> {

    public ComplianceArithmeticConstraintEncoder(Lattice lattice, Map<AnnotationMirror, Integer> typeToInt) {
        super(lattice, typeToInt);
    }

    /**Wrapper method to get integer id of an AnnotationMirror to avoid Map get operations*/
    private int id(AnnotationMirror am) {
        return typeToInt.get(am);
    }

    private VecInt[] additionEncoding(VariableSlot left, VariableSlot right, ArithmeticVariableSlot result) {
        List<VecInt> resultClauses = new ArrayList<VecInt>();

        // AES + _ = AES_
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.AES), lattice),
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.UNDERLINE), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.AES), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.UNDERLINE), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_), lattice)));
        // AES_ + 128 = AES_128
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.AES_), lattice),
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL128), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_128), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.AES_), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_128), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL128), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_128), lattice)));
        // AES_ + 256 = AES_256
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.AES_), lattice),
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL256), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_256), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.AES_), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_256), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL256), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES_256), lattice)));
        // 128 + 128 = 256
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL128), lattice),
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL128), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL256), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL128), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL256), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL128), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL256), lattice)));
        // int + int = int
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL), lattice)));
        // bot + bot = bot
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.BOTTOMVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.BOTTOMVAL), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.BOTTOMVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.BOTTOMVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.BOTTOMVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.BOTTOMVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.BOTTOMVAL), lattice)));
        // string/int + string/int = string
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice),
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.STRINGVAL), lattice)));
        // unknown + any = unknown
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice)));
        resultClauses.add(VectorUtils.asVec(
                MathUtils.mapIdToMatrixEntry(left.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                MathUtils.mapIdToMatrixEntry(right.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice),
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.UNKNOWNVAL), lattice)));
        // not possible
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.AES), lattice)));
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.UNDERLINE), lattice)));
        resultClauses.add(VectorUtils.asVec(
                -MathUtils.mapIdToMatrixEntry(result.getId(), id(ComplianceAnnotationMirrorHolder.INTVAL128), lattice)));

        return resultClauses.toArray(new VecInt[resultClauses.size()]);
    }


    @Override
    public VecInt[] encodeVariable_Variable(ArithmeticConstraint.ArithmeticOperationKind opKind, VariableSlot left, VariableSlot right, ArithmeticVariableSlot result) {
        if (opKind.getSymbol().equals("+")) {
            return additionEncoding(left, right, result);
        }

        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeVariable_Constant(ArithmeticConstraint.ArithmeticOperationKind opKind, VariableSlot left, ConstantSlot right, ArithmeticVariableSlot result) {
        if (opKind.getSymbol().equals("+")) {
            return additionEncoding(left, right, result);
        }

        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeConstant_Variable(ArithmeticConstraint.ArithmeticOperationKind opKind, ConstantSlot left, VariableSlot right, ArithmeticVariableSlot result) {
        if (opKind.getSymbol().equals("+")) {
            return additionEncoding(left, right, result);
        }

        return new VecInt[0];
    }

    @Override
    public VecInt[] encodeConstant_Constant(ArithmeticConstraint.ArithmeticOperationKind opKind, ConstantSlot left, ConstantSlot right, ArithmeticVariableSlot result) {
        if (opKind.getSymbol().equals("+")) {
            return additionEncoding(left, right, result);
        }

        return new VecInt[0];
    }
}
