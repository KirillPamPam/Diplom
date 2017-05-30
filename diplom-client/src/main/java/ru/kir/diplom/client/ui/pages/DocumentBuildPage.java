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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
import org.docx4j.wml.*;
import ru.kir.diplom.client.model.Margins;
import ru.kir.diplom.client.model.Style;
import ru.kir.diplom.client.model.TextFragment;
import ru.kir.diplom.client.model.TextProperties;
import ru.kir.diplom.client.service.http.RestClientService;
import ru.kir.diplom.client.util.Constants;
import ru.kir.diplom.client.util.Helper;
import ru.kir.diplom.client.util.WordConstants;
import ru.kir.diplom.client.word.WordHelper;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
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
    private Button patterns = new Button("История шаблонов");
    private ObservableList<String> selected = FXCollections.observableArrayList();
    private ObservableList<String> preSelect = FXCollections.observableArrayList();
    private RestClientService clientService = RestClientService.getInstance();
    private NumberingDefinitionsPart ndp;
    private ProgressBar progressBar = new ProgressBar();
    private Map<String, String> properties;
    private ComboBox<String> styles;
    private String singleName;
    private TextField luField = new TextField();

    public DocumentBuildPage(Stage stage, FragmentPage fragmentPage) {
        this.stage = stage;
        this.fragmentPage = fragmentPage;

        singleName = fragmentPage.getSingleSourceName();

        preSelect.addAll(clientService.getAllTextFragments(singleName)
                .stream().map(TextFragment::getFragmentName).collect(Collectors.toList()));
        List<String> removing = preSelect.stream().filter(fragment -> fragment.contains("АННОТАЦИЯ")).collect(Collectors.toList());
        removing.forEach(preSelect::remove);
        //preSelect.remove();

        init();
    }

    private void init() {
        progressBar.setVisible(false);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints(325, 300, Double.MAX_VALUE);
        ColumnConstraints col2 = new ColumnConstraints(25, 300, Double.MAX_VALUE);
        ColumnConstraints col3 = new ColumnConstraints(325, 300, Double.MAX_VALUE);
        ColumnConstraints col4 = new ColumnConstraints(225, 300, Double.MAX_VALUE);
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        styles = new ComboBox<>(FXCollections.observableArrayList(clientService.getAllStyles()
                .stream()
                .map(Style::getName)
                .collect(Collectors.toList())));

        allFragments = new ListView<>(preSelect);
        chosenFragments = new ListView<>(selected);

        Label all = new Label("Все фрагменты");
        Label style = new Label("Стиль");
        all.setFont(Font.font("Arial", 15));
        Label chosen = new Label("Выбранные фрагменты");
        chosen.setFont(Font.font("Arial", 15));
        Label lu = new Label("Обозначение ЛУ");
        luField.setPrefWidth(200);
        HBox luBox = new HBox(10);
        luBox.setAlignment(Pos.CENTER);
        luBox.getChildren().addAll(lu, luField);

        HBox styleB = new HBox(10);
        styleB.getChildren().addAll(style, styles);

        VBox rightBut = new VBox(10);
        rightBut.getChildren().addAll(patterns, styleB, create, back, progressBar);
        rightBut.setAlignment(Pos.CENTER_LEFT);

        GridPane.setHalignment(all, HPos.CENTER);
        GridPane.setHalignment(chosen, HPos.CENTER);

        gridPane.add(all, 0, 0);
        gridPane.add(chosen, 2, 0);
        gridPane.add(luBox, 2, 2);
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

        patterns.setOnAction(event -> {
            TablePage tablePage = new TablePage(stage, fragmentPage);
            tablePage.initData(clientService.getAllDocPatterns(singleName));

            stage.setScene(tablePage.getScene());
        });

        create.setOnAction(event -> {
            if (selected.size() == 0) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Выберите хотя бы один фрагмент", null, null, null);
                return;
            }

            if (styles.getValue().equals("")) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Выберите стиль", null, null, null);
                return;
            }

            if (luField.getText().equals("")) {
                Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Введите обозначение ЛУ", null, null, null);
                return;
            }

            properties = getProp();
            String path = getDocPath();

            if (path != null) {
                if (path.equals("")) {
                    Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Закройте документ, прежде чем создать", null, null, null);
                    return;
                }

                ExecutorService executorService = Executors.newFixedThreadPool(2);
                Future future = executorService.submit(() -> createDoc(path));

                progressBar.setVisible(true);

                executorService.submit(() -> handleResult(future));

                executorService.shutdown();
            }
        });
    }

    private void handleResult(Future future) {
        try {
            Boolean result = (Boolean) future.get();
            if (result)
                Platform.runLater(() -> {
                    progressBar.setVisible(false);
                    Helper.makeInformationWindow(Alert.AlertType.INFORMATION, "Документ создан", null, null, null);
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

    private boolean createDoc(String docName) throws Docx4JException {
        WordprocessingMLPackage wordprocessingMLPackage = getPackage();
        MainDocumentPart documentPart = wordprocessingMLPackage.getMainDocumentPart();
        ObjectFactory objectFactory = Context.getWmlObjectFactory();
        WordHelper.setPageMargins(wordprocessingMLPackage, objectFactory, getMargins());

        String lu = luField.getText();

        addFirstOrSecondPage(documentPart, docName, lu, "/FirstPage.docx");
        addFirstOrSecondPage(documentPart, docName, lu, "/SecondPage.docx");
      //  WordHelper.addBreak(objectFactory, documentPart, STBrType.PAGE);

        for(int i = 1; i < 10; i++){
            documentPart.getPropertyResolver().activateStyle(String.format("TOC%s", i));
        }

        try {
            WordHelper.addPage(objectFactory, wordprocessingMLPackage, documentPart, lu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int index;
        TextFragment annotation = clientService.getTextFragmentByName("АННОТАЦИЯ - " + docName.substring(docName.lastIndexOf("\\") + 1), singleName);
        if (annotation != null) {
            documentPart.addObject(WordHelper.createPar(objectFactory, annotation.getFragmentName().substring(0, annotation.getFragmentName().indexOf("-") - 1), sectionProp()));
            WordHelper.addBreak(objectFactory, documentPart, STBrType.TEXT_WRAPPING);
            List<String> annotationPar = WordHelper.getWordParagraphs(annotation.getText());
            annotationPar.forEach(par -> documentPart.addObject(WordHelper.createPar(objectFactory, par, parTextProp("225"))));
            WordHelper.addBreak(objectFactory, documentPart, STBrType.PAGE);
            index = 44 + annotationPar.size() + 4;
        }
        else
            index = 41 + 4;


        documentPart.addObject(WordHelper.createPar(objectFactory, "СОДЕРЖАНИЕ", sectionProp()));
        WordHelper.addBreak(objectFactory, documentPart, STBrType.PAGE);

        long numId = 1;

        String patternFragments = "";
        StringBuilder builder = new StringBuilder(patternFragments);

        selected.forEach(selected -> builder.append(selected).append(";"));

        for (int i = 0; i < selected.size(); i++) {
            int sectionIndex = i + 1;

            TextFragment textFragment = clientService.getTextFragmentByName(selected.get(i), singleName);

            String name = sectionIndex + ". " + textFragment.getFragmentName();

            List<String> paragraphs = WordHelper.getWordParagraphs(textFragment.getText());

            documentPart.addObject(WordHelper.createPar(objectFactory, name, sectionProp()));
            for (int j = 0; j < paragraphs.size(); j++) {
                boolean hasTitle = true;
                if (paragraphs.get(j).matches("[1-9]\\.[1-9].*")) {
                    WordHelper.addBreak(objectFactory, documentPart, STBrType.TEXT_WRAPPING);

                    if (paragraphs.get(j).matches("[1-9]\\.[1-9]\\.[1-9].*")) {
                        if (paragraphs.get(j).contains(Constants.NO_TITLE)) {
                            hasTitle = false;
                            String textWithNoTitle = paragraphs.get(j).replace(Constants.NO_TITLE, paragraphs.get(j + 1));
                            paragraphs.remove(j+1);
                            documentPart.addObject(WordHelper.createPar(objectFactory, textWithNoTitle.replaceFirst("[1-9]", String.valueOf(sectionIndex)), parTextProp("0")));
                        } else {
                            if (paragraphs.get(j).matches("[1-9]\\.[1-9]\\.[1-9]\\.[1-9].*")) {
                                documentPart.addObject(WordHelper
                                        .createPar(objectFactory, paragraphs.get(j).replaceFirst("[1-9]", String.valueOf(sectionIndex)), parNameProp(WordConstants.HEADING4)));
                            } else {
                                documentPart.addObject(WordHelper
                                        .createPar(objectFactory, paragraphs.get(j).replaceFirst("[1-9]", String.valueOf(sectionIndex)), parNameProp(WordConstants.HEADING3)));
                            }
                        }
                    }
                    else
                        documentPart.addObject(WordHelper
                                .createPar(objectFactory, paragraphs.get(j).replaceFirst("[1-9]", String.valueOf(sectionIndex)), parNameProp(WordConstants.HEADING2)));
                    if (hasTitle && !paragraphs.get(j + 1).matches("[1-9].*"))
                        WordHelper.addBreak(objectFactory, documentPart, STBrType.TEXT_WRAPPING);
                } else if (paragraphs.get(j).matches("-.*")) {
                    documentPart.addObject(WordHelper
                            .createNumberedParagraph(numId, 0, paragraphs.get(j).replaceFirst("-", ""), objectFactory, parTextProp("225")));
                    if (j == paragraphs.size()-1 || !paragraphs.get(j+1).startsWith("-")) {
                        numId = ndp.restart(1, 0, 1);
                    }
                }
                else {
                    if (paragraphs.get(j).matches("\\t?C:\\\\.*")) {
                        try {
                            String[] images = paragraphs.get(j).split("-");
                            if (images.length == 2) {
                                String imagePath = images[0];
                                String imageName = images[1];
                                if (imagePath.contains("\t"))
                                    imagePath = imagePath.replaceFirst("\\t", "");
                                documentPart.addObject(addImage(wordprocessingMLPackage, imagePath));
                                documentPart.addObject(WordHelper.createPar(objectFactory, imageName, imageNameProp()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        documentPart.addObject(WordHelper.createPar(objectFactory, paragraphs.get(j), parTextProp("225")));
                }
            }
            if (i != selected.size()-1)
                WordHelper.addBreak(objectFactory, documentPart, STBrType.PAGE);
        }
        WordHelper.addBreak(objectFactory, documentPart, STBrType.PAGE);
        addLastPage(documentPart, objectFactory);
        try {
            WordHelper.generateToc(wordprocessingMLPackage, index);
            wordprocessingMLPackage.save(new File(docName + ".docx"));
            clientService.createDocPattern(docName.substring(docName.lastIndexOf("\\") + 1),
                    builder.toString(), styles.getValue(), luField.getText(), singleName);
        } catch (Docx4JException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void addFirstOrSecondPage(MainDocumentPart documentPart, String docName, String lu, String path) throws Docx4JException {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        WordprocessingMLPackage second = WordprocessingMLPackage.load(getClass().getResourceAsStream(path));

        second.getMainDocumentPart().getContent().forEach(content -> {
            P p = (P) content;
            p.getContent().forEach(r -> {
                R r1 = (R) r;
                r1.getContent().forEach(o -> {
                    if (((JAXBElement) o).getValue() instanceof org.docx4j.wml.Text) {
                        org.docx4j.wml.Text t = (org.docx4j.wml.Text) ((JAXBElement) o).getValue();
                        String text = t.getValue();
                        switch (text) {
                            case "Год":
                                t.setValue(String.valueOf(year));
                                break;
                            case "Название документа":
                                t.setValue(docName.substring(docName.lastIndexOf("\\") + 1));
                                break;
                            case "НАЗВАНИЕ ПРОГРАММЫ":
                                t.setValue(singleName.toUpperCase());
                                break;
                            case "код":
                                t.setValue(lu);
                                break;
                        }
                    }
                });
            });
            documentPart.addObject(content);
        });
    }

    private void addLastPage(MainDocumentPart documentPart, ObjectFactory objectFactory) throws Docx4JException {
        WordprocessingMLPackage last = WordprocessingMLPackage.load(getClass().getResourceAsStream("/ChangesPage.docx"));

        last.getMainDocumentPart().getContent().forEach(content -> {
            if (content instanceof JAXBElement) {
                ((Tc) ((JAXBElement) ((Tr) ((Tbl) ((JAXBElement) content).getValue())
                        .getContent().get(0))
                        .getContent().get(0)).getValue())
                        .getContent().set(0, WordHelper.createPar(objectFactory, "Лист регистрации изменений", sectionProp()));
            }
            documentPart.addObject(content);
        });
    }

    private P addImage(WordprocessingMLPackage mlPackage, String path) throws Exception {
        File file = new File(path);

        InputStream inputStream = new FileInputStream(file);
        long fileLength = file.length();

        byte[] bytes = new byte[(int)fileLength];

        int offset = 0;
        int numRead = 0;

        while(offset < bytes.length
                && (numRead = inputStream.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        inputStream.close();
/*
        String filenameHint = null;
        String altText = null;*/

        int id1 = 0;
        int id2 = 1;

        return WordHelper.newImage(mlPackage, bytes, null, null, id1, id2);
    }

    private String getDocPath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить документ");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null)
            return file.toString();

        return null;
    }

    private Map<String, String> getProp() {
        Map<String, String> properties = new HashMap<>();

        Style style = clientService.getStyleByName(styles.getValue());
        List<String> prop = new ArrayList<>(Arrays.asList(style.getProperties().split(";")));

        prop.forEach(property -> {
            String[] wordProperties = property.split(":");
            String key = wordProperties[0];
            String value = wordProperties[1];

            switch (key) {
                case WordConstants.TEXT_SIZE:
                case WordConstants.SECTION_SIZE:
                case WordConstants.OTHER_SIZE:
                    properties.put(key, Helper.textSizeConvert(value));
                    break;
                case WordConstants.SECTION_JC:
                case WordConstants.TEXT_JC:
                    properties.put(key, Helper.jcConvert(value));
                    break;
                case WordConstants.INTERVAL:
                    properties.put(key, Helper.lineIntervalConvert(value));
                    break;
                case WordConstants.MARGIN_BOT:
                case WordConstants.MARGIN_TOP:
                case WordConstants.MARGIN_RIGHT:
                case WordConstants.MARGIN_LEFT:
                    properties.put(key, Helper.marginsConvert(value));
                    break;
                default:
                    properties.put(key, value);
                    break;
            }
        });

        return properties;
    }

    private Margins getMargins() {
        return new Margins(properties.get(WordConstants.MARGIN_LEFT), properties.get(WordConstants.MARGIN_RIGHT),
                properties.get(WordConstants.MARGIN_BOT), properties.get(WordConstants.MARGIN_TOP));
    }

    private TextProperties sectionProp() {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(Boolean.valueOf(properties.get(WordConstants.SECTION_BOLD)));
        textProperties.setItalic(Boolean.valueOf(properties.get(WordConstants.SECTION_ITALIC)));
        textProperties.setFontFamily(properties.get(WordConstants.TEXT_FONT));
        textProperties.setSize(properties.get(WordConstants.TEXT_SIZE));
        textProperties.setStyle(WordConstants.HEADING1);
        textProperties.setIndent("0");
        textProperties.setJustification(properties.get(WordConstants.SECTION_JC));
        textProperties.setLineInterval(properties.get(WordConstants.INTERVAL));

        return textProperties;
    }

    private TextProperties parNameProp(String heading) {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(Boolean.valueOf(properties.get(WordConstants.OTHER_BOLD)));
        textProperties.setItalic(Boolean.valueOf(properties.get(WordConstants.OTHER_ITALIC)));
        textProperties.setFontFamily(properties.get(WordConstants.TEXT_FONT));
        textProperties.setSize(properties.get(WordConstants.OTHER_SIZE));
        textProperties.setJustification(WordConstants.JC_BOTH);
        textProperties.setIndent("0");
        textProperties.setStyle(heading);
        textProperties.setLineInterval(properties.get(WordConstants.INTERVAL));

        return textProperties;
    }

    private TextProperties parTextProp(String indent) {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(false);
        textProperties.setItalic(false);
        textProperties.setFontFamily(properties.get(WordConstants.TEXT_FONT));
        textProperties.setSize(properties.get(WordConstants.TEXT_SIZE));
        textProperties.setJustification(WordConstants.JC_BOTH);
        textProperties.setIndent(indent);
        textProperties.setStyle("");
        textProperties.setLineInterval(properties.get(WordConstants.INTERVAL));

        return textProperties;
    }

    private TextProperties imageNameProp() {
        TextProperties textProperties = new TextProperties();
        textProperties.setBold(false);
        textProperties.setItalic(false);
        textProperties.setFontFamily(properties.get(WordConstants.TEXT_FONT));
        textProperties.setSize(properties.get(WordConstants.TEXT_SIZE));
        textProperties.setJustification(WordConstants.JC_CENTER);
        textProperties.setIndent("0");
        textProperties.setStyle("");
        textProperties.setLineInterval(properties.get(WordConstants.INTERVAL));

        return textProperties;
    }

    public Scene getScene() {
        return scene;
    }

    public ObservableList<String> getSelected() {
        return selected;
    }

    public TextField getLuField() {
        return luField;
    }

    public ComboBox<String> getStyles() {
        return styles;
    }

    public ObservableList<String> getPreSelect() {
        return preSelect;
    }
}
