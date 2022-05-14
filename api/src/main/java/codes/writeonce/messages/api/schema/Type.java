package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public interface Type {

    <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E;

    interface Visitor<V, E extends Throwable> {

        V visit(@Nonnull SimpleType type) throws E;

        V visit(@Nonnull RecordType type) throws E;
    }
}
