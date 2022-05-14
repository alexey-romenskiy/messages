package codes.writeonce.messages.api;

import codes.writeonce.messages.api.schema.RecordType;

import javax.annotation.Nonnull;

public interface MessageRecord {

    @Nonnull
    RecordType getRecordType();
}
