package ru.kir.diplom.backend.model.client;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 18.03.2017.
 */
public class ClientSingleSource {
    private String singleName;
    private List<ClientTextFragment> textFragments;

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public List<ClientTextFragment> getTextFragments() {
        return textFragments;
    }

    public void setTextFragments(List<ClientTextFragment> textFragments) {
        this.textFragments = textFragments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientSingleSource that = (ClientSingleSource) o;
        return Objects.equals(singleName, that.singleName) &&
                Objects.equals(textFragments, that.textFragments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(singleName, textFragments);
    }
}
