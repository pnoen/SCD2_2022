package au.edu.sydney.soft3202.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        assertEquals(2.5, shoppingBasket.getValue(), "Didn't add apple.");

        shoppingBasket.addItem("orange", 1);
        assertEquals(3.75, shoppingBasket.getValue(), "Didn't add orange.");

        shoppingBasket.addItem("pear", 1);
        assertEquals(6.75, shoppingBasket.getValue(), "Didn't add pear.");

        shoppingBasket.addItem("banana", 2);
        assertEquals(16.65, shoppingBasket.getValue(), "Didn't add bananas.");
    }

    @Test
    public void addItemNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem(null, 1);},
                "Didn't throw IllegalArgumentException for a null item in addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a null was provided.");
        assertEquals(0, shoppingBasket.getItems().size(), "Added item to the basket when a null was provided.");
    }

    @Test
    public void addItemNonMatchingItem() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem("persimmons", 1);},
                "Didn't throw IllegalArgumentException for a non matching item in addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a non matching item was provided.");
        assertEquals(0, shoppingBasket.getItems().size(), "Added non matching item to the basket.");
    }

    @Test
    public void addItemOutOfRangeCount() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem("apple", 0);},
                "Didn't throw IllegalArgumentException for an out of range count for addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a count out of range was provided");
        assertEquals(0, shoppingBasket.getItems().size(), "Added item to the basket when a count out of range was provided");
    }

    @Test
    public void addItemNegativeCount() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.addItem("apple", -1);},
                "Didn't throw IllegalArgumentException for a negative count for addItem.");
        assertNull(shoppingBasket.getValue(), "Added item cost when a negative count was provided");
        assertEquals(0, shoppingBasket.getItems().size(), "Added item to the basket when a negative count was provided");
    }

    @Test
    public void addItemCaseInsensitiveItem() {
        shoppingBasket.addItem("Apple", 1);
        assertEquals(2.5, shoppingBasket.getValue(), "Didn't add apple (Case Insensitive).");
        assertEquals(1, shoppingBasket.getItems().size(), "Didn't add apple to the list of items.");

        shoppingBasket.addItem("oRange", 1);
        assertEquals(3.75, shoppingBasket.getValue(), "Didn't add orange (Case Insensitive).");
        assertEquals(2, shoppingBasket.getItems().size(), "Didn't add orange to the list of items.");

        shoppingBasket.addItem("pEAr", 1);
        assertEquals(6.75, shoppingBasket.getValue(), "Didn't add pear (Case Insensitive).");
        assertEquals(3, shoppingBasket.getItems().size(), "Didn't add pear to the list of items.");

        shoppingBasket.addItem("banaNA", 1);
        assertEquals(11.7, shoppingBasket.getValue(), "Didn't add banana (Case Insensitive).");
        assertEquals(4, shoppingBasket.getItems().size(), "Didn't add banana to the list of items.");
    }

    @Test
    public void addItemLargeCount() {
        shoppingBasket.addItem("apple", 50);
        assertEquals(125.0, shoppingBasket.getValue(), "Didn't add a large number of apples.");
        assertEquals(1, shoppingBasket.getItems().size(), "Didn't add apple to the list of items.");
    }

    @Test
    public void removeItemNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem(null, 1);},
                "Didn't throw IllegalArgumentException for a null item in removeItem.");
    }

    @Test
    public void removeItemNullItemNonEmptyBasket() {
        shoppingBasket.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem(null, 1);},
                "Didn't throw IllegalArgumentException for a null item in removeItem.");
        assertEquals(2.5, shoppingBasket.getValue(), "Removed item cost when a null was provided.");
        assertEquals(1, shoppingBasket.getItems().size(), "Removed item from the basket when a null was provided.");
    }

    @Test
    public void removeItemNonMatchingItem() {
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem("persimmons", 1);},
                "Didn't throw IllegalArgumentException for a non matching item in removeItem.");
    }

    @Test
    public void removeItemNonMatchingItemNonEmptyBasket() {
        shoppingBasket.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem("persimmons", 1);},
                "Didn't throw IllegalArgumentException for a non matching item in removeItem.");
        assertEquals(2.5, shoppingBasket.getValue(), "Removed item cost when a non matching item was provided.");
        assertEquals(1, shoppingBasket.getItems().size(), "Removed item from the basket when a non matching was provided.");
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
        assertEquals(1, shoppingBasket.getItems().size(), "Removed item from the basket when a out of range count was provided.");
    }

    @Test
    public void removeItemNegativeCount() {
        shoppingBasket.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> {shoppingBasket.removeItem("apple", -1);},
                "Didn't throw IllegalArgumentException for a negative count in removeItem.");
        assertNull(shoppingBasket.getValue(), "Removed item cost when a negative count was provided.");
        assertEquals(0, shoppingBasket.getItems().size(), "Removed item from the basket when a negative count was provided.");
    }

    @Test
    public void removeItemCaseInsensitiveItem() {
        shoppingBasket.addItem("apple", 1);
        shoppingBasket.addItem("orange", 1);
        shoppingBasket.addItem("pear", 1);
        shoppingBasket.addItem("banana", 1);

        assertTrue(shoppingBasket.removeItem("Apple", 1), "Didn't remove apple (Case Insensitive)");
        assertEquals(9.2, shoppingBasket.getValue(), "Didn't reduce the total cost when removing an apple.");
        assertEquals(3, shoppingBasket.getItems().size(), "Didn't remove apple from the list of items.");

        assertTrue(shoppingBasket.removeItem("oRange", 1), "Didn't remove orange (Case Insensitive)");
        assertEquals(7.95, shoppingBasket.getValue(), "Didn't reduce the total cost when removing an orange.");
        assertEquals(2, shoppingBasket.getItems().size(), "Didn't remove orange from the list of items.");

        assertTrue(shoppingBasket.removeItem("peAR", 1), "Didn't remove pear (Case Insensitive).");
        assertEquals(4.95, shoppingBasket.getValue(), "Didn't reduce the total cost when removing a pear.");
        assertEquals(1, shoppingBasket.getItems().size(), "Didn't remove pear from the list of items.");

        assertTrue(shoppingBasket.removeItem("banANA", 1), "Didn't remove banana (Case Insensitive).");
        assertNull(shoppingBasket.getValue(), "Didn't reduce the total cost when removing a banana.");
        assertEquals(0, shoppingBasket.getItems().size(), "Removed banana from the list of items even when there was a larger existing amount.");
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
    }

    @Test
    public void removeItemNotFoundItemWithFilledBasket() {
        shoppingBasket.addItem("pear", 1);

        assertFalse(shoppingBasket.removeItem("apple", 1), "Removed missing item from the basket.");
    }

    @Test
    public void getItemEmptyList() {
        assertEquals(0, shoppingBasket.getItems().size(), "Doesn't start as an empty list.");
    }

    @Test
    public void getItemAddItem() {
        shoppingBasket.addItem("apple", 1);

        assertEquals(1, shoppingBasket.getItems().size(), "Didn't add the apple to the list of items.");
    }

    @Test
    public void getValueEmptyBasket() {
        assertNull(shoppingBasket.getValue(), "Doesn't start as an empty list, cost not null.");
    }

    @Test
    public void clearEmptyBasket() {
        shoppingBasket.clear();
        assertEquals(0, shoppingBasket.getItems().size(), "Doesn't clear when the basket is empty.");
    }

    @Test
    public void clear() {
        shoppingBasket.addItem("apple", 1);

        shoppingBasket.clear();
        assertEquals(0, shoppingBasket.getItems().size(), "Doesn't clear when the basket has items.");
    }


}
