package ru.kir.diplom.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.diplom.backend.dao.TextFragmentDao;
import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.TextFragment;
import ru.kir.diplom.backend.model.client.ClientTextFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
@Service("textService")
@Transactional
public class TextFragmentServiceImpl implements TextFragmentService {
    @Autowired
    private TextFragmentDao textFragmentDao;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public void createTextFragment(TextFragment textFragment) {
        textFragmentDao.createTextFragment(textFragment);
    }

    @Override
    public ClientTextFragment getTextFragment(String name) {
        return mapper.map(textFragmentDao.getTextFragment(name), ClientTextFragment.class);
    }

    @Override
    public List<ClientTextFragment> getAll() {
        List<TextFragment> textFragments = textFragmentDao.getAll();
        List<ClientTextFragment> clientTextFragments = new ArrayList<>();
        textFragments.forEach(textFragment -> {
            clientTextFragments.add(mapper.map(textFragment, ClientTextFragment.class));
        });

        return clientTextFragments;
    }

    @Override
    public List<ClientTextFragment> getAll(SingleSource singleSource) {
        List<TextFragment> textFragments = textFragmentDao.getAll(singleSource);
        List<ClientTextFragment> clientTextFragments = new ArrayList<>();
        textFragments.forEach(textFragment -> clientTextFragments.add(mapper.map(textFragment, ClientTextFragment.class)));

        return clientTextFragments;
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
