package ru.kir.diplom.client.ui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ru.kir.diplom.client.ui.pages.MainPage;

/**
 * Created by Kirill Zhitelev on 03.04.2017.
 */
public class MainDocBuild extends Application {
    private MainPage mainPage;
    @Override
    public void start(Stage primaryStage) throws Exception {

        mainPage = new MainPage(primaryStage);

        primaryStage.setScene(mainPage.getScene());
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Documentation build");
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

