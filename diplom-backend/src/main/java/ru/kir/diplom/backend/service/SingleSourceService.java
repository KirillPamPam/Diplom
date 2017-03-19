package ru.kir.diplom.backend.service;

import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.client.ClientSingleSource;
import ru.kir.diplom.backend.model.rest.RequestCreateSingleSource;
import ru.kir.diplom.backend.model.rest.RequestUpdateSingleSource;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public interface SingleSourceService {
    void createSingleSource(RequestCreateSingleSource source);

    SingleSource getSingleSource(String name);

    ClientSingleSource getClientSingleSource(String name);

    void deleteSingleSource(String id);

    void updateSingleSource(String id, RequestUpdateSingleSource source);

    List<ClientSingleSource> getAll();
}
