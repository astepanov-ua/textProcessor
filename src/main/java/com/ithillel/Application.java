package com.ithillel;

import com.ithillel.service.TextProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    TextProcessor textProcessor;

    public void save(String key, final String text) {
        textProcessor.save(key, text);
    }

    public String getByKey(String key) {
        return textProcessor.getByKey(key);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        TextProcessor textProcessor = applicationContext.getBean("textProcessor", TextProcessor.class);
        textProcessor.save("1.txt", "simple text");
        System.out.println(textProcessor.getByKey("1.txt"));
    }
}
