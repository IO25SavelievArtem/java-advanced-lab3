import javax.persistence.Table;
import javax.persistence.Column;

/**
 * Represents the Order entity mapped to the "orders" table in the database.
 */
@Table(name = "orders")
class Order {

    /** The unique identifier for the order. */
    @Column(name = "order_id")
    private int orderId;

    /** The unique identifier for the user who placed the order. */
    @Column(name = "user_id")
    private int userId;

    /** The total amount of the order. */
    @Column(name = "amount")
    private double amount;

    /**
     * Constructs a new Order with the specified order ID, user ID, and amount.
     *
     * @param orderId The order's ID.
     * @param userId  The user ID associated with the order.
     * @param amount  The total amount for the order.
     */
    public Order(int orderId, int userId, double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }
}