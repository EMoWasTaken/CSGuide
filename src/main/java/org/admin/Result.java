package org.admin;

import org.data.*;
import java.util.*;
import com.gurobi.gurobi.*;

public class Result {
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

    public static List<Modul> linearSum(List<Modul> input) {
        try {
            double[] inputRatings = new double[input.size()];
            for(int i = 0; i < input.size(); i++)
                inputRatings[i] = input.get(i).rating;

            double[] cpCounts = new double[input.size()];
            for(int i = 0; i < input.size(); i++)
                cpCounts[i] = input.get(i).cp;

            double[] practicals = new double[input.size()];
            for(int i = 0; i < input.size(); i++)
                practicals[i] = input.get(i).type == Type.PRAKTIKUM ? 1 : 0;

            double[] seminars = new double[input.size()];
            for(int i = 0; i < input.size(); i++)
                seminars[i] = input.get(i).type == Type.SEMINAR ? 1 : 0;

            GRBEnv env = new GRBEnv(true);
            env.set("logFile", "gurobi.log");
            env.start();

            GRBModel model = new GRBModel(env);

            int variableCount = input.size();
            GRBVar[] variables = new GRBVar[variableCount];
            for(int i = 0; i < variableCount; i++)
                variables[i] = model.addVar(0, 1, 0, GRB.BINARY, "x" + (i + 1));

            GRBLinExpr objective = new GRBLinExpr();
            for(int i = 0; i < variableCount; i++)
                objective.addTerm(inputRatings[i], variables[i]);
            model.setObjective(objective, GRB.MAXIMIZE);

            GRBLinExpr cpConstraint1 = new GRBLinExpr();
            for(int i = 0; i < variableCount; i++)
                cpConstraint1.addTerm(cpCounts[i], variables[i]);
            model.addConstr(cpConstraint1, GRB.LESS_EQUAL, 43, "c1");

            GRBLinExpr cpConstraint2 = new GRBLinExpr();
            for(int i = 0; i < variableCount; i++)
                cpConstraint1.addTerm(cpCounts[i], variables[i]);
            model.addConstr(cpConstraint1, GRB.GREATER_EQUAL, 39, "c1");

            GRBLinExpr practicalConstraint1 = new GRBLinExpr();
            for(int i = 0; i < variableCount; i++)
                practicalConstraint1.addTerm(practicals[i], variables[i]);
            model.addConstr(practicalConstraint1, GRB.LESS_EQUAL, 2, "c2");

            GRBLinExpr practicalConstraint2 = new GRBLinExpr();
            for(int i = 0; i < variableCount; i++)
                practicalConstraint2.addTerm(practicals[i], variables[i]);
            model.addConstr(practicalConstraint2, GRB.GREATER_EQUAL, 1, "c3");

            GRBLinExpr seminarConstraint1 = new GRBLinExpr();
            for(int i = 0; i < variableCount; i++)
                seminarConstraint1.addTerm(seminars[i], variables[i]);
            model.addConstr(seminarConstraint1, GRB.LESS_EQUAL, 3, "c4");

            GRBLinExpr seminarConstraint2 = new GRBLinExpr();
            for(int i = 0; i < variableCount; i++)
                seminarConstraint2.addTerm(seminars[i], variables[i]);
            model.addConstr(seminarConstraint2, GRB.GREATER_EQUAL, 1, "c5");

            model.optimize();

            List<Modul> recommendation = new ArrayList<>();

            if(model.get(GRB.IntAttr.Status) == GRB.OPTIMAL) {
                for(int i = 0; i < variableCount; i++)
                    if(variables[i].get(GRB.DoubleAttr.X) == 1.0)
                        recommendation.add(input.get(i));
            }

            model.dispose();
            env.dispose();

            return recommendation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        /*double[] cpCounts = new double[input.size()];
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
            System.out.println("No optimal solution found.");*/
    }
}
