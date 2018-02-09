package com.jianq.manager.spm.todo.controller;

import com.jianq.manager.spm.todo.entity.Todo;
import com.jianq.manager.spm.todo.service.TodoService;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo on 2017/10/24
 */
@RestController
@RequestMapping("/**/todo")
public class TodoController {
    @Autowired
    private UserService userService;
    @Autowired
    private TodoService todoService;

    /**
     * @param userCode 用户手机号
     * @param todo
     * @return
     */
    @RequestMapping("/list")
    public Object listTodo(String userCode, Todo todo) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        todo.setUserId(user.getUserId());
        return ReturnUtil.success(todoService.selectByTodo(todo));
    }
}
