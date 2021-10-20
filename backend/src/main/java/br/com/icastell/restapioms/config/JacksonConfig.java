package br.com.icastell.restapioms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.icastell.restapioms.domain.payment.PaymentBankSlip;
import br.com.icastell.restapioms.domain.payment.PaymentCart;

@Configuration
public class JacksonConfig {
	/**
	 * this class of configuration register the child class (subtypes - concrect class) of a abstract class
	 * all class of configuration is configured in the start of program execution
	 * https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	 */

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PaymentCart.class);
				objectMapper.registerSubtypes(PaymentBankSlip.class);
				super.configure(objectMapper);
			};
		};
		return builder;
	}
}
