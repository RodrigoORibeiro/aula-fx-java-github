package br.com.senac.aulaprojeto.services;

import br.com.senac.aulaprojeto.db.ConexaoDataBase;
import br.com.senac.aulaprojeto.model.Cliente;
import br.com.senac.aulaprojeto.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private static ConexaoDataBase conexao = new ConexaoDataBase();

    public static List <Cliente> carregarClientes(){
        List<Cliente> out = new ArrayList<>();

        try {
            Connection conn = conexao.getConexao();

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("select * from cliente;");

            while (rs.next()){
                Cliente cli = new Cliente(rs.getInt("id"),
                    rs.getString("nome"),rs.getString("sobrenome"),
                            rs.getString("cpf"),rs.getString("rg"),
                            rs.getString("telefone"), rs.getString("email"));
                    out.add(cli);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    /*public static List <Endereco> carregarEnderecos(){
        List<Endereco> aut = new ArrayList<>();

        try {
            Connection cann = conexao.getConexao();

            Statement ste = cann.createStatement();
            ResultSet end = ste.executeQuery("select * from enderecos");

            while (end.next()){
                Endereco ende = new Endereco(end.getInt("id_cliente_end"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    public static void inserirClientes(Cliente cliente){
        try {
            Connection conn = conexao.getConexao();

            String sqlInsert = "insert into cliente(nome,sobrenome,cpf,rg,telefone, email) values (?,?,?,?,?,?)";

            PreparedStatement pre= conn.prepareStatement(sqlInsert);
            pre.setString(1, cliente.getNome());
            pre.setString(2, cliente.getSobrenome());
            pre.setString(3, cliente.getCpf());
            pre.setString(4, cliente.getRg());
            pre.setString(5,cliente.getTelefone());
            pre.setString(6,cliente.getEmail());

            pre.execute();

            pre.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static  boolean deletarCliente (int codCliente){
        try {
            Connection conn = conexao.getConexao();

            String deleteSql = "delete from cliente where id = ?";

            PreparedStatement del = conn.prepareStatement(deleteSql);
            del.setInt(1,codCliente);

            return del.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean atualizarCliente (int codCliente , Cliente cli){
        try {
            Connection conn = conexao.getConexao();

            String updateSql = "update cliente set nome = ?, sobrenome = ?, cpf = ?, rg = ?, telefone = ?, email = ? where id= ?";

            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1,cli.getNome());
            ps.setString(2,cli.getSobrenome());
            ps.setString(3,cli.getCpf());
            ps.setString(4,cli.getRg());
            ps.setString(5, cli.getTelefone());
            ps.setString(6,cli.getEmail());
            ps.setInt(7,codCliente);

            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean buscarClienteByDocumento (String cpf){
        try {
            Connection conn = conexao.getConexao();

            String selectSql = "select * from cliente where cpf = '" + cpf +"'";

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(selectSql);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
