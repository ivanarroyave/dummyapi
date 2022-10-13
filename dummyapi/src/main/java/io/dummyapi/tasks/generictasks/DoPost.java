package io.dummyapi.tasks.generictasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import java.util.HashMap;
import java.util.Map;

public class DoPost implements Task {
    private String resource;
    private Map<String, Object> headers = new HashMap<>();
    private String bodyRequest;

    public DoPost usingTheResource(String resource) {
        this.resource = resource;
        return this;
    }

    public DoPost withHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public DoPost andBodyRequest(String bodyRequest) {
        this.bodyRequest = bodyRequest;
        return this;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(resource).with(
                        requestSpecification -> requestSpecification.relaxedHTTPSValidation()
                                .headers(headers)
                                .body(bodyRequest)

                )
        );
    }

    public static DoPost doPost(){
        return new DoPost();
    }
}
