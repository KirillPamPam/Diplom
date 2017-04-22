package ru.kir.diplom.client.ui.pages;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.kir.diplom.client.service.http.RestClientService;
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
    private RestClientService clientService = RestClientService.getInstance();

    public StyleOperationPage(Stage stage) {
        this.stage = stage;

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

        Label textSize = new Label("Размер текста");
        Label labelSectSize = new Label("Размер заголовка");
        Label fontFamily = new Label("Шрифт");
        Label interval = new Label("Межстрочный интервал");
        Label jcLabel = new Label("Выравнивание текста");
        Label jcSect = new Label("Выравнивание заголовка");
        Label boldSect = new Label("Полужирный");
        Label italicSect = new Label("Курсив");

        ComboBox<String> sizes = new ComboBox<>(WordHelper.initSizes());
        ComboBox<String> sizesSect = new ComboBox<>(WordHelper.initSizes());
        ComboBox<String> fonts = new ComboBox<>(WordHelper.initFonts());
        fonts.setPrefWidth(150);
        ComboBox<String> intervals = new ComboBox<>(WordHelper.initIntervals());
        ComboBox<String> jc = new ComboBox<>(WordHelper.initJc());
        jc.setPrefWidth(150);
        ComboBox<String> jcSectBox = new ComboBox<>(WordHelper.initJc());
        jc.setPrefWidth(150);
        CheckBox sectBold = new CheckBox();
        CheckBox sectItalic = new CheckBox();

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

        Label mainLabel = new Label("Общее");
        mainLabel.setFont(Font.font("Arial", 18));
        GridPane.setHalignment(mainLabel, HPos.CENTER);

        Label sectionLabel = new Label("Разделы");
        sectionLabel.setFont(Font.font("Arial", 18));
        GridPane.setHalignment(sectionLabel, HPos.CENTER);

        Label otherLabel = new Label("Подразделы, пункты, подпункты");
        otherLabel.setFont(Font.font("Arial", 18));
        GridPane.setHalignment(otherLabel, HPos.CENTER);

        gridPane.add(mainLabel, 1, 0);
        gridPane.add(textB, 0, 1);
        gridPane.add(fontB, 1, 1);
        gridPane.add(intervalB, 2, 1);
        gridPane.add(jcB, 0, 2);
        gridPane.add(sectionLabel, 1, 3);
        gridPane.add(sectB, 0, 5);
        gridPane.add(boldSectB, 1, 4);
        gridPane.add(italicSectB, 2, 4);
        gridPane.add(sizeSectB, 0, 4);
        gridPane.add(create, 1, 6);

        create.setOnAction(event -> {
            Map<String, String> prop = new HashMap<>();

            prop.put(WordConstants.TEXT_SIZE, sizes.getValue());
            prop.put(WordConstants.TEXT_FONT, fonts.getValue());
            prop.put(WordConstants.INTERVAL, intervals.getValue());
            prop.put(WordConstants.TEXT_JC, jc.getValue());

            String propText = "";
            StringBuilder builder = new StringBuilder(propText);

            prop.forEach((key, property) -> {
                builder.append(key).append(":").append(property).append(";");
            });

            clientService.createStyle("test", String.valueOf(builder));
        });

        scene = new Scene(gridPane);
    }

    public Scene getScene() {
        return scene;
    }
}
