package ru.kir.diplom.client.model.http;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class RequestUpdateTextFragment {
    private String id;
    private String text;
    private String fragmentName;

    public RequestUpdateTextFragment() {
    }

    public RequestUpdateTextFragment(String id, String text, String fragmentName) {
        this.id = id;
        this.text = text;
        this.fragmentName = fragmentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }
}
