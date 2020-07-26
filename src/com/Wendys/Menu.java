package com.Wendys;

import java.math.BigDecimal;

public class Menu {

    //head node for the linked list, forming the Menu
    Node head;
    //size counter for the Menu
    int menu_size;

    //Node class used in the linked list
    class Node{

        int item_id;
        String item_name;
        BigDecimal item_price;
        //next pointer
        Node next;

        //constructor for Node
        Node(int item_id,String item_name, BigDecimal item_price){
            this.item_id=item_id;
            this.item_name=item_name;
            this.item_price=item_price;
            next=null;

        }

        //item_id
        //return the ID of the Item in the Menu
        int getID(Node node){
            return node.item_id;
        }

        //item_name
        //return the name of the Item in the Menu
        String getName(Node node){
            return node.item_name;
        }

        //item_price
        //return the Price of the Item in the Menu
        BigDecimal getPrice(Node node){
            return node.item_price;
        }


    }

    //add new Item in the Menu,
    //if the Menu is not empty, add at the tail of the linked list
    /**
     *
     * @param id ID of the item
     * @param item_name name of the item
     * @param item_price price of the item
     */
    void addItem(int id,String item_name,BigDecimal item_price){

        Node new_node=new Node(id,item_name,item_price);
        menu_size++;

        //if the linked list is empty, new Item is the head
        if( head == null){
            head=new Node(id,item_name,item_price);
        }

        //if the linked list is not empty, add the new item at the tail of the linked list
        //TODO adding else{} resolve the #issue1
        else {

            //the next of the tail node is null
            new_node.next = null;

            Node last = head;

            //traverse the linked list to find the current tail before the add
            while (last.next != null) {
                last = last.next;
            }

            //the current tail before the add is no longer have null as its next, update the next of the node to the new tail node
            last.next = new_node;
        }

    }

    //TODO need to fix #issue1 - printList() print the head element twice
    void printList(){

        //temp
        Node tempNode=head;

        //empty
        if(tempNode==null){
            System.out.println("Empty Menu");

        }
        //traverse the whole linked list
        else{
            System.out.println("The Menu");
            while(tempNode != null ){
                System.out.println( tempNode.item_id + "   " + tempNode.item_name + "   " + tempNode.item_price );

                tempNode=tempNode.next;
            }
        }
    }

    /**
     *
     * @param id id of the item in the menu
     * @return status code of the method :
     *  0 - success
     *  1 - Exception
     */
    int deleteItem(int id){

        //handle empty list
        if(head == null){
            return 1;
        }

        //the target id is the head item in the linked list
        if( head.item_id == id ){

            head=head.next;
            //decrease the menu size counter as the item get deleted from the menu
            menu_size--;
            return 0;

        }

        //Normal case
        Node current=head;
        Node prev=null;

        while (current != null && current.item_id != id){

            prev=current;
            current=current.next;

        }
        // the id is not existing in the linked list
        if( current == null){
            return 1;
        }

        //delete the item
        prev.next=current.next;
        menu_size--;

        return 0;
    }

    /**
     *
     * @param id id of the item
     * @return Node of the item with parameters ( item_id, item_name,item_price)
     */
    Node getItem(int id){

        if( head.item_id == id ){
            return head;

        }

        Node current=head;

        //traverset the linked list to find the item with matching id
        while( current != null){

            if(current.item_id == id){
                return current;
            }
            current=current.next;
        }

        //item not found
        return null;
    }

    //return the size of the menu (linked list)
    int returnMenuSize(){
        return this.menu_size;
    }

    Node mergeSort(Node head) {

        if(head == null || head.next == null){
            return head;
        }

        Node middleNode=getMiddle(head);
        Node nextOF_middleNode=middleNode.next;

        middleNode.next=null;

        Node left=mergeSort(head);

        Node right=mergeSort(nextOF_middleNode);

        Node sortedList=sortedMerge(left,right);

        return sortedList;

    }

    Node mergeSortID(Node head) {

        if(head == null || head.next == null){
            return head;
        }

        Node middleNode=getMiddle(head);
        Node nextOF_middleNode=middleNode.next;

        middleNode.next=null;

        Node left=mergeSortID(head);

        Node right=mergeSortID(nextOF_middleNode);

        Node sortedList=sortedMergeID(left,right);

        return sortedList;

    }

    private Node getMiddle(Node head){

        if(head == null){
            return head;
        }

        Node slowPointer= head;
        Node fastPointer= head;

        while( fastPointer.next != null && fastPointer.next.next != null){
            slowPointer=slowPointer.next;
            fastPointer=fastPointer.next.next;

        }

        return slowPointer;
    }

    private Node sortedMerge(Node a, Node b){

        Node result=null;

        if( a == null){
            return b;
        }
        if(b == null){
            return a;
        }

        if( a.item_price.doubleValue() <= b.item_price.doubleValue() ){
            result =a;
            result.next=sortedMerge(a.next,b);
        }
        else{
            result = b;
            result.next = sortedMerge(a, b.next);
        }

        return result;

    }

    private Node sortedMergeID(Node a, Node b){

        Node result=null;

        if( a== null){
            return b;
        }
        if(b == null){
            return a;
        }

        if( a.item_id <= b.item_id ){
            result =a;
            result.next=sortedMergeID(a.next,b);
        }
        else{
            result = b;
            result.next = sortedMergeID(a, b.next);
        }

        return result;

    }

}
