package presentation.Food;

/**
 * Admin menu options related to Food management.
 */
public enum FoodAdminOption {
    REGISTER_FOOD(1, "Register New Food"),
    EDIT_FOOD(2, "Edit Food"),
    DELETE_FOOD(3, "Delete Food"),
    VIEW_ALL_FOOD(4, "View All Food");

    private final int code;
    private final String label;

    FoodAdminOption(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static FoodAdminOption fromCode(int code) {
        for (FoodAdminOption opt : values()) {
            if (opt.code == code) {
                return opt;
            }
        }
        return null;
    }
}

