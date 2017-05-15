package ru.kir.diplom.backend.model.client;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public class ClientDocPattern {
    private String id;
    private String name;
    private String fragments;
    private ClientStyle style;
    private String luValue;

    public String getLuValue() {
        return luValue;
    }

    public void setLuValue(String luValue) {
        this.luValue = luValue;
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

    public String getFragments() {
        return fragments;
    }

    public void setFragments(String fragments) {
        this.fragments = fragments;
    }

    public ClientStyle getStyle() {
        return style;
    }

    public void setStyle(ClientStyle style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDocPattern that = (ClientDocPattern) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(fragments, that.fragments) &&
                Objects.equals(style, that.style) &&
                Objects.equals(luValue, that.luValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fragments, style, luValue);
    }
}
