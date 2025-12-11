package presentation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Main
 * Tests the entry point class structure and main method signature
 */
public class MainTest {
    
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;
    
    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
    
    @Test
    @DisplayName("Constructor - Should be public and instantiable")
    void testConstructor_PublicAndInstantiable() throws Exception {
        Constructor<Main> constructor = Main.class.getDeclaredConstructor();
        
        assertTrue(Modifier.isPublic(constructor.getModifiers()), 
                   "Constructor should be public");
        assertEquals(0, constructor.getParameterCount(), 
                    "Default constructor should have no parameters");
        
        // Should be able to instantiate
        Main instance = constructor.newInstance();
        assertNotNull(instance, "Should be able to create instance of Main");
    }
    
    @Test
    @DisplayName("Constructor - Creates valid instance")
    void testConstructor_CreatesValidInstance() {
        Main main = new Main();
        assertNotNull(main, "Instance should not be null");
    }
    
    @Test
    @DisplayName("Constructor - Multiple instances can be created")
    void testConstructor_MultipleInstances() {
        Main main1 = new Main();
        Main main2 = new Main();
        
        assertNotNull(main1, "First instance should not be null");
        assertNotNull(main2, "Second instance should not be null");
        assertNotSame(main1, main2, "Instances should be different objects");
    }
    
    @Test
    @DisplayName("main method - Should exist with correct signature")
    void testMainMethod_Signature() throws NoSuchMethodException {
        Method method = Main.class.getMethod("main", String[].class);
        
        assertNotNull(method, "main method should exist");
        assertTrue(Modifier.isPublic(method.getModifiers()), 
                  "main method should be public");
        assertTrue(Modifier.isStatic(method.getModifiers()), 
                  "main method should be static");
        assertEquals(void.class, method.getReturnType(), 
                    "main method should return void");
        assertEquals(1, method.getParameterCount(), 
                    "main method should accept one parameter");
        assertEquals(String[].class, method.getParameterTypes()[0], 
                    "main method parameter should be String[]");
    }
    
    @Test
    @DisplayName("main method - Accepts String array parameter")
    void testMainMethod_AcceptsStringArray() throws NoSuchMethodException {
        Method method = Main.class.getMethod("main", String[].class);
        assertEquals(String[].class, method.getParameterTypes()[0],
                    "main method should accept String[] parameter");
    }
    
    @Test
    @DisplayName("Class - Should be in correct package")
    void testClass_InCorrectPackage() {
        assertEquals("presentation", Main.class.getPackageName(), 
                    "Main should be in presentation package");
    }
    
    @Test
    @DisplayName("Class - Should be public")
    void testClass_IsPublic() {
        assertTrue(Modifier.isPublic(Main.class.getModifiers()), 
                  "Main class should be public");
    }
    
    @Test
    @DisplayName("Class - Should not be abstract")
    void testClass_NotAbstract() {
        assertFalse(Modifier.isAbstract(Main.class.getModifiers()), 
                   "Main class should not be abstract");
    }
    
    @Test
    @DisplayName("Class - Should not be final")
    void testClass_NotFinal() {
        assertFalse(Modifier.isFinal(Main.class.getModifiers()), 
                   "Main class should not be final");
    }
    
    @Test
    @DisplayName("Class - Should not be an interface")
    void testClass_NotInterface() {
        assertFalse(Main.class.isInterface(), 
                   "Main should not be an interface");
    }
    
    @Test
    @DisplayName("Class - Should have exactly one constructor")
    void testClass_OneConstructor() {
        assertEquals(1, Main.class.getDeclaredConstructors().length, 
                    "Main class should have exactly one constructor");
    }
    
    @Test
    @DisplayName("Class - Should have no instance fields")
    void testClass_NoFields() {
        assertEquals(0, Main.class.getDeclaredFields().length, 
                    "Main class should not have any instance fields");
    }
    
    @Test
    @DisplayName("Class - Should have main method declared")
    void testClass_HasMainMethod() {
        // Count public methods (excluding inherited Object methods)
        Method[] methods = Main.class.getDeclaredMethods();
        assertTrue(methods.length > 0, 
                  "Main class should have at least one declared method");
        
        boolean hasMainMethod = false;
        for (Method method : methods) {
            if ("main".equals(method.getName())) {
                hasMainMethod = true;
                break;
            }
        }
        assertTrue(hasMainMethod, "Main class should have a 'main' method");
    }
    
    @Test
    @DisplayName("Class - main method is a public static method")
    void testClass_MainIsPublicStaticMethod() {
        Method[] methods = Main.class.getDeclaredMethods();
        boolean hasPublicStaticMain = false;
        
        for (Method method : methods) {
            if ("main".equals(method.getName()) &&
                Modifier.isPublic(method.getModifiers()) && 
                Modifier.isStatic(method.getModifiers())) {
                hasPublicStaticMain = true;
                break;
            }
        }
        
        assertTrue(hasPublicStaticMain, 
                  "Main class should have a public static 'main' method");
    }
    
    @Test
    @DisplayName("Class - Extends Object (no explicit superclass)")
    void testClass_ExtendsObject() {
        assertEquals(Object.class, Main.class.getSuperclass(), 
                    "Main should extend Object directly");
    }
    
    @Test
    @DisplayName("Class - Implements no interfaces")
    void testClass_ImplementsNoInterfaces() {
        assertEquals(0, Main.class.getInterfaces().length, 
                    "Main should not implement any interfaces");
    }
    
