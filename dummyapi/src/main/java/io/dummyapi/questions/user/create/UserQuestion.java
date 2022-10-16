package io.dummyapi.questions.user.create;

import io.dummyapi.models.user.create.UserResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import static net.serenitybdd.screenplay.rest.questions.LastResponse.received;

public class UserQuestion implements Question<UserResponse> {
    @Override
    public UserResponse answeredBy(Actor actor) {
        return received().answeredBy(actor).as(UserResponse.class);
    }

    public static UserQuestion theInformationOfCreatedUser(){
        return new UserQuestion();
    }
}
