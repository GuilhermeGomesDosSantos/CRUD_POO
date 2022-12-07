import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteControl {
	private StringProperty nome = new SimpleStringProperty("");
	private IntegerProperty idade = new SimpleIntegerProperty(0);
	private IntegerProperty CNH = new SimpleIntegerProperty(0);
	private StringProperty instagram = new SimpleStringProperty("");
	
	private ClienteDAO timeDao = new ClienteDAOImpl();
	
	private boolean editando = false;
	private String nomeAntigo = null;
	
	private ObservableList<Cliente> lista = 
			FXCollections.observableArrayList();
	
	public Cliente getEntity() { 
		Cliente c = new Cliente();
		c.setNome(nome.get());
		c.setIdada(idade.get());
		c.setCNH(CNH.get());
		c.setInstagram(instagram.get());
		return c;
	}
	
	public void setEntity(Cliente c) { 
		nome.set( c.getNome() );
		idade.set( c.getIdade() );
		CNH.set( c.getCNH() );
		instagram.set( c.getInstagram() );
	}
	
	public void editar() { 
		this.editando = true;
		this.nomeAntigo = nome.get();
	}
	
	public void salvar() {
		Cliente c = getEntity();
		if (this.editando) {
			timeDao.atualizar(nomeAntigo, c);
		} else { 
			timeDao.criar(c);
		}
	}
	
	public void limpar() { 
		nome.set("");
		idade.set(0);
		CNH.set(0);
		instagram.set("");
		this.editando = false;
		this.nomeAntigo = null;
	}
	
	public void apagar(Cliente c) { 
		timeDao.apagar(c);
	}
		
	public void pesquisar() { 
		List<Cliente> tempLista = timeDao.pesquisarPorNome( nome.get() );
		lista.clear();
		lista.addAll(tempLista);
	}
	
	public StringProperty nomeProperty() {
		return nome;
	}
	
	public IntegerProperty idadeProperty() {
		return idade;
	}
	
	public IntegerProperty CNHProperty() {
		return CNH;
	}
	
	public StringProperty instagramProperty() {
		return instagram;
	}

	public ObservableList<Cliente> getLista() {
		return lista;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public String getNomeAntigo() {
		return nomeAntigo;
	}

	public void setNomeAntigo(String nomeAntigo) {
		this.nomeAntigo = nomeAntigo;
	}
}