package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public class IntDeserializer extends AbstractIntDeserializer<Integer> {

    public int intValue() {
        return value;
    }

    @Nonnull
    @Override
    public Integer value() {
        return intValue();
    }
}
