package ru.kir.diplom.backend.dao;

import ru.kir.diplom.backend.model.SingleSource;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public interface SingleSourceDao {
    void createSingleSource(SingleSource source);

    SingleSource getSingleSourceByName(String name);

    SingleSource getSingleSourceById(String id);

    void deleteSingleSource(SingleSource source);

    void updateSingleSource(SingleSource source);

    List<SingleSource> getAll();
}
