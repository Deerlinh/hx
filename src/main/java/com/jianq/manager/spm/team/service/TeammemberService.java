package com.jianq.manager.spm.team.service;

import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.team.entity.Teammember;
import com.jianq.manager.spm.todo.entity.Todo;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
public interface TeammemberService {
    int delete(Integer id);

    int insert(Teammember record);

    Teammember selectById(Integer id);

    int update(Teammember record);

    Teammember getNextRoleTeammember(Integer teamId, Integer userId);

    Integer approveOrder(Order order, Todo todo, Integer state, String opinion, Integer nextUserId);

    Teammember selectByTeamIdAndUserId(Integer teamId, Integer userId);

    List<Teammember> selectByTeamIdAndRoleId(Integer teamId, Integer applicantRoleId);
}
