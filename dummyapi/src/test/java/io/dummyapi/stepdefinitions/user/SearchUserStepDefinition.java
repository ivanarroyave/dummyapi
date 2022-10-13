package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.stepdefinitions.ServiceSetup;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import static io.dummyapi.tasks.generictasks.DoGet.doGet;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class SearchUserStepDefinition extends ServiceSetup {

    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_USER = "user";

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
    public void heShouldSeeTheListOfAllUsersInGeneral() {
        //String soapResponse = new String(LastResponse.received().answeredBy(theActorInTheSpotlight()).asByteArray(), StandardCharsets.UTF_8);
        LastResponse.received().answeredBy(theActorInTheSpotlight()).prettyPrint();
    }

    //Scenario: Search an user by id.
    @When("he search a specific user by the identification code")
    public void heSearchASpecificUserByTheIdentificationCode() {
    }

    @Then("he should see the specific user information.")
    public void heShouldSeeTheSpecificUserInformation() {

    }
}
