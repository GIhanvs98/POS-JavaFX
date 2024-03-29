package com.gihan.pos.controller;

import com.gihan.pos.util.PasswordManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpFormController {

    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassWord;

    public void btnRegisterNowOnAction(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
          Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka2",
                    "root","1234");
          String sql="INSERT INTO user VALUES (?,?)";
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
          preparedStatement.setString(1,txtEmail.getText());
          preparedStatement.setString(2, PasswordManager.encryptPassword(txtPassWord.getText()));
         if ( preparedStatement.executeUpdate()>0){
             new Alert(Alert.AlertType.CONFIRMATION,"Registration Success").show();
             clearFields();

         }else {
             new Alert(Alert.AlertType.WARNING,"Try Again").show();
             clearFields();
         }
        } catch (ClassNotFoundException | SQLException e) {
         new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private void clearFields() {
        txtEmail.clear();
        txtEmail.clear();
    }

    public void btnAlreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }
    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")))
        );
        stage.centerOnScreen();
        stage.show();
    }
}
