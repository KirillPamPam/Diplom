package ru.kir.diplom.backend.service;

import ru.kir.diplom.backend.model.client.ClientStyle;
import ru.kir.diplom.backend.model.rest.RequestCreateStyle;
import ru.kir.diplom.backend.model.rest.RequestUpdateStyle;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 20.04.2017.
 */
public interface StyleService {
    ClientStyle getStyleByName(String name);

    ClientStyle getStyleById(String id);

    List<ClientStyle> getAll();

    void createStyle(RequestCreateStyle style);

    boolean updateStyle(String id, RequestUpdateStyle style);

    boolean deleteStyle(String id);
}
