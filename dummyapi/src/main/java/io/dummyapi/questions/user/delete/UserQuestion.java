package io.dummyapi.questions.user.delete;

import io.dummyapi.models.user.create.UserResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class UserQuestion implements Question<UserResponse> {
    @Override
    public UserResponse answeredBy(Actor actor) {
        return lastResponse().as(UserResponse.class);
    }

    public static UserQuestion theInformationOfCreatedUser(){
        return new UserQuestion();
    }
}
