package oo.kr.shared.global.db;

import static oo.kr.shared.global.db.DataSourceType.MASTER;
import static oo.kr.shared.global.db.DataSourceType.SLAVE;

import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSourceManager {

  private static final ThreadLocal<DataSourceType> currentDataSourceName = new ThreadLocal<>();

  public static DataSourceType getCurrentDataSourceName() {
    return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? SLAVE : MASTER;
  }

  public static void setCurrentDataSourceName(DataSourceType dataSourceType) {
    currentDataSourceName.set(dataSourceType);
  }

  public static void removeCurrentDataSourceName() {
    currentDataSourceName.remove();
  }

}