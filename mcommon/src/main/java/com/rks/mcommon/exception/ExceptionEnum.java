package com.rks.mcommon.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import static com.rks.mcommon.constants.CommonConstants.FAILED;
import static com.rks.mcommon.constants.CommonConstants.INTERNAL_SERVER_ERROR;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.*;

public enum ExceptionEnum {

  BAD_REQUEST_EXCEPTION(400) {
    @Override
    public BaseException throwException() {
        return new ExternalServiceErrorException(FAILED, BAD_REQUEST_ERROR_CODE, INTERNAL_SERVER_ERROR);
    }
  },
  UNAUTHORIZED_EXCEPTION(401) {
    @Override
    public BaseException throwException() {
        return new ExternalServiceErrorException(FAILED, UNAUTHORIZED_ERROR_CODE, INTERNAL_SERVER_ERROR);
    }
  },
  NOT_FOUND(404) {
    @Override
    public BaseException throwException() {
        return new ExternalServiceErrorException(FAILED, NOT_FOUND_ERROR_CODE, INTERNAL_SERVER_ERROR);
    }
  },
  MICRO_SERVICE_ERROR_EXCEPTION(500) {
    @Override
    public BaseException throwException() {
      return new MicroServiceErrorException(FAILED, MICRO_SERVICE_ERROR_CODE,
          INTERNAL_SERVER_ERROR);
    }
  },
  MICRO_SERVICE_UNAVAILABLE_EXCEPTION(503) {
    @Override
    public BaseException throwException() {
      return new MicroServiceUnavailableException(FAILED,
          INTEGRATION_SERVICE_UNAVAILABLE_ERROR_CODE,
          INTERNAL_SERVER_ERROR);
    }
  };

  private Integer code;

  ExceptionEnum(Integer code) {
    this.code = code;
  }

  public static BaseException getMappedException(int code) {
    for (ExceptionEnum b : ExceptionEnum.values()) {
      if (b.getCode() == code) {
        return b.throwException();
      }
    }
    return new BaseException(FAILED, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR);
  }

  public int getCode() {
    return this.code;
  }

  public abstract BaseException throwException();
}