import javax.persistence.Table;
import javax.persistence.Column;
import java.lang.reflect.Field;

/**
 * A generic SQL generator for creating, inserting, updating, and deleting records
 * in a database table based on Java classes annotated with {@link Table} and {@link Column}.
 *
 * <p>This class uses reflection to generate SQL statements dynamically.
 *
 * @param <T> the type of the object for which SQL statements are generated.
 */
public class SqlGenerator<T> {
    /** The class type to be processed. */
    private final Class<T> type;

    /**
     * Constructs a new {@code SqlGenerator} instance for the specified class type.
     *
     * @param type the class type annotated with {@link Table}.
     */
    public SqlGenerator(Class<T> type) {
        this.type = type;
    }

    /**
     * Generates a SQL "CREATE TABLE" statement based on the annotated fields of the class.
     *
     * @return the SQL "CREATE TABLE" statement as a string.
     * @throws IllegalArgumentException if the class is not annotated with {@link Table}.
     */
    public String generateCreate() {
        if (!type.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Table");
        }

        StringBuilder sb = new StringBuilder();
        String tableName = type.getAnnotation(Table.class).name();
        sb.append("CREATE TABLE ").append(tableName).append(" (");

        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                String columnName = field.getAnnotation(Column.class).name();
                String columnType = mapJavaTypeToSqlType(field.getType());
                sb.append(columnName).append(" ").append(columnType).append(", ");
            }
        }
        sb.delete(sb.length() - 2, sb.length()); // Removes the trailing comma
        sb.append(");");

        return sb.toString();
    }

    /**
     * Generates a SQL "INSERT INTO" statement for a given instance of the class.
     *
     * @param instance the object containing the data to be inserted.
     * @return the SQL "INSERT INTO" statement as a string.
     * @throws IllegalAccessException if a field is inaccessible during reflection.
     * @throws IllegalArgumentException if the class is not annotated with {@link Table}.
     */
    public String generateInsert(T instance) throws IllegalAccessException {
        if (!type.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Table");
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder values = new StringBuilder();
        String tableName = type.getAnnotation(Table.class).name();
        sb.append("INSERT INTO ").append(tableName).append(" (");

        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                String columnName = field.getAnnotation(Column.class).name();
                sb.append(columnName).append(", ");
                values.append("'").append(field.get(instance)).append("', ");
            }
        }

        sb.delete(sb.length() - 2, sb.length()).append(") VALUES (");
        values.delete(values.length() - 2, values.length()).append(");");
        sb.append(values);

        return sb.toString();
    }

    /**
     * Generates a SQL "UPDATE" statement for a given instance of the class with a specified condition.
     *
     * @param instance the object containing the data to update.
     * @param condition the condition for updating rows (e.g., "id = 1").
     * @return the SQL "UPDATE" statement as a string.
     * @throws IllegalAccessException if a field is inaccessible during reflection.
     * @throws IllegalArgumentException if the class is not annotated with {@link Table}.
     */
    public String generateUpdate(T instance, String condition) throws IllegalAccessException {
        if (!type.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Table");
        }

        StringBuilder sb = new StringBuilder();
        String tableName = type.getAnnotation(Table.class).name();
        sb.append("UPDATE ").append(tableName).append(" SET ");

        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                String columnName = field.getAnnotation(Column.class).name();
                sb.append(columnName).append(" = '").append(field.get(instance)).append("', ");
            }
        }

        sb.delete(sb.length() - 2, sb.length()); // Removes the trailing comma
        sb.append(" WHERE ").append(condition).append(";");

        return sb.toString();
    }

    /**
     * Generates a SQL "DELETE FROM" statement with a specified condition.
     *
     * @param condition the condition for deleting rows (e.g., "id = 1").
     * @return the SQL "DELETE FROM" statement as a string.
     * @throws IllegalArgumentException if the class is not annotated with {@link Table}.
     */
    public String generateDelete(String condition) {
        if (!type.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Table");
        }

        String tableName = type.getAnnotation(Table.class).name();
        return "DELETE FROM " + tableName + " WHERE " + condition + ";";
    }

    /**
     * Maps a Java data type to its corresponding SQL data type.
     *
     * @param javaType the Java data type.
     * @return the corresponding SQL data type as a string.
     * @throws IllegalArgumentException if the Java type is unsupported.
     */
    private String mapJavaTypeToSqlType(Class<?> javaType) {
        if (javaType == int.class || javaType == Integer.class) {
            return "INTEGER";
        } else if (javaType == String.class) {
            return "TEXT";
        } else if (javaType == double.class || javaType == Double.class) {
            return "REAL";
        } else {
            throw new IllegalArgumentException("Unsupported Java type: " + javaType.getName());
        }
    }
}