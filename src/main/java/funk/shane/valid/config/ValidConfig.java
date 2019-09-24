
package funk.shane.valid.config;

import java.time.Duration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ValidConfig {
    private static final int READ_TIMEOUT = 10;
    private static final int CONN_TIMEOUT = 20;
    
	@Bean
	public static RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder
          .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT))
          .setConnectTimeout(Duration.ofSeconds(CONN_TIMEOUT))
          .build();
	}

    @Bean
	public ConversionService conversionService() {
		return new DefaultFormattingConversionService();
    }
	
	@Bean
	public LocalValidatorFactoryBean factoryBean() {
		LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
		factoryBean.setProviderClass(HibernateValidator.class);
		factoryBean.afterPropertiesSet();

		return factoryBean;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder
			.json()
			.featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.build();
	}
}