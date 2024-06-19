package com.example.busappoop;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.Objects;



public class BusController {

    private Stage stage;
    private Scene scene;
    @FXML
    private Label sessionText;
    @FXML
    private TableView<Model> busLineTable;
    @FXML
    private TableColumn<Model, Integer> colID;
    @FXML
    private TableColumn<Model, String> colOd;
    @FXML
    private TableColumn<Model, String> colDo;
    @FXML
    private TableColumn<Model, Date> colKrece;
    @FXML
    private TableColumn<Model, Date> colStize;
    @FXML
    private TableColumn<Model, Double> colCijena;
    @FXML
    private TableColumn<Model, Integer> colSlobodno;
    @FXML
    private Label rezervacijeLabel;
    @FXML
    private ChoiceBox<Integer> rezervacijaPicker;

    ObservableList<Model> busData = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
        sessionSetCheck();
        refreshTable();
    }


    @FXML
    protected void logoutButton(ActionEvent event) throws IOException {
        CachedUser.clearUserData();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    protected void refreshTable() throws SQLException {
        busData.clear();
        Connect conn = new Connect();
        ObservableList<Model> busData = conn.getBusData();
        colID.setCellValueFactory(new PropertyValueFactory<Model, Integer>("ID"));
        colOd.setCellValueFactory(new PropertyValueFactory<Model, String>("locationFrom"));
        colDo.setCellValueFactory(new PropertyValueFactory<Model, String>("locationTo"));
        colKrece.setCellValueFactory(new PropertyValueFactory<Model, Date>("departureTime"));
        colStize.setCellValueFactory(new PropertyValueFactory<Model, Date>("arrivalTime"));
        colCijena.setCellValueFactory(new PropertyValueFactory<Model, Double>("Price"));
        colSlobodno.setCellValueFactory(new PropertyValueFactory<Model, Integer>("maxFreeSpace"));
        busLineTable.setItems(busData);
        checkLineAmount();
        loadUserReservations();

    }

    protected void loadUserReservations() throws SQLException {
        Connect conn = new Connect();
        ObservableList<Integer> userReservations = FXCollections.observableArrayList(conn.loadUserReservations());
        userReservations.forEach((res) -> {
            rezervacijeLabel.setText(res.toString());
        });
    }

    protected void checkLineAmount(){
        Connect conn = new Connect();
        rezervacijaPicker.setItems(FXCollections.observableArrayList(conn.checkLineAmount()));
    }

    @FXML
    protected void rezervisiKartu(){
        Connect conn = new Connect();
        conn.setReservation(rezervacijaPicker.getValue());
        rezervacijeLabel.setText(rezervacijaPicker.getValue().toString());
    }

     protected void sessionSetCheck() {
         if((CachedUser.getInstance().getUsername()) == null){
             sessionText.setText("You are not logged in. // DEBUG");
         } else {
             sessionText.setText(CachedUser.getInstance().getUsername());
             sessionText.setTextFill(Color.MEDIUMPURPLE);
         }
     }

}
