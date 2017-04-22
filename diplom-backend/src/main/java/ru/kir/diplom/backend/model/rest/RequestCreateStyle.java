package ru.kir.diplom.backend.model.rest;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 20.04.2017.
 */
public class RequestCreateStyle {
    @NotEmpty(message = "Required field")
    private String name;
    @NotEmpty(message = "Required field")
    private String properties;

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
        RequestCreateStyle that = (RequestCreateStyle) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, properties);
    }
}
