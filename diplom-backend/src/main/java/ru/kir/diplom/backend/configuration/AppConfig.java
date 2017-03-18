package ru.kir.diplom.backend.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Kirill Zhitelev on 15.03.2017.
 */
@Configuration
@ComponentScan(basePackages = "ru.kir.diplom.backend.*")
@EnableWebMvc
@Import({HibernateConfiguration.class})
public class AppConfig {
}
