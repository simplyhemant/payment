package com.simply.payment.handler;

import java.util.Map;

public record ErrorResponse(
    Map<String, String> errors
) {

}
