package ru.kir.diplom.client.model;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class SingleSource {
    private String id;
    private String singleName;
    private List<TextFragment> textFragments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public List<TextFragment> getTextFragments() {
        return textFragments;
    }

    public void setTextFragments(List<TextFragment> textFragments) {
        this.textFragments = textFragments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleSource that = (SingleSource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(singleName, that.singleName) &&
                Objects.equals(textFragments, that.textFragments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, singleName, textFragments);
    }

    @Override
    public String toString() {
        return "SingleSource{" +
                "id='" + id + '\'' +
                ", singleName='" + singleName + '\'' +
                ", textFragments=" + textFragments +
                '}';
    }
}
