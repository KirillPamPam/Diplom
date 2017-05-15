package ru.kir.diplom.client.model;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public class TableDocPattern {
    private String name;
    private String fragments;
    private String luValue;
    private String style;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public String getLuValue() {
        return luValue;
    }

    public void setLuValue(String luValue) {
        this.luValue = luValue;
    }
}
