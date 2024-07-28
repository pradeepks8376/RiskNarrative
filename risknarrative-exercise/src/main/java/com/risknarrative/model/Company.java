package com.risknarrative.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {
    private Long company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private Address address;
    private List<Officer> officers;
}
