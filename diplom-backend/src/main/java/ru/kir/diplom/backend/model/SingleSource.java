package ru.kir.diplom.backend.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
@Entity
@Table(name = "single_sources")
@DynamicUpdate
public class SingleSource {
    @Id
    @Column(name = "single_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "single_name")
    private String singleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "singleSource", cascade = CascadeType.ALL)
    private Set<TextFragment> textFragments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "singleSource", cascade = CascadeType.ALL)
    private Set<DocPattern> docPatterns;

    public Set<DocPattern> getDocPatterns() {
        return docPatterns;
    }

    public void setDocPatterns(Set<DocPattern> docPatterns) {
        this.docPatterns = docPatterns;
    }

    public Set<TextFragment> getTextFragments() {
        return textFragments;
    }

    public void setTextFragments(Set<TextFragment> textFragments) {
        this.textFragments = textFragments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    @Override
    public String toString() {
        return "SingleSource{" +
                "id=" + id +
                ", singleName='" + singleName + '\'' +
                ", textFragments=" + textFragments +
                '}';
    }
}
