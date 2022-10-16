package io.dummyapi.questions.user.delete;

import io.dummyapi.models.user.delete.User;
import io.dummyapi.utils.Words;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import static io.dummyapi.utils.Words.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.screenplay.rest.questions.LastResponse.received;

public class DeleteUserQuestion implements Question<Words> {

    private final User user;

    public DeleteUserQuestion(User user) {
        this.user = user;
    }

    @Override
    public Words answeredBy(Actor actor) {
        if (this.user.getId().equals(received().answeredBy(actor).as(User.class).getId()))
            return DONE;
        else
            return NO_DONE;
    }

    public static DeleteUserQuestion theDeleteProcessWith(User user){
        return new DeleteUserQuestion(user);
    }

}