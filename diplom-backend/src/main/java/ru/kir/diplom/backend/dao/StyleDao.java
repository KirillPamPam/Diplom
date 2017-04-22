package ru.kir.diplom.backend.dao;

import ru.kir.diplom.backend.model.Style;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 20.04.2017.
 */
public interface StyleDao {

    Style getStyleByName(String name);

    Style getStyleById(String id);

    List<Style> getAll();

    void createStyle(Style style);

    void updateStyle(Style style);

    void deleteStyle(Style style);

}
