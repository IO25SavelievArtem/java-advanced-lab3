import javax.persistence.Table;
import javax.persistence.Column;

/**
 * Represents the User entity mapped to the "users" table in the database.
 */
@Table(name = "users")
class User {

    /** The unique identifier for the user. */
    @Column(name = "id")
    private int id;

    /** The name of the user. */
    @Column(name = "name")
    private String name;

    /** The age of the user. */
    @Column(name = "age")
    private int age;

    /**
     * Constructs a new User with the specified ID, name, and age.
     *
     * @param id   The user's ID.
     * @param name The user's name.
     * @param age  The user's age.
     */
    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}