package classes;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.codehaus.groovy.runtime.DefaultGroovyMethodsSupport.closeQuietly;

public final class ApplicationProperties {

    private static final ApplicationProperties INSTANCE = new ApplicationProperties();
    private final Map<String, String> properties = new HashMap<>();
    private static final String PATH_TO_PROPERTIES = "src/main/resources/application.properties";

    private ApplicationProperties() {
        Properties pr = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(PATH_TO_PROPERTIES));
            pr.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(in);
        }

        Enumeration e = pr.propertyNames();
        String key;
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            properties.put(key, pr.getProperty(key));
        }
    }

    public static ApplicationProperties getInstance() {
        return INSTANCE;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public ApplicationProperties setProperty(String key, String value) {
        properties.put(key, value);
        return this;
    }

    /**
     * Перезаписывает файл свойств текущими значениями объекта properties.
     */
    public ApplicationProperties save() {
        Properties p = new Properties();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(PATH_TO_PROPERTIES);
            p.putAll(properties);
            p.store(os, "update values");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(os);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Параметры property файла \n" + properties.toString();
    }

}
