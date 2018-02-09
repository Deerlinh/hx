package com.jianq.manager.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录AOP实现
 */
@Aspect
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private String requestPath = null; // 请求地址
    private Map<?, ?> inputParamMap = null; // 传入参数
    Object outputResult = null;

    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间

    /**
     * @param joinPoint
     * @Description: 方法调用前触发 记录开始时间
     */
    @Before("execution(* com.jianq.manager.*.*.controller..*.*(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
    }

    /**
     * @param joinPoint
     * @Description: 方法调用后触发 记录结束时间
     */
    @After("execution(* com.jianq.manager.*.*.controller..*.*(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间
        this.printOptLog();
    }

    /**
     * @param pjp
     * @return
     * @throws Throwable
     * @Description: 环绕触发
     */
    @Around("execution(* com.jianq.manager.*.*.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        /**
         * 1.获取request信息 2.根据request获取session 3.从session中取出登录用户信息
         */
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        // 获取输入参数
        inputParamMap = request.getParameterMap();
        // 获取请求地址
        requestPath = request.getRequestURI();

        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
        outputResult = new HashMap<String, Object>();
        outputResult = pjp.proceed();// result的值就是被拦截方法的返回值
        return outputResult;
    }

    /**
     * @Description: 输出日志
     */
    private void printOptLog() {
        String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(startTimeMillis);
        logger.info("\n ============================================================================= \n 当前请求URL："
                + requestPath
                + "\n 请求参数："
                + JSONObject.toJSONString(inputParamMap)
                + ""
                + "\n 返回结果："
                + outputResult
                + "\n 请求时间："
                + optTime
                + "       处理时长："
                + (endTimeMillis - startTimeMillis)
                + "ms ;\n ====================================================================================================================================");
    }
}