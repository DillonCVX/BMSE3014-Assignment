package presentation.Food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import controller.FoodController;
import model.Food;
import presentation.General.UserCancelledException;
import presentation.General.UserInputHandler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive test class for FoodHandler
 * Aims for >90% code coverage
 */
@ExtendWith(MockitoExtension.class)
public class FoodHandlerTest {
    
    @Mock
    private FoodController foodController;
    
    @Mock
    private UserInputHandler inputHandler;
    
    private FoodHandler foodHandler;
    private ByteArrayOutputStream outputStream;
    
    @BeforeEach
    void setUp() {
        foodHandler = new FoodHandler(foodController, inputHandler);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    // ============= handleRegisterFood Tests =============
    
    @Test
    @DisplayName("handleRegisterFood - Valid input confirmed - Should register")
    void testHandleRegisterFood_ValidInputConfirmed() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Chicken Rice");
        when(foodController.validateFoodName("Chicken Rice")).thenReturn(true);
        when(foodController.isFoodNameUnique("Chicken Rice")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.50);
        when(foodController.validateFoodPrice(10.50)).thenReturn(true);
        when(inputHandler.readString(contains("type"))).thenReturn("S");
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "Chicken Rice", 10.50, "Set"));
        
        foodHandler.handleRegisterFood();
        
        verify(foodController).registerFood(any(Food.class));
        assertTrue(outputStream.toString().contains("Food Details"));
    }
    
    @Test
    @DisplayName("handleRegisterFood - User cancels at confirmation")
    void testHandleRegisterFood_UserCancelsAtConfirmation() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Chicken Rice");
        when(foodController.validateFoodName("Chicken Rice")).thenReturn(true);
        when(foodController.isFoodNameUnique("Chicken Rice")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.50);
        when(foodController.validateFoodPrice(10.50)).thenReturn(true);
        when(inputHandler.readString(contains("type"))).thenReturn("Set");
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(false);
        
        foodHandler.handleRegisterFood();
        
        verify(foodController, never()).registerFood(any(Food.class));
        assertTrue(outputStream.toString().contains("cancel"));
    }
    
    @Test
    @DisplayName("handleRegisterFood - Invalid name retries")
    void testHandleRegisterFood_InvalidNameRetries() {
        when(inputHandler.readString(contains("food name")))
                .thenReturn("Food123")
                .thenReturn("Chicken Rice");
        when(foodController.validateFoodName("Food123")).thenReturn(false);
        when(foodController.validateFoodName("Chicken Rice")).thenReturn(true);
        when(foodController.isFoodNameUnique("Chicken Rice")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.50);
        when(foodController.validateFoodPrice(10.50)).thenReturn(true);
        when(inputHandler.readString(contains("type"))).thenReturn("A");
        when(foodController.validateFoodType("A la carte")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "Chicken Rice", 10.50, "A la carte"));
        
        foodHandler.handleRegisterFood();
        
        verify(inputHandler, times(2)).readString(contains("food name"));
        assertTrue(outputStream.toString().contains("letters only"));
    }
    
    @Test
    @DisplayName("handleRegisterFood - Duplicate name retries")
    void testHandleRegisterFood_DuplicateNameRetries() {
        when(inputHandler.readString(contains("food name")))
                .thenReturn("Existing")
                .thenReturn("New Food");
        when(foodController.validateFoodName(anyString())).thenReturn(true);
        when(foodController.isFoodNameUnique("Existing")).thenReturn(false);
        when(foodController.isFoodNameUnique("New Food")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.50);
        when(foodController.validateFoodPrice(10.50)).thenReturn(true);
        when(inputHandler.readString(contains("type"))).thenReturn("s");
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "New Food", 10.50, "Set"));
        
        foodHandler.handleRegisterFood();
        
        verify(inputHandler, times(2)).readString(contains("food name"));
        assertTrue(outputStream.toString().contains("already exists"));
    }
    
    @Test
    @DisplayName("handleRegisterFood - Invalid price retries")
    void testHandleRegisterFood_InvalidPriceRetries() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Chicken Rice");
        when(foodController.validateFoodName("Chicken Rice")).thenReturn(true);
        when(foodController.isFoodNameUnique("Chicken Rice")).thenReturn(true);
        when(inputHandler.readDouble(contains("price")))
                .thenReturn(0.0)
                .thenReturn(10.50);
        when(foodController.validateFoodPrice(0.0)).thenReturn(false);
        when(foodController.validateFoodPrice(10.50)).thenReturn(true);
        when(inputHandler.readString(contains("type"))).thenReturn("Set");
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "Chicken Rice", 10.50, "Set"));
        
        foodHandler.handleRegisterFood();
        
        verify(inputHandler, times(2)).readDouble(contains("price"));
        assertTrue(outputStream.toString().contains("not able to be 0 or negative"));
    }
    
    @Test
    @DisplayName("handleRegisterFood - Invalid type retries")
    void testHandleRegisterFood_InvalidTypeRetries() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Chicken Rice");
        when(foodController.validateFoodName("Chicken Rice")).thenReturn(true);
        when(foodController.isFoodNameUnique("Chicken Rice")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.50);
        when(foodController.validateFoodPrice(10.50)).thenReturn(true);
        when(inputHandler.readString(contains("type")))
                .thenReturn("Invalid")
                .thenReturn("S");
        when(foodController.validateFoodType("Invalid")).thenReturn(false);
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "Chicken Rice", 10.50, "Set"));
        
        foodHandler.handleRegisterFood();
        
        verify(inputHandler, times(2)).readString(contains("type"));
        assertTrue(outputStream.toString().contains("S (Set) or A (A la carte)"));
    }
    
    @Test
    @DisplayName("handleRegisterFood - User cancels with X")
    void testHandleRegisterFood_UserCancelsWithX() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Chicken Rice");
        when(foodController.validateFoodName("Chicken Rice")).thenReturn(true);
        when(foodController.isFoodNameUnique("Chicken Rice")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenThrow(new UserCancelledException());
        
        foodHandler.handleRegisterFood();
        
        assertTrue(outputStream.toString().contains("cancelled"));
    }
    
    @Test
    @DisplayName("handleRegisterFood - Registration returns null")
    void testHandleRegisterFood_RegistrationReturnsNull() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Test");
        when(foodController.validateFoodName("Test")).thenReturn(true);
        when(foodController.isFoodNameUnique("Test")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.50);
        when(foodController.validateFoodPrice(10.50)).thenReturn(true);
        when(inputHandler.readString(contains("type"))).thenReturn("Set");
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(null);
        
        foodHandler.handleRegisterFood();
        
        verify(foodController).registerFood(any(Food.class));
    }
    
    // ============= handleEditFood Tests =============
    
    @Test
    @DisplayName("handleEditFood - Edit name successfully")
    void testHandleEditFood_EditNameSuccessfully() {
        Food food = new Food(2000, "Old Name", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice"))).thenReturn(1);
        when(inputHandler.readString(contains("Enter food name"))).thenReturn("New Name");
        when(foodController.validateFoodName("New Name")).thenReturn(true);
        when(foodController.isFoodNameUnique("New Name")).thenReturn(true);
        Food updatedFood = new Food(2000, "New Name", 10.50, "Set");
        when(foodController.updateFood(any(Food.class))).thenReturn(updatedFood);
        when(inputHandler.readString(contains("edit anything else"))).thenReturn("X");
        
        foodHandler.handleEditFood();
        
        verify(foodController).updateFood(any(Food.class));
        assertTrue(outputStream.toString().contains("updated successfully"));
    }
    
    @Test
    @DisplayName("handleEditFood - Edit price successfully")
    void testHandleEditFood_EditPriceSuccessfully() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice"))).thenReturn(2);
        when(inputHandler.readDouble(contains("Enter food price"))).thenReturn(12.50);
        when(foodController.validateFoodPrice(12.50)).thenReturn(true);
        Food updatedFood = new Food(2000, "Chicken Rice", 12.50, "Set");
        when(foodController.updateFood(any(Food.class))).thenReturn(updatedFood);
        when(inputHandler.readString(contains("edit anything else"))).thenReturn("X");
        
        foodHandler.handleEditFood();
        
        verify(foodController).updateFood(any(Food.class));
        String output = outputStream.toString();
        assertTrue(output.contains("updated successfully") || output.contains("12.5"));
    }
    
    @Test
    @DisplayName("handleEditFood - Edit type successfully")
    void testHandleEditFood_EditTypeSuccessfully() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice"))).thenReturn(3);
        when(inputHandler.readString(contains("Enter food type"))).thenReturn("A");
        when(foodController.validateFoodType("A la carte")).thenReturn(true);
        Food updatedFood = new Food(2000, "Chicken Rice", 10.50, "A la carte");
        when(foodController.updateFood(any(Food.class))).thenReturn(updatedFood);
        when(inputHandler.readString(contains("edit anything else"))).thenReturn("X");
        
        foodHandler.handleEditFood();
        
        verify(foodController).updateFood(any(Food.class));
        String output = outputStream.toString();
        assertTrue(output.contains("updated successfully") || output.contains("A la carte"));
    }
    
    @Test
    @DisplayName("handleEditFood - Invalid choice then valid")
    void testHandleEditFood_InvalidChoiceThenValid() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice"))).thenReturn(99);
        when(inputHandler.readString(contains("edit anything else"))).thenReturn("X");
        
        foodHandler.handleEditFood();
        
        assertTrue(outputStream.toString().contains("invalid"));
    }
    
    @Test
    @DisplayName("handleEditFood - Search by name")
    void testHandleEditFood_SearchByName() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("Chicken Rice");
        when(foodController.getFoodByName("Chicken Rice")).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice"))).thenReturn(1);
        when(inputHandler.readString(contains("Enter food name"))).thenReturn("Updated Name");
        when(foodController.validateFoodName("Updated Name")).thenReturn(true);
        when(foodController.isFoodNameUnique("Updated Name")).thenReturn(true);
        when(foodController.updateFood(any(Food.class))).thenReturn(food);
        when(inputHandler.readString(contains("edit anything else"))).thenReturn("X");
        
        foodHandler.handleEditFood();
        
        verify(foodController).getFoodByName("Chicken Rice");
    }
    
    @Test
    @DisplayName("handleEditFood - User cancels with X")
    void testHandleEditFood_UserCancelsWithX() {
        when(inputHandler.readString(contains("id or name to edit"))).thenThrow(new UserCancelledException());
        
        foodHandler.handleEditFood();
        
        assertTrue(outputStream.toString().contains("cancelled"));
    }
    
    @Test
    @DisplayName("handleEditFood - Update returns null")
    void testHandleEditFood_UpdateReturnsNull() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice"))).thenReturn(1);
        when(inputHandler.readString(contains("Enter food name"))).thenReturn("New Name");
        when(foodController.validateFoodName("New Name")).thenReturn(true);
        when(foodController.isFoodNameUnique("New Name")).thenReturn(true);
        when(foodController.updateFood(any(Food.class))).thenReturn(null);
        when(inputHandler.readString(contains("edit anything else"))).thenReturn("X");
        
        foodHandler.handleEditFood();
        
        verify(foodController).updateFood(any(Food.class));
    }
    
    // ============= handleDeleteFood Tests =============
    
    @Test
    @DisplayName("handleDeleteFood - User confirms deletion")
    void testHandleDeleteFood_UserConfirms() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("delete"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readYesNo(contains("sure want to delete"))).thenReturn(true);
        when(foodController.deleteFood(2000)).thenReturn(true);
        
        foodHandler.handleDeleteFood();
        
        verify(foodController).deleteFood(2000);
        assertTrue(outputStream.toString().contains("deleted successfully"));
    }
    
    @Test
    @DisplayName("handleDeleteFood - User cancels deletion")
    void testHandleDeleteFood_UserCancels() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("delete"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readYesNo(contains("sure want to delete"))).thenReturn(false);
        
        foodHandler.handleDeleteFood();
        
        verify(foodController, never()).deleteFood(anyInt());
        assertTrue(outputStream.toString().contains("cancel"));
    }
    
    @Test
    @DisplayName("handleDeleteFood - Search by name")
    void testHandleDeleteFood_SearchByName() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("delete"))).thenReturn("Chicken Rice");
        when(foodController.getFoodByName("Chicken Rice")).thenReturn(food);
        when(inputHandler.readYesNo(contains("sure want to delete"))).thenReturn(true);
        when(foodController.deleteFood(2000)).thenReturn(true);
        
        foodHandler.handleDeleteFood();
        
        verify(foodController).deleteFood(2000);
        assertTrue(outputStream.toString().contains("deleted successfully"));
    }
    
    @Test
    @DisplayName("handleDeleteFood - User cancels with X")
    void testHandleDeleteFood_UserCancelsWithX() {
        when(inputHandler.readString(contains("delete"))).thenThrow(new UserCancelledException());
        
        foodHandler.handleDeleteFood();
        
        assertTrue(outputStream.toString().contains("cancelled"));
    }
    
    @Test
    @DisplayName("handleDeleteFood - Delete returns false")
    void testHandleDeleteFood_DeleteReturnsFalse() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("delete"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readYesNo(contains("sure want to delete"))).thenReturn(true);
        when(foodController.deleteFood(2000)).thenReturn(false);
        
        foodHandler.handleDeleteFood();
        
        verify(foodController).deleteFood(2000);
    }
    
    // ============= handleDisplayAllFoods Tests =============
    
    @Test
    @DisplayName("handleDisplayAllFoods - Multiple foods")
    void testHandleDisplayAllFoods_MultipleFoods() {
        List<Food> foods = Arrays.asList(
            new Food(2000, "Food 1", 10.00, "Set"),
            new Food(2001, "Food 2", 15.00, "A la carte")
        );
        when(foodController.getAllFoods()).thenReturn(foods);
        when(inputHandler.readChar(contains("Press X"))).thenReturn('X');
        
        foodHandler.handleDisplayAllFoods();
        
        verify(foodController).getAllFoods();
    }
    
    @Test
    @DisplayName("handleDisplayAllFoods - Wait for X retries")
    void testHandleDisplayAllFoods_WaitForXRetries() {
        List<Food> foods = Arrays.asList(new Food(2000, "Food 1", 10.00, "Set"));
        when(foodController.getAllFoods()).thenReturn(foods);
        when(inputHandler.readChar(contains("Press X")))
                .thenReturn('a')
                .thenReturn('x');
        
        foodHandler.handleDisplayAllFoods();
        
        verify(inputHandler, times(2)).readChar(contains("Press X"));
    }
    
    @Test
    @DisplayName("handleDisplayAllFoods - Empty food list")
    void testHandleDisplayAllFoods_EmptyFoodList() {
        when(foodController.getAllFoods()).thenReturn(Arrays.asList());
        when(inputHandler.readChar(contains("Press X"))).thenReturn('X');
        
        foodHandler.handleDisplayAllFoods();
        
        verify(foodController).getAllFoods();
    }
    
    @Test
    @DisplayName("handleEditFood - Continue editing with lowercase x")
    void testHandleEditFood_ContinueEditingWithLowercaseX() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice"))).thenReturn(1);
        when(inputHandler.readString(contains("Enter food name"))).thenReturn("New Name");
        when(foodController.validateFoodName("New Name")).thenReturn(true);
        when(foodController.isFoodNameUnique("New Name")).thenReturn(true);
        when(foodController.updateFood(any(Food.class))).thenReturn(food);
        when(inputHandler.readString(contains("edit anything else"))).thenReturn("x");
        
        foodHandler.handleEditFood();
        
        verify(foodController).updateFood(any(Food.class));
    }
    
    @Test
    @DisplayName("handleEditFood - Continue editing loop twice")
    void testHandleEditFood_ContinueEditingLoopTwice() {
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("2000");
        when(foodController.getFoodById(2000)).thenReturn(food);
        when(inputHandler.readInt(contains("Select your choice")))
                .thenReturn(1)
                .thenReturn(2);
        when(inputHandler.readString(contains("Enter food name"))).thenReturn("New Name");
        when(foodController.validateFoodName("New Name")).thenReturn(true);
        when(foodController.isFoodNameUnique("New Name")).thenReturn(true);
        when(inputHandler.readDouble(contains("Enter food price"))).thenReturn(15.00);
        when(foodController.validateFoodPrice(15.00)).thenReturn(true);
        when(foodController.updateFood(any(Food.class))).thenReturn(food);
        when(inputHandler.readString(contains("edit anything else")))
                .thenReturn("")
                .thenReturn("X");
        
        foodHandler.handleEditFood();
        
        verify(inputHandler, times(2)).readInt(contains("Select your choice"));
    }
    
    @Test
    @DisplayName("convertFoodType - null input")
    void testConvertFoodType_NullInput() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Test");
        when(foodController.validateFoodName("Test")).thenReturn(true);
        when(foodController.isFoodNameUnique("Test")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.00);
        when(foodController.validateFoodPrice(10.00)).thenReturn(true);
        when(inputHandler.readString(contains("type")))
                .thenReturn(null)
                .thenReturn("Set");
        when(foodController.validateFoodType(null)).thenReturn(false);
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "Test", 10.00, "Set"));
        
        foodHandler.handleRegisterFood();
        
        verify(inputHandler, times(2)).readString(contains("type"));
    }
    
    @Test
    @DisplayName("convertFoodType - empty string input")
    void testConvertFoodType_EmptyStringInput() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Test");
        when(foodController.validateFoodName("Test")).thenReturn(true);
        when(foodController.isFoodNameUnique("Test")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.00);
        when(foodController.validateFoodPrice(10.00)).thenReturn(true);
        when(inputHandler.readString(contains("type")))
                .thenReturn("")
                .thenReturn("Set");
        when(foodController.validateFoodType("")).thenReturn(false);
        when(foodController.validateFoodType("Set")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "Test", 10.00, "Set"));
        
        foodHandler.handleRegisterFood();
        
        verify(inputHandler, times(2)).readString(contains("type"));
    }
    
    @Test
    @DisplayName("convertFoodType - lowercase a to A la carte")
    void testConvertFoodType_LowercaseA() {
        when(inputHandler.readString(contains("food name"))).thenReturn("Test");
        when(foodController.validateFoodName("Test")).thenReturn(true);
        when(foodController.isFoodNameUnique("Test")).thenReturn(true);
        when(inputHandler.readDouble(contains("price"))).thenReturn(10.00);
        when(foodController.validateFoodPrice(10.00)).thenReturn(true);
        when(inputHandler.readString(contains("type"))).thenReturn("a");
        when(foodController.validateFoodType("A la carte")).thenReturn(true);
        when(inputHandler.readYesNo(contains("proceed"))).thenReturn(true);
        when(foodController.registerFood(any(Food.class))).thenReturn(new Food(2000, "Test", 10.00, "A la carte"));
        
        foodHandler.handleRegisterFood();
        
        verify(foodController).registerFood(any(Food.class));
    }
    
    @Test
    @DisplayName("searchFoodByIdOrName - search by uppercase X throws exception")
    void testSearchFoodByIdOrName_UppercaseX() {
        when(inputHandler.readString(contains("id or name to edit"))).thenReturn("X");
        
        foodHandler.handleEditFood();
        
        assertTrue(outputStream.toString().contains("cancelled"));
    }
    
    @Test
    @DisplayName("handleDeleteFood - Search by name not found retries")
    void testHandleDeleteFood_SearchByNameNotFoundRetries() {
        when(inputHandler.readString(contains("delete")))
                .thenReturn("NonExistent")
                .thenReturn("Chicken Rice");
        when(foodController.getFoodByName("NonExistent")).thenReturn(null);
        Food food = new Food(2000, "Chicken Rice", 10.50, "Set");
        when(foodController.getFoodByName("Chicken Rice")).thenReturn(food);
        when(inputHandler.readYesNo(contains("sure want to delete"))).thenReturn(true);
        when(foodController.deleteFood(2000)).thenReturn(true);
        
        foodHandler.handleDeleteFood();
        
        verify(inputHandler, times(2)).readString(contains("delete"));
        assertTrue(outputStream.toString().contains("Unable to find"));
    }
}
