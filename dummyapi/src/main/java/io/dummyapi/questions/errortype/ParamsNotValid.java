package io.dummyapi.questions.errortype;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class ParamsNotValid implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).as(io.dummyapi.models.errortype.paramsnotvalid.ParamsNotValid.class).getError();
    }

    public static ParamsNotValid theErrorType(){
        return new ParamsNotValid();
    }
}
