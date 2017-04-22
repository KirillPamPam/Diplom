package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 22.04.2017.
 */
public class RequestUpdateStyle {
    private String id;
    private String name;
    private String properties;

    public RequestUpdateStyle(String id, String name, String properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
