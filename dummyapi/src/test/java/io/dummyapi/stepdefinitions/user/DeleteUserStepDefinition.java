package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.models.user.delete.User;
import io.dummyapi.questions.errortype.AppId;
import io.dummyapi.questions.errortype.ParamsNotValid;
import io.dummyapi.questions.errortype.PathNotFound;
import io.dummyapi.questions.errortype.ResourceNotFound;
import io.dummyapi.stepdefinitions.ServiceSetup;

import static io.dummyapi.questions.general.ResponseCode.was;
import static io.dummyapi.questions.general.SchemaValidator.problems;
import static io.dummyapi.questions.user.delete.DeleteUserQuestion.theDeleteProcessWith;
import static io.dummyapi.questions.user.delete.UserQuestion.theInformationOfCreatedUser;
import static io.dummyapi.tasks.generictasks.DoDelete.doDelete;
import static io.dummyapi.tasks.generictasks.DoPost.doPost;
import static io.dummyapi.utils.FileUtilities.readFile;
import static io.dummyapi.utils.Json.generateJson;
import static io.dummyapi.utils.faker.UserFaker.*;
import static io.dummyapi.utils.Words.DONE;
import static io.dummyapi.utils.schema.MySchemaPaths.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DeleteUserStepDefinition extends ServiceSetup {
    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_CREATE = "user/create";
    private static final String RESOURCE_DELETE = "user/%s";
    private static final String RESOURCE_DELETE_WRONG_FORMAT = "user/WrongFormatLuloUserId";
    private static final String INEXISTENT_APP_ID = "inexistentLuloAppId";
    private static final String RESOURCE_PATH_NOT_FOUND = "user/";
    private final User user = new User();

    //  Background:
    @Given("the admin user are in the remove users section")
    public void theAdminUserAreInTheRemoveUsersSection() {
        generalSetup(ACTOR_NAME);
    }

    //  Scenario: Delete an user.
    @When("he select an user to delete")
    public void heSelectAnUserToDelete() {
        //Is necessary to create a user before delete her.
        createUser();

        //Delete process for created user.
        deleteUserWithHeaders(String.format(RESOURCE_DELETE, user.getId()));
    }

    @Then("he should see that the selected user is really removed from the system.")
    public void heShouldSeeThatTheSelectedUserIsReallyRemovedFromTheSystem() {
        statusCodeValidation(SC_OK);
        schemaValidation(DELETE_USER_SCHEMA.getValue());

        theActorInTheSpotlight().should(
                seeThat(
                        "the delete user information process ",
                        theDeleteProcessWith(user),
                        is(DONE))
        );
    }

    //  Scenario: Delete a user - App-id not exists in header configuration.
    @When("the manager user proceed to delete the user")
    public void theManagerUserProceedToDeleteTheUser() {
        headers.replace(APP_ID, INEXISTENT_APP_ID);
        deleteUserWithHeaders(RESOURCE_DELETE_WRONG_FORMAT);
    }

    @Then("he should see an error type named {string}")
    public void heShouldSeeAnErrorTypeNamed(String errorTypeName) {
        statusCodeValidation(SC_FORBIDDEN);
        schemaValidation(APP_ID_NO_EXIST_SCHEMA.getValue());
        appIdErrorTypeName(errorTypeName);
    }

    //  Scenario: Delete a user - App-id missing in header configuration.
    @When("the manager user proceed to delete an user")
    public void theManagerUserProceedToDeleteAnUser() {
        theActorInTheSpotlight().attemptsTo(
                doDelete()
                        .usingTheResource(RESOURCE_DELETE_WRONG_FORMAT)
                        .noUserHeaders()
                        .alsoPrintTheLastResponse()
        );
    }

    @Then("he should see an error type named like {string}")
    public void heShouldSeeAnErrorTypeNamedLike(String errorTypeName) {
        statusCodeValidation(SC_FORBIDDEN);
        schemaValidation(APP_ID_MISSING_SCHEMA.getValue());
        appIdErrorTypeName(errorTypeName);
    }

    //  Scenario: Delete a user with an invalid identification.
    @When("the manager user proceed to delete an user using an invalid identification")
    public void theManagerUserProceedToDeleteAnUserUsingAnInvalidIdentification() {
        deleteUserWithHeaders(RESOURCE_DELETE_WRONG_FORMAT);
    }

    @Then("he should see an error reference to {string}")
    public void heShouldSeeAnErrorReferenceTo(String errorTypeName) {
        statusCodeValidation(SC_BAD_REQUEST);
        schemaValidation(PARAMS_NO_VALID_SCHEMA.getValue());

        theActorInTheSpotlight().should(
                seeThat(
                        "the error type name ",
                        ParamsNotValid.theErrorType(),
                        is(errorTypeName))
        );
    }

    //  Scenario: Delete a user with an identification deleted previously.
    @When("the manager user proceed to delete an user that previously was deleted")
    public void theManagerUserProceedToDeleteAnUserThatPreviouslyWasDeleted() {
        //Is necessary to create a user before delete her.
        createUser();

        //Delete process for created user.
        deleteUserWithHeaders(String.format(RESOURCE_DELETE, user.getId()));

        //Repeat the delete process.
        deleteUserWithHeaders(String.format(RESOURCE_DELETE, user.getId()));
    }

    @Then("he should see an error type reference to {string}")
    public void heShouldSeeAnErrorTypeReferenceTo(String errorTypeName) {
        statusCodeValidation(SC_NOT_FOUND);
        schemaValidation(RESOURCE_NOT_FOUND.getValue());

        theActorInTheSpotlight().should(
                seeThat(
                        "the error type name ",
                        ResourceNotFound.theErrorType(),
                        is(errorTypeName))
        );
    }

    //  Scenario: Try to delete some user without user identification.
    @When("the manager user proceed to delete a user but without user identification")
    public void theManagerUserProceedToDeleteAUserButWithoutUserIdentification() {
        deleteUserWithHeaders(RESOURCE_PATH_NOT_FOUND);
    }

    @Then("he should see a message related to error type {string}")
    public void heShouldSeeAMessageRelatedToErrorType(String errorTypeName) {
        statusCodeValidation(SC_NOT_FOUND);
        schemaValidation(PATH_NOT_FOUND.getValue());

        theActorInTheSpotlight().should(
                seeThat(
                        "the error type name ",
                        PathNotFound.theErrorType(),
                        is(errorTypeName))
        );
    }

    //Common methods.
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

    private void appIdErrorTypeName(String errorTypeName){
        theActorInTheSpotlight().should(
                seeThat(
                        "the error type name ",
                        AppId.theErrorType(),
                        is(errorTypeName))
        );
    }

    private void createUser(){
        theActorInTheSpotlight().wasAbleTo(
                doPost()
                        .usingTheResource(RESOURCE_CREATE)
                        .withHeaders(headers)
                        .andBodyRequest(
                                generateJson(
                                        generateUser(SPANISH_CODE_LANGUAGE, COUNTRY_CODE, EMAIL_DOMAIN)
                                )
                        )
                        .alsoPrintTheLastResponse()
        );

        user.setId(theInformationOfCreatedUser().answeredBy(theActorInTheSpotlight()).getId());
    }

    private void deleteUserWithHeaders(String resource){
        theActorInTheSpotlight().attemptsTo(
                doDelete()
                        .usingTheResource(resource)
                        .withHeaders(headers)
                        .alsoPrintTheLastResponse()
        );
    }
}
