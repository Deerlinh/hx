package com.jianq.manager.spm.cargo.service.impl;

import com.jianq.manager.spm.cargo.dao.TeamCargoMapper;
import com.jianq.manager.spm.cargo.entity.TeamCargo;
import com.jianq.manager.spm.cargo.service.TeamCargoService;
import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.service.IOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo on 2017/11/7
 */
@Service
public class TeamCargoServiceImpl implements TeamCargoService {
    private static final Log LOG = LogFactory.getLog(CargoServiceImpl.class);
    @Autowired
    private TeamCargoMapper teamCargoMapper;
    @Autowired
    private IOrderService orderService;

    @Override
    public TeamCargo selectByCargoId(Integer cargoId) {
        TeamCargo teamCargo = null;
        if (cargoId != null) {
            try {
                teamCargo = teamCargoMapper.selectByCargoId(cargoId);
            } catch (Exception e) {
                LOG.error("方法 selectByCargoId 异常,{}", e);
            }
        }
        return teamCargo;
    }

    @Override
    public Integer getTeamIdByOrderId(Integer orderId) {
        Integer teamId = null;
        if (orderId != null) {
            Order order = orderService.selectById(orderId);
            if (order != null) {
                TeamCargo teamCargo = this.selectByCargoId(order.getCargoId());
                if (teamCargo != null) {
                    teamId = teamCargo.getTeamId();
                }
            }
        }
        return teamId;
    }
}
