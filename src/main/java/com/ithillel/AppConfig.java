package com.ithillel;

import com.ithillel.service.HashMapStorage;
import com.ithillel.service.InMemoryTextProcessor;
import com.ithillel.service.Storage;
import com.ithillel.service.TextProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ithillel.service")
public class AppConfig {

    @Bean (name = "storage")
    public Storage getStorage() {
        return new HashMapStorage();
    }

    @Bean(name = "textProcessor")
    public TextProcessor getTextProcessor() {
        return new InMemoryTextProcessor(getStorage());
    }
}
