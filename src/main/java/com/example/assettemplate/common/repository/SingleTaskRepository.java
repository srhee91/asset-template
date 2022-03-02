package com.example.assettemplate.common.repository;

import com.example.assettemplate.common.repository.entity.SingleTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SingleTaskRepository extends JpaRepository<SingleTask, String> {
    List<SingleTask> findAllByBulkTaskId(String bulkTaskId);
}
