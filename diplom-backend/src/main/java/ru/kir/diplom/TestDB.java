package ru.kir.diplom;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kir.diplom.backend.service.TextFragmentService;

/**
 * Created by Kirill Zhitelev on 12.03.2017.
 */
public class TestDB {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.kir.diplom.backend");

        TextFragmentService service = context.getBean("textService", TextFragmentService.class);

        System.out.println(service.getTextFragmentsByPattern("разработки"));
    }
}
