package ru.kir.diplom.client.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.kir.diplom.client.model.Style;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Constants;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kirill Zhitelev on 20.04.2017.
 */
public class StylePage {
    private Stage stage;
    private Scene scene;
    private RestClientService clientService = RestClientService.getInstance();
    private ListView<String> stylesView;
    private ObservableList<String> stylesCollection = FXCollections.observableArrayList();
    private List<Style> styles;
    private Button addStyle = new Button("Создать");
    private Button removeStyle = new Button("Удалить");
    private Button back = new Button("Назад");
    private FragmentPage fragmentPage;

    public StylePage(Stage stage, FragmentPage fragmentPage) {
        this.stage = stage;
        this.fragmentPage = fragmentPage;

        styles = clientService.getAllStyles();

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

        stylesCollection.addAll(styles.stream().map(Style::getName).collect(Collectors.toList()));
        stylesView = new ListView<>(stylesCollection);

        Label styleLabel = new Label("Стили");
        styleLabel.setFont(Font.font("Arial", 15));
        GridPane.setHalignment(styleLabel, HPos.CENTER);

        VBox styleButtons = new VBox(15);
        styleButtons.setAlignment(Pos.CENTER_RIGHT);
        styleButtons.getChildren().addAll(addStyle, removeStyle, back);

        gridPane.add(styleButtons, 0, 1);
        gridPane.add(styleLabel, 1, 0);
        gridPane.add(stylesView, 1, 1);

        handleBut(stylesView);

        scene = new Scene(gridPane);
    }

    private void handleBut(ListView<String> view) {
        addStyle.setOnAction(event -> stage.setScene(new StyleOperationPage(stage, null, this, Constants.CREATE_OPERATION).getScene()));

        view.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = view.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    Style style = styles.get(selectedIndex);
                    stage.setScene(new StyleOperationPage(stage, style, this, Constants.READ_OPERATION).getScene());
                }
            }
        });

        back.setOnAction(event -> stage.setScene(fragmentPage.getScene()));
    }

    public ObservableList<String> getStylesCollection() {
        return stylesCollection;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public ListView<String> getStylesView() {
        return stylesView;
    }

    public Scene getScene() {
        return scene;
    }
}
