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
package org.talend.hadoop.distribution.component;

/**
 * created by hcyi on Sep 9, 2016 Detailled comment
 *
 */
public interface MaprdbComponent extends HadoopComponent {

    /**
     * @return true if the distribution does support MapR DB
     */
    public boolean doSupportMapRDB();
}
