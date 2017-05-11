package ru.kir.diplom.client.ui.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ru.kir.diplom.client.model.TextFragment;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Constants;
import ru.kir.diplom.client.util.Helper;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 09.04.2017.
 */
public class FragmentOperationPage {
    private Scene scene;
    private Stage stage;
    private RestClientService clientService = RestClientService.getInstance();
    private TextFragment textFragment;
    private String operation;
    private Button add = new Button("Создать");
    private Button cancel = new Button("Отмена");
    private Button update = new Button("Изменить");
    private Button help = new Button("?");
    private FragmentPage fragmentPage;
    private TextField nameField = new TextField();
    private TextArea textArea = new TextArea();

    public FragmentOperationPage(FragmentPage fragmentPage, Stage stage, TextFragment textFragment, String operation) {
        this.stage = stage;
        this.textFragment = textFragment;
        this.operation = operation;
        this.fragmentPage = fragmentPage;

        init();
    }

    private void init() {
        GridPane gridPane = new GridPane();
        //  gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints(120, 300, Double.MAX_VALUE);
        ColumnConstraints col2 = new ColumnConstraints(660, 300, Double.MAX_VALUE);
        ColumnConstraints col3 = new ColumnConstraints(120, 300, Double.MAX_VALUE);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        Label name = new Label("Название фрагмента");
        Label text = new Label("Текст фрагмента");
        nameField.setMaxWidth(300);
        textArea.setPrefHeight(350);
        textArea.setWrapText(true);

        GridPane.setHalignment(name, HPos.RIGHT);
        GridPane.setHalignment(text, HPos.RIGHT);

        gridPane.add(name, 0, 0);
        gridPane.add(text, 0, 1);
        gridPane.add(nameField, 1, 0);
        gridPane.add(textArea, 1, 1);
        gridPane.add(help, 2, 1);

        if (Objects.equals(operation, Constants.READ_OPERATION)) {
            nameField.setText(textFragment.getFragmentName());
            textArea.setText(textFragment.getText());

            nameField.setEditable(false);
            textArea.setEditable(false);

            HBox createBut = new HBox(15);
            createBut.setAlignment(Pos.CENTER);
            createBut.getChildren().addAll(update, cancel);

            gridPane.add(createBut, 1, 2);
        }
/*        else if (Objects.equals(operation, Constants.UPDATE_OPERATION)) {
            nameField.setText(textFragment.getFragmentName());
            textArea.setText(textFragment.getText());

            HBox createBut = new HBox(15);
            createBut.setAlignment(Pos.CENTER);
            createBut.getChildren().addAll(update, cancel);

            gridPane.add(createBut, 1, 2);
        }*/
        else if (Objects.equals(operation, Constants.CREATE_OPERATION)) {
            HBox createBut = new HBox(15);
            createBut.setAlignment(Pos.CENTER);
            createBut.getChildren().addAll(add, cancel);

            gridPane.add(createBut, 1, 2);
        }

        handleButtons();

        scene = new Scene(gridPane);
    }

    private void handleButtons() {
        add.setOnAction(event -> {
            String name = nameField.getText();
            String text = textArea.getText();

            if (!Helper.checkFragmentName(name)) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Название фрагмента должно начинаться с буквы", null, null, null);
                return;
            }

            if (name.equals("") || text.equals("")) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Заполните все поля", null, null, null);
                return;
            }

            clientService.createTextFragment(name, text, fragmentPage.getSingleSourceName());
            fragmentPage.getFragments().add(clientService.getTextFragmentByName(name));
            fragmentPage.getFragmentsCollection().add(name);
            cancel.fire();
        });

        cancel.setOnAction(event -> stage.setScene(fragmentPage.getScene()));

        help.setOnAction(event -> Helper.makeInformationWindow(Alert.AlertType.INFORMATION, Constants.FORMAT, null, "Форматирование текста", 800.0));

        update.setOnAction(event -> {
            if (update.getText().equals("Изменить")) {
                update.setText("Сохранить");
                nameField.setEditable(true);
                textArea.setEditable(true);
            }
            else if (update.getText().equals("Сохранить")) {
                String name = nameField.getText();
                String text = textArea.getText();

                if (!Helper.checkFragmentName(name)) {
                    Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Название фрагмента должно начинаться с буквы", null, null, null);
                    return;
                }

                TextFragment textFragment = fragmentPage.getFragments().get(fragmentPage.getFragmentView().getSelectionModel().getSelectedIndex());
                textFragment.setText(text);
                textFragment.setFragmentName(name);

                clientService.updateTextFragment(textFragment.getId(), text, name);
                fragmentPage.getFragments().set(fragmentPage.getFragmentView().getSelectionModel().getSelectedIndex(), textFragment);
                fragmentPage.getFragmentsCollection().set(fragmentPage.getFragmentView().getSelectionModel().getSelectedIndex(), name);
                cancel.fire();
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}
