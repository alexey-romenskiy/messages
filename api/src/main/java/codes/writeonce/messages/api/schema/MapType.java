package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class MapType extends SimpleType {

    @Nonnull
    private final Type keyType;

    @Nonnull
    private final Type valueType;

    public MapType(@Nonnull Type keyType, @Nonnull Type valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    @Nonnull
    public Type getKeyType() {
        return keyType;
    }

    @Nonnull
    public Type getValueType() {
        return valueType;
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
