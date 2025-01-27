package items;

import containers.LinkedList;


/**
 * An Inventory is composed of n slots. Each slot may store only
 * one type of item--specified by *slots*.
 * <p>
 * Once all slots are filled, no additional Item types may be
 * stored. Individual slots may contain any number of the same
 * Item--if the Item is stackable.
 */
public class Inventory
{
    /**
     * This is the Default Inventory size.
     */
    public static final int DEFAULT_SIZE = 10;

    /**
     * This is utility function that takes two ItemStacks and adds the
     * number of items in the right-hand side stack to the left-hand side stack.
     *
     * @param lhs stack whose size will be increased
     * @param rhs stack whose size we need to examine
     */
    public static void mergeStacks(ItemStack lhs, ItemStack rhs)    // WORK ON_______________________________________________________________________________________________________________________
    {
        // lhs needs to have items added to it.
        // rhs's size is needed
        // lhs.????(rhs.????)
        lhs.addItems(rhs.size());


    }

    /**
     * Individual item slots--each ItemStack occupies one slot.
     */
    private LinkedList<ItemStack> slots;

    /**
     * Total number of distinct Item types that can be stored.
     */
    private int capacity;

    /**
     * Default to an inventory with 10 slots.
     */
    public Inventory()
    {
        this(DEFAULT_SIZE);
    }

    /**
     * Create an inventory with n slots.
     *
     * @param desiredCapacity size of the new Inventory
     */
    public Inventory(int desiredCapacity)
    {
        this.slots    = new LinkedList<ItemStack>();
        this.capacity = desiredCapacity;
    }

    /**
     * Determine the number of slots currently in use.
     */
    public int utilizedSlots()
    {
        return this.slots.currentSize;
    }

    /**
     * Determine the number of empty (unused) slots.
     */
    public int emptySlots()
    {
        return this.totalSlots() - this.utilizedSlots();
    }

    /**
     * Retrieve the capacity (number of distinct types of items) that this
     * inventory can store.
     */
    public int totalSlots()
    {
        return this.capacity;
    }

    /**
     * Determine if the inventory is considered full.
     *
     * @return true if the current size is equal to capacity
     */
    public boolean isFull()           // WORK ON_______________________________________________________________________________________________________________________
    {
        // Replace the next line
        //return false;
        return this.slots.currentSize == this.capacity;
    
    
    }

    /**
     * Determine if the inventory is empty.
     *
     * @return true if current size is zero
     */
    public boolean isEmpty()
    {
        return this.slots.currentSize == 0;
    }

    /**
     * Search through all slots (Nodes in the LinkedList) and look for a
     * matching ItemStack.
     *
     * @param key stack for which the search is being conducted
     *
     * @return matching stack if one was found and `null` otherwise
     */
    public ItemStack findMatchingItemStack(ItemStack key)    // WORK ON_______________________________________________________________________________________________________________________
    {
        // Add the necessary sequential search loop

        //not 100% if I need to set up the iterator or if its been already, trial and error is da way
        LinkedList.Node<ItemStack> temp = this.slots.head;

        while (temp != null) {
            if (temp.data.equals(key)) {
                return temp.data;
            }

            //if not a match
            temp = temp.next;
        }

        //if it never finds a match :(
        return null;
    }

    /**
     * This is the standard Linked List append operation from Review 01
     *
     * @param toAdd data that we want to store in a Node and add to the list
     */
    public void addItemStackNoCheck(ItemStack toAdd)    // WORK ON_______________________________________________________________________________________________________________________
    {
        LinkedList.Node<ItemStack> newNode = new LinkedList.Node<>(toAdd);

        // Use the appendNode/add logic from Review 1 as your starting point
        // Once we reach this function... we know that `toAdd` must be stored

        //if the funciton is empty
        if (this.slots.head == null) {
            this.slots.head = newNode;
            this.slots.tail = newNode;
            this.slots.currentSize = 1;

        } else {

            this.slots.tail.next = newNode;  // debugger doesnt like this, if it failes redo this perchance??
            this.slots.tail = newNode;

            /* 
            // example code from guthub
            // of the existing list
            this.tail.next = newNode;

            // Update tail;
            this.tail = this.tail.next;
            // tail = newNode; 
            */


            this.slots.currentSize++;

        }
        
    }

    /**
     * Add one or more items to the inventory list.
     *
     * @param stack new stack of items to add
     *
     * @return true if *stack* was added and false otherwise
     */
    public boolean addItems(ItemStack stack)
    {
        ItemStack match = this.findMatchingItemStack(stack);

        // if a match was found
        if (match != null) {
            // If the Item is stackable, add it to the ItemStack
            if (match.permitsStacking()) {
                mergeStacks(match, stack);

                return true;
            }
        }

        if (this.slots.currentSize < capacity) {
            this.addItemStackNoCheck(stack);
            return true;
        }

        return false;
    }

    /**
     * *Print* a Summary of the Inventory and all Items contained within.
     */
    @Override
    public String toString()
    {
        String summaryLine = String.format(
            " -Used %d of %d slots%n", this.slots.currentSize, this.capacity
        );

        StringBuilder strBld = new StringBuilder();
        strBld.append(summaryLine);

        LinkedList.Node<ItemStack> it = this.slots.head;

        while (it != null) {
            String itemLine = String.format("  %s%n", it.data);
            strBld.append(itemLine);

            it = it.next;
        }

        return strBld.toString();
    }
}
