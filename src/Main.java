/**
 * Main class to demonstrate CRUD SQL generation using annotated Java objects.
 */
public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        // Sample data
        User user = new User(1, "John Doe", 30);
        Product product = new Product(101, "Laptop", 1500.0);
        Order order = new Order(5001, 1, 1500.0);

        // Using specific generators
        UserSQLGenerator userGenerator = new UserSQLGenerator();
        ProductSQLGenerator productGenerator = new ProductSQLGenerator();
        OrderSQLGenerator orderGenerator = new OrderSQLGenerator();

        // Demonstrate CRUD generation
        System.out.println("User Table SQL:");
        System.out.println(userGenerator.generateCreate());
        System.out.println(userGenerator.generateInsert(user));
        System.out.println(userGenerator.generateUpdate(user, "id = 1"));
        System.out.println(userGenerator.generateDelete("id = 1"));
        System.out.println();

        System.out.println("Product Table SQL:");
        System.out.println(productGenerator.generateCreate());
        System.out.println(productGenerator.generateInsert(product));
        System.out.println(productGenerator.generateUpdate(product, "id = 101"));
        System.out.println(productGenerator.generateDelete("id = 101"));
        System.out.println();

        System.out.println("Order Table SQL:");
        System.out.println(orderGenerator.generateCreate());
        System.out.println(orderGenerator.generateInsert(order));
        System.out.println(orderGenerator.generateUpdate(order, "id = 5001"));
        System.out.println(orderGenerator.generateDelete("id = 5001"));
        System.out.println();


    }
}