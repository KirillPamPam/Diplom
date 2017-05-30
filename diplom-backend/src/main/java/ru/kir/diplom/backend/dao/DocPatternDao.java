package ru.kir.diplom.backend.dao;

import ru.kir.diplom.backend.model.DocPattern;
import ru.kir.diplom.backend.model.SingleSource;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public interface DocPatternDao {

    void createDocPattern(DocPattern docPattern, SingleSource singleSource);

    List<DocPattern> getAll(SingleSource singleSource);

    List<DocPattern> getDocPatternByTemplate(String template);

    DocPattern getDocPatternById(String id);

}
