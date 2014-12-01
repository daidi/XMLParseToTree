package org.abego.treelayout.util;

import static org.abego.treelayout.internal.util.Contract.checkArg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abego.treelayout.TreeForTreeLayout;

/**
 * Provides a generic implementation for the {@link TreeForTreeLayout}
 * interface, applicable to any type of tree node.
 * <p>
 * It allows you to create a tree "from scratch", without creating any new
 * class.
 * <p>
 * To create a tree you must provide the root of the tree (see
 * {@link #DefaultTreeForTreeLayout(Object)}. Then you can incrementally
 * construct the tree by adding children to the root or other nodes of the tree
 * (see {@link #addChild(Object, Object)} and
 * {@link #addChildren(Object, Object...)}).
 * 
 * @author Udo Borkowski (ub@abego.org)
 * 
 * @param <TreeNode>
 */
public class DefaultTreeForTreeLayout<TreeNode> extends
                AbstractTreeForTreeLayout<TreeNode> {

        private List<TreeNode> emptyList;

        private List<TreeNode> getEmptyList() {
                if (emptyList == null) {
                        emptyList = new ArrayList<TreeNode>();
                }
                return emptyList;
        }

        private Map<TreeNode, List<TreeNode>> childrenMap = new HashMap<TreeNode, List<TreeNode>>();
        private Map<TreeNode, TreeNode> parents = new HashMap<TreeNode, TreeNode>();

        /**
         * Creates a new instance with a given node as the root
         * 
         * @param root
         *            the node to be used as the root.
         */
        public DefaultTreeForTreeLayout(TreeNode root) {
                super(root);
        }

        @Override
        public TreeNode getParent(TreeNode node) {
                return parents.get(node);
        }

        @Override
        public List<TreeNode> getChildrenList(TreeNode node) {
                List<TreeNode> result = childrenMap.get(node);
                return result == null ? getEmptyList() : result;
        }

        /**
         * 
         * @param node
         * @return true iff the node is in the tree
         */
        public boolean hasNode(TreeNode node) {
                return node == getRoot() || parents.containsKey(node);
        }

        /**
         * @param parentNode
         *            [hasNode(parentNode)]
         * @param node
         *            [!hasNode(node)]
         */
        public void addChild(TreeNode parentNode, TreeNode node) {
                checkArg(hasNode(parentNode), "parentNode is not in the tree");
                checkArg(!hasNode(node), "node is already in the tree");

                List<TreeNode> list = childrenMap.get(parentNode);
                if (list == null) {
                        list = new ArrayList<TreeNode>();
                        childrenMap.put(parentNode, list);
                }
                list.add(node);
                parents.put(node, parentNode);
        }

        public void addChildren(TreeNode parentNode, TreeNode... nodes) {
                for (TreeNode node : nodes) {
                        addChild(parentNode, node);
                }
        }

}
