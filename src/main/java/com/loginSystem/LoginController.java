package com.loginSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label workingText;
    @FXML
    private Label registerText;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    private DBController connectDB;

    @FXML
    private void onRegisterButtonClick(ActionEvent event) throws SQLException, NoSuchAlgorithmException, IOException {
        connectDB = new DBController();
        if (passwordField.getText().equals("")){
            registerText.setText("Please, type username and password");
            registerText.setTextFill(Color.RED);
            return;
        }
        if (loginField.getText().equals("")){
            registerText.setText("Please, type username and password");
            registerText.setTextFill(Color.RED);
            return;
        }
        if (connectDB.connected){
            if (!connectDB.checkUser(loginField.getText())){
                connectDB.registerUser(loginField.getText(), passwordField.getText());
                registerText.setText("New user completely signed up. You can click Back now");
                registerText.setTextFill(Color.GREEN);
            } else {
                registerText.setText("Username already exists");
                registerText.setTextFill(Color.RED);
            }
        }
    }

    @FXML
    private void onSignUpButtonClick(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onBackButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onConfirmButtonClick(ActionEvent event) throws SQLException, NoSuchAlgorithmException, IOException {
        connectDB = new DBController();
        if (connectDB.connected){
            if (connectDB.checkCreds(loginField.getText(), passwordField.getText())){
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginned-view.fxml")));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                workingText.setText("Wrong credentials");
                workingText.setTextFill(Color.RED);
            }
        } else {
            workingText.setText("Couldn't connect to database");
            workingText.setTextFill(Color.RED);
        }
    }

    @FXML
    private void onLogOutButtonClick(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}