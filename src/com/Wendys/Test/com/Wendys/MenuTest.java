package com.Wendys;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import com.Wendys.Menu;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @org.junit.jupiter.api.Test
    void addItem() {
        Menu menu=new Menu();
        menu.addItem(1,"Hamburger", BigDecimal.TEN);
        menu.addItem(1,"Hamburger", new BigDecimal("6.00"));



    }

    @org.junit.jupiter.api.Test
    void printList() {
        Menu menu=new Menu();
        menu.addItem(1,"Hamburger1", BigDecimal.TEN);
        menu.addItem(2,"Hamburger2", new BigDecimal("6.00"));
        menu.addItem(3,"Hamburger3", new BigDecimal("7.00"));
        menu.printList();
        menu.deleteItem(2);
        menu.printList();

        System.out.println(menu.menu_size);

    }
    @org.junit.jupiter.api.Test
    void printList_empty() {
        Menu empty_test=new Menu();
        empty_test.printList();
    }

    @Test
    void deleteItem() {
    }

    @Test
    void getItem() {

        Menu menu =new Menu();
        menu.addItem(1,"Hamburger", BigDecimal.TEN);
        menu.addItem(2,"Hamburger2", BigDecimal.TEN);

        Menu.Node item=menu.getItem(2);

        String menuname= item.getName(item);

        System.out.println(menuname);



    }

    @Test
    void mergeSort() {
        Menu menu=new Menu();
        menu.addItem(1,"Hamburger1", new BigDecimal("10.00"));
        menu.addItem(5,"Hamburger2", new BigDecimal("9.00"));
        menu.addItem(3,"Hamburger3", new BigDecimal("7.00"));

        menu.printList();
        menu.head=menu.mergeSort(menu.head);
        menu.printList();

        menu.head=menu.mergeSortID(menu.head);
        menu.printList();
    }
}