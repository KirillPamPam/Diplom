package ru.kir.diplom.backend.model.client;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 18.03.2017.
 */
public class ClientTextFragment {
    private String id;
    private String fragmentName;
    private String text;

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTextFragment that = (ClientTextFragment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fragmentName, that.fragmentName) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fragmentName, text);
    }
}
