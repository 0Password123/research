package com.github.hubin0011.rest.rw.aspect;


import com.github.hubin0011.rest.rw.ICachableDomain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class RestfulRWAspect {

    private Map<String,Object> cachedMap = new HashMap<String, Object>();

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 拦截Restful请求，根据请求的方法进行资源缓存处理。
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(public * com.github.hubin0011.rest.rw.controller.*RestController.*(..))")
    public Object intercept(ProceedingJoinPoint pjp) throws Throwable{
        //取得请求方法类别
        RequestMethod requestMethod = getRequestMethod(pjp);
        //取得请求资源的id
        String id = getParamId(pjp);

        //判断是GET请求，如果缓存中存在请求资源，则直接从缓存中返回资源。
        if(requestMethod == RequestMethod.GET && id != null){
            if(cachedMap.containsKey(id)){
                System.out.println("直接从缓存中返回资源。资源id:" + id);
                return cachedMap.get(id);
            }
        }

        //继续进行拦截链，取得执行结果
        Object obj = pjp.proceed();

        //判断是POST请求，则将资源信息加入到缓存中
        if(requestMethod == RequestMethod.POST ){
            //取得资源信息数据
            Object resource = getPostDomain(pjp);
            if(resource instanceof ICachableDomain){
                ICachableDomain domain = (ICachableDomain)resource;
                //取得资源id
                String newId = domain.getId();
                if(newId != null){
                    System.out.println("将资源添加到缓存。资源id:" + newId);
                    cachedMap.put(newId,resource);
                }
            }

        }

        //判断是PUT请求，则将资源信息更新到缓存中
        if(requestMethod == RequestMethod.PUT){
            if(id != null){
                Object param = getPutDomain(pjp);
                System.out.println("从缓存中更新相应资源。资源id:" + id);
                cachedMap.put(id,param);
            }
        }

        //判断是DELETE请求，则从缓存中删除相应资源
        if(requestMethod == RequestMethod.DELETE){
            if(id != null){
                System.out.println("从缓存中删除相应资源。资源id:" + id);
                cachedMap.remove(id);
            }
        }

        //返回执行结果
        log.debug("返回执行结果");
        return obj;
    }

    /**
     * 取得Restful请求的资源id
     *
     * @param pjp
     * @return
     */
    protected String getParamId(ProceedingJoinPoint pjp){
        String id = null;

        Object[] args = pjp.getArgs();
        if(args.length >= 1){
            id = args[0].toString();
        }

        return id;
    }

    /**
     * 取得Post请求时资源的信息数据
     * @param pjp
     * @return
     */
    protected Object getPostDomain(ProceedingJoinPoint pjp){
        Object obj = null;

        Object[] args = pjp.getArgs();
        if(args.length == 1){
            obj = args[0];
        }

        return obj;
    }

    /**
     * 取得Put请求时资源的信息数据
     * @param pjp
     * @return
     */
    protected Object getPutDomain(ProceedingJoinPoint pjp){
        Object obj = null;

        Object[] args = pjp.getArgs();
        if(args.length == 2){
            obj = args[1];
        }

        return obj;
    }

    /**
     * 取得Restful请求的方法
     * @param pjp
     * @return
     */
    protected RequestMethod getRequestMethod(ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        RequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(targetMethod, RequestMapping.class);
        RequestMethod[] methods = methodRequestMapping.method();

        if(methods.length <= 0){
            return null;
        }
        return methods[0];
    }
}
