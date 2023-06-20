package br.com.senac.aulaprojeto.controller;

import br.com.senac.aulaprojeto.model.Cliente;
import br.com.senac.aulaprojeto.model.Endereco;
import br.com.senac.aulaprojeto.services.ClienteService;
import br.com.senac.aulaprojeto.services.EnderecoService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FxmlView ("/main.fxml")
public class CadastroClienteController {
    @FXML
    private javafx.scene.control.TextField nome;
    @FXML
    private javafx.scene.control.TextField sobrenome;
    @FXML
    private javafx.scene.control.TextField cpf;
    @FXML
    private javafx.scene.control.TextField rg;
    @FXML
    private javafx.scene.control.TextField email;
    @FXML
    private javafx.scene.control.TextField telefone;
    @FXML
    private TableView<Cliente> tabelaCliente;
    @FXML
    private TableColumn <Cliente, String> columnNome;
    @FXML
    private TableColumn <Cliente, String> columnSobrenome;
    @FXML
    private TableColumn <Cliente, String> columnCPF;
    @FXML
    private TableColumn <Cliente, String> columnRG;
    @FXML
    private TableColumn <Cliente, String> columnEmail;
    @FXML
    private TableColumn <Cliente, String> columnTelefone;
    @FXML
    private TableView<Endereco> tabelaEndereco;
    @FXML
    private javafx.scene.control.TextField cidade;
    @FXML
    private javafx.scene.control.TextField estado;
    @FXML
    private javafx.scene.control.TextField bairro;
    @FXML
    private javafx.scene.control.TextField rua ;
    @FXML
    private javafx.scene.control.TextField cep;
    @FXML
    private javafx.scene.control.TextField codCliente;
    @FXML
    private TableColumn <Endereco, String> columnCidade;
    @FXML
    private TableColumn <Endereco, String> columnEstado;
    @FXML
    private TableColumn <Endereco, String> columnBairro;
    @FXML
    private TableColumn <Endereco, String> columnRua;
    @FXML
    private TableColumn <Endereco, String> columnCep;
    @FXML
    private TableColumn <Endereco, Integer> columnCodCliente;

    private Integer index= -1;
    private Integer indexx= -1;

    public void initialize (){
        columnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        columnSobrenome.setCellValueFactory(new PropertyValueFactory<>("Sobrenome"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        columnRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        columnCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        columnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        columnBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        columnRua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        columnCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        columnCodCliente.setCellValueFactory(new PropertyValueFactory<>("codCliente"));

        this.carregarCliente();
        this.carregarEndereco();

        tabelaCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                if(evt.getClickCount() ==2){
                    Cliente cli = tabelaCliente.getSelectionModel().getSelectedItem();
                    nome.setText(cli.getNome());
                    sobrenome.setText(cli.getSobrenome());
                    cpf.setText(cli.getCpf());
                    rg.setText(cli.getRg());
                    telefone.setText(cli.getTelefone());
                    email.setText(cli.getEmail());

                    index = cli.getId();
                }
            }
        });
        tabelaEndereco.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent est) {
                if(est.getClickCount() ==2){
                    Endereco end = tabelaEndereco.getSelectionModel().getSelectedItem();
                    //id.setText(String.valueOf(end.getId()));
                    cidade.setText(end.getCidade());
                    estado.setText(end.getEstado());
                    bairro.setText(end.getBairro());
                    rua.setText(end.getBairro());
                    cep.setText(String.valueOf(end.getCep()));
                    codCliente.setText(String.valueOf(end.getCodCliente()));

                    indexx = end.getCodCliente();
                }
            }
        });
    }

    public void executeSalvar (){
        Cliente cli = new Cliente();
        cli.setNome(nome.getText());
        cli.setSobrenome(sobrenome.getText());
        cli.setCpf(cpf.getText());
        cli.setRg(rg.getText());
        cli.setTelefone(telefone.getText());
        cli.setEmail(email.getText());

        if (!cli.getCpf().matches("[0-9]*") && !cli.getTelefone().matches("[0-9]*")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("Informe apenas numeros no Documento e no Telefone");
            alert.show();
        }else  if (index > -1){
            ClienteService.atualizarCliente(index,cli);
            index=-1;
            this.limparCampos();
        }else {
            if(ClienteService.buscarClienteByDocumento(cli.getCpf())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerta");
                alert.setHeaderText("Documento ja cadastrado");
                alert.show();
            }else {
                ClienteService.inserirClientes(cli);
                this.limparCampos();
            }
        }
        if (cli.getCpf().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos não informado");
            alert.setHeaderText("Nome ou documento não informado!");
            alert.show();
        }else{
            ClienteService.inserirClientes(cli);
            this.limparCampos();
        }
        this.carregarCliente();
    }

    public void executeExcluir(){
        if (index > -1){
            ClienteService.deletarCliente(index);
            this.carregarCliente();
            index= -1;
            this.limparCampos();
        }
    }

    public void limparCampos(){
        nome.setText("");
        sobrenome.setText("");
        cpf.setText("");
        rg.setText("");
        telefone.setText("");
        email.setText("");
    }

    public void carregarCliente (){
        tabelaCliente.getItems().remove(0,tabelaCliente.getItems().size());
        List<Cliente> cliList = ClienteService.carregarClientes();

        tabelaCliente.getItems().addAll(cliList);
    }

    public void carregarEndereco(){
        tabelaEndereco.getItems().remove(0,tabelaEndereco.getItems().size());
        List<Endereco> endList = EnderecoService.carregarEnderecos();

        tabelaEndereco.getItems().addAll(endList);
    }
    public void executarSave (){
        Endereco endere = new Endereco();
        endere.setCidade(cidade.getText());
        endere.setEstado(estado.getText());
        endere.setBairro(bairro.getText());
        endere.setRua(rua.getText());
        endere.setCep(cep.getText());
        endere.setCodCliente(Integer.parseInt(codCliente.getText()));

        if (tabelaEndereco.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("Informe apenas numeros no campo numero");
            alert.show();
        }

        if (!endere.getCep().matches("[0-9]*")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("Informe apenas numeros no campo numero");
            alert.show();
        }else  if (indexx < 0){
            EnderecoService.atualizarEndereco(index,endere);
            indexx=0;
            this.limparCamposEndereco();
        }else {
            if(EnderecoService.buscarEnderecoByCep(endere.getCep())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerta");
                alert.setHeaderText("Cep ja cadastrado");
                alert.show();
            }else {
                EnderecoService.inserirEndereco(endere);
                this.limparCamposEndereco();
            }
        }
        if (endere.getCep().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos não informado");
            alert.setHeaderText("CEP não informado");
            alert.show();
        }else{
            EnderecoService.inserirEndereco(endere);
            this.limparCamposEndereco();
        }
        this.carregarEndereco();
    }
    public void executarDelete(){
        if (indexx > -1){
            EnderecoService.deletarEndereco(indexx);
            this.carregarEndereco();
            indexx= -1;
            this.limparCamposEndereco();
        }
        else {
            System.out.println("Saiu do if");
        }

    }
    public void limparCamposEndereco(){
        cidade.setText("");
        estado.setText("");
        bairro.setText("");
        rua.setText("");
        cep.setText("");
        codCliente.setText("");
    }

}
