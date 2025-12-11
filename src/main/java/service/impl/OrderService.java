package service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import model.*;
import repository.interfaces.ICustomerRepository;
import repository.interfaces.IOrderRepository;
import repository.interfaces.IPaymentMethodRepository;
import service.interfaces.IOrderService;
import service.interfaces.IPaymentService;

/**
 * Order Service Implementation
 * Contains business logic for order operations
 * Follows SOLID: Single Responsibility Principle, Dependency Inversion Principle
 */
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;
    private final IPaymentMethodRepository paymentMethodRepository;
    private final IPaymentService paymentService;

    public OrderService(IOrderRepository orderRepository, 
                            ICustomerRepository customerRepository, 
                            IPaymentMethodRepository paymentMethodRepository, 
                            IPaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentService = paymentService;
    }

    @Override
    public Order createOrder(int customerId, List<OrderDetails> orderDetailsList, 
                             String paymentType, String cardNumber, String expiryDate) throws IllegalArgumentException {
        // Validate customer exists
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }
        Customer customer = customerOpt.get();

        // Validate order details
        if (orderDetailsList == null || orderDetailsList.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        // Recompute authoritative total using BigDecimal and validate details
        BigDecimal computedTotal = BigDecimal.ZERO;
        for (OrderDetails detail : orderDetailsList) {
            if (detail == null)
                throw new IllegalArgumentException("Order detail cannot be null");

            if (detail.getQuantity() <= 0)
                throw new IllegalArgumentException("Quantity must be > 0");

            if (detail.getQuantity() > 100)
                throw new IllegalArgumentException(
                    "Quantity too large for item: " + detail.getFood().getFoodId()
                );

            BigDecimal unit = detail.getUnitPriceDecimal();
            if (unit == null)
                throw new IllegalArgumentException("Unit price missing");

            BigDecimal expectedSubtotal = unit.multiply(BigDecimal.valueOf(detail.getQuantity()))
                                              .setScale(2, RoundingMode.HALF_UP);

            BigDecimal actualSubtotal = detail.getSubtotalDecimal() != null
                                        ? detail.getSubtotalDecimal().setScale(2, RoundingMode.HALF_UP)
                                        : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

            if (expectedSubtotal.compareTo(actualSubtotal) != 0) {
                throw new IllegalArgumentException(
                    "Order detail subtotal mismatch for item: " +
                    (detail.getFood() != null ? detail.getFood().getFoodId() : "unknown")
                );
            }

            computedTotal = computedTotal.add(expectedSubtotal);
        }

        computedTotal = computedTotal.setScale(2, RoundingMode.HALF_UP);
        double totalPrice = computedTotal.doubleValue();

        // Get payment method
        Optional<PaymentMethod> paymentMethodOpt = paymentMethodRepository
                .findByCustomerIdAndType(customerId, paymentType);
        if (paymentMethodOpt.isEmpty()) {
            throw new IllegalArgumentException("Payment method not found");
        }
        PaymentMethod paymentMethod = paymentMethodOpt.get();

        // Process payment
        try {
            paymentService.processPayment(customerId, paymentType, totalPrice, cardNumber, expiryDate);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Payment failed: " + e.getMessage());
        }

        // Create order using Builder pattern (keeps construction logic centralized)
        Order order = new Order.Builder()
                        .orderDate(new Date())
                        .customer(customer)
                        .orderDetails(orderDetailsList)
                        .totalPrice(BigDecimal.valueOf(totalPrice))
                        .paymentMethod(paymentMethod)
                        .status("COMPLETED")
                        .build();

        // Save order
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public double calculateTotalPrice(List<OrderDetails> orderDetailsList) {
        double total = 0.0;
        for (OrderDetails detail : orderDetailsList) {
            total += detail.getSubtotal();
        }
        return total;
    }

    // Builder moved to model.Order for reuse; no internal builder here.
}
