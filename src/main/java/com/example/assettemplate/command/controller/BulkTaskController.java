package com.example.assettemplate.command.controller;

import com.example.assettemplate.command.controller.dto.BulkTaskRequest;
import com.example.assettemplate.command.service.BulkTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bulk")
@Slf4j
@RequiredArgsConstructor
public class BulkTaskController {

    private final BulkTaskService bulkTaskService;

    @PostMapping("/withdrawRequest")
    public void requestBulkWithdraw(@RequestBody BulkTaskRequest request) {
        log.info("Controller /bulk/withdrawRequest");
        bulkTaskService.requestBulkTask(request.getBulkTaskId());
    }

    @PostMapping("/depositRequest")
    public void requestBulkDeposit(@RequestBody BulkTaskRequest request) {
        log.info("Controller /bulk/depositRequest");
//        bulkTaskService.requestBulkTask(request.getBulkTaskId());
    }
}
