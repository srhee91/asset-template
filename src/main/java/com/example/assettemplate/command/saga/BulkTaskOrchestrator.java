package com.example.assettemplate.command.saga;

import com.example.assettemplate.common.commandapi.CountSingleTaskCommand;
import com.example.assettemplate.common.commandapi.DoSingleTaskCommand;
import com.example.assettemplate.common.eventapi.BulkTaskDoneEvent;
import com.example.assettemplate.common.eventapi.BulkTaskRequestedEvent;
import com.example.assettemplate.common.eventapi.SingleTaskDoneEvent;
import com.example.assettemplate.common.repository.SingleTaskRepository;
import com.example.assettemplate.common.repository.entity.SingleTask;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("command")
@Slf4j
//@Saga
@Service
public class BulkTaskOrchestrator {

    @Autowired
    private SingleTaskRepository singleTaskRepository;
    @Autowired
    private CommandGateway commandGateway;

//    @StartSaga
//    @SagaEventHandler(associationProperty = "bulkTaskId")
    @EventHandler
    public void on(BulkTaskRequestedEvent event) {
        log.info("EventHandler         BulkTaskRequestedEvent. event : {}", event);

        String bulkTaskId = event.getBulkTaskId();
        List<SingleTask> singleTasks = singleTaskRepository.findAllByBulkTaskId(bulkTaskId);
        for(SingleTask singleTask: singleTasks) {
            String singleTaskId = singleTask.getSingleTaskId();
//            SagaLifecycle.associateWith("singleTaskId", singleTaskId);
            commandGateway.send(new DoSingleTaskCommand(singleTaskId, bulkTaskId));
        }
    }

//    @SagaEventHandler(associationProperty = "singleTaskId")
    @EventHandler
    public void on(SingleTaskDoneEvent event) {
        log.info("EventHandler         SingleTaskDoneEvent. event : {}", event);

        String bulkTaskId = event.getBulkTaskId();
//        SagaLifecycle.associateWith("bulkTaskId", bulkTaskId);
        commandGateway.send(new CountSingleTaskCommand(bulkTaskId, event.getSingleTaskId()));
    }

//    @SagaEventHandler(associationProperty = "bulkTaskId")
//    @EndSaga
    @EventHandler
    public void on(BulkTaskDoneEvent event) {
        log.info("EventHandler         BulkTaskDoneEvent. event : {}", event);
    }
}
