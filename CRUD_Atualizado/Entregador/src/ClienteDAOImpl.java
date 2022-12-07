import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ClienteDAOImpl implements ClienteDAO {
	public final static String URL = 
			"jdbc:mariadb://localhost:3306/Consulta";
	public final static String USER = "root";
	public final static String PASS = "123456";
	private Connection con;
	
	public ClienteDAOImpl() { 
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
			
		} catch(ClassNotFoundException | SQLException e) { 
			e.printStackTrace();
		}
	}
	
	@Override
	public void criar(Cliente c) {	
		String sql = "INSERT INTO Info_Entregador " +
				"(nome, idade, CNH, instagram) " +
				"VALUES ('" + c.getNome() + 
				"', " + c.getIdade() + 
				", '" + c.getCNH() +
				"', '" + c.getInstagram() + "')";
		try { 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> pesquisarPorNome(String nome) {
		List<Cliente> lista = new ArrayList<>();
		String sql = "SELECT * FROM Info_Entregador "
				+ "WHERE nome LIKE '%" + nome + "%'";
		try { 
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) { 
				Cliente c = new Cliente();
				c.setNome(rs.getString("nome"));
				c.setIdada(rs.getInt("idade"));
				c.setCNH(rs.getInt("CNH"));
				c.setInstagram(rs.getString("instagram"));
				lista.add(c);
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return lista;
	}
	
	public void apagar(Cliente c) { 
		String sql = "DELETE FROM Info_Entregador " +
				"WHERE nome = '" + c.getNome() + "'";
		try { 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	public void atualizar(String nomeAntigo, Cliente c) { 
		String sql = "UPDATE Info_Entregador " +
				" SET nome = '" + c.getNome() + 
				"', idade = " + c.getIdade() + 
				", CNH = '" + c.getCNH() +
				"', instagram = '" + c.getInstagram() + 
				"' WHERE nome = '" + nomeAntigo + "'";
		try { 
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}

}