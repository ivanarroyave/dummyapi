package io.dummyapi.tasks.generictasks;

import io.restassured.specification.RequestSpecification;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import java.util.HashMap;
import java.util.Map;
import static io.dummyapi.interactions.general.LastResponse.printLastResponse;

public class DoDelete implements Task {
    private boolean printLastResponse;
    private String resource;

    private boolean userHeaders = true;
    private Map<String, Object> headers = new HashMap<>();

    public DoDelete usingTheResource(String resource) {
        this.resource = resource;
        return this;
    }

    public DoDelete noUserHeaders() {
        this.userHeaders = false;
        return this;
    }

    public DoDelete withHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public DoDelete alsoPrintTheLastResponse() {
        printLastResponse = true;
        return this;
    }

    public DoDelete() {
        printLastResponse = false;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Check.whether(userHeaders)
                        .andIfSo(
                                Delete.from(resource)
                                .with(
                                        requestSpecification -> requestSpecification.headers(headers).relaxedHTTPSValidation()
                                )
                        )
                        .otherwise(
                                Delete.from(resource)
                                        .with(
                                                RequestSpecification::relaxedHTTPSValidation
                                        )
                        )
                ,
                Check.whether(printLastResponse)
                        .andIfSo(
                                printLastResponse()
                        )
        );
    }

    public static DoDelete doDelete(){
        return new DoDelete();
    }
}
