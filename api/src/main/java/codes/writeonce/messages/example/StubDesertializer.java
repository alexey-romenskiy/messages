package codes.writeonce.messages.example;

import codes.writeonce.messages.api.MessageRecord;
import codes.writeonce.messages.example.deserializer.Deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class StubDesertializer implements Deserializer<MessageRecord> {

    private static final int GROW_FACTOR_MULTIPLIER = 3;

    private static final int GROW_FACTOR_DIVIDER = 2;

    private int state;

    private int bytesRemaining;

    private int intValue;

    private int[] initialStates;

    private int stateStackPosition;

    private int[] stateStack;

    private int intStackPosition;

    private int[] intStack;

    private int floatStackPosition;

    private float[] floatStack;

    @Override
    public void reset() {
        state = 0;
        // TODO:
    }

    @Override
    public int consume(@Nonnull ByteBuffer buffer, int remaining) {

        while (true) {
            switch (state) {
                case 0:
                    bytesRemaining = 1;//???
                    initialStates = null;//???
                    intValue = 0;
                    state = 1;
                    pushState(2);
                case 1:
                    if (bytesRemaining > remaining) {
                        switch (remaining) {
                            case 3 -> intValue =
                                    intValue << 24 | (0xff & buffer.get()) << 16 | 0xffff & buffer.getShort();
                            case 2 -> intValue = intValue << 16 | 0xffff & buffer.getShort();
                            case 1 -> intValue = intValue << 8 | 0xff & buffer.get();
                            case 0 -> {
                                return -1;
                            }
                            default -> throw new IllegalStateException();
                        }
                        bytesRemaining -= remaining;
                        return -1;
                    } else {
                        switch (bytesRemaining) {
                            case 4 -> intValue = buffer.getInt();
                            case 3 -> intValue =
                                    intValue << 24 | (0xff & buffer.get()) << 16 | 0xffff & buffer.getShort();
                            case 2 -> intValue = intValue << 16 | 0xffff & buffer.getShort();
                            case 1 -> intValue = intValue << 8 | 0xff & buffer.get();
                            default -> throw new IllegalStateException();
                        }
                        remaining -= bytesRemaining;
                    }
                    popState();
                    break;
                case 2:
                    state = initialStates[intValue];
                    break;
                case 3:
                    pushInt();
                    popState();
                    break;
                case 4:
                    pushFloat();
                    popState();
                    break;
            }
        }
    }

    private void popState() {
        state = stateStack[--stateStackPosition];
    }

    private void pushState(int state) {

        final var length = stateStack.length;
        if (stateStackPosition == length) {
            stateStack = Arrays.copyOf(stateStack, grow(length));
        }
        stateStack[stateStackPosition++] = state;
    }

    private void pushInt() {

        final var length = intStack.length;
        if (intStackPosition == length) {
            intStack = Arrays.copyOf(intStack, grow(length));
        }
        intStack[intStackPosition++] = intValue;
    }

    private void pushFloat() {

        final var length = floatStack.length;
        if (floatStackPosition == length) {
            floatStack = Arrays.copyOf(floatStack, grow(length));
        }
        floatStack[floatStackPosition++] = Float.intBitsToFloat(intValue);
    }

    private static int grow(int length) {
        return (length + 1) * GROW_FACTOR_MULTIPLIER / GROW_FACTOR_DIVIDER;
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {
        return 0; // TODO:
    }

    @Nonnull
    @Override
    public MessageRecord value() {
        return null; // TODO:
    }
}
