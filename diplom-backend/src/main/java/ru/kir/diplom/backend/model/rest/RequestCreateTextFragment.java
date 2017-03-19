package ru.kir.diplom.backend.model.rest;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 18.03.2017.
 */
public class RequestCreateTextFragment {
    @NotEmpty(message = "Required field")
    private String name;
    @NotEmpty(message = "Required field")
    private String text;
    @NotEmpty(message = "Required field")
    private String sourceName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestCreateTextFragment that = (RequestCreateTextFragment) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(text, that.text) &&
                Objects.equals(sourceName, that.sourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, sourceName);
    }
}
