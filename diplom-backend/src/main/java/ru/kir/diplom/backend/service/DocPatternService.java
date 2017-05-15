package ru.kir.diplom.backend.service;

import ru.kir.diplom.backend.model.client.ClientDocPattern;
import ru.kir.diplom.backend.model.rest.RequestCreateDocPattern;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
public interface DocPatternService {

    void createDocPattern(RequestCreateDocPattern docPattern);

    List<ClientDocPattern> getAll();

    List<ClientDocPattern> getDocPatternByTemplate(String template);

    ClientDocPattern getDocPatternById(String id);

}
