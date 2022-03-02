package com.example.assettemplate.common.eventapi;

import lombok.Value;

@Value
public class BulkTaskDoneEvent {
    private final String bulkTaskId;
}
