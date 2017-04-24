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
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Helper;
import ru.kir.diplom.client.util.WordConstants;
import ru.kir.diplom.client.word.WordHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kirill Zhitelev on 22.04.2017.
 */
public class StyleOperationPage {
    private Scene scene;
    private Stage stage;
    private Button create = new Button("Создать");
    private Button back = new Button("Назад");
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

    public StyleOperationPage(Stage stage, StylePage stylePage) {
        this.stage = stage;
        this.stylePage = stylePage;

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
        buttonB.getChildren().addAll(create, back);

        GridPane.setHalignment(create, HPos.CENTER);

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

        handleBut();

        scene = new Scene(gridPane);
    }

    private void handleBut() {
        create.setOnAction(event -> {
            String name = nameField.getText();
            if (name.equals("")) {
                Helper.makeInformationWindow(Alert.AlertType.ERROR, "Введите название стиля", null, null);
                return;
            }

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

            clientService.createStyle(name, String.valueOf(builder));
            stylePage.getStyles().add(clientService.getStyleByName(name));
            stylePage.getStylesCollection().add(name);
            back.fire();
        });

        back.setOnAction(event -> stage.setScene(stylePage.getScene()));
    }

    public Scene getScene() {
        return scene;
    }
}
