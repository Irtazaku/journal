package com.journal;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrations;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

@SpringBootApplication
@EnableAutoConfiguration
public class JournalApplication extends WebMvcAutoConfiguration.EnableWebMvcConfiguration {

	public JournalApplication(ObjectProvider<WebMvcProperties> mvcPropertiesProvider, ObjectProvider<WebMvcRegistrations> mvcRegistrationsProvider, ListableBeanFactory beanFactory) {
		super(mvcPropertiesProvider, mvcRegistrationsProvider, beanFactory);
	}

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		super.configurePathMatch(configurer);
		configurer.setUseRegisteredSuffixPatternMatch(false);
		configurer.setUseSuffixPatternMatch(false);
	}
}
