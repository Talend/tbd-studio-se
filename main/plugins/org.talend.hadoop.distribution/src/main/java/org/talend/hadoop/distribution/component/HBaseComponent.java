// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.component;

/**
 * Interface that exposes specific HBase methods.
 *
 */
public interface HBaseComponent extends HadoopComponent {

    /**
     * @return true if the distribution does support the new HBase API.
     */
    public boolean doSupportNewHBaseAPI();

    /**
     * @return true if the distribution does support MapR DB
     */
    public boolean doSupportMapRDB();

    /**
     * @return true if the distribution does support MapR DB
     */
    public boolean doSupportHBase2x();
}
