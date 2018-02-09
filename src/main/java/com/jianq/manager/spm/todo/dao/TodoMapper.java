package com.jianq.manager.spm.todo.dao;

import com.jianq.manager.spm.todo.entity.Todo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TodoMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Todo record);

    Todo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Todo todo);

    List<Todo> selectByTodo(Todo todo);

    Integer selectOrderRoleId(@Param("teamId") Integer teamId, @Param("orderId") Integer orderId);

    String selectUserNameByOrderId(Integer orderId);

    Integer selectTodoStatusByOrderIdAndUserCode(@Param("orderId") Integer orderId, @Param("userCode") String userCode);
}