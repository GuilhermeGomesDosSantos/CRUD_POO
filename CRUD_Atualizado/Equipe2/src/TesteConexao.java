import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TesteConexao {
	
	public final static String URL = "jdbc:mariadb://localhost:3306/Consulta";
	public final static String USER = "root";
	public final static String PASS = "123456";
	
	public static void main(String[] args) throws ClassNotFoundException{
		Class.forName("org.mariadb.jdbc.Driver");
		System.out.println("Classe Carregada");
		try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
			
			System.out.println("Conectado no BD");
		} catch (SQLException e) {
	e.printStackTrace();
		}
		} 
	}