package ru.kir.diplom.backend.service;

import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.TextFragment;
import ru.kir.diplom.backend.model.client.ClientTextFragment;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public interface TextFragmentService {
    void createTextFragment(TextFragment textFragment);

    ClientTextFragment getTextFragment(String name);

    List<ClientTextFragment> getAll();

    List<ClientTextFragment> getAll(SingleSource singleSource);

    void deleteTextFragment(TextFragment textFragment);

    void updateTextFragment(TextFragment textFragment);
}
