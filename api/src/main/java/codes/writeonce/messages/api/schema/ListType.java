package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class ListType extends SimpleType {

    @Nonnull
    private final Type itemType;

    public ListType(@Nonnull Type itemType) {
        this.itemType = itemType;
    }

    @Nonnull
    public Type getItemType() {
        return itemType;
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
