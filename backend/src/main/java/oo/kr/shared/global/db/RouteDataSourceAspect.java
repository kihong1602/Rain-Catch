package oo.kr.shared.global.db;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Profile("!test & local")
@Component
public class RouteDataSourceAspect {

  @Before("@annotation(org.springframework.transaction.annotation.Transactional) && execution(* *(..))")
  public void setDataSource() {
    DataSourceType dataSourceType = RoutingDataSourceManager.getCurrentDataSourceName();
    RoutingDataSourceManager.setCurrentDataSourceName(dataSourceType);
  }

  @After("@annotation(org.springframework.transaction.annotation.Transactional)")
  public void clearDataSource() {
    RoutingDataSourceManager.removeCurrentDataSourceName();
  }

}