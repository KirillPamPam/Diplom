package ru.kir.diplom.backend.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kir.diplom.backend.model.Style;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 20.04.2017.
 */
@Repository("styleDao")
public class StyleDaoImpl implements StyleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Style getStyleByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return (Style) session.createCriteria(Style.class).add(Restrictions.eq("name", name)).uniqueResult();
    }

    @Override
    public Style getStyleById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (Style) session.get(Style.class, Integer.parseInt(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Style> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Style.class).list();
    }

    @Override
    public void createStyle(Style style) {
        Session session = sessionFactory.getCurrentSession();
        session.save(style);
    }

    @Override
    public void updateStyle(Style style) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(style);
    }

    @Override
    public void deleteStyle(Style style) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(style);
    }
}
