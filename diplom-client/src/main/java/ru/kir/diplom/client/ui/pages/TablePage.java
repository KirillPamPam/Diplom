package ru.kir.diplom.client.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import ru.kir.diplom.client.model.DocPattern;
import ru.kir.diplom.client.model.TableDocPattern;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public class TablePage {
    private Scene scene;
    private Stage stage;
    private Button back = new Button("Назад");
    private Button choose = new Button("Выбрать");
    private ObservableList<TableDocPattern> docPatterns = FXCollections.observableArrayList();
    private TableView<TableDocPattern> table = new TableView<>();
    private TableColumn<TableDocPattern, String> name = new TableColumn<>("Название");
    private TableColumn<TableDocPattern, String> fragments = new TableColumn<>("Фрагменты");
    private TableColumn<TableDocPattern, String> style = new TableColumn<>("Стиль");
    private TableColumn<TableDocPattern, String> luValue = new TableColumn<>("Обозначение ЛУ");
    private FragmentPage fragmentPage;

    public TablePage(Stage stage, FragmentPage fragmentPage) {
        this.stage = stage;
        this.fragmentPage = fragmentPage;

        init();
    }

    private void init() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        name.setPrefWidth(200);
        fragments.setPrefWidth(400);
        style.setPrefWidth(100);
        luValue.setPrefWidth(155);

        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(choose, back);

        table.setPrefHeight(400);
        table.getColumns().addAll(name, fragments, style, luValue);

        ColumnConstraints col1 = new ColumnConstraints(860, 800, Double.MAX_VALUE);
        gridPane.getColumnConstraints().addAll(col1);

        initParams();

        table.setItems(docPatterns);

        gridPane.add(table, 0, 0);
        gridPane.add(box, 0, 1);

        back.setOnAction(event -> stage.setScene(new DocumentBuildPage(stage, fragmentPage).getScene()));

        choose.setOnAction(event -> {
            TableDocPattern docPattern = table.getSelectionModel().getSelectedItem();
            DocumentBuildPage buildPage = new DocumentBuildPage(stage, fragmentPage);
            List<String> selected = Arrays.asList(docPattern.getFragments().split(";"));

            buildPage.getLuField().setText(docPattern.getLuValue());
            buildPage.getSelected().addAll(selected);
            buildPage.getStyles().setValue(docPattern.getStyle());

            selected.forEach(select -> buildPage.getPreSelect().remove(select));

            stage.setScene(buildPage.getScene());
        });

        scene = new Scene(gridPane);
    }

    public void initData(List<DocPattern> patterns) {
        ModelMapper mapper = new ModelMapper();
        Converter<DocPattern, String> converter = new AbstractConverter<DocPattern, String>() {
            @Override
            protected String convert(DocPattern docPattern) {
                return docPattern.getStyle().getName();
            }
        };
        mapper.addMappings(new PropertyMap<DocPattern, TableDocPattern>() {
            @Override
            protected void configure() {
                using(converter).map(source).setStyle(null);
            }
        });
        patterns.forEach(pattern -> docPatterns.add(mapper.map(pattern, TableDocPattern.class)));
    }

    private void initParams() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        fragments.setCellValueFactory(new PropertyValueFactory<>("fragments"));
        style.setCellValueFactory(new PropertyValueFactory<>("style"));
        luValue.setCellValueFactory(new PropertyValueFactory<>("luValue"));

        name.setCellFactory(param -> {
            TableCell<TableDocPattern, String> tableCell = new TableCell<>();
            Text text = new Text();
            tableCell.setGraphic(text);
            tableCell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(name.widthProperty());
            text.textProperty().bind(tableCell.itemProperty());
            return tableCell;
        });
        fragments.setCellFactory(param -> {
            TableCell<TableDocPattern, String> tableCell = new TableCell<>();
            Text text = new Text();
            tableCell.setGraphic(text);
            tableCell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(fragments.widthProperty());
            text.textProperty().bind(tableCell.itemProperty());
            return tableCell;
        });
    }

    public Scene getScene() {
        return scene;
    }
}
