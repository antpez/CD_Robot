package Lists;

import CDArchiveProject.CDRecord;
import CDArchiveProject.RecordStorage;

import java.util.ArrayList;
import java.util.List;

public class LinkedList {

    public static void main(String[] args) {
        LinkedList myList = new LinkedList();

        System.out.println(myList.toString());

        myList.append(new LinkedList.Node("c"));
        myList.append(new LinkedList.Node("d"));
        myList.append(new LinkedList.Node("e"));
        myList.append(new LinkedList.Node("f"));

        myList.prepend(new LinkedList.Node("b"));
        myList.prepend(new LinkedList.Node("a"));

        System.out.println(myList.toString());

        myList.find("d").setData("!");

        System.out.println(myList.toString());

        myList.insertAfter(myList.find("e"), new LinkedList.Node("+"));

        System.out.println(myList.toString());

        myList.remove(myList.find("e"));

        System.out.println(myList.toString());
    }

    Node head, tail;

    public static class Node {
        Object data;
        Node next;

        public Node(Object data) { this.data = data; }

        public Object getData() { return data; }

        public void setData(Object data) { this.data = data; }

        public Node getNext() { return next; }
    }

    public void prepend(Node node) {
        if (this.head == null || this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            node.next = this.head;
            this.head = node;
        }
    }


    public void append(Node node){
        if (this.head == null || this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
    }

    public Node find(Object data) {
        Node focusNode = this.head;

        while (focusNode !=null) {
            if (focusNode.data == data) {
                return focusNode;
            }
            focusNode = focusNode.next;
        }
        return null;
    }

    public void insertAfter(Node nodeBefore, Node nodeAfter) {
        if (this.tail == nodeBefore) {
            this.tail = nodeAfter;
        }

        nodeAfter.next = nodeBefore.next;
        nodeBefore.next = nodeAfter;
    }

    public void remove(Node node) {
        if (node == this.head) {
            this.head = node.next;
        }
        if (node == this.tail) {
            this.tail = null;
        }
        Node focusNode = this.head;
        Node previousNode = null;

        while (focusNode !=null) {
            if (focusNode == node) {
                previousNode.next = node.next;
            }
            previousNode = focusNode;
            focusNode = focusNode.next;
        }
    }

    public String toString() {
        Node focusNode = this.head;
        String str = "Linked List: ";

        while (focusNode !=null){
            str += focusNode.data.toString();
            if  (focusNode.next != null) {
                str += " -> ";
            }
            focusNode = focusNode.next;
        }
        return str;
    }
}
