package io.dummyapi.questions.user.create;

import io.dummyapi.models.user.create.UserBody;
import io.dummyapi.models.user.create.UserResponse;
import io.dummyapi.utils.Words;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import static io.dummyapi.utils.Words.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class PostUserQuestion implements Question<Words> {

    private final UserBody userBody;

    public PostUserQuestion(UserBody userBody) {
        this.userBody = userBody;
    }

    @Override
    public Words answeredBy(Actor actor) {
        if (informationComparisonAndValidation(lastResponse().as(UserResponse.class), userBody))
            return REGISTERED;
        else
            return NOT_REGISTERED;
    }

    public static PostUserQuestion theCreated(UserBody user){
        return new PostUserQuestion(user);
    }

    private boolean informationComparisonAndValidation(UserResponse userResponse , UserBody userBody){
        return (!userResponse.getId().isEmpty() &&
                userResponse.getTitle().equals(userBody.getTitle()) &&
                userResponse.getFirstName().equals(userBody.getFirstName()) &&
                userResponse.getLastName().equals(userBody.getLastName()) &&
                userResponse.getGender().equals(userBody.getGender()) &&
                userResponse.getPhone().equals(userBody.getPhone()) &&
                !userResponse.getRegisterDate().isEmpty() &&
                !userResponse.getUpdatedDate().isEmpty()
        );
    }
}
