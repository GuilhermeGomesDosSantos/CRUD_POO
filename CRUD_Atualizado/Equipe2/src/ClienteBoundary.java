import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class ClienteBoundary extends Application {
	private TextField txtNome = new TextField("");
	private TextField txtIdade = new TextField("");
	private TextField txtApelido = new TextField("");
	private TextField txtInstagram = new TextField("");

	private ClienteControl control = new ClienteControl();

	private Button btnSalvar = new Button("Salvar");
	private Button btnPesquisar = new Button("Pesquisar");

	private TableView<Cliente> table = new TableView<>();

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		GridPane gp = new GridPane();

		Scene scn = new Scene(bp, 400, 300);
		bp.setTop(gp);
		bp.setCenter(table);

		prepararTable();

		gp.add(new Label("Nome"), 0, 0);
		gp.add(txtNome, 1, 0);
		gp.add(new Label("Idade"), 0, 1);
		gp.add(txtIdade, 1, 1);
		gp.add(new Label("Apelido"), 0, 2);
		gp.add(txtApelido, 1, 2);
		gp.add(new Label("Instagram"), 0, 3);
		gp.add(txtInstagram, 1, 3);

		gp.add(btnSalvar, 0, 4);
		gp.add(btnPesquisar, 1, 4);
		
		gp.setVgap(10);
		gp.setHgap(10);
		
		gp.setTranslateX(500);
		gp.setTranslateY(00);

		btnSalvar.setOnAction( e -> { 
			control.salvar();
			control.limpar();
			control.pesquisar();
		});

		btnPesquisar.setOnAction( e -> control.pesquisar() );
		vincular();
		
		stage.close();
		stage.setScene(scn);
		stage.setTitle("Cadastro de Clientes");
		stage.show();
	}

	private void prepararTable() {
		TableColumn<Cliente, String> col1 = new TableColumn<>("Nome");
		col1.setCellValueFactory(
				new PropertyValueFactory<Cliente, String>("nome") ); 

		TableColumn<Cliente, Integer> col2 = new TableColumn<>("Idade");
		col2.setCellValueFactory(
				new PropertyValueFactory<Cliente, Integer>("idade") ); 

		TableColumn<Cliente, String> col3 = new TableColumn<>("Apelido");
		col3.setCellValueFactory(
				new PropertyValueFactory<Cliente, String>("apelido") ); 

		TableColumn<Cliente, String> col4 = new TableColumn<>("Instagram");
		col4.setCellValueFactory(
				new PropertyValueFactory<Cliente, String>("instagram") ); 

		TableColumn<Cliente, String> col5 = new TableColumn<>("Ações");

		Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>> cellFactory
		= //
		new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
			@Override
			public TableCell call(final TableColumn<Cliente, String> param) {
				final TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {
					final Button btnApagar = new Button("Apagar");
					final Button btnEditar = new Button("Editar");
					
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btnApagar.setOnAction(event -> {
								Cliente c = getTableView().getItems().get(getIndex());
								control.apagar(c);
								control.limpar();
								control.pesquisar();
							});
							btnEditar.setOnAction(event -> {
								Cliente c = getTableView().getItems().get(getIndex());
								control.setEntity(c);
								control.editar();
							});
							FlowPane fpanel = new FlowPane();
							fpanel.getChildren().addAll(btnEditar, btnApagar);
							setGraphic(fpanel);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		col5.setCellFactory(cellFactory);

		table.getColumns().clear();
		table.getColumns().addAll(col1, col2, col3, col4, col5);

		table.setItems(control.getLista());

	}

	public void vincular() { 
		Bindings.bindBidirectional(control.nomeProperty(), 
				txtNome.textProperty());
		Bindings.bindBidirectional(control.apelidoProperty(), 
				txtApelido.textProperty());
		Bindings.bindBidirectional(control.instagramProperty(), 
				txtInstagram.textProperty());
		StringConverter<? extends Number> converterNumber = 
				new IntegerStringConverter();
		Bindings.bindBidirectional(txtIdade.textProperty(),
				control.idadeProperty(),
				(StringConverter)converterNumber);
	}

	public static void main(String[] args) {
		Application.launch(ClienteBoundary.class, args);
	}
}