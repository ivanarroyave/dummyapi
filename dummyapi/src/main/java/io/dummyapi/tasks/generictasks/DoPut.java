package io.dummyapi.tasks.generictasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import java.util.HashMap;
import java.util.Map;

public class DoPut implements Task {
    private String resource;
    private Map<String, Object> headers = new HashMap<>();
    private String bodyRequest;

    public DoPut usingTheResource(String resource) {
        this.resource = resource;
        return this;
    }

    public DoPut withHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public DoPut andBodyRequest(String bodyRequest) {
        this.bodyRequest = bodyRequest;
        return this;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to(resource).with(
                        requestSpecification -> requestSpecification.relaxedHTTPSValidation()
                                .headers(headers)
                                .body(bodyRequest)

                )
        );
    }

    public static DoPut doPut(){
        return new DoPut();
    }
}
