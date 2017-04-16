package ru.kir.diplom.client.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*from  w w  w. j a  va 2 s .  co m*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 250, Color.WHITE);

        GridPane gridpane = new GridPane();
       // gridpane.setGridLinesVisible(true);
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(50);
        ColumnConstraints column3 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2, column3);

        Label candidatesLbl = new Label("Candidates");
        GridPane.setHalignment(candidatesLbl, HPos.CENTER);
        gridpane.add(candidatesLbl, 0, 0);

        Label selectedLbl = new Label("selected");
        gridpane.add(selectedLbl, 2, 0);
        GridPane.setHalignment(selectedLbl, HPos.CENTER);

        // Candidates
        final ObservableList<String> candidates = FXCollections
                .observableArrayList("Z", "A", "B", "C", "D", "Z", "A", "B", "C", "D", "Z", "A", "B", "C", "D");
        final ListView<String> candidatesListView = new ListView<>(candidates);
        gridpane.add(candidatesListView, 0, 1);

        final ObservableList<String> selected = FXCollections.observableArrayList();
        final ListView<String> heroListView = new ListView<>(selected);
        gridpane.add(heroListView, 2, 1);

        Button sendRightButton = new Button(" > ");
        sendRightButton.setOnAction((ActionEvent event) -> {
            String potential = candidatesListView.getSelectionModel()
                    .getSelectedItem();
            if (potential != null) {
                candidatesListView.getSelectionModel().clearSelection();
                candidates.remove(potential);
                selected.add(potential);
            }
        });

        Button sendLeftButton = new Button(" < ");
        sendLeftButton.setOnAction((ActionEvent event) -> {
            String s = heroListView.getSelectionModel().getSelectedItem();
            if (s != null) {
                heroListView.getSelectionModel().clearSelection();
                selected.remove(s);
                candidates.add(s);
            }
        });
        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(sendRightButton, sendLeftButton);

        gridpane.add(vbox, 1, 1);
        root.setCenter(gridpane);

        GridPane.setVgrow(root, Priority.ALWAYS);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
