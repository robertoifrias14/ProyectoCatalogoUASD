import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL =
        "jdbc:mysql://localhost:3306/uasd_catalogo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    // La contraseña se lee de una variable de entorno para no subirla a GitHub
    private static final String PASSWORD =
        System.getenv().getOrDefault("DB_PASSWORD", "cambia_esto");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}