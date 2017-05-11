package ru.kir.diplom.client.ui.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.kir.diplom.client.model.Style;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Constants;
import ru.kir.diplom.client.util.Helper;
import ru.kir.diplom.client.util.WordConstants;
import ru.kir.diplom.client.word.WordHelper;

import java.util.*;

/**
 * Created by Kirill Zhitelev on 22.04.2017.
 */
public class StyleOperationPage {
    private Scene scene;
    private Stage stage;
    private Button create = new Button("Создать");
    private Button back = new Button("Назад");
    private Button correct = new Button("Изменить");
    private RestClientService clientService = RestClientService.getInstance();
    private TextField rightField = new TextField();
    private TextField leftField = new TextField();
    private TextField topField = new TextField();
    private TextField botField = new TextField();
    private TextField nameField = new TextField();
    private ComboBox<String> sizes = new ComboBox<>(WordHelper.initSizes());
    private ComboBox<String> sizesSect = new ComboBox<>(WordHelper.initSizes());
    private ComboBox<String> sizesOther = new ComboBox<>(WordHelper.initSizes());
    private ComboBox<String> fonts = new ComboBox<>(WordHelper.initFonts());
    private ComboBox<String> intervals = new ComboBox<>(WordHelper.initIntervals());
    private ComboBox<String> jc = new ComboBox<>(WordHelper.initJc());
    private ComboBox<String> jcSectBox = new ComboBox<>(WordHelper.initJc());
    private CheckBox sectBold = new CheckBox();
    private CheckBox sectItalic = new CheckBox();
    private CheckBox otherBold = new CheckBox();
    private CheckBox otherItalic = new CheckBox();
    private StylePage stylePage;
    private String operation;
    private Style style;

    public StyleOperationPage(Stage stage, Style style, StylePage stylePage, String operation) {
        this.stage = stage;
        this.stylePage = stylePage;
        this.operation = operation;
        this.style = style;

        init();
    }

