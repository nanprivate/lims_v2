package com.clinomics.repository.lims;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.clinomics.entity.lims.Sample;
import com.clinomics.enums.StatusCode;

public interface SampleRepository extends JpaRepository<Sample, Integer>, JpaSpecificationExecutor<Sample> {
    List<Sample> findByIdIn(List<Integer> id);
    List<Sample> findByIdInAndStatusCodeIn(List<Integer> id, List<StatusCode> statusCodes);
}
