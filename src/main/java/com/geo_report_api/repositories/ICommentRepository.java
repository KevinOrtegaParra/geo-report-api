package com.geo_report_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geo_report_api.model.Comment;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReportId(Long id);
}
