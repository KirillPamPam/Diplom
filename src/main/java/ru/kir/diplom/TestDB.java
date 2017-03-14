package ru.kir.diplom;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.kir.diplom.backend.service.SingleSourceService;
import ru.kir.diplom.backend.service.TextFragmentService;

/**
 * Created by Kirill Zhitelev on 12.03.2017.
 */
public class TestDB {
    public static void main(String[] args) {
        ApplicationContext context = new GenericXmlApplicationContext("/spring/app-context-config.xml");
        TextFragmentService service = context.getBean("textService", TextFragmentService.class);
        SingleSourceService sourceService = (SingleSourceService) context.getBean("singleService");
        sourceService.deleteSingleSource("test");
    }
}
