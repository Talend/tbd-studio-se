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
package org.talend.designer.hdfsbrowse.hadoop.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.hadoop.service.check.ICheckedServiceProvider;

/**
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public class HadoopServiceBean {

    private String serviceName;

    private EHadoopServiceType serviceType;

    private int timeout;

    private String errorMsg;

    private ICheckedServiceProvider checkedServiceProvider;

    private HadoopServiceProperties serviceProperties;

    private boolean checked;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public EHadoopServiceType getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(EHadoopServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ICheckedServiceProvider getCheckedServiceProvider() {
        return this.checkedServiceProvider;
    }

    public void setCheckedServiceProvider(ICheckedServiceProvider checkedServiceProvider) {
        this.checkedServiceProvider = checkedServiceProvider;
    }

    public HadoopServiceProperties getServiceProperties() {
        return this.serviceProperties;
    }

    public void setServiceProperties(HadoopServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean check() {
        if (checkedServiceProvider == null) {
            this.setErrorMsg("Cannot find the checked service provider for the type: " + serviceType); //$NON-NLS-1$
            return false;
        } else {
            boolean checkResult = false;
            try {
                checkResult = checkedServiceProvider.checkService(serviceProperties, timeout);
            } catch (HadoopServerException e) {
                this.setErrorMsg(ExceptionUtils.getFullStackTrace(e));
                return false;
            }
            if (!checkResult) {
                this.setErrorMsg("Fail without exceptions..."); //$NON-NLS-1$
                return false;
            }
        }

        return true;
    }

}
