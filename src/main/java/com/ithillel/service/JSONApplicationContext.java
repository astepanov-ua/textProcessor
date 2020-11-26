package com.ithillel.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class JSONApplicationContext implements ApplicationContext {
    private Map<String, Object> beans = new HashMap<>();

    public JSONApplicationContext() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File("src/main/resources/application.json"));
            JsonNode beans = root.path("beans");
            for (JsonNode b : beans) {
                String name = b.path("name").asText();
                String type = b.path("type").asText();
                String args = "";
                JsonNode constructorArgs = b.path("constructorArgs");
                if (!constructorArgs.isMissingNode()) {
                    for (JsonNode cArgs : constructorArgs) {
                        args = cArgs.asText();
                    }
                }
                this.beans.put(name, args.isEmpty() ?
                        Class.forName(type).getConstructor().newInstance() :
                        Class.forName(type).getDeclaredConstructor(Storage.class).newInstance(beans.get(args)));
                System.out.println(getBean(name));
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    public static void main(String[] args) {
        JSONApplicationContext jsonApplicationContext = new JSONApplicationContext();
    }
}


