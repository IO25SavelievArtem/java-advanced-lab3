import javax.persistence.Table;
import javax.persistence.Column;

/**
 * Represents the Product entity mapped to the "products" table in the database.
 */
@Table(name = "products")
class Product {

    /** The unique identifier for the product. */
    @Column(name = "id")
    private int id;

    /** The title of the product. */
    @Column(name = "title")
    private String title;

    /** The price of the product. */
    @Column(name = "price")
    private double price;

    /**
     * Constructs a new Product with the specified ID, title, and price.
     *
     * @param id    The product's ID.
     * @param title The product's title.
     * @param price The product's price.
     */
    public Product(int id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }
}