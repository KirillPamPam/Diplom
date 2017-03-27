package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class RequestUpdateSingleSource {
    private String id;
    private String name;

    public RequestUpdateSingleSource() {
    }

    public RequestUpdateSingleSource(String id, String name) {
        this.id = id;
        this.name = name;
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
}
