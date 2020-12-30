package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public class DoubleDeserializer extends AbstractLongDeserializer<Double> {

    public double doubleValue() {
        return Double.longBitsToDouble(value);
    }

    @Nonnull
    @Override
    public Double value() {
        return doubleValue();
    }
}
