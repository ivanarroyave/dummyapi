package io.dummyapi.questions.user;

import io.dummyapi.models.user.searchusers.Users;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class GetUsersQuestion implements Question<Users> {

    @Override
    public Users answeredBy(Actor actor) {
        return lastResponse().as(Users.class);
    }

    public static GetUsersQuestion getUsersQuestion(){
        return new GetUsersQuestion();
    }
}
