package com.jianq.manager.spm.timer.service;

import java.util.List;

import com.jianq.manager.spm.timer.entity.TimerManager;

public interface TimerManagerService {

	int update(TimerManager record);

	List<TimerManager> list(String name);
}