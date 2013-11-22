package mudrutom.linprog;

import ilog.concert.IloException;
import ilog.concert.IloModeler;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;
import mudrutom.game.BanditPositions;
import mudrutom.game.GameNode;
import mudrutom.utils.TreeNode;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a Linear Program (LP) used for
 * solving the game, i.e. finding the Nash equilibrium.
 */
public class LinearProgram {

	/** Default bounds to be used for variable initialization. */
	public static final double LB = 0.0, UB = 1.0;

	/** The CPLEX model of this LP. */
	private final IloCplex model;
	/** Map with variables used in LP. */
	private final Map<String, IloNumVar> variables;

	/** Lower bound to be used. */
	private double lb = LB;
	/** Upper bound to be used. */
	private double ub = UB;

	/** The objective of this LP. */
	private IloNumExpr objective;
	/** The objective of this LP. */
	private double objectiveValue;

	/** Constructor of the LinearProgram class. */
	public LinearProgram() throws IloException {
		model = new IloCplex();
		variables = new LinkedHashMap<String, IloNumVar>();
		objective = null;
		objectiveValue = Double.NaN;
	}

	/** @return the model of this LP */
	public IloModeler getModel() {
		return model;
	}

	/** @return the objective value */
	public double getObjectiveValue() {
		return objectiveValue;
	}

	/** Sets bounds to be used for creating new variables. */
	public void setBounds(double lower, double upper) {
		lb = lower;
		ub = upper;
	}

	/** The LP will minimize given objective. */
	public void minimize(IloNumExpr objective) throws IloException {
		this.objective = objective;
		model.addMinimize(objective);
	}

	/** The LP will maximize given objective. */
	public void maximize(IloNumExpr objective) throws IloException {
		this.objective = objective;
		model.addMaximize(objective);
	}

	/** Tries to solve this LP instance. */
	public boolean solve() throws IloException {
		final boolean feasible = model.solve();
		if (feasible) {
			objectiveValue = model.getValue(objective);
		}
		return feasible;
	}

	/** Exports this LP into a file. */
	public void export(String filename) throws IloException {
		model.exportModel(filename + ".lp");
	}

	/** @return variable for given tree node */
	public IloNumVar getVar(TreeNode<GameNode> treeNode) throws IloException {
		return getVar(treeNode.getNode());
	}

	/** @return variable for given game node */
	public IloNumVar getVar(GameNode gameNode) throws IloException {
		return getVar("r_" + gameNode.getSequenceString());
	}

	/** @return variable for given bandit positions */
	public IloNumVar getVar(BanditPositions positions) throws IloException {
		return getVar("r_" + positions.toString());
	}

	/** @return variables for given list of tree nodes */
	public IloNumVar[] getVarsForTreeNodes(List<TreeNode<GameNode>> treeNodes) throws IloException {
		final IloNumVar[] vars = new IloNumVar[treeNodes.size()];
		final Iterator<TreeNode<GameNode>> iterator = treeNodes.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			vars[i] = getVar(iterator.next());
		}
		return vars;
	}

	/** @return variables for given list of game nodes */
	public IloNumVar[] getVarsForGameNodes(List<GameNode> gameNodes) throws IloException {
		final IloNumVar[] vars = new IloNumVar[gameNodes.size()];
		final Iterator<GameNode> iterator = gameNodes.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			vars[i] = getVar(iterator.next());
		}
		return vars;
	}

	/** @return variables for given list of bandit positions */
	public IloNumVar[] getVarsForPositions(List<BanditPositions> positionsList) throws IloException {
		final IloNumVar[] vars = new IloNumVar[positionsList.size()];
		final Iterator<BanditPositions> iterator = positionsList.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			vars[i] = getVar(iterator.next());
		}
		return vars;
	}

	/** @return new or existing variable for given name */
	public IloNumVar getVar(String name) throws IloException {
		if (variables.containsKey(name)) {
			return variables.get(name);
		} else {
			final IloNumVar var = model.numVar(lb, ub, IloNumVarType.Float, name);
			variables.put(name, var);
			return var;
		}
	}

	/** @return value of a variable for given game node */
	public double getValue(GameNode gameNode) throws IloException {
		return getValue("r_" + gameNode.getSequenceString());
	}

	/** @return value of a variable for given bandit positions */
	public double getValue(BanditPositions banditPositions) throws IloException {
		return getValue("r_" + banditPositions.toString());
	}

	/** @return value of a variable for given name */
	public double getValue(String name) throws IloException {
		if (variables.containsKey(name)) {
			return model.getValue(variables.get(name));
		} else {
			return Double.NaN;
		}
	}

	/** Closes this LP and frees all of its resources. */
	public void close() throws IloException {
		model.end();
	}
}
