package com.rks.paymentservice.mcommon.restclient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

@Component
@PropertySource(value = "classpath:http-client-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class RestClientUtil {

  @Value("${connection.request.timeout:1000}")
  private Integer connectionRequestTimeout;

  @Value("${connection.timeout:5000}")
  private Integer connectionTimeout;

  @Value("${read.timeout:50000}")
  private Integer readTimeout;

  @Value("${max.total.connection:50}")
  private Integer maxTotalConnection;

  @Value(("${max.per.channel:50}"))
  private Integer maxPerChannel;

  @Autowired
  private Environment httpClientProperties;

  private static ConcurrentHashMap<String, RestTemplate> cache = new ConcurrentHashMap<>();

  private static List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

  @PostConstruct
  public void init() {
    messageConverters.add(new FormHttpMessageConverter());
    //MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    //converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    //messageConverters.add(converter);
  }

  private Integer getConnectionTimeout(String serviceName) {
    String value = httpClientProperties.getProperty(serviceName + ".connection.timeout");
    if (StringUtils.isNotEmpty(value)) {
      try {
        return Integer.parseInt(value);
      } catch (Exception ex) {
        //ignore
      }
    }
    return connectionTimeout;
  }

  private Integer getConnectionRequestTimeout(String serviceName) {
    String value = httpClientProperties.getProperty(serviceName + ".connection.request.timeout");
    if (StringUtils.isNotEmpty(value)) {
      try {
        return Integer.parseInt(value);
      } catch (Exception ex) {
        //ignore
      }
    }
    return connectionRequestTimeout;
  }

  private Integer getReadTimeout(String serviceName) {
    String value = httpClientProperties.getProperty(serviceName + ".read.timeout");
    if (StringUtils.isNotEmpty(value)) {
      try {
        return Integer.parseInt(value);
      } catch (Exception ex) {
        //ignore
      }
    }
    return readTimeout;
  }

  private Integer getMaxTotalConnection(String serviceName) {
    String value = httpClientProperties.getProperty(serviceName + ".max.total.connection");
    if (StringUtils.isNotEmpty(value)) {
      try {
        return Integer.parseInt(value);
      } catch (Exception ex) {
        //ignore
      }
    }
    return maxTotalConnection;
  }

  private Integer getMaxPerChannel(String serviceName) {
    String value = httpClientProperties.getProperty(serviceName + ".max.per.channel");
    if (StringUtils.isNotEmpty(value)) {
      try {
        return Integer.parseInt(value);
      } catch (Exception ex) {
        //ignore
      }
    }
    return maxPerChannel;
  }

  private ClientHttpRequestFactory createRequestFactory(String serviceName) {

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(getMaxTotalConnection(serviceName));
    connectionManager.setDefaultMaxPerRoute(getMaxPerChannel(serviceName));

    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(getConnectionTimeout(serviceName))
        .setConnectionRequestTimeout(getConnectionRequestTimeout(serviceName))
        .setSocketTimeout(getReadTimeout(serviceName)).build();

    CloseableHttpClient httpClient = HttpClientBuilder.create()
        .setConnectionManager(connectionManager)
        .setDefaultRequestConfig(config).build();
    return new HttpComponentsClientHttpRequestFactory(httpClient);
  }

  private RestTemplate createRestTemplate(ClientHttpRequestFactory factory) {
    RestTemplate restTemplate = new RestTemplate(factory);
    restTemplate.getMessageConverters().addAll(messageConverters);
    for(HttpMessageConverter messageConverter : restTemplate.getMessageConverters()) {
      if(!(messageConverter instanceof AbstractJackson2HttpMessageConverter))
        continue;

      ((AbstractJackson2HttpMessageConverter)messageConverter).getObjectMapper()
          .configure(READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }
    return restTemplate;
  }

  public RestTemplate getRestTemplate(String serviceName) {
    if (cache.get(serviceName) == null) {
      ClientHttpRequestFactory factory = createRequestFactory(serviceName);
      RestTemplate template = createRestTemplate(factory);
      cache.putIfAbsent(serviceName, template);
    }
    return cache.get(serviceName);
  }

}