    private void init() {
        GridPane gridPane = new GridPane();
        //  gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(10);
        gridPane.setVgap(20);

        ColumnConstraints col1 = new ColumnConstraints(300, 300, Double.MAX_VALUE);
        ColumnConstraints col2 = new ColumnConstraints(300, 300, Double.MAX_VALUE);
        ColumnConstraints col3 = new ColumnConstraints(300, 300, Double.MAX_VALUE);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        Label textSize = new Label("Размер текста");
        Label labelSectSize = new Label("Размер заголовка");
        Label labelOtherSize = new Label("Размер заголовка");
        Label fontFamily = new Label("Шрифт");
        Label interval = new Label("Межстрочный интервал");
        Label jcLabel = new Label("Выравнивание текста");
        Label jcSect = new Label("Выравнивание заголовка");
        Label boldSect = new Label("Полужирный");
        Label italicSect = new Label("Курсив");
        Label boldOther = new Label("Полужирный");
        Label italicOther = new Label("Курсив");
        Label marginsLabel = new Label("Поля");
        Label topMarginsLabel = new Label("Верхнее");
        Label botMarginsLabel = new Label("Нижнее");
        Label leftMarginsLabel = new Label("Левое");
        Label rightMarginsLabel = new Label("Правое");
        Label nameStyle = new Label("Название");

        nameField.setMaxSize(200, 15);
        topField.setPrefWidth(40);
        botField.setPrefWidth(40);
        leftField.setPrefWidth(40);
        rightField.setPrefWidth(40);

        fonts.setPrefWidth(150);
        jc.setPrefWidth(150);
        jc.setPrefWidth(150);

        HBox nameB = new HBox(10);
        nameB.setAlignment(Pos.CENTER);
        nameB.getChildren().addAll(nameStyle, nameField);

        HBox marginFirstB = new HBox(10);
        marginFirstB.setAlignment(Pos.CENTER);
        marginFirstB.getChildren().addAll(topMarginsLabel, topField, botMarginsLabel, botField);

        HBox marginSecondB = new HBox(10);
        marginSecondB.setAlignment(Pos.CENTER);
        marginSecondB.getChildren().addAll(leftMarginsLabel, leftField, rightMarginsLabel, rightField);

        VBox margins = new VBox(15);
        margins.setAlignment(Pos.CENTER);
        margins.getChildren().addAll(marginFirstB, marginSecondB);

        HBox marginB = new HBox(10);
        marginB.setAlignment(Pos.CENTER);
        marginB.getChildren().addAll(marginsLabel, margins);

        HBox textB = new HBox(10);
        textB.setAlignment(Pos.CENTER);
        textB.getChildren().addAll(textSize, sizes);

        HBox fontB = new HBox(10);
        fontB.setAlignment(Pos.CENTER);
        fontB.getChildren().addAll(fontFamily, fonts);

        HBox intervalB = new HBox(10);
        intervalB.setAlignment(Pos.CENTER_LEFT);
        intervalB.getChildren().addAll(interval, intervals);

        HBox jcB = new HBox(10);
        jcB.setAlignment(Pos.CENTER);
        jcB.getChildren().addAll(jcLabel, jc);

        HBox sectB = new HBox(10);
        sectB.setAlignment(Pos.CENTER);
        sectB.getChildren().addAll(jcSect, jcSectBox);

        HBox boldSectB = new HBox(10);
        boldSectB.setAlignment(Pos.CENTER);
        boldSectB.getChildren().addAll(boldSect, sectBold);

        HBox italicSectB = new HBox(10);
        italicSectB.setAlignment(Pos.CENTER);
        italicSectB.getChildren().addAll(italicSect, sectItalic);

        HBox sizeSectB = new HBox(10);
        sizeSectB.setAlignment(Pos.CENTER);
        sizeSectB.getChildren().addAll(labelSectSize, sizesSect);

        HBox sizeOtherB = new HBox(10);
        sizeOtherB.setAlignment(Pos.CENTER);
        sizeOtherB.getChildren().addAll(labelOtherSize, sizesOther);

        HBox boldOtherB = new HBox(10);
        boldOtherB.setAlignment(Pos.CENTER);
        boldOtherB.getChildren().addAll(boldOther, otherBold);

        HBox italicOtherB = new HBox(10);
        italicOtherB.setAlignment(Pos.CENTER);
        italicOtherB.getChildren().addAll(italicOther, otherItalic);

        Label mainLabel = new Label("Общее");
        mainLabel.setFont(Font.font("Arial", 18));
        GridPane.setHalignment(mainLabel, HPos.CENTER);

        Label sectionLabel = new Label("Разделы");
        sectionLabel.setFont(Font.font("Arial", 18));
        GridPane.setHalignment(sectionLabel, HPos.CENTER);

        Label otherLabel = new Label("Подразделы, пункты, подпункты");
        otherLabel.setFont(Font.font("Arial", 18));
        GridPane.setHalignment(otherLabel, HPos.CENTER);

        HBox buttonB = new HBox(10);
        buttonB.setAlignment(Pos.CENTER);
        buttonB.getChildren().addAll(back);

        if (operation.equals(Constants.CREATE_OPERATION)) {
            initDefaultValues();
            buttonB.getChildren().add(0, create);
        }
        else if (operation.equals(Constants.READ_OPERATION)) {
            Map<String, String> properties = getProp();
            initStyleValues(properties);
            setDisable(true);
            buttonB.getChildren().add(0, correct);
        }

        gridPane.add(nameB, 1, 0);
        gridPane.add(mainLabel, 1, 1);
        gridPane.add(textB, 0, 2);
        gridPane.add(fontB, 1, 2);
        gridPane.add(intervalB, 2, 2);
        gridPane.add(jcB, 0, 3);
        gridPane.add(sectionLabel, 1, 4);
        gridPane.add(sectB, 0, 6);
        gridPane.add(boldSectB, 1, 5);
        gridPane.add(italicSectB, 2, 5);
        gridPane.add(sizeSectB, 0, 5);
        gridPane.add(marginB, 1, 3);
        gridPane.add(otherLabel, 1, 7);
        gridPane.add(sizeOtherB, 0, 8);
        gridPane.add(boldOtherB, 1, 8);
        gridPane.add(italicOtherB, 2, 8);
        gridPane.add(buttonB, 1, 9);

        handleBut();

        scene = new Scene(gridPane);
    }

    private void setDisable(boolean value) {
        nameField.setEditable(!value);
        sizes.setDisable(value);
        sizes.setOpacity(1);
        fonts.setDisable(value);
        fonts.setOpacity(1);
        intervals.setDisable(value);
        intervals.setOpacity(1);
        jc.setDisable(value);
        jc.setOpacity(1);
        botField.setEditable(!value);
        topField.setEditable(!value);
        rightField.setEditable(!value);
        leftField.setEditable(!value);
        jcSectBox.setDisable(value);
        jcSectBox.setOpacity(1);
        sizesSect.setDisable(value);
        sizesSect.setOpacity(1);
        sizesOther.setDisable(value);
        sizesOther.setOpacity(1);
        sectBold.setDisable(value);
        sectBold.setOpacity(1);
        sectItalic.setDisable(value);
        sectItalic.setOpacity(1);
        otherBold.setDisable(value);
        otherBold.setOpacity(1);
        otherItalic.setDisable(true);
        otherItalic.setOpacity(1);
    }

    private void initDefaultValues() {
        sizes.setValue("12");
        fonts.setValue(WordConstants.TIMES_NEW_ROMAN);
        intervals.setValue("1.15");
        jc.setValue(WordConstants.BOTH);
        botField.setText("1.5");
        leftField.setText("2");
        rightField.setText("1");
        topField.setText("2.5");
        jcSectBox.setValue(WordConstants.CENTER);
        sizesSect.setValue("14");
        sizesOther.setValue("14");
    }

