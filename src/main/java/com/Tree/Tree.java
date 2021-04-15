package com.Tree;
import java.util.ArrayList;
import com.Queue.SimpleQueue;

/**
 * Generic n-ary tree.
 *
 * @param <T> Any class type
 */
public class Tree<T>{
    public int number;
    public String name = "№" + number;
    private Node<T> root;
    public int[] counts = new int[6];
    public double alpha;
    
    /**
     * Initialize a tree with the specified root node.
     *
     * @param root The root node of the tree
     */
    public Tree(Node<T> root) {
        this.root = root;
    }

    /**
     * Checks if the tree is empty (root node is null)
     *
     * @return <code>true</code> if the tree is empty,
     * <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setNumber(Integer num)
    {
        this.number = num;
    }
    public String getName(){return name;}
    public int getNumber() {return number;}
    /**
     * Get the root node of the tree
     *
     * @return the root node.
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * Set the root node of the tree. Replaces existing root node.
     *
     * @param root The root node to replace the existing root node with.
     */
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    /**
     *
     * Check if given data is present in the tree.
     *
     * @param key The data to search for
     * @return <code>true</code> if the given key was found in the tree,
     * <code>false</code> otherwise.
     */
    public boolean exists(T key) {
        return find(root, key);
    }

    /**
     * Get the number of nodes (size) in the tree.
     *
     * @return The number of nodes in the tree
     */
    public int size() {
        return getNumberOfDescendants(root)+1;
    }

    /**
     *
     * Get the number of descendants a given node has.
     *
     * @param node The node whose number of descendants is needed.
     * @return the number of descendants
     */
    public int getNumberOfDescendants(Node<T> node) {
        int n = node.getChildrenList().size();
        for (Node<T> child : node.getChildrenList())
            n += getNumberOfDescendants(child);

        return n;

    }

    private boolean find(Node<T> node, T keyNode) {
        boolean res = false;
        if (node.getData().equals(keyNode))
            return true;

        else {
            for (Node<T> child : node.getChildrenList())
                if (find(child, keyNode))
                    res = true;
        }

        return res;
    }

    /**
     *
     * Get the list of nodes arranged by the pre-order traversal of the tree.
     *
     * @return The list of nodes in the tree, arranged in the pre-order
     */
    public ArrayList<Node<T>> getPreOrderTraversal() {
        ArrayList<Node<T>> preOrder = new ArrayList<Node<T>>();
        buildPreOrder(root, preOrder);
        return preOrder;
    }

    /**
     *
     * Get the list of nodes arranged by the post-order traversal of the tree.
     *
     * @return The list of nodes in the tree, arranged in the post-order
     */
    public ArrayList<Node<T>> getPostOrderTraversal() {
        ArrayList<Node<T>> postOrder = new ArrayList<Node<T>>();
        buildPostOrder(root, postOrder);
        return postOrder;
    }

    private void buildPreOrder(Node<T> node, ArrayList<Node<T>> preOrder) {
        preOrder.add(node);
        for (Node<T> child : node.getChildrenList()) {
            buildPreOrder(child, preOrder);
        }
    }

    private void buildPostOrder(Node<T> node, ArrayList<Node<T>> postOrder) {
        for (Node<T> child : node.getChildrenList()) {
            buildPostOrder(child, postOrder);
        }
        postOrder.add(node);
    }

    /**
     *
     * Get the list of nodes in the longest path from root to any leaf in the tree.
     *
     * For example, for the below tree
     * <pre>
     *          A
     *         / \
     *        B   C
     *           / \
     *          D  E
     *              \
     *              F
     * </pre>
     *
     * The result would be [A, C, E, F]
     *
     * @return The list of nodes in the longest path.
     */
    public ArrayList<Node<T>> getLongestPathFromRootToAnyLeaf() {
        ArrayList<Node<T>> longestPath = null;
        int max = 0;
        for (ArrayList<Node<T>> path : getPathsFromRootToAnyLeaf()) {
            if (path.size() > max) {
                max = path.size();
                longestPath = path;
            }
        }
        return longestPath;
    }


    /**
     *
     * Get the height of the tree (the number of nodes in the longest path from root to a leaf)
     *
     * @return The height of the tree.
     */
    public int getHeight() {
        return getLongestPathFromRootToAnyLeaf().size();
    }

    /**
     *
     * Get a list of all the paths (which is again a list of nodes along a path) from the root node to every leaf.
     *
     * @return List of paths.
     */
    public ArrayList<ArrayList<Node<T>>> getPathsFromRootToAnyLeaf() {
        ArrayList<ArrayList<Node<T>>> paths = new ArrayList<ArrayList<Node<T>>>();
        ArrayList<Node<T>> currentPath = new ArrayList<Node<T>>();
        getPath(root, currentPath, paths);

        return paths;
    }

    private void getPath(Node<T> node, ArrayList<Node<T>> currentPath,
                         ArrayList<ArrayList<Node<T>>> paths) {
        if (currentPath == null)
            return;

        currentPath.add(node);

        if (node.getChildrenList().size() == 0) {
            // This is a leaf
            paths.add(clone(currentPath));
        }
        for (Node<T> child : node.getChildrenList())
            getPath(child, currentPath, paths);

        int index = currentPath.indexOf(node);
        for (int i = index; i < currentPath.size(); i++)
        {   
            if(index == -1)
            {continue;}
            currentPath.remove(index);
        }
    }

    private ArrayList<Node<T>> clone(ArrayList<Node<T>> list) {
        ArrayList<Node<T>> newList = new ArrayList<Node<T>>();
        for (Node<T> node : list)
            newList.add(new Node<T>(node));

        return newList;
    }
    public String printTree() {
        SimpleQueue<Node<T>> stack1 = new SimpleQueue<>();
        String out = "1 1\n";
        stack1.add(this.getRoot());
        while (!stack1.isEmpty()) {
            Node<T> node = stack1.remove();
            if (node.equals(root)) {
            } else {
                out += (node.getParent().toString() + " " + node.toString() + "\n");
            }
            for (Node<T> tnode : node.getChildrenList()) {
                stack1.add(tnode);
            }
        }
        return out;
    }
    public void calccounts()
    {
        SimpleQueue<Node<T>> stack1 = new SimpleQueue<>();
        stack1.add(this.getRoot());
        while (!stack1.isEmpty()) {
            Node<T> node = stack1.remove();
            if(node.getChildrenList().size()== 0)
            {
                counts[0]++;
            }
            else if(node.getChildrenList().size() ==1)
            {
                counts[1]++;
            }
            else if(node.getChildrenList().size() == 2)
            {
                counts[2]++;
            }
            else if(node.getChildrenList().size() == 3)
            {
                counts[3]++;
            }
            else if(node.getChildrenList().size() == 4)
            {
                counts[4]++;
            }
            else if(node.getChildrenList().size() == 5)
            {
                counts[5]++;
            }
            for(Node<T> tnode : node.getChildrenList())
            {
                stack1.add(tnode);
            }
        }
    }
    public void calculateAlpha()
    {
        double nodes = this.size();
        double leafs = 0;
        for(var value : this.getPathsFromRootToAnyLeaf())
        {
            leafs++;
        }
        double out = nodes/leafs;
        if(out == Double.POSITIVE_INFINITY)
        {
            alpha = 1;
        }
        else
        {
        alpha = out;
        }
    }
    public double getAlpha()
    {
        return alpha;
    }
}