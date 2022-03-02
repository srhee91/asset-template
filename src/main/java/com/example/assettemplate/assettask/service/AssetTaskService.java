package com.example.assettemplate.assettask.service;

import com.example.assettemplate.assettask.exception.AssetTaskException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetTaskService {

    /**
     * php 대량회수 스크립트 내 단건작업 처리.
     * !! MUST BE IDEMPOTENT !!
     * @param singleTaskId
     * @throws AssetTaskException
     */
    @Transactional
    public void withdraw(String singleTaskId) throws AssetTaskException {
        log.info("Service withdraw. singleTaskId : {}", singleTaskId);

        // bithumb_asset_php.withdraw()
    }

    /**
     * php 대량지급 스크립트 내 단건작업 처리.
     * !! MUST BE IDEMPOTENT !!
     * @param singleTaskId
     * @throws AssetTaskException
     */
    @Transactional
    public void deposit(String singleTaskId) throws AssetTaskException {
        log.info("Service deposit. singleTaskId : {}", singleTaskId);

        // bithumb_asset_php.deposit()
    }
}
