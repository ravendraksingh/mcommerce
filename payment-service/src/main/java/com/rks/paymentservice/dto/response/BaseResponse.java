package com.rks.paymentservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.rks.paymentservice.constants.Constant.RESPONSE_CODE;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BaseResponse {

  private String status;

  private String message;

  @JsonProperty(RESPONSE_CODE)
  private Integer code;
  
  public BaseResponse(String status, String message) {
    this.message = message;
    this.status = status;
  }
}
