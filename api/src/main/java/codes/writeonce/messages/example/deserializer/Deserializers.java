package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public class Deserializers {

    @Nonnull
    protected final BooleanDeserializer booleanDeserializer;

    @Nonnull
    protected final ByteDeserializer byteDeserializer;

    @Nonnull
    protected final CharDeserializer charDeserializer;

    @Nonnull
    protected final ShortDeserializer shortDeserializer;

    @Nonnull
    protected final IntDeserializer intDeserializer;

    @Nonnull
    protected final LongDeserializer longDeserializer;

    @Nonnull
    protected final FloatDeserializer floatDeserializer;

    @Nonnull
    protected final DoubleDeserializer doubleDeserializer;

    @Nonnull
    protected final InstantDeserializer instantDeserializer;

    @Nonnull
    protected final BigDecimalDeserializer bigDecimalDeserializer;

    @Nonnull
    protected final StringDeserializer stringDeserializer;

    @Nonnull
    protected final ListDeserializer listDeserializer;

    @Nonnull
    protected final SetDeserializer setDeserializer;

    @Nonnull
    protected final MapDeserializer mapDeserializer;

    private Deserializers(
            @Nonnull BooleanDeserializer booleanDeserializer,
            @Nonnull ByteDeserializer byteDeserializer,
            @Nonnull CharDeserializer charDeserializer,
            @Nonnull ShortDeserializer shortDeserializer,
            @Nonnull IntDeserializer intDeserializer,
            @Nonnull LongDeserializer longDeserializer,
            @Nonnull FloatDeserializer floatDeserializer,
            @Nonnull DoubleDeserializer doubleDeserializer,
            @Nonnull InstantDeserializer instantDeserializer,
            @Nonnull BigDecimalDeserializer bigDecimalDeserializer,
            @Nonnull StringDeserializer stringDeserializer,
            @Nonnull ListDeserializer listDeserializer,
            @Nonnull SetDeserializer setDeserializer,
            @Nonnull MapDeserializer mapDeserializer
    ) {
        this.booleanDeserializer = booleanDeserializer;
        this.byteDeserializer = byteDeserializer;
        this.charDeserializer = charDeserializer;
        this.shortDeserializer = shortDeserializer;
        this.intDeserializer = intDeserializer;
        this.longDeserializer = longDeserializer;
        this.floatDeserializer = floatDeserializer;
        this.doubleDeserializer = doubleDeserializer;
        this.instantDeserializer = instantDeserializer;
        this.bigDecimalDeserializer = bigDecimalDeserializer;
        this.stringDeserializer = stringDeserializer;
        this.listDeserializer = listDeserializer;
        this.setDeserializer = setDeserializer;
        this.mapDeserializer = mapDeserializer;
    }

    protected Deserializers(@Nonnull Deserializers deserializers) {

        this.booleanDeserializer = deserializers.booleanDeserializer;
        this.byteDeserializer = deserializers.byteDeserializer;
        this.charDeserializer = deserializers.charDeserializer;
        this.shortDeserializer = deserializers.shortDeserializer;
        this.intDeserializer = deserializers.intDeserializer;
        this.longDeserializer = deserializers.longDeserializer;
        this.floatDeserializer = deserializers.floatDeserializer;
        this.doubleDeserializer = deserializers.doubleDeserializer;
        this.instantDeserializer = deserializers.instantDeserializer;
        this.bigDecimalDeserializer = deserializers.bigDecimalDeserializer;
        this.stringDeserializer = deserializers.stringDeserializer;
        this.listDeserializer = deserializers.listDeserializer;
        this.setDeserializer = deserializers.setDeserializer;
        this.mapDeserializer = deserializers.mapDeserializer;
    }

    @Nonnull
    public static Deserializers createDeserializers(
            @Nonnull DeserializerContext context,
            boolean compactStrings
    ) {
        final IntDeserializer intDeserializer = new IntDeserializer();
        final LongDeserializer longDeserializer = new LongDeserializer();

        return new Deserializers(
                new BooleanDeserializer(),
                new ByteDeserializer(),
                new CharDeserializer(),
                new ShortDeserializer(),
                intDeserializer,
                longDeserializer,
                new FloatDeserializer(),
                new DoubleDeserializer(),
                new InstantDeserializer(intDeserializer, longDeserializer),
                new BigDecimalDeserializer(intDeserializer, longDeserializer),
                compactStrings
                        ? new CompactingStringDeserializer(context, intDeserializer)
                        : new NoncompactingStringDeserializer(context, intDeserializer),
                new ListDeserializer(intDeserializer),
                new SetDeserializer(intDeserializer),
                new MapDeserializer(intDeserializer)
        );
    }
}
