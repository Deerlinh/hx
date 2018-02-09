package com.jianq.manager.spm.timer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianq.manager.spm.timer.dao.TimerManagerDao;
import com.jianq.manager.spm.timer.entity.TimerManager;
import com.jianq.manager.spm.timer.service.TimerManagerService;

/**
 * 
 * <br>
 * 
 * @version 1.0.0
 * @created 2017-10-11 下午3:53:13
 * @author king
 */
@Service
public class TimerManagerServiceImpl implements TimerManagerService {

	@Autowired
	private TimerManagerDao timerManagerDao;

	@Override
	public int update(TimerManager record) {

		return timerManagerDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<TimerManager> list(String name) {

		return timerManagerDao.list(name);
	}

}
