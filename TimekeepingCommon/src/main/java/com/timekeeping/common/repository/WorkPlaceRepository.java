package com.timekeeping.common.repository;

import com.timekeeping.common.entity.WorkPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkPlaceEntity, Integer > {
    WorkPlaceEntity getWorkPlaceEntityByWorkPlaceId(Integer workPlaceId);
}
