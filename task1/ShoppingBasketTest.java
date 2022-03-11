package au.edu.sydney.soft3202.task1;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingBasketTest {

    ShoppingBasket shoppingBasket;

    @BeforeEach
    public void setup() {
        this.shoppingBasket = new ShoppingBasket();
    }

    @Test
    public void addItem() {
        shoppingBasket.addItem("apple", 1);
        assertEquals(2.5, shoppingBasket.getValue(), "Didn't add apple to the total cost.");

        shoppingBasket.addItem("orange", 1);
        assertEquals(3.75, shoppingBasket.getValue(), "Didn't add orange to the total cost.");

        shoppingBasket.addItem("pear", 1);
        assertEquals(6.75, shoppingBasket.getValue(), "Didn't add pear to the total cost.");

        shoppingBasket.addItem("banana", 2);
        assertEquals(16.65, shoppingBasket.getValue(), "Didn't add bananas to the total cost.");

        boolean foundApple = false;
        boolean foundOrange = false;
        boolean foundPear = false;
        boolean foundBanana = false;

        int apple = 0;
        int orange = 0;
        int pear = 0;
        int banana = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
            else if (pair.getKey().equals("orange")) {
                foundOrange = true;
                orange += pair.getValue();
            }
            else if (pair.getKey().equals("pear")) {
                foundPear = true;
                pear += pair.getValue();
            }
            else if (pair.getKey().equals("banana")) {
                foundBanana = true;
                banana += pair.getValue();
            }
        }
        assertTrue(foundApple, "Didn't add apple to the basket.");
        assertTrue(foundOrange, "Didn't add orange to the basket.");
        assertTrue(foundPear, "Didn't add pear to the basket.");
        assertTrue(foundBanana, "Didn't add orange to the basket.");

        assertEquals(1, apple, "Didn't add the correct number of apples to the basket.");
        assertEquals(1, orange, "Didn't add the correct number of oranges to the basket.");
        assertEquals(1, pear, "Didn't add the correct number of pears to the basket.");
        assertEquals(2, banana, "Didn't add the correct number of bananas to the basket.");
    }

    @Test
    public void addItemNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem(null, 1);},
                "Didn't throw IllegalArgumentException for a null item in addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a null was provided.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Added item to the basket when a null was provided.");
    }

    @Test
    public void addItemNonMatchingItem() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem("persimmons", 1);},
                "Didn't throw IllegalArgumentException for a non matching item in addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a non matching item was provided.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Added non matching item to the basket.");
    }

    @Test
    public void addItemOutOfRangeCount() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem("apple", 0);},
                "Didn't throw IllegalArgumentException for an out of range count for addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a count out of range was provided");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Added item to the basket when a count out of range was provided");
    }

    @Test
    public void addItemNegativeCount() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem("apple", -1);},
                "Didn't throw IllegalArgumentException for a negative count for addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a negative count was provided");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Added item to the basket when a negative count was provided");
    }

    @Test
    public void addItemNullItemOutOfRangeCount() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem(null, 0);},
                "Didn't throw IllegalArgumentException for null item and out of range count for addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a null item and out of range count was provided");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Added item to the basket when a null item and out of range count was provided");
    }

    @Test
    public void addItemCaseInsensitiveItem() {
        shoppingBasket.addItem("Apple", 1);
        assertEquals(2.5, shoppingBasket.getValue(), "Didn't add apple to the total cost (Case Insensitive).");

        shoppingBasket.addItem("oRange", 1);
        assertEquals(3.75, shoppingBasket.getValue(), "Didn't add orange to the total cost (Case Insensitive).");

        shoppingBasket.addItem("pEAr", 1);
        assertEquals(6.75, shoppingBasket.getValue(), "Didn't add pear to the total cost (Case Insensitive).");

        shoppingBasket.addItem("banaNA", 1);
        assertEquals(11.7, shoppingBasket.getValue(), "Didn't add banana to the total cost (Case Insensitive).");

        boolean foundApple = false;
        boolean foundOrange = false;
        boolean foundPear = false;
        boolean foundBanana = false;

        int apple = 0;
        int orange = 0;
        int pear = 0;
        int banana = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
            else if (pair.getKey().equals("orange")) {
                foundOrange = true;
                orange += pair.getValue();
            }
            else if (pair.getKey().equals("pear")) {
                foundPear = true;
                pear += pair.getValue();
            }
            else if (pair.getKey().equals("banana")) {
                foundBanana = true;
                banana += pair.getValue();
            }
        }
        assertTrue(foundApple, "Didn't add apple to the basket (Case Insensitive).");
        assertTrue(foundOrange, "Didn't add orange to the basket (Case Insensitive).");
        assertTrue(foundPear, "Didn't add pear to the basket (Case Insensitive).");
        assertTrue(foundBanana, "Didn't add orange to the basket (Case Insensitive).");

        assertEquals(1, apple, "Didn't add the correct number of apples to the basket (Case Insensitive).");
        assertEquals(1, orange, "Didn't add the correct number of oranges to the basket (Case Insensitive).");
        assertEquals(1, pear, "Didn't add the correct number of pears to the basket (Case Insensitive).");
        assertEquals(1, banana, "Didn't add the correct number of bananas to the basket (Case Insensitive).");
    }

    @Test
    public void addItemLargeCount() {
        shoppingBasket.addItem("apple", 860000000);
        assertEquals(2150000000.0, shoppingBasket.getValue(), "Didn't add a large number of apples to the cost.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }
        assertTrue(foundApple, "Didn't add apple to the basket when a large number of apples was provided.");
        assertEquals(860000000, apple, "Didn't add the correct number of apples when a large number of apples was provided.");
    }

    @Test
    public void addItemSameItem() {
        shoppingBasket.addItem("apple", 1);
        shoppingBasket.addItem("apple", 1);
        assertEquals(5.0, shoppingBasket.getValue(), "Didn't add 2 apples to the total cost.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }
        assertTrue(foundApple, "Didn't add apple to the basket when apple was added twice in different instances.");
        assertEquals(2, apple, "Didn't add the correct number of apples when it was added twice in different instances.");
    }

    @Test
    public void addItemEmptyItem() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem("", 1);},
                "Didn't throw IllegalArgumentException for an empty item in addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when an empty item was provided.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Added item to the basket when an empty item was provided.");
    }

    @Test
    public void removeItem() {
        shoppingBasket.addItem("apple", 2);
        shoppingBasket.addItem("orange", 2);
        shoppingBasket.addItem("pear", 2);
        shoppingBasket.addItem("banana", 2);

        assertTrue(shoppingBasket.removeItem("apple", 1), "Didn't remove apple.");
        assertEquals(20.9, shoppingBasket.getValue(), "Didn't reduce the total cost when removing an apple.");

        assertTrue(shoppingBasket.removeItem("orange", 1), "Didn't remove orange.");
        assertEquals(19.65, shoppingBasket.getValue(), "Didn't reduce the total cost when removing an orange.");

        assertTrue(shoppingBasket.removeItem("pear", 1), "Didn't remove pear.");
        assertEquals(16.65, shoppingBasket.getValue(), "Didn't reduce the total cost when removing a pear.");

        assertTrue(shoppingBasket.removeItem("banana", 1), "Didn't remove banana.");
        assertEquals(11.7, shoppingBasket.getValue(), "Didn't reduce the total cost when removing a banana.");

        boolean foundApple = false;
        boolean foundOrange = false;
        boolean foundPear = false;
        boolean foundBanana = false;

        int apple = 0;
        int orange = 0;
        int pear = 0;
        int banana = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
            else if (pair.getKey().equals("orange")) {
                foundOrange = true;
                orange += pair.getValue();
            }
            else if (pair.getKey().equals("pear")) {
                foundPear = true;
                pear += pair.getValue();
            }
            else if (pair.getKey().equals("banana")) {
                foundBanana = true;
                banana += pair.getValue();
            }
        }
        assertTrue(foundApple, "Removed apple from the basket.");
        assertTrue(foundOrange, "Removed orange from the basket.");
        assertTrue(foundPear, "Removed pear from the basket.");
        assertTrue(foundBanana, "Removed orange from the basket.");

        assertEquals(1, apple, "Didn't remove the correct number of apples from the basket.");
        assertEquals(1, orange, "Didn't remove the correct number of oranges from the basket.");
        assertEquals(1, pear, "Didn't remove the correct number of pears to from basket.");
        assertEquals(1, banana, "Didn't remove the correct number of bananas from the basket.");
    }

    @Test
    public void removeItemToZero() {
        shoppingBasket.addItem("apple", 1);
        shoppingBasket.addItem("orange", 1);
        shoppingBasket.addItem("pear", 1);
        shoppingBasket.addItem("banana", 1);

        assertTrue(shoppingBasket.removeItem("apple", 1), "Didn't remove apple when the count is the same as the existing count.");
        assertTrue(shoppingBasket.removeItem("orange", 1), "Didn't remove orange when the count is the same as the existing count.");
        assertTrue(shoppingBasket.removeItem("pear", 1), "Didn't remove pear when the count is the same as the existing count.");
        assertTrue(shoppingBasket.removeItem("banana", 1), "Didn't remove banana when the count is the same as the existing count.");

        assertEquals(0.0, shoppingBasket.getValue(), "Didn't reduce the total cost when the count is the same as the existing count.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Didn't remove the correct number of items when the count is the same as the existing count.");
    }

    @Test
    public void removeItemNullItem() {
        assertFalse(shoppingBasket.removeItem(null, 1), "Didn't return false for a null item in removeItem.");
    }

    @Test
    public void removeItemNullItemNonEmptyBasket() {
        shoppingBasket.addItem("apple", 1);
        assertFalse(shoppingBasket.removeItem(null, 1), "Didn't return false for a null item in removeItem.");

        assertEquals(2.5, shoppingBasket.getValue(), "Removed item cost when a null was provided.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }
        assertTrue(foundApple, "Removed apple from the basket when a null was provided.");
        assertEquals(1, apple, "Removed item from the basket when a null was provided.");
    }

    @Test
    public void removeItemNonMatchingItem() {
        assertFalse(shoppingBasket.removeItem("persimmons", 1), "Didn't return false for a non matching item in removeItem.");
    }

    @Test
    public void removeItemNonMatchingItemNonEmptyBasket() {
        shoppingBasket.addItem("apple", 1);
        assertFalse(shoppingBasket.removeItem("persimmons", 1), "Didn't return false for a non matching item in removeItem.");

        assertEquals(2.5, shoppingBasket.getValue(), "Removed item cost when a non matching item was provided.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }
        assertTrue(foundApple, "Removed apple from the basket when a non matching item was provided.");
        assertEquals(1, apple, "Removed item from the basket when a non matching item was provided.");
    }

    @Test
    public void removeItemOutOfRangeCount() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem("apple", 0);},
                "Didn't throw IllegalArgumentException for an out of range count in removeItem.");
    }

    @Test
    public void removeItemOutOfRangeCountNonEmptyBasket() {
        shoppingBasket.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem("apple", 0);},
                "Didn't throw IllegalArgumentException for an out of range count in removeItem.");
        assertEquals(2.5, shoppingBasket.getValue(), "Removed item cost when a out of range count was provided.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }
        assertTrue(foundApple, "Removed apple from the basket when a out of range count was provided.");
        assertEquals(1, apple, "Removed item from the basket when a out of range count was provided.");
    }

    @Test
    public void removeItemNegativeCount() {
        shoppingBasket.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem("apple", -1);},
                "Didn't throw IllegalArgumentException for a negative count in removeItem.");
        assertEquals(2.5, shoppingBasket.getValue(), "Removed item cost when a negative count was provided.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }
        assertTrue(foundApple, "Removed apple from the basket when a negative count was provided.");
        assertEquals(1, apple, "Removed item from the basket when a negative count was provided.");
    }

    @Test
    public void removeItemNullItemOutOfRangeCount() {
        shoppingBasket.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem(null, 0);},
                "Didn't throw IllegalArgumentException for a null item and out of range count in removeItem.");
        assertEquals(2.5, shoppingBasket.getValue(), "Removed item cost when a null item and out of range count was provided.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }
        assertTrue(foundApple, "Removed apple from the basket when a null item and out of range cost count was provided.");
        assertEquals(1, apple, "Removed item from the basket when a null item and out of range count was provided.");
    }

    @Test
    public void removeItemCaseInsensitiveItem() {
        shoppingBasket.addItem("apple", 2);
        shoppingBasket.addItem("orange", 2);
        shoppingBasket.addItem("pear", 2);
        shoppingBasket.addItem("banana", 2);

        assertTrue(shoppingBasket.removeItem("Apple", 1), "Didn't remove apple (Case Insensitive).");
        assertEquals(20.9, shoppingBasket.getValue(), "Didn't reduce the total cost when removing an apple (Case Insensitive).");

        assertTrue(shoppingBasket.removeItem("oRange", 1), "Didn't remove orange (Case Insensitive).");
        assertEquals(19.65, shoppingBasket.getValue(), "Didn't reduce the total cost when removing an orange (Case Insensitive).");

        assertTrue(shoppingBasket.removeItem("peAR", 1), "Didn't remove pear (Case Insensitive).");
        assertEquals(16.65, shoppingBasket.getValue(), "Didn't reduce the total cost when removing a pear (Case Insensitive).");

        assertTrue(shoppingBasket.removeItem("banANA", 1), "Didn't remove banana (Case Insensitive).");
        assertEquals(11.7, shoppingBasket.getValue(), "Didn't reduce the total cost when removing a banana (Case Insensitive).");

        boolean foundApple = false;
        boolean foundOrange = false;
        boolean foundPear = false;
        boolean foundBanana = false;

        int apple = 0;
        int orange = 0;
        int pear = 0;
        int banana = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
            else if (pair.getKey().equals("orange")) {
                foundOrange = true;
                orange += pair.getValue();
            }
            else if (pair.getKey().equals("pear")) {
                foundPear = true;
                pear += pair.getValue();
            }
            else if (pair.getKey().equals("banana")) {
                foundBanana = true;
                banana += pair.getValue();
            }
        }
        assertTrue(foundApple, "Removed apple from the basket (Case Insensitive).");
        assertTrue(foundOrange, "Removed orange from the basket (Case Insensitive).");
        assertTrue(foundPear, "Removed pear from the basket (Case Insensitive).");
        assertTrue(foundBanana, "Removed orange from the basket (Case Insensitive).");

        assertEquals(1, apple, "Didn't remove the correct number of apples from the basket (Case Insensitive).");
        assertEquals(1, orange, "Didn't remove the correct number of oranges from the basket (Case Insensitive).");
        assertEquals(1, pear, "Didn't remove the correct number of pears to from basket (Case Insensitive).");
        assertEquals(1, banana, "Didn't remove the correct number of bananas from the basket (Case Insensitive).");
    }

    @Test
    public void removeItemEmptyBasket() {
        assertFalse(shoppingBasket.removeItem("apple", 1), "Removed an item from an empty basket.");
    }

    @Test
    public void removeItemCountLargerThanExisting() {
        shoppingBasket.addItem("pear", 1);

        assertFalse(shoppingBasket.removeItem("pear", 2), "Removed item when count was higher than the existing amount in the basket.");
    }

    @Test
    public void removeItemExistingLargerThanCount() {
        shoppingBasket.addItem("pear", 3);

        assertTrue(shoppingBasket.removeItem("pear", 2), "Didn't remove the pears when the count was less than the existing amount.");
        assertEquals(3.0, shoppingBasket.getValue(), "Didn't reduce the total cost when the count was less than the existing amount.");

        boolean foundPear = false;
        int pear = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("pear")) {
                foundPear = true;
                pear += pair.getValue();
            }
        }
        assertTrue(foundPear, "Removed pear from the basket when the count was less than the existing count.");
        assertEquals(1, pear, "Removed item from the basket when when the count was less than the existing count.");
    }

    @Test
    public void removeItemNotFoundItemWithFilledBasket() {
        shoppingBasket.addItem("pear", 1);

        assertFalse(shoppingBasket.removeItem("apple", 1), "Removed missing item from the basket.");
    }

    @Test
    public void removeItemSameItem() {
        shoppingBasket.addItem("apple", 3);

        assertTrue(shoppingBasket.removeItem("apple", 1), "Didn't remove apple.");
        assertTrue(shoppingBasket.removeItem("apple", 1), "Didn't remove apple when performed again");

        assertEquals(2.5, shoppingBasket.getValue(), "Didn't remove the apples when performed twice.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }

        assertTrue(foundApple, "Removed apple from the basket when there is a larger existing amount.");
        assertEquals(1, apple, "Removed incorrect amount of apples when performed twice.");
    }

    @Test
    public void removeItemEmptyItem() {
        shoppingBasket.addItem("apple", 1);
        assertFalse(shoppingBasket.removeItem("", 1), "Removed apple when an empty item was provided.");

        assertEquals(2.5, shoppingBasket.getValue(), "Reduced the total cost when an empty item was provided.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }

        assertTrue(foundApple, "Removed apple from the basket when an empty item was provided.");
        assertEquals(1, apple, "Removed apple from the basket when an empty item was provided.");
    }

    @Test
    public void getItemEmptyList() {
        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Didn't start as an empty basket.");
    }


    @Test
    public void getValueEmptyBasket() {
        assertNull(shoppingBasket.getValue(), "Doesn't start as an empty list, cost not null.");
    }

    @Test
    public void clearEmptyBasket() {
        shoppingBasket.clear();
        assertNull(shoppingBasket.getValue(), "Total cost not zero when basket cleared.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Error when clearing an empty basket.");
    }

    @Test
    public void clear() {
        shoppingBasket.addItem("apple", 1);
        shoppingBasket.clear();
        assertNull(shoppingBasket.getValue(), "Total cost not zero when basket cleared.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Didn't clear when the basket has items.");
    }

    @Test
    public void clearAddItem() {
        shoppingBasket.clear();
        shoppingBasket.addItem("apple", 1);

        assertEquals(2.5, shoppingBasket.getValue(), "Didn't add the cost when item was added after clearing.");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }

        assertTrue(foundApple, "Didn't add apple when item was added after clearing.");
        assertEquals(1, apple, "Didn't add apple when the item was added after clearing.");
    }

    @Test
    public void clearAddItemRemoveItem() {
        shoppingBasket.clear();
        shoppingBasket.addItem("apple", 1);
        assertTrue(shoppingBasket.removeItem("apple", 1), "Didn't remove item when removing after clearing.");
        assertNull(shoppingBasket.getValue(), "Didn't remove item cost when removing after clearing.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Didn't remove item from the basket when removing after clearing.");
    }

    @Test
    public void clearRemoveItem() {
        shoppingBasket.addItem("apple", 1);
        shoppingBasket.clear();
        assertFalse(shoppingBasket.removeItem("apple", 1), "Removed item from an empty basket after clearing.");
        assertNull(shoppingBasket.getValue(), "Total cost not empty after clearing and removing an item.");

        int count = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            count += pair.getValue();
        }

        assertEquals(0, count, "Removed item from an empty basket after clearing.");
    }

    @Test
    public void addItemRemoveItemAddItem() {
        shoppingBasket.addItem("apple", 2);
        assertTrue(shoppingBasket.removeItem("apple", 1), "Didn't remove the apple from the basket.");
        shoppingBasket.addItem("apple", 4);

        assertEquals(12.5, shoppingBasket.getValue(), "Didn't add apple costs after removing");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }

        assertTrue(foundApple, "Didn't add apple when item was added after removing.");
        assertEquals(5, apple, "Didn't add apple when the item was added after removing.");
    }

    @Test
    public void addItemRemoveItemToZeroAddItem() {
        shoppingBasket.addItem("apple", 1);
        assertTrue(shoppingBasket.removeItem("apple", 1), "Didn't remove the apple from the basket.");
        shoppingBasket.addItem("apple", 5);

        assertEquals(12.5, shoppingBasket.getValue(), "Didn't add apple costs after removing to 0");

        boolean foundApple = false;
        int apple = 0;

        List<Pair<String, Integer>> basket = shoppingBasket.getItems();
        for (Pair<String, Integer> pair : basket) {
            if (pair.getKey().equals("apple")) {
                foundApple = true;
                apple += pair.getValue();
            }
        }

        assertTrue(foundApple, "Didn't add apple when item was added after removing to zero.");
        assertEquals(5, apple, "Didn't add apple when the item was added after removing to zero.");
    }

}
