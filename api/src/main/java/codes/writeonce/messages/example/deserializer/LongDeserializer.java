package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public class LongDeserializer extends AbstractLongDeserializer<Long> {

    public long longValue() {
        return value;
    }

    @Nonnull
    @Override
    public Long value() {
        return longValue();
    }
}
