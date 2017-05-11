package ru.kir.diplom.client.word;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.toc.Toc;
import org.docx4j.toc.TocException;
import org.docx4j.toc.TocGenerator;
import org.docx4j.wml.*;
import ru.kir.diplom.client.model.Margins;
import ru.kir.diplom.client.model.TextProperties;
import ru.kir.diplom.client.util.Constants;
import ru.kir.diplom.client.util.WordConstants;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kirill Zhitelev on 13.04.2017.
 */
public class WordHelper {

    public static List<String> getWordParagraphs(String text) {
        Pattern paragraphPattern = Pattern.compile("[([1-9]\\.[1-9])|\\t|-].*");
        Matcher matcher = paragraphPattern.matcher(text);

        List<String> paragraphs = new ArrayList<>();

        while (matcher.find()) {
            if (isNoTitle(matcher.group()))
                paragraphs.add(matcher.group() + Constants.NO_TITLE);
            else
                paragraphs.add(matcher.group());
        }

        return paragraphs;
    }

    private static boolean isNoTitle(String title) {
        Pattern titlePatter = Pattern.compile("([1-9]\\.?)*(\\s?)*");
        Matcher matcher = titlePatter.matcher(title);

        return matcher.matches();
    }


    public static void generateToc(WordprocessingMLPackage docxPackage, int index) throws TocException {
        Toc.setTocHeadingText("");
        TocGenerator generator = new TocGenerator(docxPackage);
        generator.generateToc(index, " TOC \\o \"1-4\" \\h \\z \\u ", false);
    }

    public static void addBreak(ObjectFactory objectFactory, MainDocumentPart documentPart, STBrType type) {
        P p = objectFactory.createP();
        // Create object for r
        R r = objectFactory.createR();
        p.getContent().add(r);
        // Create object for br
        Br br = objectFactory.createBr();
        r.getContent().add(br);
        br.setType(type);
        documentPart.addObject(p);
    }

