package com.in28minutes.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
//@JsonIgnoreProperties({"field1", "field3"})
@JsonFilter("MyCustomDynamicFiltering")
public class SomeBean {

    /**
     * - there are 2 types of Jackson filtering:
     *      1. static filtering using @JsonIgnore and @JsonIgnoreProperties,
     *              and it will apply across all the APIs.
     *      2. dynamic filtering using @JsonFilter("theFilterName"), and it defined
     *              in the specific API based on our needs, then call it in the bean here
     *              using @JsonFilter("MyCustomDynamicFiltering").
     */

    private String field1;
    private String field2;
    @JsonIgnore // the preferred way for static filtering
    private String field3;
}
