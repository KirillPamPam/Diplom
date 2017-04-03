package ru.kir.diplom.backend.dao;

import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.TextFragment;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
public interface TextFragmentDao {
    void createTextFragment(TextFragment textFragment);

    TextFragment getTextFragmentByName(String name);

    TextFragment getTextFragmentById(String id);

    List<TextFragment> getTextFragmentsByPattern(String pattern);

    List<TextFragment> getAll();

    List<TextFragment> getAll(SingleSource singleSource);

    void deleteTextFragment(TextFragment textFragment);

    void updateTextFragment(TextFragment textFragment);
}
