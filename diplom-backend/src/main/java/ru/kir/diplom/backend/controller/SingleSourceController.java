package ru.kir.diplom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.client.ClientSingleSource;
import ru.kir.diplom.backend.model.client.ClientTextFragment;
import ru.kir.diplom.backend.service.SingleSourceService;
import ru.kir.diplom.backend.service.TextFragmentService;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 18.03.2017.
 */
@Controller
@RequestMapping("/singlesource")
public class SingleSourceController {
    @Autowired
    private TextFragmentService fragmentService;
    @Autowired
    private SingleSourceService sourceService;

    @RequestMapping(value = "operations/get/fragment", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ClientTextFragment getTextFragment(@RequestParam("name") String name) {
        return fragmentService.getTextFragment(name);
    }

    @RequestMapping(value = "operations/getall/fragments", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ClientTextFragment> getAllTextFragment(@RequestParam("sourcename") String singleSource) {
        SingleSource source = sourceService.getSingleSource(singleSource);
        return fragmentService.getAll(source);
    }

    @RequestMapping(value = "operations/get/source", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ClientSingleSource getSingleSource(@RequestParam("name") String name) {
        return sourceService.getClientSingleSource(name);
    }

    @RequestMapping(value = "operations/getall/source", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ClientSingleSource> getAllSingleSource() {
        return sourceService.getAll();
    }
}
