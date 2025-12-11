package presentation.Food;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FoodEditOption enum
 * Tests enum values, codes, labels, and fromCode method
 */
public class FoodEditOptionTest {
    
    @Test
    @DisplayName("values - Should return all enum values")
    void testValues_ShouldReturnAllEnumValues() {
        FoodEditOption[] values = FoodEditOption.values();
        
        assertEquals(3, values.length);
    }
    
    @Test
    @DisplayName("NAME - Should have correct code and label")
    void testName_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(1, FoodEditOption.NAME.getCode());
        assertEquals("Food Name", FoodEditOption.NAME.getLabel());
    }
    
    @Test
    @DisplayName("PRICE - Should have correct code and label")
    void testPrice_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(2, FoodEditOption.PRICE.getCode());
        assertEquals("Food Price", FoodEditOption.PRICE.getLabel());
    }
    
    @Test
    @DisplayName("TYPE - Should have correct code and label")
    void testType_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(3, FoodEditOption.TYPE.getCode());
        assertEquals("Food Type", FoodEditOption.TYPE.getLabel());
    }
    
    @Test
    @DisplayName("fromCode - Valid code 1 - Should return NAME")
    void testFromCode_ValidCode1_ReturnsName() {
        FoodEditOption option = FoodEditOption.fromCode(1);
        
        assertEquals(FoodEditOption.NAME, option);
    }
    
    @Test
    @DisplayName("fromCode - Valid code 2 - Should return PRICE")
    void testFromCode_ValidCode2_ReturnsPrice() {
        FoodEditOption option = FoodEditOption.fromCode(2);
        
        assertEquals(FoodEditOption.PRICE, option);
    }
    
    @Test
    @DisplayName("fromCode - Valid code 3 - Should return TYPE")
    void testFromCode_ValidCode3_ReturnsType() {
        FoodEditOption option = FoodEditOption.fromCode(3);
        
        assertEquals(FoodEditOption.TYPE, option);
    }
    
    @Test
    @DisplayName("fromCode - Invalid code - Should return null")
    void testFromCode_InvalidCode_ReturnsNull() {
        assertNull(FoodEditOption.fromCode(0));
        assertNull(FoodEditOption.fromCode(4));
        assertNull(FoodEditOption.fromCode(-1));
        assertNull(FoodEditOption.fromCode(99));
    }
    
    @Test
    @DisplayName("valueOf - Valid name - Should return correct enum")
    void testValueOf_ValidName_ReturnsCorrectEnum() {
        assertEquals(FoodEditOption.NAME, FoodEditOption.valueOf("NAME"));
        assertEquals(FoodEditOption.PRICE, FoodEditOption.valueOf("PRICE"));
        assertEquals(FoodEditOption.TYPE, FoodEditOption.valueOf("TYPE"));
    }
}

