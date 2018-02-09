package com.jianq.manager.spm.cargo.service;

import com.jianq.manager.spm.cargo.entity.TeamCargo;
import com.jianq.manager.spm.user.entity.User;

/**
 * @author Leo on 2017/11/7
 */
public interface TeamCargoService {
    TeamCargo selectByCargoId(Integer cargoId);

    Integer getTeamIdByOrderId(Integer orderId);
}
