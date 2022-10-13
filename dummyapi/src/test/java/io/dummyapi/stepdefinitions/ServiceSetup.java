package io.dummyapi.stepdefinitions;

import static io.dummyapi.utils.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;
import static net.serenitybdd.screenplay.actors.OnStage.*;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.apache.log4j.PropertyConfigurator;
import java.util.HashMap;

public class ServiceSetup {
    private static final String BASE_URL = "https://dummyapi.io/data/v1/";
    protected static HashMap<String, Object> headers = new HashMap<>();

    protected void generalSetup(String name){
        setUpLog4j2();
        defineDefaultHeader();
        defineActor(name);
    }

    private void setUpLog4j2(){
        PropertyConfigurator.configure(LOG4J_PROPERTIES_FILE_PATH.getValue());
    }

    private void defineActor(String name){
        setTheStage(new OnlineCast());
        theActorCalled(name).can(CallAnApi.at(BASE_URL));
    }

    private void defineDefaultHeader(){
        headers.put("app-id", "63485f907a44048db04b4361");
    }

}