    public static P createPar(ObjectFactory objectFactory, String text, TextProperties properties) {
        P paragraph = objectFactory.createP();
        Text textName = objectFactory.createText();
        textName.setValue(text);

        R nameRun = objectFactory.createR();
        nameRun.getContent().add(textName);

        RPr prop = objectFactory.createRPr();
        PPr pPr = objectFactory.createPPr();

        PPrBase.Spacing spacing = objectFactory.createPPrBaseSpacing();
        spacing.setLine(new BigInteger(properties.getLineInterval()));
        spacing.setAfter(BigInteger.valueOf(0));
        pPr.setSpacing(spacing);

        PPrBase.Ind ind = objectFactory.createPPrBaseInd();
        ind.setFirstLineChars(new BigInteger(properties.getIndent()));
        pPr.setInd(ind);

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

    public static void setPageMargins(WordprocessingMLPackage wordMLPackage, ObjectFactory factory, Margins margins) {
        try {
            Body body = wordMLPackage.getMainDocumentPart().getContents().getBody();
            PageDimensions page = new PageDimensions();
            SectPr.PgMar pgMar = page.getPgMar();
            pgMar.setBottom(new BigInteger(margins.getBot()));
            pgMar.setTop(new BigInteger(margins.getTop()));
            pgMar.setLeft(new BigInteger(margins.getLeft()));
            pgMar.setRight(new BigInteger(margins.getRight()));
/*            pgMar.setBottom(BigInteger.valueOf(852));
            pgMar.setTop(BigInteger.valueOf(1420));
            pgMar.setLeft(BigInteger.valueOf(1136));
            pgMar.setRight(BigInteger.valueOf(568));*/
            SectPr sectPr = factory.createSectPr();
            body.setSectPr(sectPr);
            sectPr.setPgMar(pgMar);
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    public static P createNumberedParagraph(long numId, long ilvl, String paragraphText, ObjectFactory factory, TextProperties textProperties) {

        P  p = factory.createP();

        org.docx4j.wml.Text  t = factory.createText();
        t.setValue(paragraphText);

        RPr prop = factory.createRPr();

        org.docx4j.wml.R  run = factory.createR();
        run.setRPr(prop);
        run.getContent().add(t);

        p.getContent().add(run);

        org.docx4j.wml.PPr ppr = factory.createPPr();
        p.setPPr(ppr);

        // Create and add <w:numPr>
        PPrBase.NumPr numPr =  factory.createPPrBaseNumPr();
        ppr.setNumPr(numPr);

        PPrBase.Spacing spacing = factory.createPPrBaseSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        ppr.setSpacing(spacing);

        HpsMeasure size = factory.createHpsMeasure();
        size.setVal(new BigInteger(textProperties.getSize()));
        prop.setSz(size);

        RFonts font = factory.createRFonts();
        font.setHAnsi(textProperties.getFontFamily());
        font.setAscii(textProperties.getFontFamily());
        prop.setRFonts(font);

        // The <w:ilvl> element
        PPrBase.NumPr.Ilvl ilvlElement = factory.createPPrBaseNumPrIlvl();
        numPr.setIlvl(ilvlElement);
        ilvlElement.setVal(BigInteger.valueOf(ilvl));

        // The <w:numId> element
        PPrBase.NumPr.NumId numIdElement = factory.createPPrBaseNumPrNumId();
        numPr.setNumId(numIdElement);
        numIdElement.setVal(BigInteger.valueOf(numId));

        return p;

    }

    public static  ObservableList<String> initSizes() {
        ObservableList<String> sizes = FXCollections.observableArrayList();
        sizes.addAll("8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26");

        return sizes;
    }

    public static  ObservableList<String> initFonts() {
        ObservableList<String> fonts = FXCollections.observableArrayList();
        fonts.addAll("Times New Roman", "Arial", "Calibri", "Verdana");

        return fonts;
    }

    public static  ObservableList<String> initIntervals() {
        System.out.println("AAA");
        ObservableList<String> intervals = FXCollections.observableArrayList();
        intervals.addAll("1.0", "1.15", "1.5", "2.0", "2.5", "3.0");

        return intervals;
    }

    public static  ObservableList<String> initJc() {
        ObservableList<String> jc = FXCollections.observableArrayList();
        jc.addAll(WordConstants.CENTER, WordConstants.LEFT, WordConstants.RIGHT, WordConstants.BOTH);

        return jc;
    }

    private static Hdr getFtr(ObjectFactory factory) throws Exception {
        // AddPage Numbers
        CTSimpleField pgnum = factory.createCTSimpleField();
        pgnum.setInstr(" PAGE \\* MERGEFORMAT ");
        RPr RPr = factory.createRPr();
        RPr.setNoProof(new BooleanDefaultTrue());
        PPr ppr = factory.createPPr();
        Jc jc = factory.createJc();
        jc.setVal(JcEnumeration.CENTER);
        ppr.setJc(jc);
        PPrBase.Spacing pprbase = factory.createPPrBaseSpacing();
        pprbase.setBefore(BigInteger.valueOf(240));
        pprbase.setAfter(BigInteger.valueOf(0));
        ppr.setSpacing(pprbase);

        R run = factory.createR();
        run.getContent().add(RPr);
        pgnum.getContent().add(run);

        JAXBElement<CTSimpleField> fldSimple = factory.createPFldSimple(pgnum);
        P para = factory.createP();
        para.getContent().add(fldSimple);
        para.setPPr(ppr);
        // Now add our paragraph to the footer
        Hdr ftr = factory.createHdr();
        ftr.getEGBlockLevelElts().add(para);
        return ftr;
    }

    public static void addPage(ObjectFactory factory, WordprocessingMLPackage pkg, MainDocumentPart main_part, String text) throws Exception {
        HeaderPart cover_hdr_part = new HeaderPart(new PartName(
                "/word/cover-header.xml")), content_hdr_part = new HeaderPart(
                new PartName("/word/content-header.xml"));
        pkg.getParts().put(cover_hdr_part);
        pkg.getParts().put(content_hdr_part);

        Hdr cover_hdr = getFtr(factory), content_hdr = getFtr(factory);

        TextProperties textProperties = new TextProperties();
        textProperties.setBold(true);
        textProperties.setItalic(false);
        textProperties.setFontFamily(WordConstants.TIMES_NEW_ROMAN);
        textProperties.setSize("28");
        textProperties.setJustification(WordConstants.JC_CENTER);
        textProperties.setIndent("0");
        textProperties.setStyle("");
        textProperties.setLineInterval("240");

        cover_hdr.getContent().add(createPar(factory, text, textProperties));
        content_hdr.getContent().add(createPar(factory, text, textProperties));

        // Bind the header JAXB elements as representing their header parts
        cover_hdr_part.setJaxbElement(cover_hdr);
        content_hdr_part.setJaxbElement(content_hdr);

        // Add the reference to both header parts to the Main Document Part
        Relationship cover_hdr_rel = main_part.addTargetPart(cover_hdr_part);
        Relationship content_hdr_rel = main_part
                .addTargetPart(content_hdr_part);

        List<SectionWrapper> sections = pkg.getDocumentModel().getSections();

        // Get last section SectPr and create a new one if it doesn't exist
        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        if (sectPr == null) {
            sectPr = factory.createSectPr();
            pkg.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }

        // link cover and content headers
        HeaderReference hdr_ref; // this variable is reused

        hdr_ref = factory.createHeaderReference();
        hdr_ref.setId(cover_hdr_rel.getId());
        hdr_ref.setType(HdrFtrRef.FIRST);
        sectPr.getEGHdrFtrReferences().add(hdr_ref);

        CTPageNumber ctPageNumber = factory.createCTPageNumber();
        ctPageNumber.setStart(BigInteger.valueOf(2));
        sectPr.setPgNumType(ctPageNumber);

        hdr_ref = factory.createHeaderReference();
        hdr_ref.setId(content_hdr_rel.getId());
        hdr_ref.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(hdr_ref);

        BooleanDefaultTrue boolanDefaultTrue = new BooleanDefaultTrue();
        sectPr.setTitlePg(boolanDefaultTrue);
        // link cover and content footers
    }

    public static P newImage(WordprocessingMLPackage wordMLPackage, byte[] bytes,
                              String filenameHint, String altText, int id1, int id2) throws Exception {
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
        Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false);

        ObjectFactory factory = new ObjectFactory();

        P  p = factory.createP();
        R  run = factory.createR();
        PPr pPr = factory.createPPr();

        Jc justification = factory.createJc();
        justification.setVal(JcEnumeration.CENTER);
        pPr.setJc(justification);
        p.setPPr(pPr);

        p.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);

        return p;
    }
}
