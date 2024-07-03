package com.example.hotell.gui;

import com.example.hotell.controller.HotelController;
import com.example.hotell.controller.UserController;
import com.example.hotell.model.Hotel;
import com.example.hotell.model.Room;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MainWindow {
    private HotelController hotelController;
    private UserController userController;
    private Stage stage;
    private List<Room> bookedRooms = new ArrayList<>();
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
    private String checkInTime;
    private String checkOutTime;
    private String currentUser;

    public MainWindow() {
        Hotel hotel = new Hotel("My Iclika Hotel");
        hotel.addRoom(new Room("101", "Single", 100.0));
        hotel.addRoom(new Room("102", "Double", 150.0));
        hotelController = new HotelController(hotel);
        userController = new UserController();
    }

    public void show(Stage stage) {
        this.stage = stage;
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Label welcomeLabel = new Label("Welcome to My Iclika Hotel");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");

        Button bookButton = new Button("Book a Room");
        bookButton.setStyle("-fx-background-color: #e76f51; -fx-text-fill: white; -fx-font-weight: bold;");
        bookButton.setOnAction(e -> showBookingScreen());

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #264653; -fx-text-fill: white; -fx-font-weight: bold;");
        loginButton.setOnAction(e -> showLoginScreen());

        vbox.getChildren().addAll(welcomeLabel, bookButton, loginButton);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }

    private void showBookingScreen() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Label titleLabel = new Label("Book a Room");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");
        ComboBox<String> roomComboBox = new ComboBox<>();
        for (var room : hotelController.getRooms()) {
            roomComboBox.getItems().add(room.getRoomNumber() + " - " + room.getType() + " - $" + room.getPrice());
        }
        TextField nameField = new TextField();
        nameField.setPromptText("Your Name");
        DatePicker checkInDatePicker = new DatePicker();
        DatePicker checkOutDatePicker = new DatePicker();

        ComboBox<String> checkInTimeComboBox = createTimeComboBox();
        ComboBox<String> checkOutTimeComboBox = createTimeComboBox();

        Button bookButton = new Button("Book");
        bookButton.setStyle("-fx-background-color: #e76f51; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #8d99ae; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> {
            if (currentUser != null) {
                showDashboard();
            } else {
                showWelcomeScreen();
            }
        });

        bookButton.setOnAction(e -> {
            String selectedRoom = roomComboBox.getValue();
            if (selectedRoom != null) {
                String[] roomDetails = selectedRoom.split(" - ");
                bookedRooms.add(new Room(roomDetails[0], roomDetails[1], Double.parseDouble(roomDetails[2].substring(1))));
            }
            guestName = nameField.getText();
            checkInDate = checkInDatePicker.getValue().toString();
            checkOutDate = checkOutDatePicker.getValue().toString();
            checkInTime = checkInTimeComboBox.getValue();
            checkOutTime = checkOutTimeComboBox.getValue();
            if (currentUser != null) {
                showDashboard();
            } else {
                showLoginScreen();
            }
        });

        vbox.getChildren().addAll(titleLabel, roomComboBox, nameField, new Label("Check-in Date:"), checkInDatePicker, new Label("Check-in Time:"), checkInTimeComboBox, new Label("Check-out Date:"), checkOutDatePicker, new Label("Check-out Time:"), checkOutTimeComboBox, bookButton, backButton);

        Scene scene = new Scene(vbox, 400, 400);
        stage.setTitle("Booking");
        stage.setScene(scene);
        stage.show();
    }

    private ComboBox<String> createTimeComboBox() {
        ComboBox<String> timeComboBox = new ComboBox<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        IntStream.range(0, 24 * 4)
                .mapToObj(i -> LocalTime.MIN.plusMinutes(15 * i).format(timeFormatter))
                .forEach(timeComboBox.getItems()::add);
        timeComboBox.getSelectionModel().select("14:00"); // Default check-in time
        return timeComboBox;
    }

    private void showLoginScreen() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Label welcomeLabel = new Label("Welcome to " + hotelController.getHotelName());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #264653; -fx-text-fill: white; -fx-font-weight: bold;");
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #e9c46a; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #8d99ae; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> showWelcomeScreen());

        loginButton.setOnAction(e -> {
            if (userController.loginUser(usernameField.getText(), passwordField.getText())) {
                currentUser = usernameField.getText();
                showDashboard();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Login failed. Incorrect username or password.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        registerButton.setOnAction(e -> showRegisterScreen());

        vbox.getChildren().addAll(welcomeLabel, usernameField, passwordField, loginButton, registerButton, backButton);

        Scene scene = new Scene(vbox, 300, 250);
        stage.setTitle("Hotel Login");
        stage.setScene(scene);
    }

    private void showRegisterScreen() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Label registerLabel = new Label("Register a New Account");
        registerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #e9c46a; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #8d99ae; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> showLoginScreen());

        registerButton.setOnAction(e -> {
            if (userController.registerUser(usernameField.getText(), passwordField.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful!", ButtonType.OK);
                alert.showAndWait();
                showLoginScreen();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username already exists. Try another.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        vbox.getChildren().addAll(registerLabel, usernameField, passwordField, registerButton, backButton);

        Scene scene = new Scene(vbox, 300, 250);
        stage.setTitle("Register");
        stage.setScene(scene);
    }

    private void showPaymentScreen() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Label paymentLabel = new Label("Proceed to Payment");
        paymentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");

        StringBuilder bookingDetailsText = new StringBuilder();
        double totalPrice = 0.0;

        for (Room room : bookedRooms) {
            bookingDetailsText.append("Room Number: ").append(room.getRoomNumber()).append("\n")
                    .append("Room Type: ").append(room.getType()).append("\n")
                    .append("Price: $").append(room.getPrice()).append("\n")
                    .append("Check-in Date: ").append(checkInDate).append(" at ").append(checkInTime).append("\n")
                    .append("Check-out Date: ").append(checkOutDate).append(" at ").append(checkOutTime).append("\n\n");
            totalPrice += room.getPrice();
        }

        bookingDetailsText.append("Total Price: $").append(totalPrice);

        Label bookingDetails = new Label(bookingDetailsText.toString());
        bookingDetails.setStyle("-fx-font-size: 14px;");

        GridPane paymentForm = new GridPane();
        paymentForm.setHgap(10);
        paymentForm.setVgap(10);
        paymentForm.setPadding(new Insets(10));

        Label cardNumberLabel = new Label("Card Number:");
        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("1234 5678 9012 3456");

        Label expiryLabel = new Label("Expiry Date:");
        TextField expiryField = new TextField();
        expiryField.setPromptText("MM/YY");

        Label cvvLabel = new Label("CVV:");
        TextField cvvField = new TextField();
        cvvField.setPromptText("123");

        paymentForm.add(cardNumberLabel, 0, 0);
        paymentForm.add(cardNumberField, 1, 0);
        paymentForm.add(expiryLabel, 0, 1);
        paymentForm.add(expiryField, 1, 1);
        paymentForm.add(cvvLabel, 0, 2);
        paymentForm.add(cvvField, 1, 2);

        Button payButton = new Button("Pay");
        payButton.setStyle("-fx-background-color: #e76f51; -fx-text-fill: white; -fx-font-weight: bold;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #8d99ae; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> showDashboard());

        payButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment successful!", ButtonType.OK);
            alert.showAndWait();
            bookedRooms.clear();
            showDashboard();
        });

        vbox.getChildren().addAll(paymentLabel, bookingDetails, paymentForm, payButton, backButton);

        Scene scene = new Scene(vbox, 400, 400);
        stage.setTitle("Payment");
        stage.setScene(scene);
    }

    private void showDashboard() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Label dashboardLabel = new Label("User Dashboard");
        dashboardLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");

        StringBuilder bookingDetailsText = new StringBuilder();
        for (Room room : bookedRooms) {
            bookingDetailsText.append("Room Number: ").append(room.getRoomNumber()).append("\n")
                    .append("Room Type: ").append(room.getType()).append("\n")
                    .append("Price: $").append(room.getPrice()).append("\n")
                    .append("Check-in Date: ").append(checkInDate).append(" at ").append(checkInTime).append("\n")
                    .append("Check-out Date: ").append(checkOutDate).append(" at ").append(checkOutTime).append("\n\n");
        }

        Label bookingDetails = new Label(bookingDetailsText.toString());
        bookingDetails.setStyle("-fx-font-size: 14px;");

        Button bookAnotherRoomButton = new Button("Book Another Room");
        bookAnotherRoomButton.setStyle("-fx-background-color: #8d99ae; -fx-text-fill: white; -fx-font-weight: bold;");
        bookAnotherRoomButton.setOnAction(e -> showBookingScreen());

        Button finalizePaymentButton = new Button("Finalize Payment");
        finalizePaymentButton.setStyle("-fx-background-color: #e76f51; -fx-text-fill: white; -fx-font-weight: bold;");
        finalizePaymentButton.setOnAction(e -> showPaymentScreen());

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #264653; -fx-text-fill: white; -fx-font-weight: bold;");
        logoutButton.setOnAction(e -> {
            currentUser = null;
            bookedRooms.clear();
            showLoginScreen();
        });

        vbox.getChildren().addAll(dashboardLabel, bookingDetails, bookAnotherRoomButton, finalizePaymentButton, logoutButton);

        Scene scene = new Scene(vbox, 300, 400);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
    }
}
