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

	/** Constructs and returns LP for the agent player. */
	public static LinearProgram buildAgentLP(final Tree<GameNode> gameTree, final Table<GameNode, BanditPositions, Double> utilityTable) throws IloException {
		final LinearProgram lp = new LinearProgram();
		final IloModeler model = lp.getModel();

		final List<GameNode> leafNodes = new LinkedList<GameNode>();

		lp.setBounds(0.0, 1.0);

		// realization plan constraints
		final Visitor<TreeNode<GameNode>, IloException> treeVisitor = new Visitor<TreeNode<GameNode>, IloException>() {
			@Override
			public void visit(TreeNode<GameNode> treeNode) throws IloException {
				if (treeNode.isLeaf()) {
					leafNodes.add(treeNode.getNode());
					return;
				}

				final IloNumVar var = lp.getVar(treeNode);
				if (treeNode.isRoot()) {
					model.addEq(var, 1.0, "c_root");
				}

				final IloNumVar[] childVars = lp.getVarsForTreeNodes(treeNode.getChildren());
				model.addEq(var, model.sum(childVars), "c_" + treeNode.getNode().getSequenceString());
			}
		};
		gameTree.applyVisitor(treeVisitor);

		lp.setBounds(Double.MIN_VALUE, Double.MAX_VALUE);

		// optimized variable, the root information set
		final IloNumVar root = lp.getVar("v_{}");
		lp.maximize(root);

		// optimizing against the best response of the opponent
		final IloNumVar[] leafVars = lp.getVarsForGameNodes(leafNodes);
		for (BanditPositions banditPositions : utilityTable.getColumnIndices()) {
			if (!banditPositions.isEmpty()) {
				double[] utilities = getUtilityVector(leafNodes, banditPositions, utilityTable);
				model.addLe(root, model.scalProd(utilities, leafVars), "c_" + banditPositions.toString());
			}
		}

		return lp;
	}

	/** Constructs and returns LP for the bandits. */
	public static LinearProgram buildBanditsLP(final Tree<GameNode> gameTree, final Table<GameNode, BanditPositions, Double> utilityTable) throws IloException {
		final LinearProgram lp = new LinearProgram();
		final IloModeler model = lp.getModel();

		final List<BanditPositions> possibleBanditPositions = utilityTable.getColumnIndices();

		lp.setBounds(0.0, 1.0);

		// realization plan constraints
		final IloNumVar[] childVars = new IloNumVar[possibleBanditPositions.size() - 1];
		IloNumVar emptyVar = null;
		int i = 0;
		for (BanditPositions banditPositions : possibleBanditPositions) {
			if (banditPositions.isEmpty()) {
				emptyVar = lp.getVar(banditPositions);
				model.addEq(emptyVar, 1.0, "c_root");
			} else {
				childVars[i++] = lp.getVar(banditPositions);
			}
		}
		model.addEq(emptyVar, model.sum(childVars), "c_choice");

		lp.setBounds(Double.MIN_VALUE, Double.MAX_VALUE);

		// optimized variable, the root information set
		final IloNumVar root = lp.getVar("v_{}");
		lp.minimize(root);

		// optimizing against the best response of the opponent
		final IloNumVar[] positionsVars = lp.getVarsForPositions(possibleBanditPositions);
		final Visitor<TreeNode<GameNode>, IloException> treeVisitor = new Visitor<TreeNode<GameNode>, IloException>() {
			@Override
			public void visit(TreeNode<GameNode> treeNode) throws IloException {
				final String name = treeNode.getNode().getSequenceString();
				if (treeNode.isLeaf()) {
					final String parentName = treeNode.getParent().getNode().getSequenceString();
					final IloNumVar parentVar = lp.getVar("v_" + parentName);
					final double[] utilities = getUtilityVector(possibleBanditPositions, treeNode.getNode(), utilityTable);
					model.addGe(parentVar, model.scalProd(utilities, positionsVars), "c_" + name);
				} else {
					final IloNumVar var = lp.getVar("v_" + name);
					for (TreeNode<GameNode> child : treeNode.getChildren()) {
						if (!child.isLeaf()) {
							String childName = child.getNode().getSequenceString();
							IloNumVar childVar = lp.getVar("v_" + childName);
							model.addGe(var, childVar, "c_" + childName);
						}
					}
				}
			}
		};
		gameTree.applyVisitor(treeVisitor);

		return lp;
	}

	/** @return utility vector for given game nodes and bandit positions */
	private static double[] getUtilityVector(List<GameNode> gameNodes, BanditPositions banditPositions,
											 Table<GameNode, BanditPositions, Double> utilityTable) {
		final double[] utilities = new double[gameNodes.size()];
		final Iterator<GameNode> nodeIterator = gameNodes.iterator();
		for (int i = 0; nodeIterator.hasNext(); i++) {
			utilities[i] = utilityTable.get(nodeIterator.next(), banditPositions);
		}
		return utilities;
	}

	/** @return utility vector for given possible bandit positions and game node */
	private static double[] getUtilityVector(List<BanditPositions> possibleBanditPositions, GameNode gameNode,
											 Table<GameNode, BanditPositions, Double> utilityTable) {
		final double[] utilities = new double[possibleBanditPositions.size()];
		final Iterator<BanditPositions> positionsIterator = possibleBanditPositions.iterator();
		for (int i = 0; positionsIterator.hasNext(); i++) {
			utilities[i] = utilityTable.get(gameNode, positionsIterator.next());
		}
		return utilities;
	}
}
