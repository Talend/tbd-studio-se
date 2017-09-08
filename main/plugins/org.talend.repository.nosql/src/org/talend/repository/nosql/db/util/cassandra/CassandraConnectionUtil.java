// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.db.util.cassandra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oro.text.regex.MalformedPatternException;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.utils.KeywordsValidator;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.db.handler.cassandra.CassandraMetadataHandler;
import org.talend.repository.nosql.db.handler.cassandra.CassandraOldVersionMetadataHandler;
import org.talend.repository.nosql.db.handler.cassandra.ICassandraMetadataHandler;

/**
 * 
 * created by ycbai on 2014年11月26日 Detailled comment
 *
 */
public class CassandraConnectionUtil {

    public static ICassandraMetadataHandler getMetadataHandler(NoSQLConnection connection) {
        if (isOldVersion(connection)) {
            return CassandraOldVersionMetadataHandler.getInstance();
        }if(isUpgradeVersion(connection)){
               return CassandraMetadataHandler.getInstanceForUpgradeDataStax();
        } else {
            boolean isDatastaxApiType = ICassandraConstants.API_TYPE_DATASTAX.equals(connection.getAttributes().get(
                    INoSQLCommonAttributes.API_TYPE));
            if (isDatastaxApiType) {
                return CassandraMetadataHandler.getInstanceForDataStax();
            } else {
                return CassandraMetadataHandler.getInstance();
            }
        }
    }

    public static boolean isOldVersion(NoSQLConnection connection) {
        String dbVersion = connection.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        return "CASSANDRA_1_1_2".equals(dbVersion); //$NON-NLS-1$
    }
    
    public static boolean isUpgradeVersion(NoSQLConnection connection) {
        String dbVersion = connection.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        try{
             Pattern pattern = Pattern.compile("CASSANDRA_(\\d+)_(\\d+)");//$NON-NLS-1$
             Matcher matcher = pattern.matcher(dbVersion);
             while (matcher.find()) {
                 String firstStr = matcher.group(1);
                 Integer firstInt = Integer.parseInt(firstStr);
                 if(firstInt>2){
                     return true; 
                 }else if(firstInt<2){
                     return false;
                 }else{
                     String secondStr= matcher.group(2);
                     Integer secondInt = Integer.parseInt(secondStr);
                     if(secondInt<2){
                         return false;
                     }else{
                         return true;
                     }
                 }
             }
        } catch (Exception ex) {
            //do nothing
        }
        return false; 
    }

}
