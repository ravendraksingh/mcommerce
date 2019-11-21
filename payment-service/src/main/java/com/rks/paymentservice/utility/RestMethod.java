package com.rks.paymentservice.utility;

import com.rks.paymentservice.exceptions.BaseException;
import com.rks.paymentservice.exceptions.ExceptionEnum;
import com.rks.paymentservice.exceptions.MicroServiceUnavailableException;
import com.rks.paymentservice.mcommon.restclient.RestClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.rks.paymentservice.constants.Constant.*;
import static com.rks.paymentservice.constants.Constant.FAILED;
import static com.rks.paymentservice.constants.Constant.INTERNAL_SERVER_ERROR;
import static com.rks.paymentservice.constants.ErrorCodeConstants.*;

@Component
public class RestMethod {

  private static final Logger logger = LoggerFactory.getLogger(RestMethod.class);

  private static RestClientUtil restClientUtil;

  // Map to hold Service name and its unavailability code mapping. This code will be used to create
  // alerts on DataDog. For any new client, please add your mapping.
  private static final Map<String, Integer> UNAVAILABLE_ERROR_CODE_MAP = new HashMap<>();

  private static final HashSet<String> RETRY_ENABLED_SERVICES = new HashSet<>(Arrays.asList(ORDER_GET));

  static {
    UNAVAILABLE_ERROR_CODE_MAP.put(ORDER_SERVICE, ORDER_SERVICE_UNAVAILABLE_ERROR_CODE);
  }

  @Autowired
  RestMethod(RestClientUtil restClientUtil) {
    RestMethod.restClientUtil = restClientUtil;
  }

  public static <T, U> U restRequest(final HttpHeaders httpHeaders, final Class<U> responseClass,
                                     final String url, final String serviceName, final HttpMethod httpMethod,
                                     final T requestObject) {
    try {
      return getResponse(httpHeaders, responseClass, url, serviceName, httpMethod, requestObject);
    } catch (BaseException ex) {
      if (ex.getCode() == INTEGRATION_SERVICE_UNAVAILABLE_ERROR_CODE &&
          RETRY_ENABLED_SERVICES.contains(serviceName) && httpMethod.toString()
          .equals(HttpMethod.GET.toString())) {
        logger.debug("Retrying hitting url:{}.", url);
        return getResponse(httpHeaders, responseClass, url, serviceName, httpMethod, requestObject);
      }
      throw ex;
    }
  }

  /*private static <T, U> U getResponse(final HttpHeaders httpHeaders, final Class<U> responseClass,
                                      final String url, final String serviceName, final HttpMethod httpMethod,
                                      final T requestObject) {
    RestTemplate restTemplate = restClientUtil.getRestTemplate(serviceName);
    try {
      HttpEntity httpEntity = new HttpEntity(requestObject, httpHeaders);
      ResponseEntity<U> responseEntity = restTemplate
          .exchange(url, httpMethod, httpEntity, responseClass);
      *//*if(url.contains("v2/transactions/imps/status?txnDateTime=")){
        logger.info("IMPS status response : {}" ,responseEntity.getBody());
      }*//*
      return responseEntity.getBody();
    } catch (HttpStatusCodeException e) {
      logger.error("Response Body: {}. Http Status Code error while calling external service. {}",
          e.getResponseBodyAsString(),CommonUtils.exceptionFormatter(e));
    } catch (ResourceAccessException e) {
      logger.error("Micro Service unavailable error while calling external service. {}",
          CommonUtils.exceptionFormatter(e));
      throw new MicroServiceUnavailableException(FAILED, INTEGRATION_SERVICE_UNAVAILABLE_ERROR_CODE,
          INTERNAL_SERVER_ERROR);
    }
  }*/

  private static <T, U> U getResponse(final HttpHeaders httpHeaders, final Class<U> responseClass,
                                      final String url, final String serviceName, final HttpMethod httpMethod,
                                      final T requestObject) {
    RestTemplate restTemplate = restClientUtil.getRestTemplate(serviceName);
    try {
      HttpEntity httpEntity = new HttpEntity(requestObject, httpHeaders);
      ResponseEntity<U> responseEntity = restTemplate
              .exchange(url, httpMethod, httpEntity, responseClass);
      if(url.contains("v2/transactions/imps/status?txnDateTime=")){
        logger.info("IMPS status response : {}" ,responseEntity.getBody());
      }
      return responseEntity.getBody();
    } catch (HttpStatusCodeException e) {
      logger.error("Response Body: {}. Http Status Code error while calling external service. {}",
              e.getResponseBodyAsString(),CommonUtils.exceptionFormatter(e));
      //hack for handling new oauth api httpStatusCodes
      //if(OAUTH.equalsIgnoreCase(serviceName)){
      //  throw OauthApiExceptionEnum.getMappedException(e.getStatusCode().value());
      //} else {
      //  throw ExceptionEnum.getMappedException(e.getStatusCode().value());
      //}
      throw ExceptionEnum.getMappedException(e.getStatusCode().value());
    } catch (ResourceAccessException e) {
      logger.error("Micro Service unavailable error while calling external service. {}",
              CommonUtils.exceptionFormatter(e));
      //pushUnavailableCodeToDatadog(serviceName);
      throw new MicroServiceUnavailableException(FAILED, INTEGRATION_SERVICE_UNAVAILABLE_ERROR_CODE,
              INTERNAL_SERVER_ERROR);
    }
  }
}

