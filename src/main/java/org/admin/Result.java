package org.admin;

import com.google.ortools.linearsolver.*;
import com.google.ortools.modelbuilder.LinearConstraint;
import org.data.*;
import java.util.*;
import com.google.ortools.*;

public class Result {
    static { System.loadLibrary("jniortools"); }

    public static List<Modul> greedyPick(List<Modul> input) {
        Modul seminar = null;
        Modul practical = null;

        for(int i = 0; i < input.size(); i++) {
            if(input.get(i).type == Type.SEMINAR) {
                seminar = input.get(i);
                input.remove(i);
                break;
            }
        }

        for(int i = 0; i < input.size(); i++) {
            if(input.get(i).type == Type.PRAKTIKUM) {
                practical = input.get(i);
                input.remove(i);
                break;
            }
        }

        List<Modul> recommendation = new ArrayList<>();

        int cp = 0;
        recommendation.add(seminar);
        cp += seminar.cp;
        recommendation.add(practical);
        cp += practical.cp;

        int seminarCount = 1;
        int practCount = 1;

        while(cp < 39) {
            boolean valid = true;
            Modul module = input.get(0);
            if(module.type == Type.SEMINAR) {
                if(seminarCount >= 3)
                    valid = false;
                seminarCount++;
            }
            else if(module.type == Type.PRAKTIKUM) {
                if(practCount >= 2)
                    valid = false;
                practCount++;
            }

            if((module.abbreviation.equals("B-ATIG") && recommendation.contains(Modul.find("B-ATIK"))) || (module.abbreviation.equals("B-ATIK") && recommendation.contains(Modul.find("B-ATIG"))) || (module.abbreviation.equals("FPGR-FP") && recommendation.contains(Modul.find("FPKL-FP"))) || (module.abbreviation.equals("FPKL-FP") && recommendation.contains(Modul.find("FPGR-FP"))))
                valid = false;

            if(valid) {
                recommendation.add(module);
                cp += module.cp;
            }

            input.remove(0);
        }

        return recommendation;
    }

    public static void linearSum(List<Modul> input) {
        double[] cpCounts = new double[input.size()];
        for(int i = 0; i < input.size(); i++)
            cpCounts[i] = input.get(i).cp;

        double[] inputRatings = new double[input.size()];
        for(int i = 0; i < input.size(); i++)
            inputRatings[i] = input.get(i).rating;

        MPSolver solver = new MPSolver("ILP_Solver", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

        int variableCount = input.size();
        MPVariable[] variables = new MPVariable[variableCount];
        for(int i = 0; i < variableCount; i++)
            variables[i] = solver.makeIntVar(0.0, 1.0, "x" + (i + 1));

        double[][] coefficients = { cpCounts };
        double[] bounds = {39};

        for(int i = 0; i < coefficients.length; i++) {
            MPConstraint constraint = solver.makeConstraint(0, bounds[i], "Constraint" + (i + 1));
            for(int j = 0; j < variableCount; j++)
                constraint.setCoefficient(variables[j], coefficients[i][j]);
        }

        MPObjective target = solver.objective();
        for(int i = 0; i < variableCount; i++) {
            target.setCoefficient(variables[i], inputRatings[i]);
        }
        target.setMaximization();

        MPSolver.ResultStatus resultStatus = solver.solve();
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Optimal solution found:");
            for (int i = 0; i < variableCount; i++)
                System.out.printf("x%d = %f%n", i + 1, variables[i].solutionValue());
            System.out.printf("Optimal objective value: %f%n", target.value());
        } else
            System.out.println("No optimal solution found.");
    }
}
