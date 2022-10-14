package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.models.user.searchusers.Users;
import io.dummyapi.stepdefinitions.ServiceSetup;
import java.io.IOException;
import static io.dummyapi.questions.general.ResponseCode.was;
import static io.dummyapi.questions.general.ReturnIntegerValue.returnIntegerValue;
import static io.dummyapi.questions.general.SchemaValidator.problems;
import static io.dummyapi.questions.user.GetUsersQuestion.getUsersQuestion;
import static io.dummyapi.tasks.generictasks.DoGet.doGet;
import static io.dummyapi.utils.FileUtilities.readFile;
import static io.dummyapi.utils.MySchemaPaths.SEARCH_USERS_SCHEMA;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class SearchUserStepDefinition extends ServiceSetup {

    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_USER = "user";
    private static final String RESOURCE_SPECIFIC_USER = "user/60d0fe4f5311236168a109ca";

    //Background.
    @Given("the admin user are in the searching section")
    public void theAdminUserAreInTheRightForm() {
        generalSetup(ACTOR_NAME);
    }

    //Scenario: Search all users.
    @When("he search for all users in general")
    public void heSearchForAllUsersInGeneral() {
        theActorInTheSpotlight().attemptsTo(
                doGet()
                        .usingResources(RESOURCE_USER)
                        .withHeaders(headers)
        );
    }

    @Then("he should see the list of all users in general.")
    public void heShouldSeeTheListOfAllUsersInGeneral() throws IOException {

        Users users = getUsersQuestion().answeredBy(theActorInTheSpotlight());

        theActorInTheSpotlight().should(
                seeThat("The status code ", was(), equalTo(SC_OK)),
                seeThat(
                        "The schema is right ",
                        problems().inFile(readFile(SEARCH_USERS_SCHEMA.getValue())).relatedWithSchema(),
                        is(false)
                ),
                seeThat("", returnIntegerValue(users.getTotal()), is(99)),
                seeThat("", returnIntegerValue(users.getTotal()), is(0)),
                seeThat("", returnIntegerValue(users.getTotal()), is(20))
        );


    }

    //Scenario: Search a user by id.
    @When("he search a specific user by the identification code")
    public void heSearchASpecificUserByTheIdentificationCode() {
        theActorInTheSpotlight().attemptsTo(
                doGet()
                        .usingResources(RESOURCE_SPECIFIC_USER)
                        .withHeaders(headers)
        );
    }

    @Then("he should see the specific user information.")
    public void heShouldSeeTheSpecificUserInformation() {

    }
}
