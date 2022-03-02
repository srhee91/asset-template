package com.example.assettemplate.query.projection;

import com.example.assettemplate.common.eventapi.BulkTaskDoneEvent;
import com.example.assettemplate.common.eventapi.BulkTaskRequestedEvent;
import com.example.assettemplate.common.eventapi.SingleTaskDoneEvent;
import com.example.assettemplate.common.repository.BulkTaskRepository;
import com.example.assettemplate.common.repository.SingleTaskRepository;
import com.example.assettemplate.common.repository.entity.BulkTask;
import com.example.assettemplate.common.repository.entity.SingleTask;
import com.example.assettemplate.common.repository.enums.AcdnHdlStatCd;
import com.example.assettemplate.common.repository.enums.PaywdExectYn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("query")
@Service
@Slf4j
@RequiredArgsConstructor
@ProcessingGroup("bulk-task-projection")
public class BulkTaskProjection {

    private final BulkTaskRepository bulkTaskRepository;
    private final SingleTaskRepository singleTaskRepository;

    @EventHandler
    public void on(BulkTaskRequestedEvent event) {
        log.info("EventHandler         BulkTaskRequestedEvent. event : {}", event);

        BulkTask bulkTask = bulkTaskRepository.findById(event.getBulkTaskId()).orElseThrow();
        bulkTask.setPaywdExectYn(PaywdExectYn.INPROGRESS);
        bulkTaskRepository.save(bulkTask);
    }

    @EventHandler
    public void on(SingleTaskDoneEvent event) {
        log.info("EventHandler         SingleTaskDoneEvent. event : {}", event);

        SingleTask singleTask = singleTaskRepository.findById(event.getSingleTaskId()).orElseThrow();
        singleTask.setState(AcdnHdlStatCd.COMPLETED);
        singleTaskRepository.save(singleTask);
    }

    @EventHandler
    public void on(BulkTaskDoneEvent event) {
        log.info("EventHandler         BulkTaskDoneEvent. event : {}", event);

        BulkTask bulkTask = bulkTaskRepository.findById(event.getBulkTaskId()).orElseThrow();
        bulkTask.setPaywdExectYn(PaywdExectYn.DONE);
        bulkTaskRepository.save(bulkTask);
    }
}
