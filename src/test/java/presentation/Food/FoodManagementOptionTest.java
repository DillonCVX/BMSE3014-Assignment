package presentation.Food;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FoodManagementOption enum
 * Tests enum values, codes, labels, and fromCode method
 */
public class FoodManagementOptionTest {
    
    @Test
    @DisplayName("values - Should return all enum values")
    void testValues_ShouldReturnAllEnumValues() {
        FoodManagementOption[] values = FoodManagementOption.values();
        
        assertEquals(5, values.length);
    }
    
    @Test
    @DisplayName("REGISTER_FOOD - Should have correct code and label")
    void testRegisterFood_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(1, FoodManagementOption.REGISTER_FOOD.getCode());
        assertEquals("Register New Food", FoodManagementOption.REGISTER_FOOD.getLabel());
    }
    
    @Test
    @DisplayName("EDIT_FOOD - Should have correct code and label")
    void testEditFood_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(2, FoodManagementOption.EDIT_FOOD.getCode());
        assertEquals("Edit Food", FoodManagementOption.EDIT_FOOD.getLabel());
    }
    
    @Test
    @DisplayName("DELETE_FOOD - Should have correct code and label")
    void testDeleteFood_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(3, FoodManagementOption.DELETE_FOOD.getCode());
        assertEquals("Delete Food", FoodManagementOption.DELETE_FOOD.getLabel());
    }
    
    @Test
    @DisplayName("VIEW_ALL_FOOD - Should have correct code and label")
    void testViewAllFood_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(4, FoodManagementOption.VIEW_ALL_FOOD.getCode());
        assertEquals("View All Food", FoodManagementOption.VIEW_ALL_FOOD.getLabel());
    }
    
    @Test
    @DisplayName("EXIT - Should have correct code and label")
    void testExit_ShouldHaveCorrectCodeAndLabel() {
        assertEquals(0, FoodManagementOption.EXIT.getCode());
        assertEquals("Back to Admin Menu", FoodManagementOption.EXIT.getLabel());
    }
    
    @Test
    @DisplayName("fromCode - Valid code 1 - Should return REGISTER_FOOD")
    void testFromCode_ValidCode1_ReturnsRegisterFood() {
        FoodManagementOption option = FoodManagementOption.fromCode(1);
        
        assertEquals(FoodManagementOption.REGISTER_FOOD, option);
    }
    
    @Test
    @DisplayName("fromCode - Valid code 2 - Should return EDIT_FOOD")
    void testFromCode_ValidCode2_ReturnsEditFood() {
        FoodManagementOption option = FoodManagementOption.fromCode(2);
        
        assertEquals(FoodManagementOption.EDIT_FOOD, option);
    }
    
    @Test
    @DisplayName("fromCode - Valid code 3 - Should return DELETE_FOOD")
    void testFromCode_ValidCode3_ReturnsDeleteFood() {
        FoodManagementOption option = FoodManagementOption.fromCode(3);
        
        assertEquals(FoodManagementOption.DELETE_FOOD, option);
    }
    
    @Test
    @DisplayName("fromCode - Valid code 4 - Should return VIEW_ALL_FOOD")
    void testFromCode_ValidCode4_ReturnsViewAllFood() {
        FoodManagementOption option = FoodManagementOption.fromCode(4);
        
        assertEquals(FoodManagementOption.VIEW_ALL_FOOD, option);
    }
    
    @Test
    @DisplayName("fromCode - Valid code 0 - Should return EXIT")
    void testFromCode_ValidCode0_ReturnsExit() {
        FoodManagementOption option = FoodManagementOption.fromCode(0);
        
        assertEquals(FoodManagementOption.EXIT, option);
    }
    
    @Test
    @DisplayName("fromCode - Invalid code - Should return null")
    void testFromCode_InvalidCode_ReturnsNull() {
        assertNull(FoodManagementOption.fromCode(99));
        assertNull(FoodManagementOption.fromCode(-1));
        assertNull(FoodManagementOption.fromCode(5));
    }
    
    @Test
    @DisplayName("valueOf - Valid name - Should return correct enum")
    void testValueOf_ValidName_ReturnsCorrectEnum() {
        assertEquals(FoodManagementOption.REGISTER_FOOD, 
                     FoodManagementOption.valueOf("REGISTER_FOOD"));
        assertEquals(FoodManagementOption.EXIT, 
                     FoodManagementOption.valueOf("EXIT"));
    }
}

