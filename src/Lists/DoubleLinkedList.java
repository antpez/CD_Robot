package Lists;

public class DoubleLinkedList {
    //member: head and tail nodes
    public static void main(String[] args) {
        DoubleLinkedList myList = new DoubleLinkedList();

        myList.append(new DoubleLinkedList.Node("c"));
        myList.append(new DoubleLinkedList.Node("d"));
        myList.append(new DoubleLinkedList.Node("e"));
        myList.append(new DoubleLinkedList.Node("f"));

        myList.prepend(new DoubleLinkedList.Node("b"));
        myList.prepend(new DoubleLinkedList.Node("a"));

        System.out.println(myList.toString());
        System.out.println(myList.toStringReverse());

        myList.insertAfter(myList.find("e"), new DoubleLinkedList.Node("Hi Jasper"));

        System.out.println(myList.toString());
        System.out.println(myList.toStringReverse());
    }
    Node head, tail;

    public static class Node {
        //member: next and previous nodes
        Object data;
        Node next;
        Node previous;

        //member: to hold data
        public Node(Object data) { this.data = data; }

        //constructor

        //method: get data
        public Object getData() { return data; }

        //method: set data
        public void setData(Object data) { this.data = data; }

        //method: get next
        public DoubleLinkedList.Node getNext() { return next; }

        //method: get previous
        public DoubleLinkedList.Node getPrevious() { return previous; }
    }

    //method: prepend
    public void prepend(DoubleLinkedList.Node node) {
        if (this.head == null || this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            node.next = this.head;
            this.head.previous = node;
            this.head = node;
        }
    }

    //method: append
    public void append(Node node) {
        if (this.head == null || this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            node.previous = this.tail;
            this.tail = node;
        }
    }

    //Method: find
    public DoubleLinkedList.Node find(Object data) {
        DoubleLinkedList.Node focusNode = this.head;

        while (focusNode !=null) {
            if (focusNode.data == data) {
                return focusNode;
            }
            focusNode = focusNode.next;
        }
        return null;
    }

    //method: insertAfter
    public void insertAfter(DoubleLinkedList.Node nodeBefore, DoubleLinkedList.Node nodeAfter) {
        if (this.tail == nodeBefore) {
            this.tail = nodeAfter;
        }

        nodeAfter.next = nodeBefore.next;
        nodeBefore.next = nodeAfter;

        nodeAfter.previous = nodeBefore;
        nodeAfter.next.previous = nodeAfter;
    }

    //method: remove
    public void remove(DoubleLinkedList.Node node) {
        if (node == this.head) {
            this.head = node.next;
        }
        if (node == this.tail) {
            this.tail = null;
        }
        DoubleLinkedList.Node focusNode = this.head;
        DoubleLinkedList.Node previousNode = null;

        while (focusNode !=null) {
            if (focusNode == node) {
                previousNode.next = node.next;
            }
            previousNode = focusNode;
            focusNode = focusNode.next;
        }
    }

    //method: toString
    public String toString() {
        DoubleLinkedList.Node focusNode = this.head;
        String str = "Linked List: ";

        while (focusNode !=null){
            str += focusNode.data.toString();
            if  (focusNode.next != null) {
                str += " -> \n";
            }
            focusNode = focusNode.next;
        }
        return str;
    }

    //method: toStringReverse
    public String toStringReverse() {
        DoubleLinkedList.Node focusNode = this.tail;
        String str = "Reversed Linked List: ";

        while (focusNode !=null) {
            str += focusNode.data.toString();
            if  (focusNode.previous != null) {
                str += " <- ";
            }
            focusNode = focusNode.previous;
        }
        return str;
    }

}
