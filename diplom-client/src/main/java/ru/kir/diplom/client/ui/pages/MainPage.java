package ru.kir.diplom.client.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.kir.diplom.client.model.SingleSource;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Constants;
import ru.kir.diplom.client.util.Helper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kirill Zhitelev on 03.04.2017.
 */
public class MainPage {
    private Scene scene;
    private RestClientService clientService = RestClientService.getInstance();
    private List<SingleSource> singleSources;
    private Button addSource = new Button("Создать");
    private Button correctSource = new Button("Изменить");
    private Button removeSource = new Button("Удалить");
    private ObservableList<String> sources = FXCollections.observableArrayList();
    private Stage stage;

    public MainPage(Stage stage) {
        this.stage = stage;
        singleSources = clientService.getAllSingleSources();

        init();
    }

    private void init() {

        GridPane gridPane = new GridPane();
      //  gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints(300, 300, Double.MAX_VALUE);
        ColumnConstraints col2 = new ColumnConstraints(300, 300, Double.MAX_VALUE);
        ColumnConstraints col3 = new ColumnConstraints(300, 300, Double.MAX_VALUE);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        VBox sourceButtons = new VBox(15);
        sourceButtons.setAlignment(Pos.CENTER_RIGHT);
        sourceButtons.getChildren().addAll(addSource, correctSource, removeSource);

        Label sourceLabel = new Label("Единые источники");
        sourceLabel.setFont(Font.font("Arial", 15));
        GridPane.setHalignment(sourceLabel, HPos.CENTER);

        sources.addAll(singleSources.stream().map(SingleSource::getSingleName).collect(Collectors.toList()));
        ListView<String> sourceView = new ListView<>(sources);
        sourceView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        gridPane.add(sourceLabel, 1, 0);
        gridPane.add(sourceButtons, 0, 1);
        gridPane.add(sourceView, 1, 1);

        handleButtons(sourceView);
        makeFragmentPage(sourceView);

        scene = new Scene(gridPane);

    }

    private void handleButtons(ListView<String> sourceView) {
        addSource.setOnAction(event -> initPopup("Создать", sourceView));

        correctSource.setOnAction(event -> {
            if (sourceView.getSelectionModel().getSelectedItem() == null || sourceView.getSelectionModel().getSelectedItems().size() > 1) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Выберите источник", null, null);
                return;
            }
            initPopup("Изменить", sourceView);
        });

        removeSource.setOnAction(event -> {
            if (sourceView.getSelectionModel().getSelectedItem() == null) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Выберите источник", null, null);
                return;
            }

            boolean isRemove = Helper.makeRemoveDialog(Alert.AlertType.INFORMATION,
                    "Удалить выбранный источник?", null, null, Constants.OK_BUTTON, Constants.CANCEL_BUTTON);

            if (isRemove) {

                int selectedSize = sourceView.getSelectionModel().getSelectedItems().size();

                if (selectedSize == 1) {
                    SingleSource deleted = singleSources.get(sourceView.getSelectionModel().getSelectedIndex());
                    clientService.deleteSingleSource(deleted.getId());
                    sources.remove(sourceView.getSelectionModel().getSelectedItem());
                    singleSources.remove(deleted);
                } else {
                    //TODO:удаление всех источников и фрагментов
                }
            }
        });
    }

    private void initPopup(String button, ListView<String> sourceView) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 0, 50, 0));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints(115, 115, Double.MAX_VALUE);
        ColumnConstraints col2 = new ColumnConstraints(235, 235, Double.MAX_VALUE);
        gridPane.getColumnConstraints().addAll(col1, col2);

        Label name = new Label("Название");
        GridPane.setHalignment(name, HPos.RIGHT);
        TextField nameField = new TextField();
        nameField.setMaxSize(200, 15);
        Button operation = new Button(button);

        gridPane.add(name, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(operation, 1, 1);

        Scene createScene = new Scene(gridPane, 350, 200);

        Stage stage = new Stage();
        stage.setTitle("Создание источника");
        stage.setResizable(false);
        stage.setScene(createScene);

        if (button.equals("Изменить")) {
            nameField.setText(sourceView.getSelectionModel().getSelectedItem());
            stage.setTitle("Изменение источника");
        }

        operation.setOnAction(event1 -> {
            String sourceName = nameField.getText();

            if (sourceName.equals("")) {
                Helper.makeInformationWindow(Alert.AlertType.ERROR, "Введите название источника", null, null);
                return;
            }
            if (button.equals("Создать")) {
                clientService.createSingleSource(sourceName);
                singleSources.add(clientService.getSingleSource(sourceName));
                sources.add(sourceName);
            }
            else if (button.equals("Изменить")) {
                SingleSource updated = singleSources.get(sourceView.getSelectionModel().getSelectedIndex());
                updated.setSingleName(sourceName);
                clientService.updateSingleSource(updated.getId(), sourceName);
                sources.set(sourceView.getSelectionModel().getSelectedIndex(), sourceName);
                singleSources.set(sourceView.getSelectionModel().getSelectedIndex(), updated);
            }
            stage.close();
        });

        stage.show();
    }

    private void makeFragmentPage(ListView<String> sourceView) {
        sourceView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FragmentPage fragmentPage = new FragmentPage(this, stage, singleSources.get(sourceView.getSelectionModel().getSelectedIndex()));
                stage.setScene(fragmentPage.getScene());
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}
