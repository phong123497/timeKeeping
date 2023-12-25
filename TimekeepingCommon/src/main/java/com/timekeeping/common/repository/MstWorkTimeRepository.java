package com.timekeeping.common.repository;

import com.timekeeping.common.entity.MstWorktimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MstWorkTimeRepository extends JpaRepository<MstWorktimeEntity,Integer> {
    MstWorktimeEntity getMstWorktimeEntityByWorktimeId(Integer workTimeId);
}
