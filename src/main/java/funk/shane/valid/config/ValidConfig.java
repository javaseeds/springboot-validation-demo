/*
 *   Copyright (c) 2019 Shane Funk - Javaseeds Consulting
 *   All rights reserved.

 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */


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