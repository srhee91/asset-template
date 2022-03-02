package com.example.assettemplate.command.service;

import com.example.assettemplate.common.commandapi.RequestBulkTaskCommand;
import com.example.assettemplate.common.repository.BulkTaskRepository;
import com.example.assettemplate.common.repository.entity.BulkTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulkTaskService {

    private final CommandGateway commandGateway;
    private final BulkTaskRepository bulkTaskRepository;

    public void requestBulkTask(String seq) {
        BulkTask bulkTask = bulkTaskRepository.findById(seq).orElseThrow();
        commandGateway.sendAndWait(new RequestBulkTaskCommand(seq, bulkTask.getTargetMemberCount()));
    }
}
