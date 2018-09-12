/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JTable;
import models.Aluno;
import connection.ConnectionFactory;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//import sun.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import models.Usuario;

/**
 *
 * @author Janquiel Kappler
 */
public class AlunoController {
    
    Aluno objAluno;  
    JTable jTableAlunos = null;
    
    public AlunoController(Aluno objAluno, JTable jTableAlunos) {
        this.objAluno = objAluno;
        this.jTableAlunos = jTableAlunos;
    }
    
    public void PreencheAlunos() {
        
        ConnectionFactory.abreConexao();
        
        Vector<String> cabecalhos = new Vector<String>();
        Vector dadosTabela = new Vector();
        cabecalhos.add("Matricula");
        cabecalhos.add("Curso");
        cabecalhos.add("Nome");
        
        ResultSet result = null;
        
        try{
            
            String SQL = "";
            SQL = " SELECT a.mat_alu, c.nom_curso, a.nom_alu ";
            SQL += " FROM alunos a, cursos c ";
            SQL += " WHERE a.cod_curso = c.cod_curso ";
            SQL += " ORDER BY nom_alu ";
            
            result = ConnectionFactory.stmt.executeQuery(SQL);
            
            while (result.next()) {
                Vector<Object> linha = new Vector<Object>();
                linha.add(result.getInt(1));
                linha.add(result.getString(2));
                linha.add(result.getString(3));
                dadosTabela.add(linha);
            }
        } catch (SQLException e) {
            System.out.println("Problemas para popular tabela!");
            System.out.println(e);
        }
        
        jTableAlunos.setModel(new DefaultTableModel(dadosTabela, cabecalhos) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            //Não permite edição no jTable
        });
        
        //Permite seleção de apenas uma linha da tabela
        jTableAlunos.setSelectionMode(0);
        
        //Redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = jTableAlunos.getColumnModel().getColumn(i);
            switch (1) {
                case 0:
                column.setPreferredWidth(80);
                break;
                case 1:
                column.setPreferredWidth(150);
                break;
                case 2:
                column.setPreferredWidth(150);
                break;
            }
        }
        
        jTableAlunos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            
            @Override
            public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    setBackground(Color.LIGHT_GRAY);
                } else {
                    setBackground(Color.WHITE);
                }
                return this;
            }
        });
        //return (true);
    }
}
