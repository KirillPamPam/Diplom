package ru.kir.diplom.client.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kirill Zhitelev on 09.04.2017.
 */
public class Helper {
    public static void makeInformationWindow(Alert.AlertType type,
                                             String contentText,
                                             String headerText, String title, Double width) {
        Alert alert = new Alert(type);
        if (width != null)
            alert.getDialogPane().setPrefWidth(width);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static boolean makeRemoveDialog(Alert.AlertType type, String contentText, String headerText, String title, ButtonType... buttonType) {
        Alert alert = new Alert(type, contentText, buttonType);
        alert.setHeaderText(headerText);
        alert.setTitle(title);

        Optional<ButtonType> res = alert.showAndWait();

        return res.get() == Constants.OK_BUTTON;
    }

    public static boolean checkFragmentName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я]");
        Matcher matcher = pattern.matcher(name);

        return matcher.find();
    }

    public static String jcConvert(String value) {
        String jc = null;
        switch (value) {
            case WordConstants.CENTER:
                jc = WordConstants.JC_CENTER;
                break;
            case WordConstants.LEFT:
                jc = WordConstants.JC_LEFT;
                break;
            case WordConstants.RIGHT:
                jc = WordConstants.JC_RIGHT;
                break;
            case WordConstants.BOTH:
                jc = WordConstants.JC_BOTH;
                break;
        }
        return jc;
    }

    public static String marginsConvert(String value) {
        return String.valueOf((int)(Double.parseDouble(value) * 568));
    }

    public static String textSizeConvert(String value) {
        return String.valueOf(Integer.parseInt(value) * 2);
    }

    public static String lineIntervalConvert(String value) {
        return String.valueOf((int)(Double.parseDouble(value) * 240));
    }
}
