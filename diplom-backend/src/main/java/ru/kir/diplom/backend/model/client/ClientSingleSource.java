package ru.kir.diplom.backend.model.client;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 18.03.2017.
 */
public class ClientSingleSource {
    private String id;
    private String singleName;
    private List<ClientTextFragment> textFragments;
    private List<ClientDocPattern> docPatterns;

    public List<ClientDocPattern> getDocPatterns() {
        return docPatterns;
    }

    public void setDocPatterns(List<ClientDocPattern> docPatterns) {
        this.docPatterns = docPatterns;
    }

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
        ClientSingleSource that = (ClientSingleSource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(singleName, that.singleName) &&
                Objects.equals(textFragments, that.textFragments) &&
                Objects.equals(docPatterns, that.docPatterns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, singleName, textFragments, docPatterns);
    }
}
