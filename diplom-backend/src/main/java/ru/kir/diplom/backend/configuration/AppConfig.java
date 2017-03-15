package ru.kir.diplom.backend.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Kirill Zhitelev on 15.03.2017.
 */
@Configuration
@ComponentScan(basePackages = "ru.kir.diplom.backend.*")
@Import({HibernateConfiguration.class})
public class AppConfig {
}
