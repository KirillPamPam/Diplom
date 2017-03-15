package ru.kir.diplom.backend.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kir.diplom.backend.model.SingleSource;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
@Repository("singleDao")
public class SingleSourceDaoImpl implements SingleSourceDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createSingleSource(SingleSource source) {
        Session session = sessionFactory.getCurrentSession();
        session.save(source);
    }

    @Override
    public SingleSource getSingleSource(String name) {
        Session session = sessionFactory.getCurrentSession();
        return (SingleSource) session.createCriteria(SingleSource.class)
                .add(Restrictions.eq("singleName", name)).uniqueResult();
    }

    @Override
    public void deleteSingleSource(String name) {
        Session session = sessionFactory.getCurrentSession();
        SingleSource source = getSingleSource(name);
        session.delete(source);
    }

    @Override
    public void updateSingleSource(SingleSource source) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(source);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SingleSource> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(SingleSource.class).list();
    }
}
