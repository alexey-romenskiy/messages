package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class LongType extends SimpleType {

    public static final LongType INSTANCE = new LongType();

    private LongType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
