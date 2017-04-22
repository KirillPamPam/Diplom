package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 22.04.2017.
 */
public class RequestDeleteStyle {
    private String id;

    public RequestDeleteStyle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
