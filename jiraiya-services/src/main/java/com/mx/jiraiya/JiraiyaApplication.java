    package com.mx.jiraiya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.mx.jiraiya"})
public class JiraiyaApplication extends SpringBootServletInitializer {

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(JiraiyaApplication.class);
        }

        public static void main(String[] args) throws Exception {
            SpringApplication.run(JiraiyaApplication.class, args);
        }
    }