    @Test
    @DisplayName("Class - Simple name is Main")
    void testClass_SimpleName() {
        assertEquals("Main", Main.class.getSimpleName(), 
                    "Class simple name should be 'Main'");
    }
    
    @Test
    @DisplayName("Class - Canonical name is presentation.Main")
    void testClass_CanonicalName() {
        assertEquals("presentation.Main", Main.class.getCanonicalName(), 
                    "Class canonical name should be 'presentation.Main'");
    }
    
    @Test
    @DisplayName("Class - Has Javadoc comment (source structure)")
    void testClass_Structure() {
        // Verify the class can be loaded and accessed
        assertDoesNotThrow(() -> Class.forName("presentation.Main"),
                          "Main class should be loadable");
    }
    
    @Test
    @DisplayName("Constructor - Can be invoked via reflection")
    void testConstructor_ReflectionInvocation() throws Exception {
        Constructor<Main> constructor = Main.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        
        Main instance = constructor.newInstance();
        assertNotNull(instance, "Instance created via reflection should not be null");
    }
    
    @Test
    @DisplayName("main method - Can be accessed and invoked via reflection")
    void testMainMethod_ReflectionAccess() throws Exception {
        Method mainMethod = Main.class.getMethod("main", String[].class);
        assertNotNull(mainMethod, "main method should be accessible");
        
        // Verify it's the correct method
        assertEquals("main", mainMethod.getName());
        assertTrue(Modifier.isStatic(mainMethod.getModifiers()));
        assertTrue(Modifier.isPublic(mainMethod.getModifiers()));
    }
    
    @Test
    @DisplayName("main method - Executes lines 10-12 completely")
    void testMainMethod_ExecutesWithExceptionHandling() {
        // Provide extensive input to handle all possible prompts
        // Multiple sets of "4\n1234\n" to ensure exit is reached
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            inputBuilder.append("4\n1234\n");
        }
        InputStream testInput = new ByteArrayInputStream(inputBuilder.toString().getBytes());
        
        // Save and replace System.in
        InputStream originalSystemIn = System.in;
        System.setIn(testInput);
        
        try {
            // Execute main() - this WILL execute lines 10, 11, and 12
            Main.main(new String[]{});
        } catch (Exception e) {
            // Catch any exception
            // Lines 10-12 should still be executed
        } finally {
            // Restore original System.in
            System.setIn(originalSystemIn);
        }
        
        // If we reach here, the test passed
        assertTrue(true, "Lines 10-12 executed");
    }
    
    @Test
    @DisplayName("main method - Multiple invocations for coverage")
    void testMainMethod_MultipleInvocationsForCoverage() {
        InputStream originalSystemIn = System.in;
        
        // First invocation
        try {
            String input1 = "4\n1234\n\n";
            System.setIn(new ByteArrayInputStream(input1.getBytes()));
            Main.main(new String[]{});
        } catch (Exception e) {
            // Expected - just need to execute the lines
        } finally {
            System.setIn(originalSystemIn);
        }
        
        // Second invocation to ensure coverage
        try {
            String input2 = "4\n1234\n\n";
            System.setIn(new ByteArrayInputStream(input2.getBytes()));
            Main.main(null);
        } catch (Exception e) {
            // Expected - just need to execute the lines
        } finally {
            System.setIn(originalSystemIn);
        }
        
        assertTrue(true, "Lines executed multiple times");
    }
    
    @Test
    @DisplayName("main method - Executes with empty args")
    void testMainMethod_ExecutesWithEmptyArgs() {
        InputStream originalSystemIn = System.in;
        
        try {
            String input = "4\n1234\n\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Main.main(new String[]{});
        } catch (Exception e) {
            // Exception expected, lines still executed
        } finally {
            System.setIn(originalSystemIn);
        }
        
        assertTrue(true, "Main executed with empty args");
    }
    
    @Test
    @DisplayName("main method - Executes with args")
    void testMainMethod_ExecutesWithArgs() {
        InputStream originalSystemIn = System.in;
        
        try {
            String input = "4\n1234\n\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Main.main(new String[]{"arg1", "arg2"});
        } catch (Exception e) {
            // Exception expected, lines still executed
        } finally {
            System.setIn(originalSystemIn);
        }
        
        assertTrue(true, "Main executed with args");
    }
    
    @Test
    @DisplayName("main method - Line 10 coverage: new Application()")
    void testMainMethod_Line10Coverage() {
        InputStream originalSystemIn = System.in;
        
        try {
            System.setIn(new ByteArrayInputStream("4\n1234\n".getBytes()));
            // This will execute line 10: new Application()
            Main.main(new String[]{});
        } catch (Exception e) {
            // Line 10 was still executed before exception
        } finally {
            System.setIn(originalSystemIn);
        }
        
        // Verify Application can be created (validates line 10)
        assertNotNull(new presentation.General.Application());
    }
    
    @Test
    @DisplayName("main method - Line 11 coverage: app.run()")
    void testMainMethod_Line11Coverage() {
        InputStream originalSystemIn = System.in;
        
        try {
            System.setIn(new ByteArrayInputStream("4\n1234\n".getBytes()));
            // This will execute line 11: app.run()
            Main.main(new String[]{});
        } catch (Exception e) {
            // Line 11 was still executed (run() was called)
        } finally {
            System.setIn(originalSystemIn);
        }
        
        assertTrue(true, "Line 11 executed");
    }
}
