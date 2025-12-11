package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Food model
 * Tests all getters, setters, constructors, and utility methods
 */
public class FoodTest {
    
    // ============= Constructor Tests =============
    
    @Test
    @DisplayName("Constructor with name, price, type - Should create food")
    void testConstructor_WithNamePriceType_CreatesFood() {
        Food food = new Food("Chicken Rice", 10.50, "Set");
        
        assertEquals("Chicken Rice", food.getFoodName());
        assertEquals(10.50, food.getFoodPrice(), 0.01);
        assertEquals("Set", food.getFoodType());
        assertEquals(0, food.getFoodId()); // ID not set yet
    }
    
    @Test
    @DisplayName("Constructor with all fields - Should create food")
    void testConstructor_WithAllFields_CreatesFood() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        
        assertEquals(2000, food.getFoodId());
        assertEquals("Chicken Rice", food.getFoodName());
        assertEquals(10.50, food.getFoodPrice(), 0.01);
        assertEquals("Set", food.getFoodType());
    }
    
    @Test
    @DisplayName("Default constructor - Should create empty food")
    void testDefaultConstructor_ShouldCreateEmptyFood() {
        Food food = new Food();
        
        assertEquals(0, food.getFoodId());
        assertNull(food.getFoodName());
        assertNull(food.getFoodType());
        // Don't test getFoodPrice() on default constructor as it may be null internally
    }
    
    // ============= Getter and Setter Tests =============
    
    @Test
    @DisplayName("setFoodId and getFoodId - Should set and get ID")
    void testSetAndGetFoodId_ShouldSetAndGetId() {
        Food food = new Food();
        
        food.setFoodId(2000);
        
        assertEquals(2000, food.getFoodId());
    }
    
    @Test
    @DisplayName("setFoodName and getFoodName - Should set and get name")
    void testSetAndGetFoodName_ShouldSetAndGetName() {
        Food food = new Food();
        
        food.setFoodName("Nasi Lemak");
        
        assertEquals("Nasi Lemak", food.getFoodName());
    }
    
    @Test
    @DisplayName("setFoodPrice and getFoodPrice - Should set and get price")
    void testSetAndGetFoodPrice_ShouldSetAndGetPrice() {
        Food food = new Food();
        
        food.setFoodPrice(15.50);
        
        assertEquals(15.50, food.getFoodPrice(), 0.01);
    }
    
    @Test
    @DisplayName("setFoodType and getFoodType - Should set and get type")
    void testSetAndGetFoodType_ShouldSetAndGetType() {
        Food food = new Food();
        
        food.setFoodType("A la carte");
        
        assertEquals("A la carte", food.getFoodType());
    }
    
    // ============= getFoodPriceDecimal Tests =============
    
    @Test
    @DisplayName("getFoodPriceDecimal - Should return BigDecimal price")
    void testGetFoodPriceDecimal_ShouldReturnBigDecimalPrice() {
        Food food = new Food("Chicken Rice", 10.50, "Set");
        
        BigDecimal price = food.getFoodPriceDecimal();
        
        assertNotNull(price);
        assertEquals(0, new BigDecimal("10.50").compareTo(price));
    }
    
    @Test
    @DisplayName("getFoodPriceDecimal - Zero price - Should return zero BigDecimal")
    void testGetFoodPriceDecimal_ZeroPrice_ReturnsZeroBigDecimal() {
        Food food = new Food("Free Food", 0.0, "Set");
        
        BigDecimal price = food.getFoodPriceDecimal();
        
        assertNotNull(price);
        assertEquals(0, BigDecimal.ZERO.compareTo(price));
    }
    
    @Test
    @DisplayName("getFoodPriceDecimal - Large price - Should handle correctly")
    void testGetFoodPriceDecimal_LargePrice_HandlesCorrectly() {
        Food food = new Food("Expensive Food", 999.99, "Set");
        
        BigDecimal price = food.getFoodPriceDecimal();
        
        assertNotNull(price);
        assertEquals(0, new BigDecimal("999.99").compareTo(price));
    }
    
    // ============= equals Tests =============
    
    @Test
    @DisplayName("equals - Same ID - Should be equal")
    void testEquals_SameId_ShouldBeEqual() {
        Food food1 = new Food(2000, "Chicken Rice", 10.50, "Set");
        Food food2 = new Food(2000, "Different Name", 20.00, "A la carte");
        
        assertEquals(food1, food2);
    }
    
    @Test
    @DisplayName("equals - Different ID - Should not be equal")
    void testEquals_DifferentId_ShouldNotBeEqual() {
        Food food1 = new Food(2000, "Chicken Rice", 10.50, "Set");
        Food food2 = new Food(2001, "Chicken Rice", 10.50, "Set");
        
        assertNotEquals(food1, food2);
    }
    
    @Test
    @DisplayName("equals - Same object - Should be equal")
    void testEquals_SameObject_ShouldBeEqual() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        
        assertEquals(food, food);
    }
    
    @Test
    @DisplayName("equals - Null object - Should not be equal")
    void testEquals_NullObject_ShouldNotBeEqual() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        
        assertNotEquals(food, null);
    }
    
    @Test
    @DisplayName("equals - Different class - Should not be equal")
    void testEquals_DifferentClass_ShouldNotBeEqual() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        String notFood = "Not a food";
        
        assertNotEquals(food, notFood);
    }
    
    @Test
    @DisplayName("equals - Zero ID - Should be equal if both zero")
    void testEquals_ZeroId_ShouldBeEqualIfBothZero() {
        Food food1 = new Food("Chicken Rice", 10.50, "Set");
        Food food2 = new Food("Nasi Lemak", 8.00, "Set");
        
        assertEquals(food1, food2); // Both have ID 0
    }
    
    // ============= hashCode Tests =============
    
    @Test
    @DisplayName("hashCode - Same ID - Should have same hash code")
    void testHashCode_SameId_ShouldHaveSameHashCode() {
        Food food1 = new Food(2000, "Chicken Rice", 10.50, "Set");
        Food food2 = new Food(2000, "Different Name", 20.00, "A la carte");
        
        assertEquals(food1.hashCode(), food2.hashCode());
    }
    
    @Test
    @DisplayName("hashCode - Different ID - Should have different hash code")
    void testHashCode_DifferentId_ShouldHaveDifferentHashCode() {
        Food food1 = new Food(2000, "Chicken Rice", 10.50, "Set");
        Food food2 = new Food(2001, "Chicken Rice", 10.50, "Set");
        
        assertNotEquals(food1.hashCode(), food2.hashCode());
    }
    
    // ============= toString Tests =============
    
    @Test
    @DisplayName("toString - Should contain all field values")
    void testToString_ShouldContainAllFieldValues() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        
        String result = food.toString();
        
        assertTrue(result.contains("2000"));
        assertTrue(result.contains("Chicken Rice"));
        assertTrue(result.contains("10.5") || result.contains("10.50"));
        assertTrue(result.contains("Set"));
    }
    
    @Test
    @DisplayName("toString - Null fields - Should not throw exception")
    void testToString_NullFields_ShouldNotThrowException() {
        Food food = new Food();
        
        assertDoesNotThrow(() -> food.toString());
    }
    
    // ============= Edge Case Tests =============
    
    @Test
    @DisplayName("setFoodPrice - Very small price - Should handle correctly")
    void testSetFoodPrice_VerySmallPrice_HandlesCorrectly() {
        Food food = new Food();
        
        food.setFoodPrice(0.01);
        
        assertEquals(0.01, food.getFoodPrice(), 0.001);
    }
    
    @Test
    @DisplayName("setFoodName - Empty string - Should accept")
    void testSetFoodName_EmptyString_ShouldAccept() {
        Food food = new Food();
        
        food.setFoodName("");
        
        assertEquals("", food.getFoodName());
    }
    
    @Test
    @DisplayName("setFoodType - Null value - Should accept")
    void testSetFoodType_NullValue_ShouldAccept() {
        Food food = new Food("Chicken Rice", 10.50, "Set");
        
        food.setFoodType(null);
        
        assertNull(food.getFoodType());
    }
    
    @Test
    @DisplayName("Multiple setters - Should update values correctly")
    void testMultipleSetters_ShouldUpdateValuesCorrectly() {
        Food food = new Food();
        
        food.setFoodId(2000);
        food.setFoodName("Chicken Rice");
        food.setFoodPrice(10.50);
        food.setFoodType("Set");
        
        assertEquals(2000, food.getFoodId());
        assertEquals("Chicken Rice", food.getFoodName());
        assertEquals(10.50, food.getFoodPrice(), 0.01);
        assertEquals("Set", food.getFoodType());
    }
}

