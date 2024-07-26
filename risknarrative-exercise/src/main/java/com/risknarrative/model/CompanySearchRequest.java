package com.risknarrative.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanySearchRequest {
    private String companyName;
    private String companyNumber;
    private boolean activeOnly;
}
