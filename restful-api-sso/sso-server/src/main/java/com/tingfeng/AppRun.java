package com.tingfeng;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
// Servlet 的listener filter标注需要这个才生效
@ServletComponentScan
@Configuration
public class AppRun extends WebMvcConfigurerAdapter {
	@Value("${cros.allow.headers}")
	private String corsAllowHeader = "Set-Cookie,x-auth-token,content-type,X-Requested-With,XMLHttpRequest,Authorization,Content-Disposition,Cookie,Content-Length,Accept,Accept-Encoding,Accept-Language,Cache-Control,Connection,Content-Type,Host,Origin,Referer,Upgrade-Insecure-Requests,User-Agent,If-Modified-Since,If-None-Match";
	@Value("${cros.expose.headers}")
	private String corsExposeHeader = "Set-Cookie,x-auth-token,content-type,X-Requested-With,XMLHttpRequest,Authorization,Content-Disposition,Cookie,Content-Length,Accept,Accept-Encoding,Accept-Language,Cache-Control,Connection,Content-Type,Host,Origin,Referer,Upgrade-Insecure-Requests,User-Agent,If-Modified-Since,If-None-Match";
	@Value("${cros.allow.methods}")
	private String corsMethod = "GET,POST,HEAD,OPTIONS,PUT,DELETE,TRACE,CONNECT";
	@Value("${cros.allow.domains}")
	private String corsDomain = "http://localhost:8080,http://localhost:8083";
	// @Bean
	// public MyServletContextListener getContext() {
	// return new MyServletContextListener();
	// }


	@Bean
	public FilterRegistrationBean corsFilter() {

		UrlBasedCorsConfigurationSource source = configurationSource();
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

	private UrlBasedCorsConfigurationSource configurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		List<String> allowedHeaders = initParam(corsAllowHeader);
		List<String> exposedHeaders = initParam(corsExposeHeader);
		List<String> allowedMethods = initParam(corsMethod);
		List<String> allowedOrigins = initParam(corsDomain);
		corsConfig.setAllowedHeaders(allowedHeaders);
		corsConfig.setAllowedMethods(allowedMethods);
		corsConfig.setAllowedOrigins(allowedOrigins);
		corsConfig.setExposedHeaders(exposedHeaders);
		corsConfig.setMaxAge(36000L);
		corsConfig.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	private List<String> initParam(String name) {
		String[] tmps = name.split(",");
		List<String> params = Arrays.asList(tmps);
		return params;
	}
	

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }
	
}
