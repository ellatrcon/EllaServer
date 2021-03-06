package com.ellactron.provissioning.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ji.wang on 2017-07-24.
 */
@EnableAutoConfiguration
@Configuration
@Import({RepositoryConfiguration.class})
@PropertySource({"classpath:config/config.properties"})
public class ApplicationConfiguration {
}


