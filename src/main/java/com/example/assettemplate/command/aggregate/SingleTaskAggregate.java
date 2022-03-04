package com.example.assettemplate.command.aggregate;

import com.example.assettemplate.assettask.service.AssetTaskService;
import com.example.assettemplate.common.commandapi.DoSingleTaskCommand;
import com.example.assettemplate.common.eventapi.SingleTaskDoneEvent;
import com.example.assettemplate.common.exception.SingleTaskException;
import com.example.assettemplate.common.repository.enums.AcdnHdlStatCd;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@NoArgsConstructor
public class SingleTaskAggregate {

    @AggregateIdentifier
    private String singleTaskId;
    private String bulkTaskId;
    private AcdnHdlStatCd state;

    // CommandHandler는 single task 종류에 따라 각 패키지에서 관리하도록 수정
    private AssetTaskService assetTaskService;

    @CommandHandler
    public SingleTaskAggregate(DoSingleTaskCommand command, @Autowired AssetTaskService assetService) {
        log.info("CommandHandling {}", command);

        this.assetTaskService = assetService;

        try {
            this.assetTaskService.withdraw(command.getSingleTaskId());
        } catch(SingleTaskException e) {
            //TODO Fail Event 발행
        }

        apply(new SingleTaskDoneEvent(command.getSingleTaskId(), command.getBulkTaskId()));
    }

    @EventSourcingHandler
    public void on(SingleTaskDoneEvent event) {
        log.info("EventSourcing {}", event);

        this.singleTaskId = event.getSingleTaskId();
        this.bulkTaskId = event.getBulkTaskId();
        this.state = AcdnHdlStatCd.COMPLETED;
    }
}

