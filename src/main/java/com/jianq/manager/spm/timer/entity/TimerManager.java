package com.jianq.manager.spm.timer.entity;

import java.util.Date;

public class TimerManager {
	private Integer id;

	private String name;

	private String belongsSystem;

	private String packagePath;

	private String cron;

	private Integer execStatus;

	private Date lastTime;

	private String lastResult;

	private Date createTime;

	private TimerManager() {
	}

	public TimerManager(Integer id, Integer execStatus, String cron) {
		super();
		this.id = id;
		this.cron = cron;
		this.execStatus = execStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getBelongsSystem() {
		return belongsSystem;
	}

	public void setBelongsSystem(String belongsSystem) {
		this.belongsSystem = belongsSystem == null ? null : belongsSystem
				.trim();
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath == null ? null : packagePath.trim();
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron == null ? null : cron.trim();
	}

	public Integer getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(Integer execStatus) {
		this.execStatus = execStatus;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getLastResult() {
		return lastResult;
	}

	public void setLastResult(String lastResult) {
		this.lastResult = lastResult == null ? null : lastResult.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}