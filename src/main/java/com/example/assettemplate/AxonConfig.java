package com.example.assettemplate;

import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerTrackingEventProcessor(
                "bulk-task-projection",
                org.axonframework.config.Configuration::eventStore,
                c -> TrackingEventProcessorConfiguration.forParallelProcessing(2)
                        .andBatchSize(1)
        );
        configurer.registerSequencingPolicy(
                "bulk-task-projection",
                configuration -> SequentialPerAggregatePolicy.instance()
        );
    }

    // TODO: event store snapshot 찍는 구현 필요할 듯
}
