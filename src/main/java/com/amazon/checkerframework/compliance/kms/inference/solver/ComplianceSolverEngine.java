package com.amazon.checkerframework.compliance.kms.inference.solver;

import checkers.inference.InferenceResult;
import checkers.inference.model.Constraint;
import checkers.inference.model.Slot;
import checkers.inference.solver.SolverEngine;
import checkers.inference.solver.backend.SolverFactory;
import checkers.inference.solver.backend.maxsat.MaxSatFormatTranslator;
import checkers.inference.solver.backend.maxsat.MaxSatSolverFactory;
import checkers.inference.solver.frontend.Lattice;
import org.checkerframework.framework.type.QualifierHierarchy;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ComplianceSolverEngine extends SolverEngine {
    @Override
    protected SolverFactory createSolverFactory() {
        return new MaxSatSolverFactory(){
            @Override
            public MaxSatFormatTranslator createFormatTranslator(Lattice lattice) {
                return new ComplianceFormatTranslator(lattice);
            }
        };
    }
}
