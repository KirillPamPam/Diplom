package ru.kir.diplom.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.diplom.backend.dao.SingleSourceDao;
import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.client.ClientSingleSource;
import ru.kir.diplom.backend.model.rest.RequestCreateSingleSource;
import ru.kir.diplom.backend.model.rest.RequestUpdateSingleSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Zhitelev on 08.03.2017.
 */
@Service("singleService")
@Transactional
public class SingleSourceServiceImpl implements SingleSourceService {
    @Autowired
    private SingleSourceDao singleSourceDao;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public void createSingleSource(RequestCreateSingleSource source) {
        SingleSource singleSource = new SingleSource();
        singleSource.setSingleName(source.getName());
        singleSourceDao.createSingleSource(singleSource);
    }

    @Override
    public SingleSource getSingleSource(String name) {
        return singleSourceDao.getSingleSourceByName(name);
    }

    @Override
    public ClientSingleSource getClientSingleSource(String name) {
        SingleSource singleSource = singleSourceDao.getSingleSourceByName(name);
        if(singleSource == null)
            return null;
        return mapper.map(singleSource, ClientSingleSource.class);
    }

    @Override
    public boolean deleteSingleSource(String id) {
        SingleSource singleSource = singleSourceDao.getSingleSourceById(id);
        if (singleSource == null)
            return false;
        singleSourceDao.deleteSingleSource(singleSource);
        return true;
    }

    @Override
    public boolean updateSingleSource(String id, RequestUpdateSingleSource source) {
        SingleSource singleSource = singleSourceDao.getSingleSourceById(id);
        if (singleSource == null)
            return false;
        singleSource.setSingleName(source.getName());
        singleSourceDao.updateSingleSource(singleSource);
        return true;
    }

    @Override
    public List<ClientSingleSource> getAll() {
        List<SingleSource> singleSources = singleSourceDao.getAll();
        List<ClientSingleSource> clientSingleSources = new ArrayList<>();
        singleSources.forEach(singleSource -> {
            clientSingleSources.add(mapper.map(singleSource, ClientSingleSource.class));
        });

        return clientSingleSources;
    }
}
