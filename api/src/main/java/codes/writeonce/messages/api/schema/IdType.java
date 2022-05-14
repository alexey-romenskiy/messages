package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class IdType extends SimpleType {

    @Nonnull
    private final String entityType;

    public IdType(@Nonnull String entityType) {
        this.entityType = entityType;
    }

    @Nonnull
    public String getEntityType() {
        return entityType;
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
