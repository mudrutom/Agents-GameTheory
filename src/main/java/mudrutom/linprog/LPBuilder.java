package mudrutom.linprog;

import ilog.concert.IloException;
import ilog.concert.IloModeler;
import ilog.concert.IloNumVar;
import mudrutom.game.BanditsConfig;
import mudrutom.game.Cell;
import mudrutom.game.GameNode;
import mudrutom.game.GameTreeHelper;
import mudrutom.utils.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility class used for constructing Linear Programs.
 */
public class LPBuilder {

	private LPBuilder() {}

	/** Constructs and returns LP for given input. */
	public static LinearProgram buildPForAgent(List<TreeNode<GameNode>> nodes, BanditsConfig banditsConfig) throws IloException {
		final LinearProgram lp = new LinearProgram();
		final IloModeler model = lp.getModel();

		lp.setBounds(0.0, 1.0);

		// realization plan constraints
		for (TreeNode<GameNode> treeNode : nodes) {
			if (treeNode.isLeaf()) continue;

			IloNumVar var = lp.getVar(treeNode);
			IloNumVar[] childVars = lp.getVars(treeNode.getChildren());
			model.addEq(var, model.sum(childVars));

			if (treeNode.isRoot()) {
				model.addEq(var, 1.0);
			}
		}

		lp.setBounds(Double.MIN_VALUE, Double.MAX_VALUE);

		// optimized variable, the root information set
		final IloNumVar root = lp.getVar("v0");
		lp.maximize(root);

		final List<TreeNode<GameNode>> leafNodes = new LinkedList<TreeNode<GameNode>>();
		for (TreeNode<GameNode> treeNode : nodes) {
			if (treeNode.isLeaf()) leafNodes.add(treeNode);
		}

		// optimizing against the opponent best response
		final List<List<Cell>> possiblePositions = banditsConfig.getPossiblePositions();
		IloNumVar[] leafVars = lp.getVars(leafNodes);
		for (List<Cell> banditPositions : possiblePositions) {
			double[] utilities = new double[leafNodes.size()];
			int i = 0;
			for (TreeNode<GameNode> leaf : leafNodes) {
				List<Cell> dangersOnPath = GameTreeHelper.findDangersOnPath(leaf);
				utilities[i++] = banditsConfig.computeExpectedUtility(leaf.getNode().getUtility(), dangersOnPath, banditPositions);
			}
			model.addLe(root, model.scalProd(utilities, leafVars));
		}

		return lp;
	}
}
