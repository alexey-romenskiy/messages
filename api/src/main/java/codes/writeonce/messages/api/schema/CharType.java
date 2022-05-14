package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class CharType extends SimpleType {

    public static final CharType INSTANCE = new CharType();

    private CharType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
