package Trees;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    /**
     * Some Sample data for the binary tree
     * Prints out the Binary tree in System.Out
     * @param args
     */
    public static void main(String[] args) {

        Node[] nodes = new Node[] {
                new Node (40, "1"),
                new Node (30, "2"),
                new Node (50, "3"),
        };

        BinaryTree tree = new BinaryTree(List.of(nodes));

        System.out.println("Pre Order"  + tree.traversePreOrder());
        System.out.println("In Order"   + tree.traverseInOrder());
        System.out.println("Post Order" + tree.traversePostOrder());

        tree.find(40).setData("Woohoo!");
        System.out.println(tree.traverseInOrder());

    }

    /**
     * The process of creating the Binary tree
     */
    public static class Node {
        int key;
        Node left, right;
        Object data;

        public Node (int key, Object data){
            this.key = key;
            this.data = data;
        }

        public int getKey() {
            return key;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public String toString() { return Integer.toString(this.key)
                + " = " + data.toString();
        }
    }
    Node root;

    /**
     * Makes the constructor from
     * the list
     */
    public BinaryTree() {}
    //constructor from list
    public BinaryTree(List<Node> nodes) {
        for (Node node : nodes) {
            this.insert(node);
        }
    }


    public Node find(int key) {
        return find(this.root, key);
    }

    private Node find(Node focusNode, int key) {
        if (focusNode.key == key) {
            return focusNode;
        } else if (key < focusNode.key) {
            return find(focusNode.left, key);
        } else if (key < focusNode.key) {
            return find(focusNode.right, key);
        } else {
            return null;
        }
    }

    public void insert (Node insertNode) {
        if (this.root == null) {
            this.root = insertNode;
        } else {
            insert(this.root, insertNode);
        }
    }

    private void insert (Node focusNode, Node insertNode) {
        if (insertNode.key < focusNode.key) {
            if (focusNode.left == null) {
                focusNode.left = insertNode;
            } else {
                insert(focusNode.left, insertNode);
            }

        } else if (insertNode.key > focusNode.key) {
                if (focusNode.right == null) {
                    focusNode.right = insertNode;
                } else { insert(focusNode.right, insertNode);
            }
        }
    }

    /**
     * Sorts the data by the pre order
     * method for the binary tree
     * @return
     */
    public ArrayList<Node> traversePreOrder() {
        return traversePreOrder(this.root);
    }

    private ArrayList<Node> traversePreOrder(Node focusNode) {
        ArrayList<Node> nodes = new ArrayList<>();

        if (focusNode != null) {
            nodes.add(focusNode);
            nodes.addAll(traversePreOrder(focusNode.left));
            nodes.addAll(traversePreOrder(focusNode.right));
        }
        return nodes;
    }

    /**
     * Sorts the data by the in order
     * method for the binary tree
     * @return
     */
    public ArrayList<Node> traverseInOrder() {
        return traverseInOrder(this.root);
    }

    private ArrayList<Node> traverseInOrder (Node focusNode) {
        ArrayList<Node> nodes = new ArrayList<>();

        if (focusNode != null) {
            nodes.addAll(traverseInOrder(focusNode.left));
            nodes.add(focusNode);
            nodes.addAll(traverseInOrder(focusNode.right));
        }
        return nodes;
    }

    /**
     * Sorts the data by the post order
     * method for the binary tree
     * @return
     */
    public ArrayList<Node> traversePostOrder() {
        return traversePostOrder(this.root);
    }

    private ArrayList<Node> traversePostOrder (Node focusNode) {
        ArrayList<Node> nodes = new ArrayList<>();

        if (focusNode != null) {
            nodes.addAll(traversePostOrder(focusNode.left));
            nodes.addAll(traversePostOrder(focusNode.right));
            nodes.add(focusNode);
        }
        return nodes;
    }


}
