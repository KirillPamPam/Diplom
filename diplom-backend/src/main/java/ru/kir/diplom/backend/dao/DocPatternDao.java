package ru.kir.diplom.backend.dao;

import ru.kir.diplom.backend.model.DocPattern;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public interface DocPatternDao {

    void createDocPattern(DocPattern docPattern);

    List<DocPattern> getAll();

    List<DocPattern> getDocPatternByTemplate(String template);

    DocPattern getDocPatternById(String id);

}
