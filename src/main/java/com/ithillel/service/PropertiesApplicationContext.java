package com.ithillel.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesApplicationContext implements ApplicationContext {
    private Map<String, Object> beans = new HashMap<>();

    public PropertiesApplicationContext() {
        Properties applicationProperties = new Properties();
        try {
            applicationProperties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; ; i++) {
            String name = applicationProperties.getProperty("beans[" + i + "].name");
            String type = applicationProperties.getProperty("beans[" + i + "].type");
            String args = applicationProperties.getProperty("beans[" + i + "].args");
            if (name == null || type == null) {
                break;
            } else {
                try {
                    beans.put(name, args == null ?
                            Class.forName(type).getConstructor().newInstance() :
                            Class.forName(type).getDeclaredConstructor(Storage.class).newInstance(beans.get(args)));
                    System.out.println(getBean(name));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    public static void main(String[] args) {
        PropertiesApplicationContext propertiesApplicationContext = new PropertiesApplicationContext();
    }
}
