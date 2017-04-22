package ru.kir.diplom.backend.model.rest;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 20.04.2017.
 */
public class RequestUpdateStyle {
    @NotEmpty(message = "Should not be empty")
    private String id;
    @NotEmpty(message = "Should not be empty")
    private String name;
    @NotEmpty(message = "Should not be empty")
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
        RequestUpdateStyle that = (RequestUpdateStyle) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, properties);
    }
}
