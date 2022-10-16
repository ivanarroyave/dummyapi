package io.dummyapi.questions.user.update;

import io.dummyapi.models.user.create.UserResponse;
import io.dummyapi.utils.Words;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import static io.dummyapi.utils.Words.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class PutUserQuestion implements Question<Words> {

    private final UserResponse userResponseBefore;

    public PutUserQuestion(UserResponse userResponseBefore) {
        this.userResponseBefore = userResponseBefore;
    }

    @Override
    public Words answeredBy(Actor actor) {
        if (informationComparisonAndValidation(userResponseBefore, lastResponse().as(UserResponse.class)))
            return DONE;
        else
            return NO_DONE;
    }

    public static PutUserQuestion theUpdateProcessWith(UserResponse userResponseBefore){
        return new PutUserQuestion(userResponseBefore);
    }

    private boolean informationComparisonAndValidation(UserResponse userResponseBefore , UserResponse userResponseAfter){
        return (userResponseBefore.getId().equals(userResponseAfter.getId()) &&
                userResponseBefore.getTitle().equals(userResponseAfter.getTitle()) &&
                !userResponseBefore.getFirstName().equals(userResponseAfter.getFirstName()) &&
                !userResponseBefore.getLastName().equals(userResponseAfter.getLastName()) &&
                userResponseBefore.getGender().equals(userResponseAfter.getGender()) &&
                userResponseBefore.getEmail().equals(userResponseAfter.getEmail()) &&
                !userResponseBefore.getPhone().equals(userResponseAfter.getPhone()) &&
                userResponseBefore.getRegisterDate().equals(userResponseAfter.getRegisterDate()) &&
                !userResponseBefore.getUpdatedDate().equals(userResponseAfter.getUpdatedDate())
        );
    }
}
