package com.dimple.temp;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, com.asiainfo.config.datasource.DataSource ds) throws Throwable {
        String dsId = ds.value();
        if (com.asiainfo.config.datasource.DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
            logger.info("Use DataSource :{} >", dsId, point.getSignature());
            com.asiainfo.config.datasource.DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        } else {
            logger.info("数据源[{}]不存在，使用默认数据源 >{}", dsId, point.getSignature());
            com.asiainfo.config.datasource.DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, com.asiainfo.config.datasource.DataSource ds) {
        logger.debug("Revert DataSource : " + ds.value() + " > " + point.getSignature());
        com.asiainfo.config.datasource.DynamicDataSourceContextHolder.removeDataSourceRouterKey();
    }
}
