package ru.kir.diplom.backend.model.rest;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 19.03.2017.
 */
public class RequestDeleteSingleSource {
    @NotEmpty(message = "Required field")
    private String id;

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
        RequestDeleteSingleSource that = (RequestDeleteSingleSource) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
