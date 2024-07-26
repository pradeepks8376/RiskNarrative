package com.risknarrative.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    private String premises;
    private String postal_code;
    private String country;
    private String locality;
    private String address_line_1;
}
