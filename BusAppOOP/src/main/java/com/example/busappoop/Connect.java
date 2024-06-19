package com.example.busappoop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Connect {
    /*
      Connect to db
     */
    private Connection connect() {
        // sqlite con
        String url = "jdbc:sqlite:busALPHA.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void setReservation(Integer reservationId){
        String sql = "select user_id from users where username = '" + CachedUser.getInstance().getUsername() + "'";
        int userId = 0;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)){

            while (rs.next()) {
                userId = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        final String sql2 = "INSERT INTO reservation (user_id, bus_line_id, bus_full) " +
                "VALUES ('" + userId + "','" + reservationId + "', + false)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:busALPHA.sqlite");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql2);
            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean loginValidate(User user) {
        String sql = "select * from users where username = '" + user.getUsername()
                + "' and password = '" + user.getPassword() + "'";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)){

            while (rs.next()) {
                if (rs.getString("username").equals(user.getUsername())
                        && rs.getString("password").equals(user.getPassword())) {
                    return true;
                }
            }

            return false;

    } catch (SQLException e) {
        System.out.println(e.getMessage());}
        return false;
    }

    public ObservableList<Model> getBusData(){
        ObservableList<Model> busList = FXCollections.observableArrayList();
        Connection conn = connect();
        String query = "SELECT * FROM bus_lines";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Model busData;

            while(rs.next()){
                busData = new Model(rs.getInt("bus_line_id"),
                        rs.getString("location_from"), rs.getString("location_to"),
                rs.getDate("departure_time"), rs.getDate("arrival_time"),
                        rs.getDouble("price"), rs.getInt("max_free_seats"));
                busList.add(busData);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return busList;
    }


    public ObservableList<Integer> checkLineAmount(){
        String sql = "select bus_line_id from bus_lines;";
        ObservableList<Integer> list = FXCollections.observableArrayList();
        int listData;
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                listData = rs.getInt("bus_line_id");
                list.add(listData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public int getUserIDbyUsername() throws SQLException {
        String sql = "select user_id from users where username = '" +
                CachedUser.getInstance().getUsername() + "'";
        int userId = 0;

        try (Connection conn = this.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)){
            while (rs.next()) {
                userId = rs.getInt("user_id");
            }
        }

        return userId;
    }

    public ObservableList<Integer> loadUserReservations() throws SQLException {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        String query = "SELECT bus_line_id FROM reservation where user_id = '" + getUserIDbyUsername() + "'";
        int listData;
        try(
                Connection conn = this.connect();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)
                ){
            while (rs.next()) {
                listData = rs.getInt("bus_line_id");
                list.add(listData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;

    }


}
