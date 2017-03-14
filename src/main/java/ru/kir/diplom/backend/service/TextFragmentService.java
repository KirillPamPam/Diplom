package ru.kir.diplom.backend.service;

import ru.kir.diplom.backend.model.TextFragment;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public interface TextFragmentService {
    void createTextFragment(TextFragment textFragment);

    TextFragment getTextFragment(String name);

    List<TextFragment> getAll();

    void deleteTextFragment(TextFragment textFragment);

    void updateTextFragment(TextFragment textFragment);
}
