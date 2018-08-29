/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.ConnectionFactory;
import tools.CaixaDeDialogo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Janquiel Kappler
 */
public class LoginController {
     public static Connection conexao = new ConnectionFactory().getConnection();
     public static String TABELA = "usuarios";
     public static PreparedStatement ps;
     public static ResultSet rs;

      public static boolean buscarUsuario(String login, String senha){
          boolean autenticado = false;
          
          try {
            ConnectionFactory.abreConexao();
            ps = conexao.prepareStatement("SELECT login_usuario, senha_usuario FROM " + TABELA + " WHERE login_usuario = ? AND senha_usuario = ?");
            ps.setString(1, login);
            ps.setString(2, senha);
            
            rs = ps.executeQuery();
            //Testar se tem alguma registro no banco de dados
            if (rs.next()) {
                //login.equals(rs.getString("login"));
                //senha.equals(rs.getString("senha"));
                autenticado = true;
            }
            return autenticado;
            } catch (SQLException e) {
               System.out.println("Erro ao fazer a consulta no banco de dados" + e.getMessage());
            }
            ConnectionFactory.closeConnection(conexao, ps, rs);
            return false;
        }
    
}
