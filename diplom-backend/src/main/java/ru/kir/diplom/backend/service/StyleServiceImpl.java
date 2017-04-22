package ru.kir.diplom.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.diplom.backend.dao.StyleDao;
import ru.kir.diplom.backend.model.Style;
import ru.kir.diplom.backend.model.client.ClientStyle;
import ru.kir.diplom.backend.model.rest.RequestCreateStyle;
import ru.kir.diplom.backend.model.rest.RequestUpdateStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Zhitelev on 20.04.2017.
 */
@Service("styleService")
@Transactional
public class StyleServiceImpl implements StyleService {
    @Autowired
    private StyleDao styleDao;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public ClientStyle getStyleByName(String name) {
        Style style = styleDao.getStyleByName(name);
        if (style == null)
            return null;

        return mapper.map(style, ClientStyle.class);
    }

    @Override
    public ClientStyle getStyleById(String id) {
        Style style = styleDao.getStyleById(id);
        if (style == null)
            return null;

        return mapper.map(style, ClientStyle.class);
    }

    @Override
    public List<ClientStyle> getAll() {
        List<Style> styles = styleDao.getAll();
        List<ClientStyle> clientStyles = new ArrayList<>();
        styles.forEach(style -> clientStyles.add(mapper.map(style, ClientStyle.class)));

        return clientStyles;
    }

    @Override
    public void createStyle(RequestCreateStyle style) {
        Style savedStyle = new Style();
        savedStyle.setName(style.getName());
        savedStyle.setProperties(style.getProperties());

        styleDao.createStyle(savedStyle);
    }

    @Override
    public boolean updateStyle(String id, RequestUpdateStyle style) {
        Style currentStyle = styleDao.getStyleById(id);
        if (currentStyle == null)
            return false;
        currentStyle.setName(style.getName());
        currentStyle.setProperties(style.getProperties());
        styleDao.updateStyle(currentStyle);

        return true;
    }

    @Override
    public boolean deleteStyle(String id) {
        Style currentStyle = styleDao.getStyleById(id);
        if (currentStyle == null)
            return false;
        styleDao.deleteStyle(currentStyle);

        return true;
    }
}
