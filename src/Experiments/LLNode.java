package Experiments;

public class LLNode {

    public static void main(String[] args) {
        LLNode myList = new LLNode("Hello");
        myList.append(new LLNode("is"));
        myList.append(new LLNode("a"));
        myList.append(new LLNode("linked"));
        myList.append(new LLNode("list"));

        LLNode foundNode = myList.find("a");
        foundNode.setData("Not a");

        System.out.println(myList);

    }

    private Object data;
    private LLNode next;

    public LLNode(Object data){
        this.data = data;
    }

    public void setData(Object data) { this.data = data; }

    public Object getData() { return this.data; }

    public void append(LLNode node) {
        LLNode tailNode = this;

        //if the next node is null, it will be the tail, otherwise move to the next node
        while (tailNode.next != null) {
            //set the next node to be checked next
            tailNode = tailNode.next;
        }

        tailNode.next = node;
    }

    public LLNode find(Object searchNodeData) {
        LLNode focusNode = this;

        while (focusNode != null) {
            if (focusNode.data == searchNodeData) {
                return focusNode;
            } else {
                focusNode = focusNode.next;
            }
        }
        return null;
    }

    public String toString() {
        String str = "Linked List: ";

        LLNode focusNode = this;

        while (focusNode != null) {
            str += focusNode.data.toString();
            if (focusNode.next != null) {
                str += " -> ";
            }
            focusNode = focusNode.next;
        }

        return str;
    }
}
