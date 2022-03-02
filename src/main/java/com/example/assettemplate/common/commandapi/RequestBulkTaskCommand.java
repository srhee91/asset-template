package com.example.assettemplate.common.commandapi;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class RequestBulkTaskCommand {
    @TargetAggregateIdentifier
    private final String bulkTaskId;
    private final int totalSingleTask;
}
