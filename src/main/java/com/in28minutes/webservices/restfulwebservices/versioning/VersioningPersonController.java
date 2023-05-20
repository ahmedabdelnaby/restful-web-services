package com.in28minutes.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    /**
     *
     * ************************ BY URL ************************
     *
     */
    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson() {

        return new PersonV1("Ahmed AbdElnaby");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson() {

        return new PersonV2(new Name("Ahmed", "AbdElnaby"));
    }

    /**
     *
     * ************************ BY REQUEST PARAMETER ************************
     *
     */
    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonRequestParameter() {

        return new PersonV1("Ahmed AbdElnaby");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestParameter() {

        return new PersonV2(new Name("Ahmed", "AbdElnaby"));
    }

    /**
     *
     * ************************ BY HEADER ATTRIBUTE ************************
     *
     */
    @GetMapping(path = "/person", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonHeaders() {

        return new PersonV1("Ahmed AbdElnaby");
    }

    @GetMapping(path = "/person", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonHeaders() {

        return new PersonV2(new Name("Ahmed", "AbdElnaby"));
    }

    /**
     *
     * ************************ BY ACCEPT HEADER ************************
     *
     */
    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeader() {

        return new PersonV1("Ahmed AbdElnaby");
    }

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonAcceptHeader() {

        return new PersonV2(new Name("Ahmed", "AbdElnaby"));
    }
}
