package ru.kir.diplom.backend.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
@Entity
@Table(name = "doc_patterns")
public class DocPattern {

    @Id
    @Column(name = "pattern_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "fragments")
    private String fragments;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "style_id")
    private Style style;

    @Column(name = "lu_value")
    private String luValue;

    public String getLuValue() {
        return luValue;
    }

    public void setLuValue(String luValue) {
        this.luValue = luValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFragments() {
        return fragments;
    }

    public void setFragments(String fragments) {
        this.fragments = fragments;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocPattern that = (DocPattern) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(fragments, that.fragments) &&
                Objects.equals(style, that.style) &&
                Objects.equals(luValue, that.luValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fragments, style, luValue);
    }
}
