package sample;

import java.sql.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;



public class ConnexionMySQL {
    Connection conn =null;
    public static Connection connectDb(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/sattendance","root","");

           // JOptionPane.showMessageDialog(null,"Connexion establish");

            return conn;

        }catch (Exception e){

            JOptionPane.showMessageDialog(null,e);
            return  null;
        }

    }}