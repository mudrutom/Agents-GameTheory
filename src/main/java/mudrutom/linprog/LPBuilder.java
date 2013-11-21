package mudrutom.linprog;

import ilog.concert.IloException;
import ilog.concert.IloModeler;
import ilog.concert.IloNumVar;
import mudrutom.game.BanditPositions;
import mudrutom.game.GameNode;
import mudrutom.utils.Table;
import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;
import mudrutom.utils.Visitor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class used for constructing Linear Programs.
 */
public class LPBuilder {

	private LPBuilder() {}

	/** Constructs and returns LP for given input. */
	public static LinearProgram buildAgentLP(Tree<GameNode> gameTree, Table<GameNode, BanditPositions, Double> utilityTable) throws IloException {
		final LinearProgram lp = new LinearProgram();
		final IloModeler model = lp.getModel();

		final List<GameNode> leafNodes = new LinkedList<GameNode>();

		lp.setBounds(0.0, 1.0);

		// realization plan constraints
		final Visitor<TreeNode<GameNode>> treeVisitor = new Visitor<TreeNode<GameNode>>() {
			@Override
			public void visit(TreeNode<GameNode> treeNode) {
				if (treeNode.isLeaf()) {
					leafNodes.add(treeNode.getNode());
					return;
				}
				try {
					IloNumVar var = lp.getVar(treeNode);
					IloNumVar[] childVars = lp.getVarsForTreeNodes(treeNode.getChildren());
					model.addEq(var, model.sum(childVars), "c_" + treeNode.getNode().getSequenceString());

					if (treeNode.isRoot()) {
						model.addEq(var, 1.0, "c_root");
					}
				} catch (IloException e) {
					System.err.println("IloException: " + e.getMessage());
					System.exit(10);
				}
			}
		};
		gameTree.applyVisitor(treeVisitor);

		lp.setBounds(Double.MIN_VALUE, Double.MAX_VALUE);

		// optimized variable, the root information set
		final IloNumVar root = lp.getVar("v0");
		lp.maximize(root);

		// optimizing against the opponent best response
		final IloNumVar[] leafVars = lp.getVarsForGameNodes(leafNodes);
		for (BanditPositions banditPositions : utilityTable.getColumnIndices()) {
			if (!banditPositions.isEmpty()) {
				double[] utilities = new double[leafNodes.size()];
				Iterator<GameNode> leafIterator = leafNodes.iterator();
				for (int i = 0; leafIterator.hasNext(); i++) {
					utilities[i] = utilityTable.get(leafIterator.next(), banditPositions);
				}
				model.addLe(root, model.scalProd(utilities, leafVars), "c_" + banditPositions.toString());
			}
		}

		return lp;
	}
}
