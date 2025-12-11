package presentation.Order;

import java.util.List;

import model.Food;
import model.OrderDetails;

/**
 * Order menu display utilities
 * Provides simple, presentation-only methods used by order flows
 */
public final class OrderMenuDisplay {

    private OrderMenuDisplay() {}

    /**
     * Display the selectable food menu used during ordering (index, name, price).
     */
    public static void displayOrderMenu(List<Food> foods) {
        System.out.println("============================ []Order Menu[] ======================");
        int index = 1;
        for (Food food : foods) {
            System.out.printf("%3d. %-30s RM %6.2f%n", index++, food.getFoodName(), food.getFoodPrice());
        }
        System.out.println("  0. Exit Order");
        System.out.println("==============================================================");
    }

    /**
     * Display a short order summary (line items + subtotal)
     */
    public static void displayOrderSummary(List<OrderDetails> details) {
        System.out.println("==================== Order Summary ====================");
        System.out.printf("%-6s %-25s %8s %6s %12s%n", "ID", "Name", "Unit", "Qty", "Subtotal");
        double total = 0.0;
        for (OrderDetails d : details) {
            if (d == null || d.getFood() == null) continue;
            System.out.printf("%6d %-25s RM %6.2f %4d RM %8.2f%n",
                    d.getFood().getFoodId(), d.getFood().getFoodName(), d.getUnitPriceDecimal().doubleValue(), d.getQuantity(), d.getSubtotal());
            total += d.getSubtotal();
        }
        System.out.println("-------------------------------------------------------");
        System.out.printf("%45s RM %8.2f%n", "Total:", total);
        System.out.println("=======================================================\n");
    }
}
