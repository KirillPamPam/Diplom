package ru.kir.diplom.backend.model.exception;

/**
 * Created by Kirill Zhitelev on 19.03.2017.
 */
public class NullRequestException extends RuntimeException {
    private String attribute;
    private String nameField;
    private Class clazz;

    public NullRequestException(String message, String attribute, Class clazz, String nameField) {
        super(message);
        this.attribute = attribute;
        this.clazz = clazz;
        this.nameField = nameField;
    }

    public String getNameField() {
        return nameField;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getAttribute() {
        return attribute;
    }
}
