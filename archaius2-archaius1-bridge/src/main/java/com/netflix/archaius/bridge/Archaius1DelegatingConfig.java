package com.netflix.archaius.bridge;

import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;

import com.netflix.archaius.api.Config;
import com.netflix.archaius.api.config.SettableConfig;
import com.netflix.archaius.config.AbstractConfig;
import com.netflix.config.ConfigurationManager;

public class Archaius1DelegatingConfig extends AbstractConfig implements SettableConfig {
    
    private AbstractConfiguration archaius1Config;

    public Archaius1DelegatingConfig() {
        archaius1Config = ConfigurationManager.getConfigInstance();
        archaius1Config.addConfigurationListener(new ConfigurationListener() {
            
            @Override
            public void configurationChanged(ConfigurationEvent event) {
                notifyConfigUpdated(null);   
            }
        });
    }

    @Override
    public Object getRawProperty(String key) {
       return archaius1Config.getProperty(key); 
    }

    @Override
    public boolean containsKey(String key) {
        return archaius1Config.containsKey(key);
    }

    @Override
    public boolean isEmpty() {
        return archaius1Config.isEmpty();
    }

    @Override
    public Iterator<String> getKeys() {
        return archaius1Config.getKeys();
    }

    @Override
    public void setProperties(Config config) {
        Iterator<String> keys = config.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            archaius1Config.addProperty(key, config.getRawProperty(key));
        }
    }

    @Override
    public void setProperties(Properties properties) {
       ConfigurationManager.loadProperties(properties);
    }

    @Override
    public <T> void setProperty(String propName, T propValue) {
        archaius1Config.setProperty(propName, propValue);
    }

    @Override
    public void clearProperty(String propName) {
        archaius1Config.clearProperty(propName);
    }

}
