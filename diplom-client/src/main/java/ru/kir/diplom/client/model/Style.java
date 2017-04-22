package ru.kir.diplom.client.model;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 22.04.2017.
 */
public class Style {
    private String id;
    private String name;
    private String properties;

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

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Style style = (Style) o;
        return Objects.equals(id, style.id) &&
                Objects.equals(name, style.name) &&
                Objects.equals(properties, style.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, properties);
    }
}
