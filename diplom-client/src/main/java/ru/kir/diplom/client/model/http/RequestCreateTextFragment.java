package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class RequestCreateTextFragment {
    private String name;
    private String text;
    private String sourceName;

    public RequestCreateTextFragment() {
    }

    public RequestCreateTextFragment(String name, String text, String sourceName) {
        this.name = name;
        this.text = text;
        this.sourceName = sourceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
