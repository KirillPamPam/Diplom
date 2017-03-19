package ru.kir.diplom.backend.model.rest;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 19.03.2017.
 */
public class RequestUpdateTextFragment {
    @NotEmpty(message = "Should not be empty")
    private String id;
    @NotEmpty(message = "Should not be empty")
    private String text;
    @NotEmpty(message = "Should not be empty")
    private String fragmentName;

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
        RequestUpdateTextFragment that = (RequestUpdateTextFragment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(fragmentName, that.fragmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, fragmentName);
    }
}
