package io.dummyapi.tasks.generictasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import java.util.HashMap;
import java.util.Map;

public class DoGet implements Task {
    private String resource;
    private Map<String, Object> headers = new HashMap<>();

    public DoGet usingResources(String resource) {
        this.resource = resource;
        return this;
    }

    public DoGet withHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;

    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(resource)
                        .with(
                                requestSpecification -> requestSpecification.headers(headers).relaxedHTTPSValidation()
                        )
        );

    }

    public static DoGet doGet(){
        return new DoGet();
    }
}
