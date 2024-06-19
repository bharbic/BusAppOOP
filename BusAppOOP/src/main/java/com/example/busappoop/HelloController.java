package com.example.busappoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class HelloController {

    @FXML
    private Stage stage;
    private Scene scene;
    private VBox vbox;
    private Parent root;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginErrorText;


    @FXML
    protected void loginButton(ActionEvent event) throws IOException, InterruptedException {
    if (loginValidation()) {
        loginErrorText.setTextFill(Color.GREEN);
        loginErrorText.setText("Logged in successfully.");
        User currentUser = new User(usernameField.getText(), passwordField.getText());
        CachedUser.cacheUserData(currentUser);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("busLineWindow.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    }


    @FXML
    protected void switchToBus(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("busLineWindow.fxml")));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}

    @FXML
    protected boolean loginValidation(){
        User currentUser = new User(usernameField.getText(), passwordField.getText());
        Connect conn = new Connect();
        if (conn.loginValidate(currentUser)){
            return true;
        } else {
            loginErrorText.setTextFill(Color.RED);
            loginErrorText.setText("Login failed.");
            return false;
        }
    }



}