package com.example.assettemplate.common.repository;

import com.example.assettemplate.common.repository.entity.BulkTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkTaskRepository extends JpaRepository<BulkTask, String> {
}
