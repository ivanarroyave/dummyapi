package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.models.user.create.UserBody;
import io.dummyapi.models.user.create.UserResponse;
import io.dummyapi.stepdefinitions.ServiceSetup;

import static io.dummyapi.interactions.general.LastResponse.printLastResponse;
import static io.dummyapi.questions.general.ResponseCode.was;
import static io.dummyapi.questions.general.SchemaValidator.problems;
import static io.dummyapi.questions.user.delete.UserQuestion.theInformationOfCreatedUser;
import static io.dummyapi.questions.user.update.PutUserQuestion.theUpdateProcessWith;
import static io.dummyapi.tasks.generictasks.DoPost.doPost;
import static io.dummyapi.tasks.generictasks.DoPut.doPut;
import static io.dummyapi.utils.FileUtilities.readFile;
import static io.dummyapi.utils.Json.generateJson;
import static io.dummyapi.utils.Words.DONE;
import static io.dummyapi.utils.faker.UserFaker.*;
import static io.dummyapi.utils.faker.UserFaker.EMAIL_DOMAIN;
import static io.dummyapi.utils.schema.MySchemaPaths.PUT_USER_SCHEMA;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UpdateUserStepDefinition extends ServiceSetup {
    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_CREATE = "user/create";
    private static final String RESOURCE_UPDATE = "user/%s";
    private UserResponse userResponseFistTime;

    @Given("the admin user are in the update user information section")
    public void theAdminUserAreInTheUpdateUserInformationSection() {
        generalSetup(ACTOR_NAME);
        theActorInTheSpotlight().wasAbleTo(
                doPost()
                        .usingTheResource(RESOURCE_CREATE)
                        .withHeaders(headers)
                        .andBodyRequest(
                                generateJson(
                                        generateUser(SPANISH_CODE_LANGUAGE, COUNTRY_CODE, EMAIL_DOMAIN)
                                )
                        )
        );

        userResponseFistTime = theInformationOfCreatedUser().answeredBy(theActorInTheSpotlight());
    }

    @When("he configure the new information and init the user updating")
    public void heConfigureTheNewInformationAndInitTheUserUpdating() {
        theActorInTheSpotlight().attemptsTo(
                doPut()
                        .usingTheResource(
                                String.format(
                                        RESOURCE_UPDATE,
                                        userResponseFistTime.getId()
                                )
                        )
                        .withHeaders(headers)
                        .andBodyRequest(
                                generateJson(
                                        generateUser(SPANISH_CODE_LANGUAGE, COUNTRY_CODE, EMAIL_DOMAIN)
                                )
                        )
        );
    }

    @Then("he should see the user information according to previous user modification.")
    public void heShouldSeeTheUserInformationAccordingToPreviousUserModification() {
        theActorInTheSpotlight().should(
                seeThat("the status code ", was(), equalTo(SC_OK)),
                seeThat(
                        "the problems with the schema ",
                        problems().inFile(readFile(PUT_USER_SCHEMA.getValue())).relatedWithSchema(),
                        is(false)
                )
                ,
                seeThat("the update user information process ", theUpdateProcessWith(userResponseFistTime), is(DONE))
        );
    }
}
