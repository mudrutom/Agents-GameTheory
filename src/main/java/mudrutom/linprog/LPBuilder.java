package mudrutom.linprog;

import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.cplex.IloCplex;
import mudrutom.game.BanditsConfig;
import mudrutom.game.Cell;
import mudrutom.game.GameNode;
import mudrutom.game.GameTreeHelper;
import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;
import mudrutom.utils.Visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LPBuilder {

	public static void solveLPForAgent(Tree<GameNode> gameTree, BanditsConfig banditsConfig) {
		try {
			final IloCplex cplex = new IloCplex();
			final Map<String, IloNumVar> rVars = new HashMap<String, IloNumVar>();

			// realization plan constraints
			final Visitor<TreeNode<GameNode>, Void> visitor = new Visitor<TreeNode<GameNode>, Void>() {
				@Override
				public Void visit(TreeNode<GameNode> treeNode, Void v) {
					try {
						if (treeNode.isLeaf()) {
							return null;
						}
						final IloNumVar rVar = rVar(treeNode, rVars, cplex);
						if (treeNode.isRoot()) {
							cplex.addEq(rVar, 1.0);
						}
						final IloNumVar[] childVars = new IloNumVar[treeNode.getChildren().size()];
						int i = 0;
						for (TreeNode<GameNode> child : treeNode.getChildren()) {
							childVars[i++] = rVar(child, rVars, cplex);
						}
						cplex.addEq(rVar, cplex.sum(childVars));
					} catch (IloException e) {
						throw new RuntimeException(e);
					}
					return null;
				}
			};
			gameTree.applyVisitor(visitor, null);

			// optimized variable
			final IloNumVar vVar = cplex.numVar(Double.MIN_VALUE, Double.MAX_VALUE, IloNumVarType.Float, "v");
			cplex.addMaximize(vVar);

			final List<TreeNode<GameNode>> leafNodes = GameTreeHelper.findLeafNodes(gameTree);
			final List<List<Cell>> possiblePositions = banditsConfig.getPossiblePositions();

			// opponent best response
			for (List<Cell> banditPositions : possiblePositions) {
				IloNumVar[] leafVars = new IloNumVar[leafNodes.size()];
				double[] utilities = new double[leafNodes.size()];
				int i = 0;
				for (TreeNode<GameNode> leaf : leafNodes) {
					List<Cell> dangersOnPath = GameTreeHelper.findDangersOnPath(leaf);
					double utility = GameTreeHelper.getUtilityValue(leaf);
					utilities[i] = banditsConfig.computeExpectedUtility(utility, dangersOnPath, banditPositions);
					leafVars[i++] = rVar(leaf, rVars, cplex);
				}
				cplex.addLe(vVar, cplex.scalProd(utilities, leafVars));
			}

			cplex.exportModel("lp.lp");
			cplex.solve();

			System.out.println("\nvalue of the game = " + cplex.getValue(vVar));

			cplex.end();
		} catch (IloException e) {
			throw new RuntimeException(e);
		}
	}

	public static IloNumVar rVar(TreeNode<GameNode> treeNode, Map<String, IloNumVar> rVars, IloCplex cplex) throws IloException {
		final String key = treeNode.getNode().getSequenceString();
		if (rVars.containsKey(key)) {
			return rVars.get(key);
		} else {
			final IloNumVar var = cplex.numVar(0.0, 1.0, IloNumVarType.Float, "r" + key);
			rVars.put(key, var);
			return var;
		}
	}
}
