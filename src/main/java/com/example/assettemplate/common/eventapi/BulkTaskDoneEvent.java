package com.example.assettemplate.common.eventapi;

import lombok.Builder;
import lombok.Value;

@Value
public class BulkTaskDoneEvent {
    String bulkTaskId;
}
