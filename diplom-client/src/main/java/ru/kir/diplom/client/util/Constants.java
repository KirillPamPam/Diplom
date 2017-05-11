package ru.kir.diplom.client.util;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class Constants {
    public static final String MAIN_PATH = "http://localhost:8080/documentation-build/singlesource/operations";

    public static final String GET_TEXT_FRAGMENT = "/get/fragment/";
    public static final String GET_TEXT_FRAGMENT_BY_NAME = "/get/fragment/name/";
    public static final String GET_TEXT_FRAGMENT_BY_PATTERN = "/get/fragment/pattern/";
    public static final String GET_ALL_TEXT_FRAGMENT = "/getall/fragment/";
    public static final String GET_SINGLE_SOURCE = "/get/source/";
    public static final String GET_ALL_SINGLE_SOURCE = "/getall/source/";
    public static final String CREATE_TEXT_FRAGMENT = "/create/fragment/";
    public static final String UPDATE_TEXT_FRAGMENT = "/update/fragment/";
    public static final String DELETE_TEXT_FRAGMENT = "/delete/fragment/";
    public static final String CREATE_SINGLE_SOURCE = "/create/source/";
    public static final String UPDATE_SINGLE_SOURCE = "/update/source/";
    public static final String DELETE_SINGLE_SOURCE = "/delete/source/";
    public static final String GET_STYLE = "/get/style/";
    public static final String GET_STYLE_BY_NAME = "/get/style/name/";
    public static final String GET_ALL_STYLE = "/getall/style/";
    public static final String CREATE_STYLE = "/create/style/";
    public static final String UPDATE_STYLE = "/update/style/";
    public static final String DELETE_STYLE = "/delete/style/";

    public static final String FORMAT = "Правила форматирования текста:\n" +
            "1. Обозначение отдельных частей:\n" +
            "\t-подразделы обозначать следующим правилом - [Номер раздела].[Номер подраздела].\n" +
            "\t-пункты обозначать следующим правилом - [Номер раздела].[Номер подраздела].[Номер пункта].\n" +
            "\t-подпункты обозначать следующим правилом - [Номер раздела].[Номер подраздела].[Номер пункта].[Номер подпункта].\n" +
            "2 Если какая-то часть не имеет заголовка, то начинать текст необходимо с новой строки\n" +
            "3. Каждый новый абзац должен начинаться с красной строки (клавиша TAB).\n" +
            "4. В тексте допускается обозначать ссылки на рисунки. Ссылка представляет собой абсолютный путь к файлу." +
            "Ссылка всегда должна быть оборзначена с новой красной строки. Пример: C:\\example.png - Рисунок 1\n" +
            "5. Элементы перечисления должны начинаться с новой строки, при чем обозначать элемент перечисления необходимо символом \"-\"";

    public static final String CREATE_OPERATION = "create";
    public static final String READ_OPERATION = "read";

    public static final String NO_TITLE = "No title";

    public static final ButtonType OK_BUTTON = new ButtonType(
            "Да", ButtonBar.ButtonData.OK_DONE);

    public static final ButtonType CANCEL_BUTTON = new ButtonType(
            "Нет", ButtonBar.ButtonData.CANCEL_CLOSE);

    public static final String APP_JSON = "application/json";

    public static final String BAD_REQUEST = "400";
}
