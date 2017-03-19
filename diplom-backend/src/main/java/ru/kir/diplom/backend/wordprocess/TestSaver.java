package ru.kir.diplom.backend.wordprocess;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.toc.Toc;
import org.docx4j.toc.TocException;
import org.docx4j.toc.TocGenerator;
import org.docx4j.wml.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kir.diplom.NumberingRestart;
import ru.kir.diplom.backend.model.TextProperties;
import ru.kir.diplom.backend.model.client.ClientTextFragment;
import ru.kir.diplom.backend.service.TextFragmentService;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public class TestSaver {
    public static void main(String[] args) throws Docx4JException, JAXBException {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.kir.diplom.backend");
        TextFragmentService service = context.getBean("textService", TextFragmentService.class);
        WordprocessingMLPackage docxPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart document = docxPackage.getMainDocumentPart();

        List<String> fragmentNames = Arrays.asList("Введение", "Назначение разработки");

        addPageBreak(docxPackage);

        fragmentNames.forEach(fragmentName -> {
            ClientTextFragment fragment = service.getClientTextFragmentById(fragmentName);
            ObjectFactory objectFactory = Context.getWmlObjectFactory();
            TextProperties textProperties = new TextProperties();
            textProperties.setBold(false);
            textProperties.setItalic(false);
            textProperties.setFontFamily("Times New Roman");
            textProperties.setSize("40");
            textProperties.setStyle("Heading1");
            textProperties.setJustification("left");

            TextProperties textProperties1 = new TextProperties();
            textProperties1.setBold(false);
            textProperties1.setItalic(false);
            textProperties1.setFontFamily("Times New Roman");
            textProperties1.setSize("28");
            textProperties1.setStyle("");
            textProperties1.setJustification("both");

            document.addObject(createPar(objectFactory, fragmentName, textProperties));
            document.addObject(createPar(objectFactory, fragment.getText(), textProperties1));
        });

        document.addObject(NumberingRestart.createNumberedParagraph(2, 0, "one"));
        document.addObject(NumberingRestart.createNumberedParagraph(2, 0, "two"));
        document.addObject(NumberingRestart.createNumberedParagraph(2, 0, "three"));

        generateToc(docxPackage);

        docxPackage.save(new File("text.docx"));
    }

    private static void generateToc(WordprocessingMLPackage docxPackage) throws TocException {
        Toc.setTocHeadingText("Содержание");
        TocGenerator generator = new TocGenerator(docxPackage);
        generator.generateToc(0, "TOC \\o \"1-3\" \\h \\z \\u ", false);
    }

    private static void addPageBreak(WordprocessingMLPackage docxPackage) {
        ObjectFactory factory = Context.getWmlObjectFactory();
        MainDocumentPart documentPart = docxPackage.getMainDocumentPart();

        Br breakObj = new Br();
        breakObj.setType(STBrType.PAGE);

        P paragraph = factory.createP();
        paragraph.getContent().add(breakObj);
        documentPart.getJaxbElement().getBody().getContent().add(paragraph);
    }

    private static P createPar(ObjectFactory objectFactory, String text, TextProperties properties) {
        P paragraph = objectFactory.createP();
        Text textName = objectFactory.createText();
        textName.setValue(text);

        R nameRun = objectFactory.createR();
        nameRun.getContent().add(textName);

        RPr prop = objectFactory.createRPr();
        PPr pPr = objectFactory.createPPr();

        PPrBase.PStyle style = objectFactory.createPPrBasePStyle();
        style.setVal(properties.getStyle());
        pPr.setPStyle(style);

        Jc justification = objectFactory.createJc();
        justification.setVal(JcEnumeration.fromValue(properties.getJustification()));
        pPr.setJc(justification);
        paragraph.setPPr(pPr);

        CTLanguage language = objectFactory.createCTLanguage();
        language.setVal("ru-RU");
        prop.setLang(language);

        HpsMeasure size = objectFactory.createHpsMeasure();
        size.setVal(new BigInteger(properties.getSize()));
        prop.setSz(size);

        RFonts font = objectFactory.createRFonts();
        font.setHAnsi(properties.getFontFamily());
        font.setAscii(properties.getFontFamily());
        prop.setRFonts(font);

        BooleanDefaultTrue bold = objectFactory.createBooleanDefaultTrue();
        bold.setVal(properties.getBold());
        prop.setB(bold);

        BooleanDefaultTrue italic = objectFactory.createBooleanDefaultTrue();
        italic.setVal(properties.getItalic());
        prop.setI(italic);

        Color color = objectFactory.createColor();
        color.setVal("#000000");
        prop.setColor(color);

        nameRun.setRPr(prop);
        paragraph.getContent().add(nameRun);

        return paragraph;
    }
}
