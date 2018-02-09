package com.jianq.manager.spm.timer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jianq.manager.spm.timer.entity.TimerManager;
import com.jianq.manager.spm.timer.service.TimerManagerService;
import com.jianq.manager.util.HttpUtil;
import com.jianq.manager.util.ReturnUtil;
import com.jianq.manager.util.StaticPropertiesUtil;

/**
 * 定时器
 */
@RequestMapping("/**/timer/")
@RestController
public class TimerController {

	private static final Logger log = LoggerFactory
			.getLogger(TimerController.class);

	private static final String server = StaticPropertiesUtil
			.getValue("current.server.url.domain");

	@Autowired
	private TimerManagerService timerManagerService;

	/**
	 * 查询任务列表
	 * 
	 * @param name
	 *            任务名
	 * @return
	 */
	@RequestMapping("list")
	public Object list(String name) {
		try {
			List<TimerManager> list = timerManagerService.list(name);
			return ReturnUtil.success("查询成功", list);
		} catch (Exception e) {
			log.error("{}", e);
			return ReturnUtil.fail("查询失败");
		}
	}

	/**
	 * 启动或手动执行一次
	 * 
	 * @param id
	 *            任务id
	 * @param packagePath
	 *            类的全包路径
	 * @param cron
	 *            执行表达式
	 * @return
	 */
	@RequestMapping("/common/{path}")
	public Object start(Integer id, String packagePath, String cron,
			String belongsSystem, @PathVariable String path) {
		String url = server + "/" + belongsSystem + "/app/timer/" + path;
		String params = "id=" + id + "&packagePath=" + packagePath + "&cron="
				+ cron;
		try {
			// 启动状态
			int execStatus = 1;
			if ("remove".equalsIgnoreCase(path)) {
				// 移除改成0:即暂停
				execStatus = 0;
			}
			TimerManager record = new TimerManager(id, execStatus, cron);
			timerManagerService.update(record);
			return HttpUtil.sendPost(url, params, false);
		} catch (Exception e) {
			log.error("{}", e);
			return ReturnUtil.fail("启动失败，请稍后重试");
		}
	}

}
