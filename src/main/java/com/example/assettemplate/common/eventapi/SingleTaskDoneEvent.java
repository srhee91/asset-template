package com.example.assettemplate.common.eventapi;

import lombok.Value;

@Value
public class SingleTaskDoneEvent {
    private final String singleTaskId;
    private final String bulkTaskId;
}
