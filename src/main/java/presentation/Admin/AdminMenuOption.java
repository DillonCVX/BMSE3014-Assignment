package presentation.Admin;

/**
 * Admin Menu Option Enum
 * Represents admin menu options with their display text
 * Follows OOP principles: Encapsulation
 */
public enum AdminMenuOption {
    FOOD_MANAGEMENT(1, "Food Management"),
    ORDER_REPORT(2, "Order Report"),
    BACK_MAIN_MENU(0, "Back Main Menu");

    private final int optionNumber;
    private final String displayText;

    /**
     * Constructor for AdminMenuOption
     * 
     * @param optionNumber The option number
     * @param displayText  The display text for the option
     */
    AdminMenuOption(int optionNumber, String displayText) {
        this.optionNumber = optionNumber;
        this.displayText = displayText;
    }

    /**
     * Get the option number
     * 
     * @return The option number
     */
    public int getOptionNumber() {
        return optionNumber;
    }

    /**
     * Get the display text
     * 
     * @return The display text
     */
    public String getDisplayText() {
        return displayText;
    }

    /**
     * Display the admin menu using enum values
     */
    public static void displayMenu() {
        
        String exitOption = "0.Back Main Menu";

        System.out.println("\n[]===============================[]");
        System.out.println("[]             Admin             []");
        System.out.println("[]===============================[]");

        // Display options in order: 1-2, then 0
        for (AdminMenuOption option : AdminMenuOption.values()) {
            if (option.getOptionNumber() != 0) {
                // Build option text
                String optionText = option.getOptionNumber() + "." + option.getDisplayText();
                String formattedLine = String.format("        %-25s", optionText);
                System.out.println(formattedLine);
            }
        }
        String exitLine = String.format("        %-25s", exitOption);
        System.out.println(exitLine);
        System.out.println("[]===============================[]\n");
    }

    /**
     * Get AdminMenuOption by option number
     * 
     * @param optionNumber The option number
     * @return AdminMenuOption or null if not found
     */
    public static AdminMenuOption getByOptionNumber(int optionNumber) {
        for (AdminMenuOption option : AdminMenuOption.values()) {
            if (option.getOptionNumber() == optionNumber) {
                return option;
            }
        }
        return null;
    }
}
