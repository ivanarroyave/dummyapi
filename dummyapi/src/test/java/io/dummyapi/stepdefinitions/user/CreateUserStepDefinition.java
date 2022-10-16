package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.models.errortype.bodynovalid.BodyNoValid;
import io.dummyapi.models.errortype.bodynovalid.Data;
import io.dummyapi.models.user.create.UserBody;
import io.dummyapi.stepdefinitions.ServiceSetup;
import static io.dummyapi.questions.errortype.BodyNoValid.theErrorTypeWithAllDescriptionLike;
import static io.dummyapi.questions.general.ResponseCode.was;
import static io.dummyapi.questions.general.SchemaValidator.problems;
import static io.dummyapi.questions.user.create.PostUserQuestion.theCreated;
import static io.dummyapi.tasks.generictasks.DoPost.doPost;
import static io.dummyapi.utils.FileUtilities.readFile;
import static io.dummyapi.utils.Json.generateJson;
import static io.dummyapi.utils.Words.ON_SCREEN;
import static io.dummyapi.utils.faker.UserFaker.*;
import static io.dummyapi.utils.schema.MySchemaPaths.BODY_NOT_VALID;
import static io.dummyapi.utils.schema.MySchemaPaths.CREATE_USER_SCHEMA;
import static io.dummyapi.utils.Words.REGISTERED;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateUserStepDefinition extends ServiceSetup {
    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_CREATE = "user/create";
    private UserBody user;

    //  Background:
    @Given("the admin user are in the creation user section")
    public void theAdminUserAreInTheCreationUserSection() {
        generalSetup(ACTOR_NAME);
        user = generateUser(SPANISH_CODE_LANGUAGE, COUNTRY_CODE, EMAIL_DOMAIN);
    }

    //  Scenario: Create a normal user.
    @When("he configure and init the user creation")
    public void heConfigureAndInitTheUserCreation() {
        post(generateJson(user));
    }

    @Then("he should see in the system a user according to previous user creation.")
    public void heShouldSeeTinTheSystemAUserAccordingToPreviousUserCreation() {
        statusCodeValidation(SC_OK);
        schemaValidation(CREATE_USER_SCHEMA.getValue());

        theActorInTheSpotlight().should(
                seeThat(
                        "the created user information ",
                        theCreated(user),
                        is(REGISTERED))
        );
    }

    //  Scenario: Try to create a user without data.
    @When("he configure and init the user creation without data")
    public void heConfigureAndInitTheUserCreationWithoutData() {
        post(generateJson(generateUserWithoutData()));
    }

    @Then("he should see a system error message that describe a body no valid format for all fields")
    public void heShouldSeeASystemErrorMessageThatDescribeABodyNoValidFormatForAllFields() {
        statusCodeValidation(SC_BAD_REQUEST);
        schemaValidation(BODY_NOT_VALID.getValue());

        theActorInTheSpotlight().should(
                seeThat(
                        "the error type with all description field messages ",
                        theErrorTypeWithAllDescriptionLike(bodyNoValid()),
                        is(ON_SCREEN)
                )
        );
    }

    private BodyNoValid bodyNoValid(){
        Data data = new Data();
        data.setEmail("Path `email` is required.");
        data.setFirstName("Path `firstName` is required.");
        data.setGender("`` is not a valid enum value for path `gender`.");
        data.setLastName("Path `lastName` is required.");
        data.setPhone("Path `phone` (``) is shorter than the minimum allowed length (5).");
        data.setTitle("`` is not a valid enum value for path `title`.");

        BodyNoValid noValid = new BodyNoValid();
        noValid.setError("BODY_NOT_VALID");
        noValid.setData(data);

        return noValid;
    }

    //Common methods.
    private void post(String body){
        theActorInTheSpotlight().attemptsTo(
                doPost()
                        .usingTheResource(RESOURCE_CREATE)
                        .withHeaders(headers)
                        .andBodyRequest(body)
                        .alsoPrintTheLastResponse()
        );
    }

    private void statusCodeValidation(int httpStatusCode){
        theActorInTheSpotlight().should(
                seeThat("the status code ", was(), equalTo(httpStatusCode))
        );
    }

    private void schemaValidation(String schemaPath){
        theActorInTheSpotlight().should(
                seeThat(
                        "the problems with the schema ",
                        problems().inFile(readFile(schemaPath)).relatedWithSchema(),
                        is(false)
                )
        );
    }
}
