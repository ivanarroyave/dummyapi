package io.dummyapi.questions.errortype;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class PathNotFound implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor).as(io.dummyapi.models.errortype.pathnotfound.PathNotFound.class).getError();
    }

    public static PathNotFound theErrorType(){
        return new PathNotFound();
    }
}
