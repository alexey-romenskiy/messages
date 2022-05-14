package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class ShortType extends SimpleType {

    public static final ShortType INSTANCE = new ShortType();

    private ShortType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
