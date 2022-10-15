package io.dummyapi.stepdefinitions.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dummyapi.models.user.create.UserResponse;
import io.dummyapi.stepdefinitions.ServiceSetup;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import static io.dummyapi.questions.general.ResponseCode.was;
import static io.dummyapi.questions.general.SchemaValidator.problems;
import static io.dummyapi.questions.user.create.UserQuestion.theInformationOfCreatedUser;
import static io.dummyapi.questions.user.search.GetUserQuestion.theInformationOf;
import static io.dummyapi.questions.user.search.GetUsersQuestion.theLimitOfUsersPerPageComparedWithUsersQuantityIs;
import static io.dummyapi.tasks.generictasks.DoGet.doGet;
import static io.dummyapi.tasks.generictasks.DoPost.doPost;
import static io.dummyapi.utils.FileUtilities.readFile;
import static io.dummyapi.utils.Json.generateJson;
import static io.dummyapi.utils.faker.UserFaker.*;
import static io.dummyapi.utils.faker.UserFaker.EMAIL_DOMAIN;
import static io.dummyapi.utils.schema.MySchemaPaths.SEARCH_USERS_SCHEMA;
import static io.dummyapi.utils.schema.MySchemaPaths.SEARCH_USER_SCHEMA;
import static io.dummyapi.utils.Words.EQUAL;
import static io.dummyapi.utils.Words.ON_SCREEN;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class SearchUserStepDefinition extends ServiceSetup {

    private static final String ACTOR_NAME = "Admin";
    private static final String RESOURCE_USER = "user";
    private static final String RESOURCE_CREATE = "user/create";
    private static final String RESOURCE_SPECIFIC_USER = "user/%s";

    private UserResponse userResponse;

    //Scenario: Search all users.
    @Given("the admin user are in the searching section")
    public void theAdminUserAreInTheRightForm() {
        generalSetup(ACTOR_NAME);
    }

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
        theActorInTheSpotlight().should(
                seeThat("the status code ", was(), equalTo(SC_OK)),
                seeThat(
                        "the problems with the schema ",
                        problems().inFile(readFile(SEARCH_USERS_SCHEMA.getValue())).relatedWithSchema(),
                        is(false)
                ),
                seeThat(
                        "the limit of users per page compared with user's quantity ",
                        theLimitOfUsersPerPageComparedWithUsersQuantityIs(), is(EQUAL)
                )
        );
    }

    //Scenario: Search a user by id.
    @Given("the admin user are in the searching section with an user in mind")
    public void theAdminUserAreInTheSearchingSectionWithAnUserInMind() {
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
        userResponse = theInformationOfCreatedUser().answeredBy(theActorInTheSpotlight());
    }

    @When("he search the specific user by the identification code")
    public void heSearchTheSpecificUserByTheIdentificationCode() {
        theActorInTheSpotlight().attemptsTo(
                doGet()
                        .usingResources(
                                String.format(
                                        RESOURCE_SPECIFIC_USER,
                                        userResponse.getId()
                                )
                        )
                        .withHeaders(headers)
        );
    }

    @Then("he should see the specific user information.")
    public void heShouldSeeTheSpecificUserInformation() {
        theActorInTheSpotlight().should(
                seeThat("the status code ", was(), equalTo(SC_OK)),
                seeThat(
                        "the problems with the schema ",
                        problems().inFile(readFile(SEARCH_USER_SCHEMA.getValue())).relatedWithSchema(),
                        is(false)
                ),
                seeThat("the information of user ", theInformationOf(userResponse), is(ON_SCREEN))
        );
    }
}
