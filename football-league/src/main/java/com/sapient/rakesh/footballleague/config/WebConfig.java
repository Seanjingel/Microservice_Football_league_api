package com.sapient.rakesh.footballleague.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

  @Value("${rest.client.read.timeout}")
  private int readTimeout;
  @Value("${rest.client.connect.timeout}")
  private int connectTimeout;


  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

//  private ClientHttpRequestFactory getClientHttpRequestFactory() {
//    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
//        new HttpComponentsClientHttpRequestFactory();
//    clientHttpRequestFactory.setReadTimeout(readTimeout);
//    clientHttpRequestFactory.setConnectTimeout(connectTimeout);
//    return clientHttpRequestFactory;
//  }
}
