package br.com.senac.aulaprojeto.services;

import br.com.senac.aulaprojeto.db.ConexaoDataBase;
import br.com.senac.aulaprojeto.model.Endereco;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoService {
    private static ConexaoDataBase conexao = new ConexaoDataBase();
    public static List<Endereco> carregarEnderecos(){
        List<Endereco> aut = new ArrayList<>();

        try {
            Connection cann = conexao.getConexao();

            Statement ste = cann.createStatement();
            ResultSet end = ste.executeQuery("select * from enderecos");

            while (end.next()){
                Endereco ende = new Endereco(end.getInt("id"),end.getString("cep"),
                        end.getString("bairro"),end.getString("numero"),
                        end.getString("cidade"),end.getString("estado"));
                aut.add(ende);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aut;
    }

    public static void inserirEndereco(Endereco endereco){
        try {
            Connection conn = conexao.getConexao();

            String sqlInsert = "insert into endereco (cidade,estado,bairro,cua,cep,codCliente) values (?,?,?,?,?,?)";

            PreparedStatement der= conn.prepareStatement(sqlInsert);
            der.setString(1,endereco.getCidade());
            der.setString(2,endereco.getEstado());
            der.setString(3, endereco.getBairro());
            der.setString(4, endereco.getRua());
            der.setString(5, endereco.getCep());
            der.setInt(6,endereco.getCodCliente());

            der.execute();

            der.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static  boolean deletarEndereco (int idEndereco){
        try {
            Connection conn = conexao.getConexao();

            String deleteSql = "delete from endereco where id = ?";

            PreparedStatement delend = conn.prepareStatement(deleteSql);
            delend.setInt(1,idEndereco);

            return delend.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean atualizarEndereco (int idEndereco, Endereco end){
        try {
            Connection conn = conexao.getConexao();

            String updateSql = "update endereco set codCliente = ?, cidade = ?, estado = ?, bairro = ?, rua = ?, cep = ? where id= ?";

            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1, end.getCidade());
            ps.setString(2, end.getEstado());
            ps.setString(3, end.getRua());
            ps.setString(4, end.getBairro());
            ps.setString(5,end.getCep());
            ps.setInt(6,end.getCodCliente());

            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean buscarEnderecoByCep (String cep){
        try {
            Connection conn = conexao.getConexao();

            String selectSql = "select * from enderecos where cep= '" + cep +"'";

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(selectSql);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
