package com.risknarrative.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Company {
    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private Address address;
    private List<Officer> officers;
}
