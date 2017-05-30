package ru.kir.diplom.backend.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kir.diplom.backend.model.DocPattern;
import ru.kir.diplom.backend.model.SingleSource;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 11.05.2017.
 */
@Repository("docDao")
public class DocPatternDaoImpl implements DocPatternDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createDocPattern(DocPattern docPattern, SingleSource singleSource) {
        Session session = sessionFactory.getCurrentSession();
        if (!getAll(singleSource).contains(docPattern))
            session.save(docPattern);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DocPattern> getAll(SingleSource singleSource) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(DocPattern.class).add(Restrictions.eq("singleSource", singleSource)).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DocPattern> getDocPatternByTemplate(String template) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(DocPattern.class).add(Restrictions.like("name", template, MatchMode.ANYWHERE)).list();
    }

    @Override
    public DocPattern getDocPatternById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (DocPattern) session.get(DocPattern.class, Integer.parseInt(id));
    }
}
