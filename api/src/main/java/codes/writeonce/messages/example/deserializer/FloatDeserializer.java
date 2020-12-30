package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public class FloatDeserializer extends AbstractIntDeserializer<Float> {

    public float floatValue() {
        return Float.intBitsToFloat(value);
    }

    @Nonnull
    @Override
    public Float value() {
        return floatValue();
    }
}
