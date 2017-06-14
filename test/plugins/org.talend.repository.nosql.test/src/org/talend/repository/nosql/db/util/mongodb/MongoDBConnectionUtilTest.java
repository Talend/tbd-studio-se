// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.db.util.mongodb;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;

/**
 * DOC PLV class global comment. Detailled comment
 */
public class MongoDBConnectionUtilTest {

    private String contextReplicaSet;

    private String builtInReplicaSet;

    @Before
    public void setUp() throws Exception {
        contextReplicaSet = "[{\"REPLICA_HOST\":\"context.Mongo1_ReplicaHost_1\",\"REPLICA_PORT\":\"context.Mongo1_ReplicaPort_1\"}]";
        builtInReplicaSet = "[{\"REPLICA_HOST\":\"localhost\",\"REPLICA_PORT\":\"12701\"}]";
    }

    @Test
    public void testGetReplicaSetList() throws Exception {
        boolean isContextMode = true;
        // test context mode
        List<HashMap<String, Object>> replicaSetList = MongoDBConnectionUtil.getReplicaSetList(contextReplicaSet, !isContextMode);
        validateResult(replicaSetList, isContextMode);
        // test built-in mode
        isContextMode = false;
        replicaSetList = MongoDBConnectionUtil.getReplicaSetList(builtInReplicaSet, !isContextMode);
        validateResult(replicaSetList, isContextMode);
    }

    private void validateResult(List<HashMap<String, Object>> list, boolean isContextMode) {
        assertTrue(list != null && !list.isEmpty());
        Map<String, Object> map = list.get(0);
        assertTrue(map.size() == 2);
        String hostParam = (String) map.get(IMongoConstants.REPLICA_HOST_KEY);
        String portParam = (String) map.get(IMongoConstants.REPLICA_PORT_KEY);
        if (isContextMode) {
            assertFalse(
                    hostParam.startsWith(TalendQuoteUtils.QUOTATION_MARK) || hostParam.endsWith(TalendQuoteUtils.QUOTATION_MARK));
            assertFalse(
                    portParam.startsWith(TalendQuoteUtils.QUOTATION_MARK) || portParam.endsWith(TalendQuoteUtils.QUOTATION_MARK));
        } else {
            assertTrue(
                    hostParam.startsWith(TalendQuoteUtils.QUOTATION_MARK) && hostParam.endsWith(TalendQuoteUtils.QUOTATION_MARK));
            assertFalse(
                    portParam.startsWith(TalendQuoteUtils.QUOTATION_MARK) || portParam.endsWith(TalendQuoteUtils.QUOTATION_MARK));
        }
    }

}
