package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.models.user.create.UserBody;
import io.dummyapi.stepdefinitions.ServiceSetup;
import static io.dummyapi.questions.general.ResponseCode.was;
import static io.dummyapi.questions.general.SchemaValidator.problems;
import static io.dummyapi.questions.user.create.PostUserQuestion.theCreated;
import static io.dummyapi.tasks.generictasks.DoPost.doPost;
import static io.dummyapi.utils.FileUtilities.readFile;
import static io.dummyapi.utils.Json.generateJson;
import static io.dummyapi.utils.faker.UserFaker.*;
import static io.dummyapi.utils.schema.MySchemaPaths.CREATE_USER_SCHEMA;
import static io.dummyapi.utils.Words.REGISTERED;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateUserStepDefinition extends ServiceSetup {
    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_CREATE = "user/create";
    private UserBody user;

    @Given("the admin user are in the creation user section")
    public void theAdminUserAreInTheCreationUserSection() {
        generalSetup(ACTOR_NAME);
        user = generateUser(SPANISH_CODE_LANGUAGE, COUNTRY_CODE, EMAIL_DOMAIN);
    }

    @When("he configure and init the user creation")
    public void heConfigureAndInitTheUserCreation() {
        theActorInTheSpotlight().attemptsTo(
                doPost()
                        .usingTheResource(RESOURCE_CREATE)
                        .withHeaders(headers)
                        .andBodyRequest(generateJson(user))
        );
    }

    @Then("he should see in the system a user according to previous user creation.")
    public void heShouldSeeTinTheSystemAUserAccordingToPreviousUserCreation() {
        theActorInTheSpotlight().should(
                seeThat("the status code ", was(), equalTo(SC_OK)),
                seeThat(
                        "the problems with the schema ",
                        problems().inFile(readFile(CREATE_USER_SCHEMA.getValue())).relatedWithSchema(),
                        is(false)
                ),
                seeThat("the created user information ", theCreated(user), is(REGISTERED))
        );
    }
}
