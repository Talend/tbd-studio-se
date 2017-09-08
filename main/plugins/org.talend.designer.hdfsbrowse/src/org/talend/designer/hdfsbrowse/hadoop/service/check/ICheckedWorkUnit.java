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
package org.talend.designer.hdfsbrowse.hadoop.service.check;

import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;

/**
 * created by ycbai on Aug 14, 2014 Detailled comment
 *
 */
public interface ICheckedWorkUnit {

    public static final int DEFAULT_TIMEOUT = 30;

    public Object execute() throws HadoopServerException;

    public void setTimeout(Integer timeout);

    public void setClassLoader(ClassLoader classLoader);

}
