package codes.writeonce.messages.example;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public interface ExampleMessage {

    boolean isBooleanValue() throws NoValueException;

    boolean getBooleanValue() throws NoValueException;

    boolean hasBooleanValue();

    @Nullable
    Boolean getBooleanValueOrNull();

    @Nonnull
    Optional<Boolean> getBooleanValueOptional();

    byte getByteValue() throws NoValueException;

    @Nullable
    Byte getByteValueOrNull();

    @Nonnull
    Optional<Byte> getByteValueOptional();

    boolean hasByteValue();

    @Nonnull
    String getStringValue() throws NoValueException;

    boolean hasStringValue();

    @Nullable
    String getStringValueOrNull();

    @Nullable
    Optional<String> getStringValueOptional();

    int getIntValue() throws NoValueException;

    OptionalInt getIntValueOptional();

    long getLongValue() throws NoValueException;

    OptionalLong getLongValueOptional();

    double getDoubleValue() throws NoValueException;

    OptionalDouble getDoubleValueOptional();
}
