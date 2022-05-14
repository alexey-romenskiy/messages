package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public abstract class SimpleType implements Type {

    public abstract <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E;

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Type.Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }

    interface Visitor<V, E extends Throwable> {

        V visit(@Nonnull BigDecimalType type) throws E;

        V visit(@Nonnull BooleanType type) throws E;

        V visit(@Nonnull ByteType type) throws E;

        V visit(@Nonnull CharType type) throws E;

        V visit(@Nonnull DoubleType type) throws E;

        V visit(@Nonnull FloatType type) throws E;

        V visit(@Nonnull IdType type) throws E;

        V visit(@Nonnull InstantType type) throws E;

        V visit(@Nonnull IntType type) throws E;

        V visit(@Nonnull ListType type) throws E;

        V visit(@Nonnull LongType type) throws E;

        V visit(@Nonnull MapType type) throws E;

        V visit(@Nonnull SetType type) throws E;

        V visit(@Nonnull ShortType type) throws E;

        V visit(@Nonnull StringType type) throws E;
    }
}
