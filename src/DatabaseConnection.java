import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static DatabaseConnection instance;

    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/studentAndClass";
    private final String userName = "root";
    private final String password = "Aa123456";

    private DatabaseConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url,userName,password);

        }catch (ClassNotFoundException exception){
            System.out.println("database'e bağlantı gerçekleştirilirken hata meydana geldi: " + exception.getMessage());
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException{
        if(instance == null || instance.getConnection().isClosed()){
            instance = new DatabaseConnection();
        }

        return instance;
    }
}
