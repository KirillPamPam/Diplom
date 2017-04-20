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
    public static void makeInformationWindow(Alert.AlertType type, String contentText, String headerText, String title) {
        Alert alert = new Alert(type);
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
}
