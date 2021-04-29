package com.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A node of any type. A node contains a data and links to it's children and it's parent.
 *
 * @param <T> The class type of the node
 */
public class Node<T> {
    public T name;
    public Node<T> parent;
    public List<Node<T>> children;
    public int height;
    
    
    public Node(T data, int h)
    {
        this.height = h;
        this.name = data;
        this.children = new ArrayList<Node<T>>();
    }

    /**
     * Initialize a node with another node's data.
     * This does not copy the node's children.
     *
     * @param node The node whose data is to be copied.
     */
    
    
    public Node(Node<T> node) {
        this.name = node.getData();
        children = new ArrayList<Node<T>>();
    }
    public List<Node<T>> getChildrenReverse()
    {
        List<Node<T>> tmp = this.children;
        Collections.reverse(tmp);
        return tmp;
    }
    /**
     *
     * Add a child to this node.
     *
     * @param node child node
     */
    public void addChild(Node<T> node) {
        node.setParent(this);
        children.add(node);
    }

    /**
     *
     * Add a child node at the given index.
     *
     * @param index The index at which the child has to be inserted.
     * @param child The child node.
     */
    public void addChildAt(int index, Node<T> child) {
        child.setParent(this);
        this.children.add(index, child);
    }
    public void setChildren(List<Node<T>> children) {
        for (Node<T> child : children)
            child.setParent(this);

        this.children = children;
    }

    /**
     * Remove all children of this node.
     */
    public void removeChildren() {
        this.children.clear();
    }

    /**
     *
     * Remove child at given index.
     *
     * @param index The index at which the child has to be removed.
     * @return the removed node.
     */
    public Node<T> removeChildAt(int index) {
        return children.remove(index);
    }
    /**
     * Remove given child of this node.
     *
     * @param childToBeDeleted the child node to remove.
     * @return <code>true</code> if the given node was a child of this node and was deleted,
     * <code>false</code> otherwise.
     */
    public boolean removeChild(Node<T> childToBeDeleted) {
        List<Node<T>> list = getChildrenList();
        return list.remove(childToBeDeleted);
    }
    public T getData() {
        return this.name;
    }
    public void setData(T data) {
        this.name = data;
    }
    public void setParent(Node<T> parent) {
        this.parent = parent;
    }
    public List<Node<T>> getChildrenList() {
        return this.children;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;

        if (obj instanceof Node) {
            if (((Node<?>) obj).getData().equals(this.name))
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
    public Node<T> getParent() {
        return parent;
    }

}