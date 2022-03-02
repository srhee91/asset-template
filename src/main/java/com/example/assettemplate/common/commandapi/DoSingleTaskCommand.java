package com.example.assettemplate.common.commandapi;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class DoSingleTaskCommand {
    @TargetAggregateIdentifier
    private final String singleTaskId;
    private final String bulkTaskId;
}
