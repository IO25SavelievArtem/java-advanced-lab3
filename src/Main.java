/**
 * The Main class serves as the entry point of the application.
 * It demonstrates the creation of User, Product, and Order objects,
 * generates SQL queries using SqlGenerator, and measures execution times
 * with and without reflection.
 */
public class Main {

    /**
     * The main method where the program execution begins.
     *
     * @param args Command-line arguments
     * @throws IllegalAccessException if reflection access fails
     */
    public static void main(String[] args) throws IllegalAccessException {
        User user = new User(1, "John Doe", 30);
        Product product = new Product(101, "Laptop", 1500.0);
        Order order = new Order(5001, 1, 1500.0);

        SqlGenerator<User> userGenerator = new SqlGenerator<>(User.class);
        SqlGenerator<Product> productGenerator = new SqlGenerator<>(Product.class);
        SqlGenerator<Order> orderGenerator = new SqlGenerator<>(Order.class);

        // Generate SQL queries
        System.out.println(userGenerator.create());
        System.out.println(userGenerator.insert(user));
        System.out.println(userGenerator.update(user, "id = 1"));
        System.out.println(userGenerator.delete("id = 1"));

        System.out.println(productGenerator.create());
        System.out.println(productGenerator.insert(product));

        System.out.println(orderGenerator.create());
        System.out.println(orderGenerator.insert(order));

        // Measure execution time with reflection
        long startReflection = System.nanoTime();
        System.out.println(userGenerator.insert(user));
        long endReflection = System.nanoTime();

        // Measure execution time without reflection
        long startWithoutReflection = System.nanoTime();
        String manualInsert = "INSERT INTO users (id, name, age) VALUES (1, 'John Doe', 30);";
        long endWithoutReflection = System.nanoTime();

        System.out.println("Reflection time: " + (endReflection - startReflection) + " ns");
        System.out.println("Manual time: " + (endWithoutReflection - startWithoutReflection) + " ns");
    }
}