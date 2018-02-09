package com.jianq.manager.spm.todo.service.impl;

import com.jianq.manager.spm.cargo.service.TeamCargoService;
import com.jianq.manager.spm.todo.dao.TodoMapper;
import com.jianq.manager.spm.todo.entity.Todo;
import com.jianq.manager.spm.todo.service.TodoService;
import com.jianq.manager.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
@Service
public class TodoServiceImpl implements TodoService {
    private static final Log LOG = LogFactory.getLog(TodoServiceImpl.class);
    @Autowired
    private TodoMapper todoMapper;
    @Autowired
    private TeamCargoService teamCargoService;

    @Override
    public Integer deleteById(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = todoMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deleteById 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Integer insertTodo(Todo todo) {
        Integer id = null;
        if (todo != null) {
            try {
                todoMapper.insertSelective(todo);
                id = todo.getId();
            } catch (Exception e) {
                LOG.error("方法 insertTodo 异常,{}", e);
            }
        }
        return id;
    }

    @Override
    public Todo selectById(Integer id) {
        Todo todo = null;
        if (id != null) {
            try {
                todo = todoMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectById 异常,{}", e);
            }
        }
        return todo;
    }

    @Override
    public Integer updateTodo(Todo todo) {
        Integer count = null;
        if (todo != null) {
            try {
                count = todoMapper.updateByPrimaryKeySelective(todo);
            } catch (Exception e) {
                LOG.error("方法 updateTodo 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public List<Todo> selectByTodo(Todo todo) {
        List<Todo> todoList = null;
        if (todo != null) {
            try {
                todoList = todoMapper.selectByTodo(todo);
            } catch (Exception e) {
                LOG.error("方法 selectByTodo 异常,{}", e);
            }
        }
        return todoList;
    }

    @Override
    public Integer selectOrderRoleId(Integer orderId) {
        //初始设为0
        Integer roleId = null;
        if (orderId != null) {
            Integer teamId = teamCargoService.getTeamIdByOrderId(orderId);
            try {
                roleId = todoMapper.selectOrderRoleId(teamId, orderId);
            } catch (Exception e) {
                LOG.error("方法 selectOrderRoleId 异常,{}", e);
            }
        }
        return roleId == null ? 0 : roleId;
    }

    @Override
    public String selectUserNameByOrderId(Integer orderId) {
        //初始设为0
        String currentProcessingPerson = null;
        if (orderId != null) {
            try {
                currentProcessingPerson = todoMapper.selectUserNameByOrderId(orderId);
            } catch (Exception e) {
                LOG.error("方法 selectOrderRoleId 异常,{}", e);
            }
        }
        return currentProcessingPerson;
    }

    @Override
    public Integer selectTodoStatusByOrderIdAndUserCode(Integer orderId, String userCode) {
        Integer status = null;
        if (orderId != null && StringUtil.isNotEmpty(userCode)) {
            try {
                status = todoMapper.selectTodoStatusByOrderIdAndUserCode(orderId, userCode);
            } catch (Exception e) {
                LOG.error("方法 selectTodoStatusByOrderIdAndUserCode 异常,{}", e);
            }
        }
        return status;
    }
}
