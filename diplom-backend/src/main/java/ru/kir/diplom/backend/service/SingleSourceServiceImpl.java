package ru.kir.diplom.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.diplom.backend.dao.SingleSourceDao;
import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.client.ClientSingleSource;

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
    public void createSingleSource(SingleSource source) {
        singleSourceDao.createSingleSource(source);
    }

    @Override
    public SingleSource getSingleSource(String name) {
        return singleSourceDao.getSingleSource(name);
    }

    @Override
    public ClientSingleSource getClientSingleSource(String name) {
        return mapper.map(singleSourceDao.getSingleSource(name), ClientSingleSource.class);
    }

    @Override
    public void deleteSingleSource(String name) {
        singleSourceDao.deleteSingleSource(name);
    }

    @Override
    public void updateSingleSource(SingleSource source) {
        singleSourceDao.updateSingleSource(source);
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
