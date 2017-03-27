package ru.kir.diplom.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.diplom.backend.dao.TextFragmentDao;
import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.TextFragment;
import ru.kir.diplom.backend.model.client.ClientTextFragment;
import ru.kir.diplom.backend.model.rest.RequestCreateTextFragment;
import ru.kir.diplom.backend.model.rest.RequestUpdateTextFragment;

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
    @Autowired
    private SingleSourceService sourceService;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public void createTextFragment(RequestCreateTextFragment textFragment) {
        TextFragment savedTextFragment = new TextFragment();
        savedTextFragment.setFragmentName(textFragment.getName());
        savedTextFragment.setText(textFragment.getText());
        savedTextFragment.setSingleSource(sourceService.getSingleSource(textFragment.getSourceName()));

        textFragmentDao.createTextFragment(savedTextFragment);
    }

    @Override
    public ClientTextFragment getClientTextFragmentById(String id) {
        TextFragment textFragment = textFragmentDao.getTextFragmentById(id);
        if (textFragment == null)
            return null;
        return mapper.map(textFragment, ClientTextFragment.class);
    }

    @Override
    public TextFragment getTextFragmentById(String id) {
        return textFragmentDao.getTextFragmentById(id);
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
    public boolean deleteTextFragment(String id) {
        TextFragment textFragment = textFragmentDao.getTextFragmentById(id);
        if (textFragment == null)
            return false;
        textFragmentDao.deleteTextFragment(textFragment);
        return true;
    }

    @Override
    public boolean updateTextFragment(String id, RequestUpdateTextFragment newTextFragment) {
        TextFragment textFragment = textFragmentDao.getTextFragmentById(id);
        if (textFragment == null)
            return false;
        textFragment.setText(newTextFragment.getText());
        textFragment.setFragmentName(newTextFragment.getFragmentName());

        textFragmentDao.updateTextFragment(textFragment);
        return true;
    }
}
