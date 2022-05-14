package codes.writeonce.messages.api;

import codes.writeonce.messages.api.schema.Schema;
import codes.writeonce.messages.example.deserializer.Deserializer;
import codes.writeonce.messages.example.serializer.Serializer;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Supplier;

public class GeneratedSchema {

    @Nonnull
    private final Schema schema;

    @Nonnull
    private final Map<String, Class<? extends MessageRecord>> recordClasses;

    @Nonnull
    private final Supplier<Deserializer<? extends MessageRecord>> binaryDeserializerFactory;

    @Nonnull
    private final Supplier<Serializer<? super MessageRecord>> binarySerializerFactory;

    public GeneratedSchema(@Nonnull Schema schema, @Nonnull Map<String, Class<? extends MessageRecord>> recordClasses,
            boolean compactingStrings, boolean interningStrings) {
        this.schema = schema;
        this.recordClasses = recordClasses;
        binaryDeserializerFactory = () -> null;
        binarySerializerFactory = () -> null;
    }

    @Nonnull
    public Schema getSchema() {
        return schema;
    }

    @Nonnull
    public Map<String, Class<? extends MessageRecord>> getRecordClasses() {
        return recordClasses;
    }

    @Nonnull
    public Deserializer<? extends MessageRecord> createBinaryDeserializer() {
        return binaryDeserializerFactory.get();
    }

    @Nonnull
    public Serializer<? super MessageRecord> createBinarySerializer() {
        return binarySerializerFactory.get();
    }
}
