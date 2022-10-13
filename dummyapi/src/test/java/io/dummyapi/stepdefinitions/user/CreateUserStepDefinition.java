package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.stepdefinitions.ServiceSetup;

public class CreateUserStepDefinition extends ServiceSetup {

    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_CREATE = "create";

    @Given("the admin user are in the creation user section")
    public void theAdminUserAreInTheCreationUserSection() {
        generalSetup(ACTOR_NAME);
    }

    @When("he configure and init the user creation")
    public void heConfigureAndInitTheUserCreation() {
    }

    @Then("he should see tin the system a user according to previous user creation.")
    public void heShouldSeeTinTheSystemAUserAccordingToPreviousUserCreation() {
    }
}
