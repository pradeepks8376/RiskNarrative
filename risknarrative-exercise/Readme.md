# Getting Started

## How to

#### Requisite
- Java 17
- Maven 3.9.6

#### Maven command
- mvn clean install

##### Demo
- Post request to http://localhost:8080/search

**Expected Request**

* The name and registration/company number are passed in via body
* The API key is passed in via header `x-api-key`
* If both fields are provided `companyNumber` is used

<pre>
{
    "companyName" : "BBC LIMITED",
    "companyNumber" : "06500244"
}
</pre>

- Pass x-api-key in the header
- Pass Content-Type in the header

**Expected Response**
<pre>

{
    "total_results": 1,
    "items": [
        {
            "company_number": 6500244,
            "company_type": "ltd",
            "title": "BBC LIMITED",
            "company_status": "active",
            "date_of_creation": "2008-02-11",
            "address": {
                "premises": "Boswell Cottage Main Street",
                "postal_code": "DN22 0AD",
                "country": "England",
                "locality": "Retford",
                "address_line_1": "North Leverton"
            },
            "officers": [
                {
                    "address": {
                        "premises": "5",
                        "postal_code": "SW20 0DP",
                        "country": "England",
                        "locality": "London",
                        "address_line_1": "Cranford Close"
                    },
                    "name": "BOXALL, Sarah Victoria",
                    "appointed_on": "2008-02-11",
                    "officer_role": "secretary"
                }
            ]
        }
    ]
}
</pre>

#### About Source Code
- RisknarrativeExerciseApplication.java is main springboot application
- CompanyController.java is RestController
- TruProxyService.java service class written to consume TruProxyAPI
- CompanyService.java service class used to Save object into Database
- CompanyRepository.java to save Companies
- OfficerRepository.java to save Officers
- AddressRepository.java to save Addresses of both company and officers
- Respective model classes

#### Unit and Integration tests 
Some Integration tests are written to cover how application is integrated with external libraries
- mock MVC is used to test Service classes CompanyServiceTest.java , TruProxyServiceTest.java
- Wire Mock is used to test how WebClient interacts with external API.

#### H2 Database used and taken screenshot of storing Companies, Officers and it's address.

![Wireframe](https://github.com/pradeepks8376/RiskNarrative/tree/main/risknarrative-exercise/H2-Database_screenshot.png)




