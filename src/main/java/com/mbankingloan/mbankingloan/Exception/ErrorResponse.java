package com.mbankingloan.mbankingloan.Exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse<T> {
    private T error;

}
