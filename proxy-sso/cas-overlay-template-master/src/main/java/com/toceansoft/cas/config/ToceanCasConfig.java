package com.toceansoft.cas.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@Configuration
@ComponentScan("com.toceansoft.cas")
@ServletComponentScan
public class ToceanCasConfig extends WebMvcConfigurerAdapter {
	

}
