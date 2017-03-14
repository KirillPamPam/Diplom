package ru.kir.diplom.backend.model;

import javax.persistence.*;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
@Entity
@Table(name = "text_fragments")
public class TextFragment {
    @Id
    @Column(name = "text_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "single_id")
    private SingleSource singleSource;

    @Column(name = "fragment_name")
    private String fragmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SingleSource getSingleSource() {
        return singleSource;
    }

    public void setSingleSource(SingleSource singleSource) {
        this.singleSource = singleSource;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }
}
