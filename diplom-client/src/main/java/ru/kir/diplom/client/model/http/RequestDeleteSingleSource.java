package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class RequestDeleteSingleSource {
    private String id;

    public RequestDeleteSingleSource() {
    }

    public RequestDeleteSingleSource(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
