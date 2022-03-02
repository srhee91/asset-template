package com.example.assettemplate.common.eventapi;

import lombok.Value;

@Value
public class SingleTaskCountedEvent {
    private final int doneCount;
}
