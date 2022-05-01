package com.shirkanesi.artemistutormapper.model.deserialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.shirkanesi.artemistutormapper.model.exercise.Exercise;
import com.shirkanesi.artemistutormapper.model.exercise.ExerciseTypes;

import java.io.IOException;

public class ExerciseDeserializer extends StdDeserializer<Exercise> {

    protected ExerciseDeserializer() {
        super(Exercise.class);
    }

    @Override
    public Exercise deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode node = jsonParser.readValueAsTree();

        TreeNode type = node.get("type");
        if (!type.isValueNode()) {
            throw new JsonParseException(jsonParser, "Field \"type\" not set on exercise!");
        }

        String typeValue = ((ValueNode) type).textValue();

        ExerciseTypes typeByInternalName = ExerciseTypes.getByInternalName(typeValue);

        return jsonParser.getCodec().treeToValue(node, typeByInternalName.getObjectClass());
    }
}
