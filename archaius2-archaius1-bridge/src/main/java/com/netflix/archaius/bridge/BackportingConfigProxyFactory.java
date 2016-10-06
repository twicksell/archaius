package com.netflix.archaius.bridge;

import com.netflix.archaius.ConfigProxyFactory;
import com.netflix.archaius.api.Config;
import com.netflix.config.ConfigurationManager;

public class BackportingConfigProxyFactory extends ConfigProxyFactory {

    public BackportingConfigProxyFactory(Config config) {
        super(config);
    }
    
    @Override
    protected <T> MethodInvoker<T> createDynamicProperty(Class<T> type, String propName, String defaultValue) {
        if(defaultValue != null && getConfig().getRawProperty(propName) == null)
        {
            ConfigurationManager.getConfigInstance().setProperty(propName, defaultValue);
        }
        return super.createDynamicProperty(type, propName, defaultValue);
    }
}
