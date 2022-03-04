package com.example.assettemplate.common.commandapi;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CountSingleTaskCommand {
    @TargetAggregateIdentifier
    String bulkTaskId;
    String singleTaskId;
}
