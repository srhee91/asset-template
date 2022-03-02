package com.example.assettemplate.common.commandapi;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CountSingleTaskCommand {
    @TargetAggregateIdentifier
    private final String bulkTaskId;
    private final String singleTaskId;
}
