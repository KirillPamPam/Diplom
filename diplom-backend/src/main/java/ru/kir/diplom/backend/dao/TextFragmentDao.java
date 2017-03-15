package ru.kir.diplom.backend.dao;

import ru.kir.diplom.backend.model.TextFragment;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public interface TextFragmentDao {
    void createTextFragment(TextFragment textFragment);

    TextFragment getTextFragment(String name);

    List<TextFragment> getAll();

    void deleteTextFragment(TextFragment textFragment);

    void updateTextFragment(TextFragment textFragment);
}
