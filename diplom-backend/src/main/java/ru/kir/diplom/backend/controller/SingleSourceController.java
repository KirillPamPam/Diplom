package ru.kir.diplom.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kir.diplom.backend.model.SingleSource;
import ru.kir.diplom.backend.model.TextFragment;
import ru.kir.diplom.backend.model.client.ClientSingleSource;
import ru.kir.diplom.backend.model.client.ClientTextFragment;
import ru.kir.diplom.backend.model.exception.InvalidRequestException;
import ru.kir.diplom.backend.model.exception.NullRequestException;
import ru.kir.diplom.backend.model.rest.*;
import ru.kir.diplom.backend.service.SingleSourceService;
import ru.kir.diplom.backend.service.TextFragmentService;
import ru.kir.diplom.backend.util.Constants;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Kirill Zhitelev on 18.03.2017.
 */
@Controller
@RequestMapping("/singlesource/operations")
public class SingleSourceController {
    @Autowired
    private TextFragmentService fragmentService;
    @Autowired
    private SingleSourceService sourceService;

    @RequestMapping(value = "get/fragment/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ClientTextFragment getTextFragment(@PathVariable(Constants.ID) String id) {
        ClientTextFragment textFragment = fragmentService.getClientTextFragmentById(id);

        if(textFragment == null)
            throw new NullRequestException(Constants.WRONG_ID, id, TextFragment.class, Constants.ID);

        return textFragment;
    }

    @RequestMapping(value = "get/fragment/pattern/{pattern}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ClientTextFragment> getTextFragmentsByPattern(@PathVariable("pattern") String pattern) {
        return fragmentService.getTextFragmentsByPattern(pattern);
    }

    @RequestMapping(value = "getall/fragment/{sourceName}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ClientTextFragment> getAllTextFragment(@PathVariable(Constants.SOURCE_NAME) String singleSource) {
        SingleSource source = sourceService.getSingleSource(singleSource);
        return fragmentService.getAll(source);
    }

    @RequestMapping(value = "get/source/{name}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ClientSingleSource getSingleSource(@PathVariable(Constants.NAME) String name) {
        ClientSingleSource singleSource = sourceService.getClientSingleSource(name);

        if(singleSource == null) {
            throw new NullRequestException(Constants.WRONG_NAME, name, SingleSource.class, Constants.NAME);
        }

        return singleSource;
    }

    @RequestMapping(value = "getall/source", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ClientSingleSource> getAllSingleSource() {
        return sourceService.getAll();
    }

    @RequestMapping(value = "create/fragment", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity createTextFragment(@RequestBody @Valid RequestCreateTextFragment textFragment,
                                             BindingResult result) {
        if(result.hasErrors()) {
            throw new InvalidRequestException(Constants.INVALID_REQUEST, result);
        }
        if(sourceService.getSingleSource(textFragment.getSourceName()) == null) {
            throw new NullRequestException(
                    Constants.WRONG_SOURCE_NAME, textFragment.getSourceName(), SingleSource.class, Constants.SOURCE_NAME);
        }
        else {
            fragmentService.createTextFragment(textFragment);
            return getOkResponse();
        }
    }

    @RequestMapping(value = "update/fragment", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity updateTextFragment(@RequestBody @Valid RequestUpdateTextFragment textFragment,
                                             BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestException(Constants.INVALID_REQUEST, result);
        }
        boolean isUpdate = fragmentService.updateTextFragment(textFragment.getId(), textFragment);

        if (!isUpdate) {
            throw new NullRequestException(Constants.WRONG_ID, textFragment.getId(), TextFragment.class, Constants.ID);
        }
        return getOkResponse();
    }

    @RequestMapping(value = "delete/fragment", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public ResponseEntity deleteTextFragment(@RequestBody @Valid RequestDeleteTextFragment textFragment, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidRequestException(Constants.INVALID_REQUEST, result);
        }
        boolean isDeleted = fragmentService.deleteTextFragment(textFragment.getId());
        if (!isDeleted) {
            throw new NullRequestException(Constants.WRONG_ID, textFragment.getId(), TextFragment.class, Constants.ID);
        }
        return getOkResponse();
    }

    @RequestMapping(value = "create/source", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public ResponseEntity createSingleSource(@RequestBody @Valid RequestCreateSingleSource singleSource, BindingResult result) {
        if(result.hasErrors()) {
            throw new InvalidRequestException(Constants.INVALID_REQUEST, result);
        }
        else {
            sourceService.createSingleSource(singleSource);
            return getOkResponse();
        }
    }

    @RequestMapping(value = "delete/source", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public ResponseEntity deleteSingleSource(@RequestBody @Valid RequestDeleteSingleSource singleSource, BindingResult result) {
        if(result.hasErrors()) {
            throw new InvalidRequestException(Constants.INVALID_REQUEST, result);
        }
        boolean isDeleted = sourceService.deleteSingleSource(singleSource.getId());
        if (!isDeleted) {
            throw new NullRequestException(Constants.WRONG_ID, singleSource.getId(), SingleSource.class, Constants.ID);
        }
        return getOkResponse();
    }

    @RequestMapping(value = "update/source", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public ResponseEntity updateSingleSource(@RequestBody @Valid RequestUpdateSingleSource singleSource, BindingResult result) {
        if(result.hasErrors()) {
            throw new InvalidRequestException(Constants.INVALID_REQUEST, result);
        }
        boolean isUpdated = sourceService.updateSingleSource(singleSource.getId(), singleSource);
        if (!isUpdated) {
            throw new NullRequestException(Constants.WRONG_ID, singleSource.getId(), SingleSource.class, Constants.ID);
        }
        return getOkResponse();
    }

    private ResponseEntity getOkResponse() {
        HttpResponseDescriptor responseDescriptor = new HttpResponseDescriptor();
        responseDescriptor.setCode(HttpStatus.OK.value());
        responseDescriptor.setFieldErrors(null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDescriptor);
    }
}
