package a3;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

import static a3.Main.csvToList;
import static org.junit.jupiter.api.Assertions.*;

public class SLinkedListTest {
    @Test
    void emptyList() {
        LList<Integer> lst0 = new SLinkedList<>();
        assertEquals(lst0.size(), 0);
    }
    @Test
    void append12() {
        LList<Integer> l1 = new SLinkedList<>();
        l1.append(42);
        assertEquals(l1.size(), 1);
        assertEquals(l1.head(), 42);
        assertEquals(l1.tail(), 42);
        l1.append(21);
        assertEquals(l1.size(), 2);
        assertEquals(l1.head(), 42);
        assertEquals(l1.tail(), 21);
        l1.append(2);
        assertEquals(l1.size(), 3);
    }
    @Test
    void prepend12() {
        LList<Integer> l1 = new SLinkedList<Integer>();
        l1.prepend(42);
        assertEquals(l1.size(), 1);
        assertEquals(l1.head(), 42);
        assertEquals(l1.tail(), 42);
        l1.prepend(21);
        assertEquals(l1.size(), 2);
        assertEquals(l1.head(), 21);
        assertEquals(l1.tail(), 42);
    }
    @Test
    void test_get() {
        LList<Integer> lst = new SLinkedList<>();
        for (int i = 0; i < 5; i++) lst.append(i);
        assertEquals(lst.size(), 5);
        for (int i = 0; i < 5; i++) {
            assertEquals(i, lst.get(i));
            lst.append(i);
        }
        assertEquals(0, lst.get(0));
        assertEquals(4,lst.get(4));

        LList<Integer> lstEasy = new SLinkedList<>();
        lstEasy.append(1);
        lstEasy.append(2);
        lstEasy.append(3);
        assertEquals(3,lstEasy.get(3));
        assertEquals(2,lstEasy.get(1));
    }
    @Test
    void test_insertBefore() {
        LList<Integer> lst = new SLinkedList<>();
        lst.append(1);
        assertEquals(lst.size(), 1);
        lst.insertBefore(2, 1);
        assertEquals(lst.size(), 2);
        assertEquals(lst.head(), 2);
        assertEquals(lst.tail(), 1);

        LList<Integer> lst2 = new SLinkedList<>();
        lst2.append(1);
        lst2.append(2);
        lst2.append(3);
        assertEquals(lst2.size(), 3);
        lst2.insertBefore(5,1);
        assertEquals(lst2.size(),4);
        assertEquals(lst2.head(), 5);
        assertEquals(lst2.tail(),3);

        LList<Integer> lstMid = new SLinkedList<>();
        lstMid.append(1);
        lstMid.append(2);
        lstMid.append(3);
        lstMid.append(4);
        lstMid.insertBefore(10,2);
        assertEquals(lstMid.head(),1);
        assertEquals(lstMid.tail(),4);
        assertEquals(lstMid.size(), 5);
    }
    @Test
    void test_contains(){
        LList<Integer> lst = new SLinkedList<>();
        lst.append(1);
        lst.append(2);
        lst.append(3);
        lst.prepend(21);
        lst.append(12);
        assertEquals(lst.contains(4), false);
        assertEquals(lst.contains(1), true);
        assertEquals(lst.contains(3), true);
        assertEquals(lst.contains(2),true);
        assertEquals(lst.contains(21),true);
        assertEquals(lst.contains(12),true);
        assertEquals(lst.contains(55),false);
    }
    @Test
    void test_remove(){
        LList<Integer> lst = new SLinkedList<>();
        lst.append(1);
        lst.append(2);
        lst.append(3);
        lst.remove(1);
        assertEquals(lst.contains(1), false);
        assertEquals(lst.size(), 2);

        LList<Integer> lst2 = new SLinkedList<>();
        lst2.append(1);
        lst2.append(2);
        lst2.append(3);
        lst2.remove(3);
        assertEquals(lst2.contains(3), false);
        assertEquals(lst2.size(), 2);

        LList<Integer> lst3 = new SLinkedList<>();
        lst3.append(1);
        lst3.append(2);
        lst3.append(3);
        lst3.append(4);
        lst3.remove(2);
        assertEquals(lst3.contains(2), false);
        assertEquals(lst3.size(), 3);

        LList<Integer> lst4 = new SLinkedList<>();
        lst4.append(1);
        lst4.append(1);
        lst4.remove(1);
        assertEquals(lst4.size(), 1);
    }
}
