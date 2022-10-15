package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.models.user.delete.User;
import io.dummyapi.stepdefinitions.ServiceSetup;
import static io.dummyapi.questions.general.ResponseCode.was;
import static io.dummyapi.questions.general.SchemaValidator.problems;
import static io.dummyapi.questions.user.delete.DeleteUserQuestion.theDeleteProcessWith;
import static io.dummyapi.questions.user.delete.UserQuestion.theInformationOfCreatedUser;
import static io.dummyapi.tasks.generictasks.DoDelete.doDelete;
import static io.dummyapi.tasks.generictasks.DoPost.doPost;
import static io.dummyapi.utils.FileUtilities.readFile;
import static io.dummyapi.utils.Json.generateJson;
import static io.dummyapi.utils.schema.MySchemaPaths.DELETE_USER_SCHEMA;
import static io.dummyapi.utils.faker.UserFaker.*;
import static io.dummyapi.utils.Words.DONE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DeleteUserStepDefinition extends ServiceSetup {
    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_CREATE = "user/create";
    private static final String RESOURCE_DELETE = "user/%s";
    private final User user = new User();

    @Given("the admin user are in the remove users section")
    public void theAdminUserAreInTheRemoveUsersSection() {
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
        user.setId(theInformationOfCreatedUser().answeredBy(theActorInTheSpotlight()).getId());
    }

    @When("he select an user to delete")
    public void heSelectAnUserToDelete() {
        theActorInTheSpotlight().attemptsTo(
                doDelete()
                        .usingTheResource(String.format(RESOURCE_DELETE, user.getId()))
                        .withHeaders(headers)
        );
    }

    @Then("he should see that the selected user is really removed from the system.")
    public void heShouldSeeThatTheSelectedUserIsReallyRemovedFromTheSystem() {
        theActorInTheSpotlight().should(
                seeThat("the status code ", was(), equalTo(SC_OK)),
                seeThat(
                        "the problems with the schema ",
                        problems().inFile(readFile(DELETE_USER_SCHEMA.getValue())).relatedWithSchema(),
                        is(false)
                ),
                seeThat("the delete user information process ", theDeleteProcessWith(user), is(DONE))
        );
    }

}
