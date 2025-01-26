package com.crewmeister.cmcodingchallenge.currency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static com.crewmeister.cmcodingchallenge.currency.constants.Constants.BASE_URL;
import static com.crewmeister.cmcodingchallenge.currency.constants.Constants.max_size;

@Configuration
public class WebClientConfig {
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(max_size))
            .build();

    @Bean
    public WebClient getWebClient(){
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(strategies)
                .build();
}

}
