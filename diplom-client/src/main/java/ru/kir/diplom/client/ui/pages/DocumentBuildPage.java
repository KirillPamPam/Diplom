package ru.kir.diplom.client.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.STBrType;
import ru.kir.diplom.client.model.TextFragment;
import ru.kir.diplom.client.model.TextProperties;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Helper;
import ru.kir.diplom.client.util.WordConstants;
import ru.kir.diplom.client.word.WordHelper;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Kirill Zhitelev on 15.04.2017.
 */
public class DocumentBuildPage {
    private Stage stage;
    private Scene scene;
    private FragmentPage fragmentPage;
    private ListView<String> allFragments;
    private ListView<String> chosenFragments;
    private Button create = new Button("Создать");
    private Button back = new Button("Назад");
    private ObservableList<String> selected = FXCollections.observableArrayList();
    private ObservableList<String> preSelect = FXCollections.observableArrayList();
    private RestClientService clientService = RestClientService.getInstance();

    public DocumentBuildPage(Stage stage, FragmentPage fragmentPage) {
        this.stage = stage;
        this.fragmentPage = fragmentPage;

        preSelect.addAll(clientService.getAllTextFragments(fragmentPage.getSingleSourceName())
                .stream().map(TextFragment::getFragmentName).collect(Collectors.toList()));

        init();
    }

    private void init() {
        GridPane gridPane = new GridPane();
        //gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints(325, 300, Double.MAX_VALUE);
        ColumnConstraints col2 = new ColumnConstraints(50, 300, Double.MAX_VALUE);
        ColumnConstraints col3 = new ColumnConstraints(325, 300, Double.MAX_VALUE);
        ColumnConstraints col4 = new ColumnConstraints(200, 300, Double.MAX_VALUE);
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        allFragments = new ListView<>(preSelect);
        chosenFragments = new ListView<>(selected);

        Label all = new Label("Все фрагменты");
        Label chosen = new Label("Выбранные фрагменты");

        VBox rightBut = new VBox(10);
        rightBut.getChildren().addAll(create, back);
        rightBut.setAlignment(Pos.CENTER_LEFT);

        GridPane.setHalignment(all, HPos.CENTER);
        GridPane.setHalignment(chosen, HPos.CENTER);

        gridPane.add(all, 0, 0);
        gridPane.add(chosen, 2, 0);
        gridPane.add(allFragments, 0, 1);
        gridPane.add(chosenFragments, 2, 1);
        gridPane.add(rightBut, 3, 1);

        handleBut(rightBut);
        dragAndDrop();

        chosenFragments.setOnDragDone(Event::consume);

        scene = new Scene(gridPane);
    }

    private void dragAndDrop() {
        chosenFragments.setOnDragDetected(event -> {
            Dragboard dragboard = chosenFragments.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(chosenFragments.getSelectionModel().getSelectedItem());
            dragboard.setContent(content);

            event.consume();
        });

        chosenFragments.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        chosenFragments.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                int thisIndex = selected.indexOf(dragboard.getString());
                int droppedIndex = selected.indexOf(((Text) event.getPickResult().getIntersectedNode()).getText());

                selected.remove(thisIndex);
                selected.add(droppedIndex, dragboard.getString());

                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        chosenFragments.setOnDragDone(Event::consume);
    }

    private void handleBut(VBox vBox) {
        allFragments.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String fragment = allFragments.getSelectionModel().getSelectedItem();

                if (fragment != null) {
                    preSelect.remove(fragment);
                    selected.add(fragment);
                }
            }
        });

        chosenFragments.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String fragment = chosenFragments.getSelectionModel().getSelectedItem();

                if (fragment != null) {
                    selected.remove(fragment);
                    preSelect.add(fragment);
                }
            }
        });

        back.setOnAction(event -> stage.setScene(fragmentPage.getScene()));

        create.setOnAction(event -> {
            if (selected.size() == 0) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Выберите хотя бы один фрагмент", null, null);
                return;
            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(this::createDoc);

            executorService.shutdown();
        });
    }

    private WordprocessingMLPackage getPackage() {
        WordprocessingMLPackage docxPackage = null;
        try {
            docxPackage = WordprocessingMLPackage.createPackage();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return docxPackage;
    }

    private void createDoc() {
        WordprocessingMLPackage wordprocessingMLPackage = getPackage();
        MainDocumentPart documentPart = wordprocessingMLPackage.getMainDocumentPart();
        ObjectFactory objectFactory = Context.getWmlObjectFactory();
        WordHelper.setPageMargins(wordprocessingMLPackage, objectFactory);
        final WordprocessingMLPackage finalDocxPackage = wordprocessingMLPackage;

        WordHelper.addBreak(wordprocessingMLPackage, STBrType.PAGE);

        for (int i = 0; i < selected.size(); i++) {
            TextFragment textFragment = clientService.getTextFragmentByName(selected.get(i));

            String name = textFragment.getFragmentName();

            List<String> paragraphs = WordHelper.getWordParagraphs(textFragment.getText());

            documentPart.addObject(WordHelper.createPar(objectFactory, name, sectionProp()));
            paragraphs.forEach(par -> {
                if (par.matches("[1-9]\\.[1-9].*")) {
                    WordHelper.addBreak(finalDocxPackage, STBrType.TEXT_WRAPPING);
                    documentPart.addObject(WordHelper.createPar(objectFactory, par, parNameProp()));
                    WordHelper.addBreak(finalDocxPackage, STBrType.TEXT_WRAPPING);
                } else {
                    documentPart.addObject(WordHelper.createPar(objectFactory, par, parTextProp()));
                }
            });
            if (i != selected.size()-1)
                WordHelper.addBreak(finalDocxPackage, STBrType.PAGE);
        }
        try {
            WordHelper.generateToc(wordprocessingMLPackage);
            wordprocessingMLPackage.save(new File("document.docx"));
            System.out.println("Done");
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    private TextProperties sectionProp() {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(true);
        textProperties.setItalic(false);
        textProperties.setFontFamily(WordConstants.TIMES_NEW_ROMAN);
        textProperties.setSize("28");
        textProperties.setStyle(WordConstants.HEADING1);
        textProperties.setIndent("0");
        textProperties.setJustification(WordConstants.JC_CENTER);

        return textProperties;
    }

    private TextProperties parNameProp() {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(false);
        textProperties.setItalic(false);
        textProperties.setFontFamily(WordConstants.TIMES_NEW_ROMAN);
        textProperties.setSize("28");
        textProperties.setJustification(WordConstants.JC_BOTH);
        textProperties.setIndent("0");
        textProperties.setStyle(WordConstants.HEADING2);

        return textProperties;
    }

    private TextProperties parTextProp() {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(false);
        textProperties.setItalic(false);
        textProperties.setFontFamily(WordConstants.TIMES_NEW_ROMAN);
        textProperties.setSize("28");
        textProperties.setJustification(WordConstants.JC_BOTH);
        textProperties.setIndent("225");
        textProperties.setStyle("");

        return textProperties;
    }

    public Scene getScene() {
        return scene;
    }
}
