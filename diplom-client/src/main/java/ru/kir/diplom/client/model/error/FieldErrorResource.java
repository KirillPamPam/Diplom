package ru.kir.diplom.client.model.error;

import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class FieldErrorResource {
    private String field;
    private String defaultMessage;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldErrorResource that = (FieldErrorResource) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(defaultMessage, that.defaultMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, defaultMessage);
    }
}
