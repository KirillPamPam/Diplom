package ru.kir.diplom.backend.service;

import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.TextFragment;
import ru.kir.diplom.backend.model.client.ClientTextFragment;
import ru.kir.diplom.backend.model.rest.RequestCreateTextFragment;
import ru.kir.diplom.backend.model.rest.RequestUpdateTextFragment;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public interface TextFragmentService {
    void createTextFragment(RequestCreateTextFragment textFragment);

    ClientTextFragment getClientTextFragmentById(String id);

    ClientTextFragment getTextFragmentByName(String name);

    TextFragment getTextFragmentById(String id);

    List<ClientTextFragment> getAll();

    List<ClientTextFragment> getAll(SingleSource singleSource);

    List<ClientTextFragment> getTextFragmentsByPattern(String pattern);

    boolean deleteTextFragment(String id);

    boolean updateTextFragment(String id, RequestUpdateTextFragment newTextFragment);
}
