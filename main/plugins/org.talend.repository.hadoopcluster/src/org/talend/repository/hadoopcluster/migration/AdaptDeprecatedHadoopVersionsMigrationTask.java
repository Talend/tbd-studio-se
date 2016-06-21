package org.talend.repository.hadoopcluster.migration;

import java.util.Date;
import java.util.GregorianCalendar;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by ycbai on 2016年6月20日 Detailled comment
 *
 */
public class AdaptDeprecatedHadoopVersionsMigrationTask extends AbstractItemMigrationTask {

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2016, 6, 20, 12, 0, 0);
        return gc.getTime();
    }

    @Override
    public ExecutionResult execute(Item item) {
        if (item instanceof HadoopClusterConnectionItem) {
            DistributionBean[] distributions = HadoopDistributionsHelper.HADOOP.getDistributions();
            if (distributions != null && distributions.length > 0) {
                boolean modified = false;
                HadoopClusterConnectionItem hcItem = (HadoopClusterConnectionItem) item;
                HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcItem.getConnection();
                DistributionBean distributionBean = HadoopDistributionsHelper.HADOOP.getDistribution(
                        hcConnection.getDistribution(), false);
                if (distributionBean == null) {
                    distributionBean = distributions[0];
                    hcConnection.setDistribution(distributionBean.getName());
                    modified = true;
                }
                String version = hcConnection.getDfVersion();
                DistributionVersion distributionVersion = distributionBean.getVersion(version, false);
                if (distributionVersion == null) {
                    DistributionVersion[] versions = distributionBean.getVersions();
                    if (versions != null && versions.length > 0) {
                        hcConnection.setDfVersion(versions[0].getVersion());
                        modified = true;
                    }
                }
                if (modified) {
                    try {
                        ProxyRepositoryFactory.getInstance().save(hcItem, true);
                        return ExecutionResult.SUCCESS_NO_ALERT;
                    } catch (PersistenceException e) {
                        ExceptionHandler.process(e);
                        return ExecutionResult.FAILURE;
                    }
                }
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

}
