package ru.kir.diplom.client.model.http;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public class RequestCreateDocPattern {
    private String name;
    private String fragments;
    private String style;
    private String luValue;
    private String sourceName;

    public RequestCreateDocPattern(String style, String fragments, String name, String luValue, String sourceName) {
        this.sourceName = sourceName;
        this.style = style;
        this.fragments = fragments;
        this.name = name;
        this.luValue = luValue;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

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
                Objects.equals(luValue, that.luValue) &&
                Objects.equals(sourceName, that.sourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fragments, style, luValue, sourceName);
    }
}
