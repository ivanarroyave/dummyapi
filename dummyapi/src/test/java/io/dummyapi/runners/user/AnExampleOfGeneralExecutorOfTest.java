package io.dummyapi.runners.user;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {
                "src/test/resources/features/user/create-user.feature",
                "src/test/resources/features/user/delete-user.feature",
                "src/test/resources/features/user/search-user.feature",
                "src/test/resources/features/user/update-user.feature"
        },
        glue = {
                "io.dummyapi.stepdefinitions.user"
        },
        plugin = {"pretty"},
        tags = ""
)
public class AnExampleOfGeneralExecutorOfTest {
}
