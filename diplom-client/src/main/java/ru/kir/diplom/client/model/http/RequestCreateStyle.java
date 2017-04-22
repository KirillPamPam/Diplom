package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 22.04.2017.
 */
public class RequestCreateStyle {
    private String name;
    private String properties;

    public RequestCreateStyle(String name, String properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
