package ru.kir.diplom.backend.model.rest;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public class RequestCreateDocPattern {
    @NotEmpty(message = "Required field")
    private String name;
    @NotEmpty(message = "Required field")
    private String fragments;
    @NotEmpty(message = "Required field")
    private String style;
    @NotEmpty(message = "Required field")
    private String luValue;

    public String getLuValue() {
        return luValue;
    }

    public void setLuValue(String luValue) {
        this.luValue = luValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFragments() {
        return fragments;
    }

    public void setFragments(String fragments) {
        this.fragments = fragments;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestCreateDocPattern that = (RequestCreateDocPattern) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(fragments, that.fragments) &&
                Objects.equals(style, that.style) &&
                Objects.equals(luValue, that.luValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fragments, style, luValue);
    }
}
