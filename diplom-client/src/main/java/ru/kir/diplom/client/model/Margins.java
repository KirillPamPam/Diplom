package ru.kir.diplom.client.model;

/**
 * Created by Kirill Zhitelev on 23.04.2017.
 */
public class Margins {
    private String left;
    private String right;
    private String bot;
    private String top;

    public Margins(String left, String right, String bot, String top) {
        this.left = left;
        this.right = right;
        this.bot = bot;
        this.top = top;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getBot() {
        return bot;
    }

    public void setBot(String bot) {
        this.bot = bot;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }
}
