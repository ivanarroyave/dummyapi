package io.dummyapi.questions.user.search;

import io.dummyapi.models.user.create.UserResponse;
import io.dummyapi.models.user.search.searchuser.User;
import io.dummyapi.utils.Words;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import static io.dummyapi.utils.Words.NOT_ON_SCREEN;
import static io.dummyapi.utils.Words.ON_SCREEN;
import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class GetUserQuestion implements Question<Words> {

    private final UserResponse user;

    public GetUserQuestion(UserResponse user) {
        this.user = user;
    }

    @Override
    public Words answeredBy(Actor actor) {
        User userFromResponse = lastResponse().as(User.class);

        if (
                user.getId().equals(userFromResponse.getId()) &&
                user.getTitle().equals(userFromResponse.getTitle()) &&
                user.getFirstName().equals(userFromResponse.getFirstName()) &&
                user.getLastName().equals(userFromResponse.getLastName()) &&
                user.getGender().equals(userFromResponse.getGender()) &&
                user.getEmail().equals(userFromResponse.getEmail()) &&
                user.getPhone().equals(userFromResponse.getPhone()) &&
                user.getRegisterDate().equals(userFromResponse.getRegisterDate()) &&
                user.getUpdatedDate().equals(userFromResponse.getUpdatedDate())
        )
            return ON_SCREEN;
        else
            return NOT_ON_SCREEN;
    }

    public static GetUserQuestion theInformationOf(UserResponse user){
        return new GetUserQuestion(user);
    }

}
