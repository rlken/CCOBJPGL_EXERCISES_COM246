package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class HomeController implements Initializable{

    ObservableList<User> mylist = FXCollections.observableArrayList();

    @FXML
    private Button createbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private Button updatebtn;

    @FXML
    private TableColumn<User, String> userclmn;

    @FXML
    private TableColumn<User, String> passwordclmn;

    @FXML
    private TableColumn<User, String> statusclmn;

    @FXML
    private TableColumn<User, String> accountclmn;

    @FXML
    private TableView<User> viewtable;

    @FXML
    Label homelabel;

    @FXML
    TextField userfield;

    @FXML
    TextField passwordfield;

    @FXML
    TextField statustextfield;

    @FXML
    ChoiceBox<String> statuschoicebox;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String filename = "accounts.txt";

    @Override
    public void initialize(URL url, ResourceBundle rb){

        String username  = LoginController.user.getUsername();
        homelabel.setText(username);

        initializeCol();
        loadData();

        statuschoicebox.getItems().addAll("Active", "Inactive");

        viewtable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            
            if (newSelection != null) {
                userfield.setText(newSelection.getUsername());
                passwordfield.setText(newSelection.getPassword());
                statuschoicebox.setValue(newSelection.getAccountstatus());
            }
        });
    }

    private void initializeCol(){

        userclmn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordclmn.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountclmn.setCellValueFactory(new PropertyValueFactory<>("accountcreated"));
        statusclmn.setCellValueFactory(new PropertyValueFactory<>("accountstatus"));
    }

    private void loadData(){

        mylist.clear();

        try {
            File myFile = new File("accounts.txt");

            if (myFile.exists()) {

                Scanner filescanner = new Scanner(myFile);

                while (filescanner.hasNextLine()) {

                    String data = filescanner.nextLine();
        
                    String username = data.split(",")[0];
                    String password = data.split(",")[1];
                    String dcreated = data.split(",")[2];
                    String status = data.split(",")[3];

                    mylist.add(new User(username, password, dcreated, status));
                } 
                viewtable.setItems(mylist);

                filescanner.close();
            }
            else {
                System.out.println(myFile.getName() + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("An error has been found");
        } 
    }

    @FXML
    private boolean createuser(ActionEvent event) {

        String username = userfield.getText();

        String password = passwordfield.getText();

        String status = statuschoicebox.getValue();

        System.out.println(status);
        username = username.trim();
        password = password.trim();
        status = status.trim();

        if(username.length() == 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no username detected");
        }

        if(password.length() == 0)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please input password");
        }

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = today.format(formatter);

        // Create user object
        User user = new User(username, password, formattedDate, status);

        try {

            BufferedWriter myWriter = new BufferedWriter(new FileWriter("accounts.txt", true));
      
            myWriter.newLine();
            myWriter.write(user.getUsername() + "," + user.getPassword() + "," + user.getAccountcreated() + "," + user.getAccountstatus());

            myWriter.close();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Account Created");
            alert.setContentText("New account successfully created");
            alert.showAndWait();
            
            loadData();

        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }

        return true;
    }
    
    @FXML  
    public boolean deleteuser(ActionEvent event) {

        User user = viewtable.getSelectionModel().getSelectedItem();

        String username = (user.getUsername());

        System.out.println(username);

        String userToDelete = username;

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { 
                    String[] parts = line.split(",");
                    if (!parts[0].equalsIgnoreCase(userToDelete)) {
                        updatedLines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < updatedLines.size(); i++) {
                writer.write(updatedLines.get(i));
                if (i < updatedLines.size() - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("This is the header");
        alert.setContentText("User '" + userToDelete + "' has been deleted (if existed).");
        alert.showAndWait();        
        loadData();

        return true;
    }
    
    @FXML
    public boolean updateuser(ActionEvent event) {

        User user = viewtable.getSelectionModel().getSelectedItem();

        String username = userfield.getText();

        String password = passwordfield.getText();

        String status = statuschoicebox.getValue();

        username = username.trim();
        password = password.trim();
        status = status.trim();

        if(username.length() == 0)
        {
            System.out.println("No username!");
            return false;
        }

        if(password.length() == 0)
        {
            System.out.println("No password!");
            return false;
        }
        String newUsername = username;
        String targetUsername = user.getUsername();
        String newPassword = password;
        String newStatus = status;

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");

                    if (parts.length == 4 && parts[0].equalsIgnoreCase(targetUsername)) {
                        updatedLines.add(newUsername + "," + newPassword + "," + user.getAccountcreated() + "," + newStatus);
                    } else {
                        updatedLines.add(line);
                    }

                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < updatedLines.size(); i++) {
                writer.write(updatedLines.get(i));
                if (i < updatedLines.size() - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("This is the header");
        alert.setContentText("User '" + targetUsername + "' has been updated.");
        alert.showAndWait();
        loadData();
        return true;
    }
}