package ru.kir.diplom.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.diplom.backend.dao.DocPatternDao;
import ru.kir.diplom.backend.dao.StyleDao;
import ru.kir.diplom.backend.model.DocPattern;
import ru.kir.diplom.backend.model.client.ClientDocPattern;
import ru.kir.diplom.backend.model.rest.RequestCreateDocPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
@Service("docService")
@Transactional
public class DocPatternServiceImpl implements DocPatternService {
    @Autowired
    private DocPatternDao docPatternDao;
    @Autowired
    private StyleDao styleDao;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public void createDocPattern(RequestCreateDocPattern docPattern) {
        DocPattern savedPattern = new DocPattern();
        savedPattern.setStyle(styleDao.getStyleByName(docPattern.getStyle()));
        savedPattern.setFragments(docPattern.getFragments());
        savedPattern.setLuValue(docPattern.getLuValue());
        savedPattern.setName(docPattern.getName());
        docPatternDao.createDocPattern(savedPattern);
    }

    @Override
    public List<ClientDocPattern> getAll() {
        List<DocPattern> docPatterns = docPatternDao.getAll();
        List<ClientDocPattern> clientDocPatterns = new ArrayList<>();
        docPatterns.forEach(pattern -> clientDocPatterns.add(mapper.map(pattern, ClientDocPattern.class)));

        return clientDocPatterns;
    }

    @Override
    public List<ClientDocPattern> getDocPatternByTemplate(String template) {
        List<DocPattern> docPatterns = docPatternDao.getDocPatternByTemplate(template);
        List<ClientDocPattern> clientDocPatterns = new ArrayList<>();
        docPatterns.forEach(pattern -> clientDocPatterns.add(mapper.map(pattern, ClientDocPattern.class)));

        return clientDocPatterns;
    }

    @Override
    public ClientDocPattern getDocPatternById(String id) {
        DocPattern docPattern = docPatternDao.getDocPatternById(id);
        if (docPattern == null)
            return null;
        return mapper.map(docPattern, ClientDocPattern.class);
    }
}
