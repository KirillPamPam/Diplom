package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class RequestCreateSingleSource {
    private String name;

    public RequestCreateSingleSource() {
    }

    public RequestCreateSingleSource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
