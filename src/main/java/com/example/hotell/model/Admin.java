package com.example.hotell.model;

import com.example.hotell.gui.MainWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Admin {
    private MainWindow mainWindow;
    private Stage stage;

    public Admin(MainWindow mainWindow, Stage stage) {
        this.mainWindow = mainWindow;
        this.stage = stage;
    }

    public void roomAdmin() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        TableView<Room> tableView = new TableView<>();
        ObservableList<Room> bookedRooms = FXCollections.observableArrayList(mainWindow.getBookedRooms()); // Dapatkan bookedRooms dari MainWindow
        tableView.setItems(bookedRooms);

        TableColumn<Room, String> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        tableView.getColumns().add(roomNumberColumn);

        TableColumn<Room, String> guestNameColumn = new TableColumn<>("Guest Name");
        guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        tableView.getColumns().add(guestNameColumn);

        TableColumn<Room, String> checkInDateColumn = new TableColumn<>("Check-in Date");
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        tableView.getColumns().add(checkInDateColumn);

        TableColumn<Room, String> checkOutDateColumn = new TableColumn<>("Check-out Date");
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        tableView.getColumns().add(checkOutDateColumn);

        TableColumn<Room, Boolean> isPaidColumn = new TableColumn<>("Paid");
        isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        tableView.getColumns().add(isPaidColumn);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #8d99ae; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> mainWindow.showLoginScreen());

        root.getChildren().addAll(tableView, backButton);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
//Untuk addroom masih bingung aku saranku apus aja sih, karna cara menyimpan datanya di instace class lain, kalo mau disesuain masih bisa
    public void addRoom() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label roomNumberLabel = new Label("Room Number:");
        TextField roomNumberField = new TextField();
        gridPane.add(roomNumberLabel, 0, 0);
        gridPane.add(roomNumberField, 1, 0);

        Label roomTypeLabel = new Label("Room Type:");
        TextField roomTypeField = new TextField();
        gridPane.add(roomTypeLabel, 0, 1);
        gridPane.add(roomTypeField, 1, 1);

        Label roomPriceLabel = new Label("Price:");
        TextField roomPriceField = new TextField();
        gridPane.add(roomPriceLabel, 0, 2);
        gridPane.add(roomPriceField, 1, 2);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String roomNumber = roomNumberField.getText();
            String type = roomTypeField.getText();
            double price = Double.parseDouble(roomPriceField.getText());
            Room room = new Room(roomNumber, type, price);
            mainWindow.getBookedRooms().add(room); // Menambahkan kamar ke bookedRooms di MainWindow

            // Close the add room window
            Stage addRoomStage = (Stage) saveButton.getScene().getWindow();
            addRoomStage.close();

            // Refresh the room admin screen
            roomAdmin();
        });
        gridPane.add(saveButton, 0, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        Stage addRoomStage = new Stage();
        addRoomStage.setScene(scene);
        addRoomStage.show();
    }
}
