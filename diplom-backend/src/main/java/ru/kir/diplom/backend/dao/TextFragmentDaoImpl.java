package ru.kir.diplom.backend.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.TextFragment;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
@Repository("textDao")
public class TextFragmentDaoImpl implements TextFragmentDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createTextFragment(TextFragment textFragment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(textFragment);
    }

    @Override
    public TextFragment getTextFragmentByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return (TextFragment) session.createCriteria(TextFragment.class)
                .add(Restrictions.eq("fragmentName", name)).uniqueResult();
    }

    @Override
    public TextFragment getTextFragmentById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (TextFragment) session.get(TextFragment.class, Integer.parseInt(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TextFragment> getTextFragmentsByPattern(String pattern) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(TextFragment.class).add(Restrictions.like("fragmentName", pattern, MatchMode.ANYWHERE)).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TextFragment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(TextFragment.class).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TextFragment> getAll(SingleSource singleSource) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(TextFragment.class)
                .add(Restrictions.eq("singleSource", singleSource)).list();
    }

    @Override
    public void deleteTextFragment(TextFragment textFragment) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(textFragment);
    }

    @Override
    public void updateTextFragment(TextFragment textFragment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(textFragment);
    }
}
