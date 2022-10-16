package io.dummyapi.utils.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.util.Set;

public class SchemaValidator {
    private SchemaValidator() {
    }

    public static Set<ValidationMessage> getShemaValidationMessage(SpecVersion.VersionFlag versionFlag, String schema, String body) throws JsonProcessingException {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(versionFlag);
        JsonSchema jsonSchema = factory.getSchema(schema);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        return jsonSchema.validate(jsonNode);
    }
}
