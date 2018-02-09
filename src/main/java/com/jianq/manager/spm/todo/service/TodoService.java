package com.jianq.manager.spm.todo.service;

import com.jianq.manager.spm.todo.entity.Todo;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
public interface TodoService {
    Integer deleteById(Integer id);

    Integer insertTodo(Todo record);

    Todo selectById(Integer id);

    Integer updateTodo(Todo record);

    List<Todo> selectByTodo(Todo todo);

    /**
     * 根据订单id查询订单审批的状态
     *
     * @param orderId
     * @return
     */
    Integer selectOrderRoleId(Integer orderId);

    /**
     * 根据订单id查询订单当前处理人
     *
     * @param orderId
     * @return
     */
    String selectUserNameByOrderId(Integer orderId);

    Integer selectTodoStatusByOrderIdAndUserCode(Integer orderId, String userCode);
}
