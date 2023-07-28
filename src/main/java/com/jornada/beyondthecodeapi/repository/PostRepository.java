package com.jornada.beyondthecodeapi.repository;

import com.jornada.beyondthecodeapi.entity.Post;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    public Post salvarPost(Post post) {
        Connection connection = null;
        try {
            // abrir conexao
            connection = ConexaoDB.getConnection();

            // executar operacao
            String sqlSequence = "SELECT post_seq.nextval AS proxval FROM DUAL";
            Statement statement = connection.createStatement();
            ResultSet retorno = statement.executeQuery(sqlSequence);

            int proximoId = -1;
            if (retorno.next()) {
                proximoId = retorno.getInt("proxval");
            }

            String sql = "INSERT INTO JORNADA1.POST (ID_POST,TITULO,CONTEUDO,ID_USER) VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, proximoId);
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getContents());
            preparedStatement.setInt(4, post.getIdUser());

            int resposta = preparedStatement.executeUpdate();
            System.out.println("salvarPost.resposta = " + resposta);

            post.setIdPost(proximoId);
            return post;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // fechar conexao
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public List<Post> listar(){
        List<Post> listaPost = new ArrayList<>();
        // abrir conexao
        Connection connection = null;
        try{
            connection = ConexaoDB.getConnection();
            String sql = "SELECT * FROM POST";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sql);
            while(res.next()) {
                Post post = new Post();
                post.setIdPost(res.getInt("id_post"));
                post.setTitle(res.getString("titulo"));
                post.setContents(res.getString("conteudo"));
                post.setIdUser(res.getInt("id_user"));
                listaPost.add(post);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try{
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return listaPost;
    }
    public boolean editar(Post post) {
        Connection connection = null;
        try {
            // abrir conexao
            connection = ConexaoDB.getConnection();

            //Código vai aqui
            String sql = "UPDATE post SET " +
                    " titulo = ?, " +
                    " conteudo = ? " +
                    " WHERE id_post = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContents());
            preparedStatement.setInt(3, post.getIdPost());

            //executar
            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // fechar conexao
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    //Excluir
    public boolean excluir(Integer id) {
        Connection connection = null;
        try {
            connection = ConexaoDB.getConnection();

            String sql = "delete from post where id_post = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int resultado = preparedStatement.executeUpdate();
            return resultado > 0;
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally {
            // fechar conexao
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return false;
    }

    public int buscarPostID(int idPost) {
        Connection connection = null;
        try {
            // Abrir conexão
            connection = ConexaoDB.getConnection();

            // Consulta SQL
            String sql = "SELECT ID_USER FROM POST WHERE ID_POST = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPost);

            // Executar consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Extrair os dados do resultado
                int idUsuario = resultSet.getInt("ID_USER");

                return idUsuario;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Fechar conexão
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return 0;
    }

}