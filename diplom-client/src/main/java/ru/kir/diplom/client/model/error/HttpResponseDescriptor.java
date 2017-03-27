package ru.kir.diplom.client.model.error;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class HttpResponseDescriptor {
    private Integer code;
    private List<FieldErrorResource> fieldErrors;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<FieldErrorResource> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponseDescriptor that = (HttpResponseDescriptor) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(fieldErrors, that.fieldErrors) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, fieldErrors, message);
    }

    @Override
    public String toString() {
        return "HttpResponseDescriptor{" +
                "code=" + code +
                ", fieldErrors=" + fieldErrors +
                ", message='" + message + '\'' +
                '}';
    }
}
