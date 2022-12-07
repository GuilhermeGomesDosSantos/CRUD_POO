import java.util.List;

public interface ClienteDAO {
	
	void criar(Cliente c);
	List<Cliente> pesquisarPorNome(String nome);
	void apagar(Cliente c);
	void atualizar(String nomeAntigo, Cliente c);

}