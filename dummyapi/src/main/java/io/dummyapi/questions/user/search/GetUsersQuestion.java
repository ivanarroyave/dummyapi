package io.dummyapi.questions.user.search;

import io.dummyapi.models.user.search.searchusers.Users;
import io.dummyapi.utils.Words;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class GetUsersQuestion implements Question<Words> {

    @Override
    public Words answeredBy(Actor actor) {

        Users users = lastResponse().as(Users.class);

        if(users.getLimit() == users.getData().size())
            return Words.EQUAL;
        else
            return Words.NO_EQUAL;
    }

    public static GetUsersQuestion theLimitOfUsersPerPageComparedWithUsersQuantityIs(){
        return new GetUsersQuestion();
    }
}
