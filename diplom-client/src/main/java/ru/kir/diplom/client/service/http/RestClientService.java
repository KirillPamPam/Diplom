package ru.kir.diplom.client.service.http;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import ru.kir.diplom.client.model.DocPattern;
import ru.kir.diplom.client.model.Style;
import ru.kir.diplom.client.model.http.*;
import ru.kir.diplom.client.model.SingleSource;
import ru.kir.diplom.client.model.TextFragment;
import ru.kir.diplom.client.model.error.HttpResponseDescriptor;
import ru.kir.diplom.client.util.Constants;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 27.03.2017.
 */
public class RestClientService {
    private Client client;
    private static RestClientService restClientService;

    private RestClientService() {
        init();
    }

    public static RestClientService getInstance() {
        if (restClientService == null)
            restClientService = new RestClientService();

        return restClientService;
    }

    private void init() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        client = Client.create(clientConfig);
    }

    public DocPattern getDocPattern(String id) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_DOCPATTERN + id);
        ClientResponse response = webResource.get(ClientResponse.class);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(DocPattern.class);
    }

    public List<DocPattern> getAllDocPatterns() {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_ALL_DOCPATTERN);
        ClientResponse response = webResource.get(ClientResponse.class);

        return response.getEntity(new GenericType<List<DocPattern>>() {});
    }

    public List<DocPattern> getAllDocPatternsByTemplate(String template) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_DOCPATTERN_BY_TEMPLATE + template);
        ClientResponse response = webResource.get(ClientResponse.class);

        return response.getEntity(new GenericType<List<DocPattern>>() {});
    }

    public HttpResponseDescriptor createDocPattern(String name, String fragments, String style, String luValue) {
        RequestCreateDocPattern docPattern = new RequestCreateDocPattern(style, fragments, name, luValue);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.CREATE_DOCPATTERN);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, docPattern);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public Style getStyle(String id) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_STYLE + id);
        ClientResponse response = webResource.get(ClientResponse.class);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(Style.class);
    }

    public Style getStyleByName(String name) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_STYLE_BY_NAME + name);
        ClientResponse response = webResource.get(ClientResponse.class);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(Style.class);
    }

    public List<Style> getAllStyles() {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_ALL_STYLE);
        ClientResponse response = webResource.get(ClientResponse.class);

        return response.getEntity(new GenericType<List<Style>>() {});
    }

    public HttpResponseDescriptor createStyle(String name, String properties) {
        RequestCreateStyle style= new RequestCreateStyle(name, properties);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.CREATE_STYLE);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, style);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public HttpResponseDescriptor updateStyle(String id, String name, String properties) {
        RequestUpdateStyle style= new RequestUpdateStyle(id, name, properties);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.UPDATE_STYLE);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, style);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public HttpResponseDescriptor deleteStyle(String id) {
        RequestDeleteStyle style= new RequestDeleteStyle(id);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.DELETE_STYLE);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, style);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public TextFragment getTextFragment(String id) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_TEXT_FRAGMENT + id);
        ClientResponse response = webResource.get(ClientResponse.class);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(TextFragment.class);
    }

    public TextFragment getTextFragmentByName(String name, String sourceName) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_TEXT_FRAGMENT_BY_NAME + name + "/" + sourceName);
        ClientResponse response = webResource.get(ClientResponse.class);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());
        return response.getEntity(TextFragment.class);
    }

    public List<TextFragment> getTextFragmentsByPattern(String pattern) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_TEXT_FRAGMENT_BY_PATTERN + pattern);
        ClientResponse response = webResource.get(ClientResponse.class);

        return response.getEntity(new GenericType<List<TextFragment>>() {});
    }

    public List<TextFragment> getAllTextFragments(String sourceName) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_ALL_TEXT_FRAGMENT + sourceName);
        ClientResponse response = webResource.get(ClientResponse.class);

        return response.getEntity(new GenericType<List<TextFragment>>(){});
    }

    public SingleSource getSingleSource(String name) {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_SINGLE_SOURCE + name);
        ClientResponse response = webResource.get(ClientResponse.class);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(SingleSource.class);
    }

    public List<SingleSource> getAllSingleSources() {
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.GET_ALL_SINGLE_SOURCE);
        ClientResponse response = webResource.get(ClientResponse.class);

        return response.getEntity(new GenericType<List<SingleSource>>() {});
    }

    public HttpResponseDescriptor createTextFragment(String name, String text, String sourceName) {
        RequestCreateTextFragment textFragment = new RequestCreateTextFragment(name, text, sourceName);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.CREATE_TEXT_FRAGMENT);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, textFragment);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public HttpResponseDescriptor updateTextFragment(String id, String text, String fragmentName) {
        RequestUpdateTextFragment textFragment = new RequestUpdateTextFragment(id, text, fragmentName);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.UPDATE_TEXT_FRAGMENT);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, textFragment);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public HttpResponseDescriptor deleteTextFragment(String id) {
        RequestDeleteTextFragment textFragment = new RequestDeleteTextFragment(id);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.DELETE_TEXT_FRAGMENT);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, textFragment);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public HttpResponseDescriptor createSingleSource(String name) {
        RequestCreateSingleSource textFragment = new RequestCreateSingleSource(name);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.CREATE_SINGLE_SOURCE);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, textFragment);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public HttpResponseDescriptor deleteSingleSource(String id) {
        RequestDeleteSingleSource textFragment = new RequestDeleteSingleSource(id);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.DELETE_SINGLE_SOURCE);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, textFragment);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }

    public HttpResponseDescriptor updateSingleSource(String id, String name) {
        RequestUpdateSingleSource textFragment = new RequestUpdateSingleSource(id, name);
        WebResource webResource = client.resource(Constants.MAIN_PATH).path(Constants.UPDATE_SINGLE_SOURCE);
        ClientResponse response = webResource.type(Constants.APP_JSON).post(ClientResponse.class, textFragment);
        if (response.getStatus() == Integer.parseInt(Constants.BAD_REQUEST))
            throw new RuntimeException(response.getEntity(HttpResponseDescriptor.class).getMessage());

        return response.getEntity(HttpResponseDescriptor.class);
    }
}
