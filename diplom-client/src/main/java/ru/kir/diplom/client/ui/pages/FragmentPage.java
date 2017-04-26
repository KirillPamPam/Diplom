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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import ru.kir.diplom.client.model.SingleSource;
import ru.kir.diplom.client.model.TextFragment;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Constants;
import ru.kir.diplom.client.util.Helper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kirill Zhitelev on 09.04.2017.
 */
public class FragmentPage {
    private RestClientService clientService = RestClientService.getInstance();
    private Scene scene;
    private Stage stage;
    private Button addFragment = new Button("Создать");
    private Button removeFragment = new Button("Удалить");
    private Button toMainPage = new Button("На главную");
    private Button toCreationDoc = new Button("Создать документ");
    private Button toStyles = new Button("Стили");
    private List<TextFragment> fragments;
    private ObservableList<String> fragmentsCollection = FXCollections.observableArrayList();
    private MainPage mainPage;
    private String singleSourceName;
    private ListView<String> fragmentView;

    public FragmentPage(MainPage mainPage, Stage stage, SingleSource singleSource) {
        this.stage = stage;
        this.mainPage = mainPage;

        singleSourceName = singleSource.getSingleName();
        fragments = clientService.getAllTextFragments(singleSourceName);

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

        VBox fragmentButtons = new VBox(15);
        fragmentButtons.setAlignment(Pos.CENTER_RIGHT);
        fragmentButtons.getChildren().addAll(addFragment, removeFragment);

        Label fragmentLabel = new Label("Фрагменты источника");
        fragmentLabel.setFont(Font.font("Arial", 15));
        GridPane.setHalignment(fragmentLabel, HPos.CENTER);

        fragmentsCollection.addAll(fragments.stream().map(TextFragment::getFragmentName).collect(Collectors.toList()));
        fragmentView = new ListView<>(fragmentsCollection);
        fragmentView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        VBox rightButtons = new VBox(15);
        toMainPage.setPrefSize(80, 60);
        toCreationDoc.setPrefSize(80, 60);
        toStyles.setPrefSize(80, 60);
        toCreationDoc.setWrapText(true);
        toCreationDoc.setTextAlignment(TextAlignment.CENTER);
        rightButtons.setAlignment(Pos.CENTER);
        rightButtons.getChildren().addAll(toCreationDoc, toStyles, toMainPage);

        gridPane.add(fragmentLabel, 1, 0);
        gridPane.add(fragmentButtons, 0, 1);
        gridPane.add(fragmentView, 1, 1);
        gridPane.add(rightButtons, 2, 1);

        handleButtons(fragmentView);
        onMouseClick(fragmentView);

        scene = new Scene(gridPane);
    }

    private void handleButtons(ListView<String> fragmentView) {
        toMainPage.setOnAction(event -> stage.setScene(mainPage.getScene()));

        toStyles.setOnAction(event -> stage.setScene(new StylePage(stage, this).getScene()));

        addFragment.setOnAction(event -> toOperationPage(null, Constants.CREATE_OPERATION));

        removeFragment.setOnAction(event -> {
            if (fragmentView.getSelectionModel().getSelectedItem() == null) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Выберите фрагмент", null, null);
                return;
            }

            boolean isRemove = Helper.makeRemoveDialog(Alert.AlertType.INFORMATION,
                    "Удалить выбранный фрагмент?", null, null, Constants.OK_BUTTON, Constants.CANCEL_BUTTON);

            if (isRemove) {
                TextFragment deleted = fragments.get(fragmentView.getSelectionModel().getSelectedIndex());
                clientService.deleteTextFragment(deleted.getId());
                fragments.remove(deleted);
                fragmentsCollection.remove(fragmentView.getSelectionModel().getSelectedItem());
            }
        });

        toCreationDoc.setOnAction(event -> stage.setScene(new DocumentBuildPage(stage, this).getScene()));
    }

    private void onMouseClick(ListView<String> fragmentView) {
        fragmentView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                toOperationPage(fragmentView, Constants.READ_OPERATION);
            }
        });
    }

    private void toOperationPage(ListView<String> fragmentView, String operation) {
        if (fragmentView != null) {
            int selectedIndex = fragmentView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                TextFragment textFragment = fragments.get(selectedIndex);
                FragmentOperationPage operationPage = new FragmentOperationPage(this, stage, textFragment, operation);
                stage.setScene(operationPage.getScene());
            }
        }
        else {
            FragmentOperationPage operationPage = new FragmentOperationPage(this, stage, null, operation);
            stage.setScene(operationPage.getScene());
        }
    }

    public Scene getScene() {
        return scene;
    }

    public String getSingleSourceName() {
        return singleSourceName;
    }

    public List<TextFragment> getFragments() {
        return fragments;
    }

    public ObservableList<String> getFragmentsCollection() {
        return fragmentsCollection;
    }

    public ListView<String> getFragmentView() {
        return fragmentView;
    }
}
