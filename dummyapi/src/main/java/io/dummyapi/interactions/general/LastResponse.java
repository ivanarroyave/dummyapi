package io.dummyapi.interactions.general;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

public class LastResponse implements Interaction {
    @Override
    public <T extends Actor> void performAs(T actor) {
        net.serenitybdd.screenplay.rest.questions.LastResponse.received().answeredBy(actor).body().prettyPrint();
    }

    public static LastResponse printLastResponse(){
        return new LastResponse();
    }
}
