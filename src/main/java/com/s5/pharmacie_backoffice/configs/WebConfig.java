// package com.s5.pharmacie_backoffice.configs;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.servlet.view.InternalResourceViewResolver;

// @Configuration
// public class WebConfig  implements WebMvcConfigurer{

//     @Bean
//     public InternalResourceViewResolver viewResolver() {
//         InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//         resolver.setPrefix("/WEB-INF/views/"); // Chemin des fichiers JSP
//         resolver.setSuffix(".jsp");          // Extension des fichiers JSP
//         return resolver;
//     }

//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//     }
// }
