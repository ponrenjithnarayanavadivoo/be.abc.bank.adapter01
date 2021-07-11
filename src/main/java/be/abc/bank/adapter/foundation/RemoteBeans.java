package be.abc.bank.adapter.foundation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@Profile("!test")
public class RemoteBeans {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());

	}
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory theClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		theClientHttpRequestFactory.setConnectTimeout(30000);
		theClientHttpRequestFactory.setReadTimeout(10000);
		theClientHttpRequestFactory.setBufferRequestBody(false);
		return theClientHttpRequestFactory;
	}

}
