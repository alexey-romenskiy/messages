package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class Serializers {

    @Nonnull
    protected final BooleanSerializer booleanSerializer;

    @Nonnull
    protected final ByteSerializer byteSerializer;

    @Nonnull
    protected final CharSerializer charSerializer;

    @Nonnull
    protected final ShortSerializer shortSerializer;

    @Nonnull
    protected final IntSerializer intSerializer;

    @Nonnull
    protected final LongSerializer longSerializer;

    @Nonnull
    protected final FloatSerializer floatSerializer;

    @Nonnull
    protected final DoubleSerializer doubleSerializer;

    @Nonnull
    protected final InstantSerializer instantSerializer;

    @Nonnull
    protected final BigDecimalSerializer bigDecimalSerializer;

    @Nonnull
    protected final StringSerializer stringSerializer;

    @Nonnull
    protected final ListSerializer listSerializer;

    @Nonnull
    protected final SetSerializer setSerializer;

    @Nonnull
    protected final MapSerializer mapSerializer;

    private Serializers(
            @Nonnull BooleanSerializer booleanSerializer,
            @Nonnull ByteSerializer byteSerializer,
            @Nonnull CharSerializer charSerializer,
            @Nonnull ShortSerializer shortSerializer,
            @Nonnull IntSerializer intSerializer,
            @Nonnull LongSerializer longSerializer,
            @Nonnull FloatSerializer floatSerializer,
            @Nonnull DoubleSerializer doubleSerializer,
            @Nonnull InstantSerializer instantSerializer,
            @Nonnull BigDecimalSerializer bigDecimalSerializer,
            @Nonnull StringSerializer stringSerializer,
            @Nonnull ListSerializer listSerializer,
            @Nonnull SetSerializer setSerializer,
            @Nonnull MapSerializer mapSerializer
    ) {
        this.booleanSerializer = booleanSerializer;
        this.byteSerializer = byteSerializer;
        this.charSerializer = charSerializer;
        this.shortSerializer = shortSerializer;
        this.intSerializer = intSerializer;
        this.longSerializer = longSerializer;
        this.floatSerializer = floatSerializer;
        this.doubleSerializer = doubleSerializer;
        this.instantSerializer = instantSerializer;
        this.bigDecimalSerializer = bigDecimalSerializer;
        this.stringSerializer = stringSerializer;
        this.listSerializer = listSerializer;
        this.setSerializer = setSerializer;
        this.mapSerializer = mapSerializer;
    }

    protected Serializers(@Nonnull Serializers serializers) {

        this.booleanSerializer = serializers.booleanSerializer;
        this.byteSerializer = serializers.byteSerializer;
        this.charSerializer = serializers.charSerializer;
        this.shortSerializer = serializers.shortSerializer;
        this.intSerializer = serializers.intSerializer;
        this.longSerializer = serializers.longSerializer;
        this.floatSerializer = serializers.floatSerializer;
        this.doubleSerializer = serializers.doubleSerializer;
        this.instantSerializer = serializers.instantSerializer;
        this.bigDecimalSerializer = serializers.bigDecimalSerializer;
        this.stringSerializer = serializers.stringSerializer;
        this.listSerializer = serializers.listSerializer;
        this.setSerializer = serializers.setSerializer;
        this.mapSerializer = serializers.mapSerializer;
    }

    @Nonnull
    public static Serializers createSerializers(boolean compactStrings) {

        final IntSerializer intSerializer = new IntSerializer();
        final LongSerializer longSerializer = new LongSerializer();

        return new Serializers(
                new BooleanSerializer(),
                new ByteSerializer(),
                new CharSerializer(),
                new ShortSerializer(),
                intSerializer,
                longSerializer,
                new FloatSerializer(),
                new DoubleSerializer(),
                new InstantSerializer(intSerializer, longSerializer),
                new BigDecimalSerializer(intSerializer, longSerializer),
                compactStrings
                        ? new CompactingStringSerializer(intSerializer)
                        : new NoncompactingStringSerializer(intSerializer),
                new ListSerializer(intSerializer),
                new SetSerializer(intSerializer),
                new MapSerializer(intSerializer)
        );
    }
}
