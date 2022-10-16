package io.dummyapi.questions.errortype;

import io.dummyapi.utils.Words;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import static io.dummyapi.utils.Words.NOT_ON_SCREEN;
import static io.dummyapi.utils.Words.ON_SCREEN;

public class BodyNoValid implements Question<Words> {

    private io.dummyapi.models.errortype.bodynovalid.BodyNoValid bodyNoValid;

    public BodyNoValid(io.dummyapi.models.errortype.bodynovalid.BodyNoValid bodyNoValid) {
        this.bodyNoValid = bodyNoValid;
    }

    @Override
    public Words answeredBy(Actor actor) {
        io.dummyapi.models.errortype.bodynovalid.BodyNoValid bodyNoValidFromSystemMessage =
                LastResponse.received().answeredBy(actor).as(
                        io.dummyapi.models.errortype.bodynovalid.BodyNoValid.class
                );

        if(
                bodyNoValidFromSystemMessage.getError().equals(bodyNoValid.getError()) &&
                        bodyNoValidFromSystemMessage.getData().getEmail().equals(bodyNoValid.getData().getEmail()) &&
                        bodyNoValidFromSystemMessage.getData().getFirstName().equals(bodyNoValid.getData().getFirstName()) &&
                        bodyNoValidFromSystemMessage.getData().getGender().equals(bodyNoValid.getData().getGender()) &&
                        bodyNoValidFromSystemMessage.getData().getLastName().equals(bodyNoValid.getData().getLastName()) &&
                        bodyNoValidFromSystemMessage.getData().getPhone().equals(bodyNoValid.getData().getPhone()) &&
                        bodyNoValidFromSystemMessage.getData().getTitle().equals(bodyNoValid.getData().getTitle())
        )
            return ON_SCREEN;
        else
            return NOT_ON_SCREEN;
    }

    public static BodyNoValid theErrorTypeWithAllDescriptionLike(io.dummyapi.models.errortype.bodynovalid.BodyNoValid bodyNoValid){
        return new BodyNoValid(bodyNoValid);
    }
}
