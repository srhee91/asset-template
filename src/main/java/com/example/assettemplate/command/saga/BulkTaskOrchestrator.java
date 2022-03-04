package com.example.assettemplate.command.saga;

import com.example.assettemplate.common.commandapi.CountSingleTaskCommand;
import com.example.assettemplate.common.commandapi.DoSingleTaskCommand;
import com.example.assettemplate.common.eventapi.BulkTaskDoneEvent;
import com.example.assettemplate.common.eventapi.BulkTaskRequestedEvent;
import com.example.assettemplate.common.eventapi.SingleTaskDoneEvent;
import com.example.assettemplate.common.repository.SingleTaskRepository;
import com.example.assettemplate.common.repository.entity.SingleTask;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Saga
public class BulkTaskOrchestrator {

    @Autowired
    private transient SingleTaskRepository singleTaskRepository;
    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "bulkTaskId")
    public void on(BulkTaskRequestedEvent event) {
        log.info("SagaEventHandling {}", event);

        String bulkTaskId = event.getBulkTaskId();
        List<SingleTask> singleTasks = singleTaskRepository.findAllByBulkTaskId(bulkTaskId);
        for(SingleTask singleTask: singleTasks) {
            String singleTaskId = singleTask.getSingleTaskId();
            SagaLifecycle.associateWith("singleTaskId", singleTaskId);
            commandGateway.send(new DoSingleTaskCommand(singleTaskId, bulkTaskId));
        }
    }

    @SagaEventHandler(associationProperty = "singleTaskId")
    public void on(SingleTaskDoneEvent event) {
        log.info("SagaEventHandling {}", event);

        String bulkTaskId = event.getBulkTaskId();
        SagaLifecycle.associateWith("bulkTaskId", bulkTaskId);
        commandGateway.send(new CountSingleTaskCommand(bulkTaskId, event.getSingleTaskId()));
    }

    @SagaEventHandler(associationProperty = "bulkTaskId")
    @EndSaga
    public void on(BulkTaskDoneEvent event) {
        log.info("SagaEventHandling {}", event);
    }
}
