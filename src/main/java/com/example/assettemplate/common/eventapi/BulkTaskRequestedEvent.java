package com.example.assettemplate.common.eventapi;

import lombok.Value;

@Value
public class BulkTaskRequestedEvent {
    private final String bulkTaskId;
    private final int totalCount;
}
