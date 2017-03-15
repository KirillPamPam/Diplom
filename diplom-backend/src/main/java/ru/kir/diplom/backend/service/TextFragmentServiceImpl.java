package ru.kir.diplom.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.diplom.backend.dao.TextFragmentDao;
import ru.kir.diplom.backend.model.TextFragment;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
@Service("textService")
@Transactional
public class TextFragmentServiceImpl implements TextFragmentService {
    @Autowired
    private TextFragmentDao textFragmentDao;

    @Override
    public void createTextFragment(TextFragment textFragment) {
        textFragmentDao.createTextFragment(textFragment);
    }

    @Override
    public TextFragment getTextFragment(String name) {
        return textFragmentDao.getTextFragment(name);
    }

    @Override
    public List<TextFragment> getAll() {
        return textFragmentDao.getAll();
    }

    @Override
    public void deleteTextFragment(TextFragment textFragment) {
        textFragmentDao.deleteTextFragment(textFragment);
    }

    @Override
    public void updateTextFragment(TextFragment textFragment) {
        textFragmentDao.updateTextFragment(textFragment);
    }
}
