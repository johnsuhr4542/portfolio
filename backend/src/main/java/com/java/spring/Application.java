package com.java.spring;

import com.java.model.domain.Article;
import com.java.model.domain.Reply;
import com.java.model.jwt.JwtTokenAuthenticationFilter;
import com.java.model.vo.ArticleVo;
import com.java.model.vo.ReplyVo;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.Environment;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackages = "com.java")
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		addRootContext(servletContext);
		addWebContext(servletContext);
		addFilters(servletContext);
		super.onStartup(servletContext);
	}

	private void addRootContext(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

	private void addWebContext(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(WebMvcConfig.class);
		ServletRegistration.Dynamic servlet =
			servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
		servlet.setMultipartConfig(
			new MultipartConfigElement(servletContext.getRealPath("/resources/temp")));
		servlet.setAsyncSupported(true);
	}

	private void addFilters(ServletContext servletContext) {
		FilterRegistration.Dynamic characterEncodingFilter =
			servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", StandardCharsets.UTF_8.name());
		characterEncodingFilter.setInitParameter("forceEncoding", Boolean.TRUE.toString());
		characterEncodingFilter.setAsyncSupported(true);
		characterEncodingFilter.addMappingForUrlPatterns(
			EnumSet.allOf(DispatcherType.class), true, "/*");

		FilterRegistration.Dynamic springSecurityFilterChain =
			servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
		springSecurityFilterChain.setAsyncSupported(true);
		springSecurityFilterChain.addMappingForUrlPatterns(
			EnumSet.allOf(DispatcherType.class), true, "/*");

		FilterRegistration.Dynamic xssFilter =
			servletContext.addFilter("xssFilter", new XssEscapeServletFilter());
		xssFilter.addMappingForUrlPatterns(
			EnumSet.allOf(DispatcherType.class), true, "/*");
	}

	@Configuration
	@EnableTransactionManagement
	protected static class RootConfig {

		@Value("${config.dataSource.driverClassName}")
		private String driverClassName;
		@Value("${config.dataSource.url}")
		private String url;
		@Value("${config.dataSource.username}")
		private String username;
		@Value("${config.dataSource.password}")
		private String password;

		@Value("${config.jpa.dialect}")
		private String dialect;

		@Bean
		public DataSource dataSource() {
			return DataSourceBuilder
				.create()
				.type(HikariDataSource.class)
				.driverClassName(driverClassName)
				.url(url)
				.username(username)
				.password(password)
				.build();
		}

		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {
			Properties props = new Properties();
			props.setProperty(Environment.DIALECT, dialect);
			props.setProperty(Environment.HBM2DDL_AUTO, "create");
			props.setProperty(Environment.SHOW_SQL, Boolean.TRUE.toString());
			props.setProperty(Environment.FORMAT_SQL, Boolean.TRUE.toString());
			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
				new LocalContainerEntityManagerFactoryBean();
			entityManagerFactoryBean.setDataSource(dataSource);
			entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
			entityManagerFactoryBean.setJpaProperties(props);
			entityManagerFactoryBean.setPackagesToScan("com.java.model.domain");
			return entityManagerFactoryBean;
		}

		@Bean
		public PlatformTransactionManager transactionManager(@Autowired EntityManagerFactory entityManagerFactory) {
			return Optional
				.ofNullable(entityManagerFactory)
				.map(JpaTransactionManager::new)
				.orElseThrow(() -> new RuntimeException("no entityManagerFactory bean set"));
		}

		@Bean
		public ModelMapper modelMapper() {
			ModelMapper modelMapper = new ModelMapper();
			// exclude FK for lazy fetching
			modelMapper.addMappings(new PropertyMap<Article, ArticleVo>() {
				@Override
				protected void configure() {
					skip(source.getAuthor(), destination.getAuthor());
				}
			});
			modelMapper.addMappings(new PropertyMap<Reply, ReplyVo>() {
				@Override
				protected void configure() {
					skip(source.getAuthor(), destination.getAuthor());
					skip(source.getArticle(), destination.getArticle());
				}
			});
			return modelMapper;
		}

	}

	@Configuration
	@EnableWebMvc
	protected static class WebMvcConfig implements WebMvcConfigurer {

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry
				.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
		}

		@Override
		public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
			for (HttpMessageConverter<?> converter: converters)
				if (converter instanceof MappingJackson2HttpMessageConverter)
					((MappingJackson2HttpMessageConverter) converter).setPrettyPrint(true);
		}

	}

	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired private UserDetailsService userDetailsServiceImpl;
		@Autowired private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

		@Override
		public void configure(WebSecurity web) throws Exception {
			web
				.ignoring()
				.antMatchers("/resources/**");
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
				.userDetailsService(userDetailsServiceImpl)
				.passwordEncoder(passwordEncoder());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.headers().disable()
				.httpBasic().disable()
				.exceptionHandling().authenticationEntryPoint(entryPoint())
				.and()

				.authorizeRequests()
				.anyRequest().permitAll()
				.and()

				.formLogin()
				.successHandler(successHandler())
				.failureHandler(failureHandler())
				.permitAll()
				.and()

				.logout()
				.permitAll()
				.and()

				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()

				.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}

		@Bean
		public AuthenticationEntryPoint entryPoint() {
			return (req, resp, ex) -> {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
			};
		}

		@Bean
		public AuthenticationSuccessHandler successHandler() {
			return (req, resp, auth) -> {
				resp.setStatus(HttpServletResponse.SC_OK);
			};
		}

		@Bean
		public AuthenticationFailureHandler failureHandler() {
			return (req, resp, ex) -> {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
			};
		}

		@Bean(name = "authenticationManager")
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}

}