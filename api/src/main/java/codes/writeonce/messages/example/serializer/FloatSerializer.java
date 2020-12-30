package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class FloatSerializer extends AbstractIntSerializer<Float> {

    public void value(float value) {
        this.value = Float.floatToRawIntBits(value);
    }

    @Override
    public void value(@Nonnull Float value) {
        value((float) value);
    }
}
