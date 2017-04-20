package ru.kir.diplom.client.ui.pages;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.Numbering;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.STBrType;
import ru.kir.diplom.client.model.TextFragment;
import ru.kir.diplom.client.model.TextProperties;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Helper;
import ru.kir.diplom.client.util.WordConstants;
import ru.kir.diplom.client.word.WordHelper;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    private NumberingDefinitionsPart ndp;
    private ProgressBar progressBar = new ProgressBar();

    public DocumentBuildPage(Stage stage, FragmentPage fragmentPage) {
        this.stage = stage;
        this.fragmentPage = fragmentPage;

        preSelect.addAll(clientService.getAllTextFragments(fragmentPage.getSingleSourceName())
                .stream().map(TextFragment::getFragmentName).collect(Collectors.toList()));

        init();
    }

    private void init() {
        progressBar.setVisible(false);

        GridPane gridPane = new GridPane();
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
        rightBut.getChildren().addAll(create, back, progressBar);
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
            String path = getDocPath();

            if (path != null) {
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                Future future = executorService.submit(() -> createDoc(path));

                progressBar.setVisible(true);

                executorService.submit(() -> handleResult(future, vBox));

                executorService.shutdown();
            }
        });
    }

    private void handleResult(Future future, VBox vBox) {
        try {
            Boolean result = (Boolean) future.get();
            if (result)
                Platform.runLater(() -> {
                    progressBar.setVisible(false);
                    Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Документ создан", null, null);
                });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private WordprocessingMLPackage getPackage() {
        WordprocessingMLPackage docxPackage = null;
        try {
            docxPackage = WordprocessingMLPackage.createPackage();
            ndp = new NumberingDefinitionsPart();
            docxPackage.getMainDocumentPart().addTargetPart(ndp);
            ndp.setJaxbElement((Numbering) XmlUtils.unmarshalString(WordConstants.initialNumbering));
        } catch (InvalidFormatException | JAXBException e) {
            e.printStackTrace();
        }
        return docxPackage;
    }

    private boolean createDoc(String path) {
        WordprocessingMLPackage wordprocessingMLPackage = getPackage();
        MainDocumentPart documentPart = wordprocessingMLPackage.getMainDocumentPart();
        ObjectFactory objectFactory = Context.getWmlObjectFactory();
        WordHelper.setPageMargins(wordprocessingMLPackage, objectFactory);

        for(int i = 1; i < 10; i++){
            documentPart.getPropertyResolver().activateStyle(String.format("TOC%s", i));
        }

        WordHelper.addBreak(objectFactory, documentPart, STBrType.PAGE);

        long numId = 1;

        for (int i = 0; i < selected.size(); i++) {
            int sectionIndex = i + 1;

            TextFragment textFragment = clientService.getTextFragmentByName(selected.get(i));

            String name = sectionIndex + ". " + textFragment.getFragmentName();

            List<String> paragraphs = WordHelper.getWordParagraphs(textFragment.getText());

            documentPart.addObject(WordHelper.createPar(objectFactory, name, sectionProp()));
            for (int j = 0; j < paragraphs.size(); j++) {
                if (paragraphs.get(j).matches("[1-9]\\.[1-9].*")) {
                    WordHelper.addBreak(objectFactory, documentPart, STBrType.TEXT_WRAPPING);

                    if (paragraphs.get(j).matches("[1-9]\\.[1-9]\\.[1-9].*"))
                        documentPart.addObject(WordHelper
                                .createPar(objectFactory, paragraphs.get(j).replaceFirst("[1-9]", String.valueOf(sectionIndex)), parNameProp(WordConstants.HEADING3)));
                    else
                        documentPart.addObject(WordHelper
                                .createPar(objectFactory, paragraphs.get(j).replaceFirst("[1-9]", String.valueOf(sectionIndex)), parNameProp(WordConstants.HEADING2)));

                    if (!paragraphs.get(j + 1).matches("[1-9].*"))
                        WordHelper.addBreak(objectFactory, documentPart, STBrType.TEXT_WRAPPING);
                } else if (paragraphs.get(j).matches("-.*")) {
                    documentPart.addObject(WordHelper
                            .createNumberedParagraph(numId, 0, paragraphs.get(j).replaceFirst("-", ""), objectFactory, parTextProp()));
                    if (j == paragraphs.size()-1 || !paragraphs.get(j+1).startsWith("-")) {
                        numId = ndp.restart(1, 0, 1);
                    }
                }
                else {
                    documentPart.addObject(WordHelper.createPar(objectFactory, paragraphs.get(j), parTextProp()));
                }
            }
            if (i != selected.size()-1)
                WordHelper.addBreak(objectFactory, documentPart, STBrType.PAGE);
        }
        try {
            WordHelper.generateToc(wordprocessingMLPackage);
            wordprocessingMLPackage.save(new File(path + ".docx"));
            System.out.println("Done");
        } catch (Docx4JException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String getDocPath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить документ");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null)
            return file.toString();

        return null;
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

    private TextProperties parNameProp(String heading) {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(true);
        textProperties.setItalic(false);
        textProperties.setFontFamily(WordConstants.TIMES_NEW_ROMAN);
        textProperties.setSize("28");
        textProperties.setJustification(WordConstants.JC_BOTH);
        textProperties.setIndent("0");
        textProperties.setStyle(heading);

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
