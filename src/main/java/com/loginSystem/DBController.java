package com.loginSystem;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Locale;

public class DBController {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/login_system";
    private static final String USER = "postgres";
    private static final String PASS = "root";

    public Connection connection = null;
    public boolean connected = false;

    private MessageDigest md;
    private byte[] digest;

    public DBController(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            return;
        }
        connected = true;
    }

    protected void registerUser(String username, String password) throws NoSuchAlgorithmException, SQLException{
        md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        digest = md.digest();
        String passwordHash = DatatypeConverter.printHexBinary(digest).toLowerCase();

        Statement st = connection.createStatement();
        st.executeUpdate(
                "INSERT INTO users(username, password) VALUES('"+username+"','"+passwordHash+"')"
        );
        connection.close();
    }

    protected boolean checkCreds(String username, String password) throws SQLException, NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        digest = md.digest();
        String passwordHash = DatatypeConverter.printHexBinary(digest).toLowerCase();

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM users WHERE username='"+username+"' AND password='"+passwordHash+"';");

        connection.close();
        return rs.next();
    }

    protected boolean checkUser(String username) throws SQLException{
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM users WHERE username='"+username+"'"
        );

        return rs.next();
    }
}
