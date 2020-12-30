package codes.writeonce.messages.example;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;

public class ExampleMessageImpl implements ExampleMessage {

    boolean booleanValue;
    byte byteValue;
    private char charValue;
    private short shortValue;
    private int intValue;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private Instant instantValue;
    private BigDecimal bigDecimalValue;
    private String stringValue;
    private List<?> list;
    private Set<?> set;
    private Map<?, ?> map;

    @Override
    public boolean isBooleanValue() throws NoValueException {
        return booleanValue;
    }

    @Override
    public boolean getBooleanValue() throws NoValueException {
        return booleanValue;
    }

    @Override
    public boolean hasBooleanValue() {
        return booleanValue;
    }

    @Override
    @Nullable
    public Boolean getBooleanValueOrNull() {
        return booleanValue;
    }

    @Override
    @Nonnull
    public Optional<Boolean> getBooleanValueOptional() {
        return Optional.empty();
    }

    @Override
    public byte getByteValue() throws NoValueException {
        return byteValue;
    }

    @Override
    @Nullable
    public Byte getByteValueOrNull() {
        return byteValue;
    }

    @Override
    @Nonnull
    public Optional<Byte> getByteValueOptional() {
        return Optional.empty();
    }

    @Override
    public boolean hasByteValue() {
        return false;
    }

    @Override
    @Nonnull
    public String getStringValue() throws NoValueException {
        return stringValue;
    }

    @Override
    public boolean hasStringValue() {
        return false;
    }

    @Override
    @Nullable
    public String getStringValueOrNull() {
        return stringValue;
    }

    @Override
    @Nullable
    public Optional<String> getStringValueOptional() {
        return Optional.empty();
    }

    @Override
    public int getIntValue() throws NoValueException {
        return intValue;
    }

    @Override
    public OptionalInt getIntValueOptional() {
        return OptionalInt.empty();
    }

    @Override
    public long getLongValue() throws NoValueException {
        return longValue;
    }

    @Override
    public OptionalLong getLongValueOptional() {
        return OptionalLong.empty();
    }

    @Override
    public double getDoubleValue() throws NoValueException {
        return doubleValue;
    }

    @Override
    public OptionalDouble getDoubleValueOptional() {
        return OptionalDouble.empty();
    }
}
