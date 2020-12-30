package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class DoubleSerializer extends AbstractLongSerializer<Double> {

    public void value(double value) {
        this.value = Double.doubleToRawLongBits(value);
    }

    @Override
    public void value(@Nonnull Double value) {
        value((double) value);
    }
}