    private void initStyleValues(Map<String, String> properties) {
        nameField.setText(style.getName());
        sizes.setValue(properties.get(WordConstants.TEXT_SIZE));
        fonts.setValue(properties.get(WordConstants.TEXT_FONT));
        intervals.setValue(properties.get(WordConstants.INTERVAL));
        jc.setValue(properties.get(WordConstants.TEXT_JC));
        botField.setText(properties.get(WordConstants.MARGIN_BOT));
        leftField.setText(properties.get(WordConstants.MARGIN_LEFT));
        rightField.setText(properties.get(WordConstants.MARGIN_RIGHT));
        topField.setText(properties.get(WordConstants.MARGIN_TOP));
        jcSectBox.setValue(properties.get(WordConstants.SECTION_JC));
        sizesSect.setValue(properties.get(WordConstants.SECTION_SIZE));
        sizesOther.setValue(properties.get(WordConstants.OTHER_SIZE));
        sectBold.setSelected(Boolean.parseBoolean(properties.get(WordConstants.SECTION_BOLD)));
        sectItalic.setSelected(Boolean.parseBoolean(properties.get(WordConstants.SECTION_ITALIC)));
        otherBold.setSelected(Boolean.parseBoolean(properties.get(WordConstants.OTHER_BOLD)));
        otherItalic.setSelected(Boolean.parseBoolean(properties.get(WordConstants.OTHER_ITALIC)));
    }

    private Map<String, String> getProp() {
        Map<String, String> properties = new HashMap<>();
        List<String> prop = new ArrayList<>(Arrays.asList(style.getProperties().split(";")));

        prop.forEach(property -> {
            String[] wordProperties = property.split(":");
            String key = wordProperties[0];
            String value = wordProperties[1];

            properties.put(key, value);
        });

        return properties;
    }

    private void handleBut() {
        create.setOnAction(event -> {
            String name = nameField.getText();
            if (isFilledTextFields()) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Заполните все поля", null, null, null);
                return;
            }

            clientService.createStyle(name, getProperties());
            stylePage.getStyles().add(clientService.getStyleByName(name));
            stylePage.getStylesCollection().add(name);
            back.fire();
        });

        correct.setOnAction(event -> {
            if (correct.getText().equals("Изменить")) {
                setDisable(false);
                correct.setText("Сохранить");
            }
            else if (correct.getText().equals("Сохранить")) {
                correct.setText("Изменить");

                if (isFilledTextFields()) {
                    Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Заполните все поля", null, null, null);
                }

                String name = nameField.getText();
                String prop = getProperties();

                style.setName(name);
                style.setProperties(prop);

                clientService.updateStyle(style.getId(), name, prop);
                stylePage.getStyles().set(stylePage.getStylesView().getSelectionModel().getSelectedIndex(), style);
                stylePage.getStylesCollection().set(stylePage.getStylesView().getSelectionModel().getSelectedIndex(), name);

                back.fire();
            }
        });

        back.setOnAction(event -> stage.setScene(stylePage.getScene()));
    }

    private boolean isFilledTextFields() {
        return nameField.getText().equals("") || botField.getText().equals("") || topField.getText().equals("") ||
                rightField.getText().equals("") || leftField.getText().equals("");
    }

    private String getProperties() {
        Map<String, String> prop = new HashMap<>();

        prop.put(WordConstants.TEXT_SIZE, sizes.getValue());
        prop.put(WordConstants.TEXT_FONT, fonts.getValue());
        prop.put(WordConstants.INTERVAL, intervals.getValue());
        prop.put(WordConstants.TEXT_JC, jc.getValue());
        prop.put(WordConstants.MARGIN_BOT, botField.getText());
        prop.put(WordConstants.MARGIN_LEFT, leftField.getText());
        prop.put(WordConstants.MARGIN_RIGHT, rightField.getText());
        prop.put(WordConstants.MARGIN_TOP, topField.getText());
        prop.put(WordConstants.SECTION_BOLD, String.valueOf(sectBold.isSelected()));
        prop.put(WordConstants.SECTION_ITALIC, String.valueOf(sectItalic.isSelected()));
        prop.put(WordConstants.SECTION_JC, jcSectBox.getValue());
        prop.put(WordConstants.SECTION_SIZE, sizesSect.getValue());
        prop.put(WordConstants.OTHER_BOLD, String.valueOf(otherBold.isSelected()));
        prop.put(WordConstants.OTHER_ITALIC, String.valueOf(otherItalic.isSelected()));
        prop.put(WordConstants.OTHER_SIZE, sizesOther.getValue());

        String properties = "";
        StringBuilder builder = new StringBuilder(properties);

        prop.forEach((key, property) -> builder.append(key).append(":").append(property).append(";"));

        return String.valueOf(builder);
    }

    public Scene getScene() {
        return scene;
    }
}
