package ru.gb.may_chat.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    private static final String SEND_TO_EVERYBODY = "Отправить всем";

    @FXML
    private VBox mainPanel;

    @FXML
    private TextArea chatArea;

    @FXML
    private ListView<String> contacts;

    @FXML
    private TextField inputField;

    @FXML
    private Button btnSend;

    public void mockAction(ActionEvent actionEvent) {
        System.out.println("mock");
    }

    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void sendMessage(ActionEvent actionEvent) {
        String text = inputField.getText();
        if (text == null || text.isBlank()) {
            return;
        }

        String message;
        if (contacts.getSelectionModel() == null
            || contacts.getSelectionModel().getSelectedItem() == null
            || contacts.getSelectionModel().getSelectedItem().equals(SEND_TO_EVERYBODY)) {
            message = String.format("Broadcast: %s", text + System.lineSeparator());
        } else {
            message = String.format("%s: %s", contacts.getSelectionModel().getSelectedItem(), text + System.lineSeparator());
        }

        chatArea.appendText(message);
        inputField.clear();
    }

    public void showGuide(ActionEvent actionEvent) {
        URL helpGuideRes = getClass().getClassLoader().getResource("help/helpGuide.html");
        if (helpGuideRes == null) {
            throw new IllegalArgumentException("helpGuide.html not found in resources/help folder");
        }

        Stage stage = new Stage();
        stage.setTitle("Help Guide");

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load(helpGuideRes.toExternalForm());

        Scene scene = new Scene(webView, webView.getPrefWidth(), webView.getPrefHeight());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> names = List.of("Отправить всем", "Vasya", "Masha", "Petya", "Valera", "Nastya");
        contacts.setItems(FXCollections.observableList(names));
    }
}
