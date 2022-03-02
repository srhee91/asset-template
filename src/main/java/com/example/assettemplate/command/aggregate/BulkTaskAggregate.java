package com.example.assettemplate.command.aggregate;

import com.example.assettemplate.common.commandapi.CountSingleTaskCommand;
import com.example.assettemplate.common.commandapi.RequestBulkTaskCommand;
import com.example.assettemplate.common.eventapi.BulkTaskDoneEvent;
import com.example.assettemplate.common.eventapi.BulkTaskRequestedEvent;
import com.example.assettemplate.common.eventapi.SingleTaskCountedEvent;
import com.example.assettemplate.common.repository.enums.PaywdExectYn;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.annotation.Profile;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Profile("command")
@Aggregate
@Slf4j
@NoArgsConstructor
public class BulkTaskAggregate {

    @AggregateIdentifier
    private String bulkTaskId;
    private PaywdExectYn state;
    private int totalCount;
    private int doneCount;

    @CommandHandler
    public BulkTaskAggregate(RequestBulkTaskCommand command) {
        log.info("CommandHandler       RequestBulkTaskCommand. command : {}", command);

        apply(new BulkTaskRequestedEvent(command.getBulkTaskId(), command.getTotalSingleTask()));
    }

    @EventSourcingHandler
    public void on(BulkTaskRequestedEvent event) {
        log.info("EventSourcingHandler BulkTaskRequestedEvent. event : {}", event);

        this.bulkTaskId = event.getBulkTaskId();
        this.state = PaywdExectYn.INPROGRESS;
        this.totalCount = event.getTotalCount();
        this.doneCount = 0;
    }

    @CommandHandler
    public void handler(CountSingleTaskCommand command) {
        log.info("CommandHandler       CountSingleTaskCommand. command : {}", command);

        int doneCount = this.doneCount + 1;
        apply(new SingleTaskCountedEvent(doneCount));
        if(this.totalCount == doneCount) {
            apply(new BulkTaskDoneEvent(this.bulkTaskId));
        }
    }

    @EventSourcingHandler
    public void on(SingleTaskCountedEvent event) {
        log.info("EventSourcingHandler SingleTaskCountedEvent. event : {}", event);

        this.doneCount++;
    }

    @EventSourcingHandler
    public void on(BulkTaskDoneEvent event) {
        log.info("EventSourcingHandler BulkTaskDoneEvent. event : {}", event);

        this.state = PaywdExectYn.DONE;
    }
}

