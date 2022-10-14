package io.dummyapi.questions.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import org.apache.log4j.Logger;

import static io.dummyapi.utils.SchemaValidator.getShemaValidationMessage;

public class SchemaValidator implements Question<Boolean> {

    private static final Logger LOGGER= Logger.getLogger(SchemaValidator.class);
    private Set<ValidationMessage> validationMessages;
    private String schema;

    public SchemaValidator inFile(String schema) {
        this.schema = schema;
        return this;
    }

    public SchemaValidator relatedWithSchema() {
        return this;
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            validationMessages = getShemaValidationMessage(
                    SpecVersion.VersionFlag.V4,
                    schema,
                    new String(LastResponse.received().answeredBy(actor).body().asByteArray(), StandardCharsets.UTF_8)
            );
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }

        generateLogForValidationMessages();

        return validationMessages.isEmpty();
    }

    private void generateLogForValidationMessages(){
        for (ValidationMessage validationMessage : validationMessages)
            LOGGER.warn(validationMessage.getMessage());
    }

    public static SchemaValidator problems(){
        return new SchemaValidator();
    }
}
