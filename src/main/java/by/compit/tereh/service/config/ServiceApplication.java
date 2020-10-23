package by.compit.tereh.service.config;

import org.hibernate.dialect.HSQLDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Hashtable;
import java.util.Properties;

@EntityScan(basePackages = "by.compit.tereh.service.model")
@EnableJpaRepositories(
		basePackages = {"by.compit.tereh.service.repository"},
		entityManagerFactoryRef = "productHierarchyEntityManagerFactory",
		transactionManagerRef = "productHierarchyTransactionManager"
)
@SpringBootApplication(scanBasePackages = "by.compit.tereh.service")
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean(name = "jdbcTemplateHierarchy")
	@Autowired
	public JdbcTemplate jdbcTemplateHierarchy(@Qualifier("catalogDatasource") DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "productHierarchyEntityManagerFactory")
	@Autowired
	public LocalContainerEntityManagerFactoryBean productHierarchyEntityManagerFactory(@Qualifier("catalogDatasource") DataSource dataSource){
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource);
		entityManager.setPackagesToScan("by.compit.tereh.service.model");
		entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		entityManager.setJpaProperties(properties);
		return entityManager;
	}


	@Bean(name = "productHierarchyTransactionManager")
	@Autowired
	public PlatformTransactionManager productHierarchyTransactionManager(@Qualifier("productHierarchyEntityManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean){
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
		return jpaTransactionManager;
	}

	@Bean(name = "catalogDatasource", destroyMethod = "")
	public DataSource catalogDataSource() throws NamingException {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("oracle.jdbc.driver.OracleDriver");
		dataSourceBuilder.url("jdbc:oracle:thin:@//10.0.0.238:1521/orclpdb");
		dataSourceBuilder.username("ctl");
		dataSourceBuilder.password("ctl");
		return dataSourceBuilder.build();

		/*Hashtable<String, String> h = new Hashtable<>(7);
		h.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		h.put(Context.PROVIDER_URL, "t3://10.0.0.238:7001");
		h.put(Context.SECURITY_PRINCIPAL,  "weblogic");
		h.put(Context.SECURITY_CREDENTIALS,  "welcome1");

		Context context = new InitialContext(h);
		return (javax.sql.DataSource) context.lookup("jdbc/belinvestbankCTL");*/
	}

	@Bean(name = "nsiDatasource", destroyMethod = "")
	public DataSource nsiDatasource() throws NamingException {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("oracle.jdbc.driver.OracleDriver");
		dataSourceBuilder.url("jdbc:oracle:thin:@//10.0.0.238:1521/orclpdb");
		dataSourceBuilder.username("NSI");
		dataSourceBuilder.password("NSI");
		return dataSourceBuilder.build();
	}

	@Bean(name = "jdbcTemplateNSI")
	public JdbcTemplate jdbcTemplateNSI(@Qualifier("nsiDatasource") DataSource nsiDataSource){
		return new JdbcTemplate(nsiDataSource);
	}

	@Bean(name = "jdbcTemplateCTL")
	public JdbcTemplate jdbcTemplateCTL(@Qualifier("catalogDatasource") DataSource ctlDataSource){
		return new JdbcTemplate(ctlDataSource);
	}

	/*@Bean(name = "catalogDatasourceForWebLogic", destroyMethod = "")
	public DataSource catalogDatasourceForWebLogic() throws NamingException {
		DataSource dataSource;
		//Hashtable<String, String> h = new Hashtable<>(7);
		//h.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		//h.put(Context.PROVIDER_URL, pURL); //For example "t3://127.0.0.1:7001"
		InitialContext context = new InitialContext();
		dataSource = (javax.sql.DataSource)context.lookup("jdbc/HR");
		return dataSource;
	}*/
}
