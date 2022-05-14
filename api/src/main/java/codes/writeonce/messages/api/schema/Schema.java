package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;
import java.util.Map;

public class Schema {

    @Nonnull
    private final Map<String, RecordType> recordTypes;

    public Schema(@Nonnull Map<String, RecordType> recordTypes) {
        this.recordTypes = recordTypes;
    }

    @Nonnull
    public Map<String, RecordType> getRecordTypes() {
        return recordTypes;
    }
}
