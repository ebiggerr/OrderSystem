package com.Wendys;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    //boolean to check if an customer made any order using option selection "1"
    boolean ordered=false;

    //initialization
    String customer_name="";
    int generated_customer_id=0;

    //logger
    static Logger logger=null;

    public static void main(String[] args) {

        //driver program
        Main main_driver=new Main();

        //logger setup and configuration
        String formattedDate = getDate();

        //location for the log file : PROJECT_DIRECTORIES/logs/logfiles/
        //example file pattern : systemLogs_2020-08-02 14_12.txt
        final String filePath="logs/logfiles/systemLogs_" + formattedDate +".txt" ;

        try {

            InputStream stream = Main.class.getClassLoader().getResourceAsStream("logging.properties");

            logger=Logger.getLogger(Main.class.getName());

            LogManager.getLogManager().readConfiguration(stream);

            Handler fileHandler = new FileHandler(filePath, true);

            logger.addHandler(fileHandler);

            logger.config("Logging File Location Configuration Done.");
            logger.info(filePath);

        } catch (IOException e) {
            logger.warning(e.toString());
        }

        //boolean to control the loop of the program
        //the program stop looping after run is updated to false
        boolean run=true;

        //Scanner object
        Scanner scanner;

        // if-else (select the operation) user select desired operation using the number in front of the description
        //eg. 1. Make an order
        // (Thus, enter 1)
        int op_selection=0;

        //authorisation of staff operation
        //reject if not a match to the pre-defined code : eg.access
        boolean authorised=false;
        
        //create a Menu object
        //linked list
        Menu menu=new Menu();

        //addItem in the Menu
        menu.addItem(1,"Hamburger",new BigDecimal("3.50"));
        menu.addItem(2,"Fish and Chips",new BigDecimal("8.00"));
        menu.addItem(3,"Caesar Salad",new BigDecimal("6.00"));
        menu.addItem(4,"Soup of the Day",new BigDecimal("4.00"));
        menu.addItem(5,"Fried Chicken Chop",new BigDecimal("7.00"));
        menu.addItem(6,"Mashed Potato",new BigDecimal("3.00"));

        //create customer list
        List<Customer> customerList=new ArrayList<Customer>();

        //create order Queue
        Queue<Cust_Order> ordersQueue= new LinkedList<Cust_Order>();

        System.out.println("Welcome to our restaurant.");

        //main loop of the whole program
        while(run) {

        System.out.println(
                "1. Make An Order\n" +
                "2. Completed An Customer Order. (Staff Operation) \n" +
                "3. View The Pending Queue Of Order. (Staff Operation) \n" +
                "4. Make Order As New Customer\n" +
                "5. View the Menu.\n" +
                "6. Exit the program" );

                scanner=new Scanner(System.in);
                op_selection=scanner.nextInt();
                scanner=null;

            //customer make an order
            if (op_selection == 1) {
                main_driver.custMakeOrder(customerList,menu,ordersQueue);
            }

            // completed an order
            //this action with dequeue the completed order and display the new order that staff need to prepare and serve
            else if(op_selection == 2){

                authorised= main_driver.check_Authorisation();

                if(authorised) {

                    //make sure to handle if the queue is already empty, there is no more order to complete and display.
                    if ( !ordersQueue.isEmpty() ) {

                        //there is only one left, so no more order can be display to staff.
                        if ( ordersQueue.size() == 1 ) {

                            Cust_Order peekItem=ordersQueue.peek();
                            logger.info("Completed order of " + peekItem.getCustomerName() + " and removed from the queue at " + getDate() );

                            ordersQueue.remove();
                            System.out.println("You Have Completed All Orders In The Queue.");
                            System.out.println("");
                        }

                        //orders in the queue are more than 1
                        else{

                            Cust_Order peekItem=ordersQueue.peek();
                            logger.info("Completed order of " + peekItem.getCustomerName() + " and removed from the queue at " + getDate() );

                            //remove the completed order from the order
                            ordersQueue.remove();
                            //prompt to user so that they know that if a queue of order A and B, completed A will remove A from the queue and display B
                            System.out.println("The previous completed order is removed from the queue. This is the pending order in the queue. ");
                            System.out.println("");

                            //order B as the example above
                            //get the head in the queue
                            Cust_Order order = ordersQueue.peek();

                            main_driver.display_Queue_Item(order);
                        }
                    }
                    else{
                        System.out.println("No order made yet in the queue.");
                        System.out.println("");
                    }
                }
                else{
                    System.out.println("Sorry. You are Not authorised to perform this operation.");
                    System.out.println("");
                }
            }
            // View the pending order from the queue
            else if(op_selection == 3) {

                authorised= main_driver.check_Authorisation();

                if (authorised) {

                    //get the head in the queue
                    Cust_Order order = ordersQueue.peek();

                    main_driver.display_Queue_Item(order);

                }
                else{
                    System.out.println("Sorry. You are Not authorised to perform this operation.");
                    System.out.println("");
                }
            }

            // new customer make a new order
            else if(op_selection == 4){
                main_driver.custMakeOrder(customerList,menu,ordersQueue);
            }

            else if(op_selection == 5){

                menu.head=menu.mergeSortID(menu.head);
                menu.printList();
                System.out.println("OR do you want to view the menu with the condition of arranging it from PRICE Lowest to Highest? ENTER \"y\" ");

                scanner=new Scanner(System.in);
                String view = scanner.nextLine();

                if( view.equals("y") ){
                    menu.head = menu.mergeSort(menu.head);
                    menu.printList();
                }
                //do nothing
            }
            //stop the program
            else{

                //stop to program due to user selection of "Exit program"
                run=false;
            }
        }
    }

    private static String getDate() {

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm");

        return myDateObj.format(myFormatObj);
    }

    /**
     *
     * @param ordered_item_LL list of the menu NODE ordered by a customer
     * @return a String array that contains all the items that have ordered by a customer
     */
    static String[] displayToString(LinkedList<Menu.Node> ordered_item_LL){

        //get the size of the linked list for the use of create a same size String array
        int size=ordered_item_LL.size();

        //create a String array with the size same with the linked list of the menu item NODE
        String []itemName=new String[size];


        for(int m=0;m<size;m++){

            //get the node with its index, using get(int index)
            Menu.Node temp= ordered_item_LL.get(m);

            //load the name of the items from linked list into the String array
            itemName[m]=temp.getName(temp);
        }
        return itemName;
    }

    /**
     *
     * @return int - generate a random ID for a new customer ( range from 1-100 )
     */
    static int generateRandom(){
        return (int)(Math.random() * 100);
    }

    /**
     *
     * @param customerList LinkedList of the Customer(int id,String name)
     * @param menu LinkedList of the Menu of the restaurant
     * //@param ordered_items LinkedList of the items selected by the customer
     * @param orderQueue Queue of the order made by all customer.
     */
    //TODO changes made trying to solve #issue1
    //void custMakeOrder( List<Customer> customerList, Menu menu ,LinkedList<Menu.Node> ordered_items,Queue<Cust_Order>orderQueue){
        void custMakeOrder( List<Customer> customerList, Menu menu,Queue<Cust_Order>orderQueue){

        //TODO #issue1 only the ordered items by a customer of a latest order stored in the LinkedList, others get replaced or erased. [FIXED]
            /** Situation
             *  When multiple orders add to the orderQueue, if orderQueue contains order A by customer A, order B by customer B
             *  when displayToString() function is called, displaying the order A, the name would be name of customer A and the price too, but the items ordered would be
             *  displaying the items of customer B.
             *
             *  Possible Cause
             *  Due to line 327 , the clear() function
             *
             *  Possible Solution:
             *  Wipe out the the clear() function , but it caused another problem which is all the items ordered, no matter customer A or B, all items stored in a single LinkedList
             *
             *  Actual Cause: Due to the LinkedList<Menu.Node> ordered_items are assigned and defined outside the method custMakeOrder(),
             *  calling the method use the same LinkedList therefore all the items before the last call get replaced.
             *
             *  Actual Solution : create, assign and define the linkedlist inside the method custMakeOrder()
             */


        ordered=false;
        boolean valid_order=false;
        int valid_counter_counter=0;

        BigDecimal temp;
        BigDecimal totalPrice =new BigDecimal("0.00");

        //a linked list to store the items ordered by a customer
        LinkedList<Menu.Node>ordered_items = new LinkedList<>();

        System.out.println("");
        System.out.println("How would you prefer to be addressed as?");
        Scanner scanner12=new Scanner(System.in);
        customer_name=scanner12.nextLine();
        scanner12=null;

        //generate a random ID for a customer
        generated_customer_id= generateRandom();

        //add the customer into the list of customer
        customerList.add(new Customer(generated_customer_id,customer_name));

        menu.printList();
        System.out.println("Select the item that you want to order. (Example : 1 2 3 4) separated with single whitespace.");

            scanner12=new Scanner(System.in);
            //storing the selected items as String and will be parsed as int array using split() function
            String orderString = scanner12.nextLine();
            scanner12=null;

            //clean the String inserted by the customer into a String array without whitespaces
            //the regex (Regular Expression) ignores all the whitespace
            // split 1 2 3 5    6 into 12356
            String[] tokens= orderString.split("\\s+");
            int []orderArray=new int[tokens.length];

            //parse 12356 into integer array {{1},{3},{3},{5},{6}}
            int i=0;
            for(String token: tokens){
                orderArray[i++]=Integer.parseInt(token);
            }

            for(int j=0;j< orderArray.length;j++){

                //retrieve the item in the menu using the item_id stored in the integer array
                Menu.Node item =menu.getItem(orderArray[j]);

                if(item != null){

                    ordered_items.add(item);

                    temp=item.getPrice(item);

                    totalPrice = temp.add(totalPrice);
                    ordered=true;
                    valid_counter_counter++;
                }
                else{
                }
            }

        //only if the counter is the same as the array length
        //then the order is considered as valid order and added into the order queue
        if( valid_counter_counter == orderArray.length) {
            //add the customer's order into the order queue
            orderQueue.add(new Cust_Order(customer_name, ordered_items, totalPrice));

            logger.info(customer_name + " made an order of total price of RM" + totalPrice + " at " + getDate() );
        }
        else{
            ordered=false;
        }

        if(ordered){
            System.out.println("Thank You. You have completed you order.");
            System.out.println("");
        }

        else{
            System.out.println("Order failed. Maybe due to invalid item selected. Please try again.");
            System.out.println("");
        }
    }

    /**
     *
     * @return boolean true if the user input matches with the pre-defined access code and, vice versa
     */
    boolean check_Authorisation(){

            System.out.println("Please enter you authorisation code.");
            Scanner s=new Scanner(System.in);
            String input=s.nextLine();

        //authorisation code for staff operation
        final String access_code = "access";
        return input.equals(access_code);
    }


    /**
     *
     * @param order public Cust_Order(String cust_name, LinkedList<Menu.Node> items, BigDecimal orderTotal)
     *              retrieve all information of a customer's order
     */
    void display_Queue_Item(Cust_Order order){

        //if the queue is not empty, then the order should not be a null
        if (order != null){

            String disp_Customer_name = order.getCustomerName();
            LinkedList<Menu.Node> disp_Menu_LL = order.getItemsLinkedList();
            BigDecimal display_total = order.getOrderTotal();

            //load all the items ordered by a customer into a String array
            String[] listMenu = displayToString(disp_Menu_LL);

            System.out.println("Order Details \n" +
                    "Customer Name: " + disp_Customer_name + "\n" +
                    "Items of the order :");

            //print out the items in String array
            for (int p = 0; p < listMenu.length; p++) {
                System.out.println(listMenu[p]);
            }

            System.out.println("");
            System.out.println("Total of the order : RM" + display_total);
            System.out.println("");

            listMenu = null;
        }

        //if the queue is empty
        //the Cust_Order order is a null
        else{
            System.out.println("Empty queue.");
            System.out.println("");
        }
    }


}
