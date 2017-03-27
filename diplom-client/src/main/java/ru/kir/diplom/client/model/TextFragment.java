package ru.kir.diplom.client.model;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class TextFragment {
    private String id;
    private String fragmentName;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextFragment that = (TextFragment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fragmentName, that.fragmentName) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fragmentName, text);
    }

    @Override
    public String toString() {
        return "TextFragment{" +
                "id='" + id + '\'' +
                ", fragmentName='" + fragmentName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
