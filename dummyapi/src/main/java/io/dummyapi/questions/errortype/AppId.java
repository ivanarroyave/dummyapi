package io.dummyapi.questions.errortype;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class AppId implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).as(io.dummyapi.models.errortype.appid.AppId.class).getError();
    }

    public static AppId theErrorType(){
        return new AppId();
    }
}
