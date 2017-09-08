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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.osgi.framework.FrameworkUtil;
import org.talend.core.utils.RegistryReader;
import org.talend.designer.hdfsbrowse.hadoop.service.EHadoopServiceType;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceBean;

/**
 * 
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public class CheckedServiceRegistryReader extends RegistryReader {

    private static final String CHECKED_SERVICE_EXTENSION_POINT = "check_service"; //$NON-NLS-1$

    private static CheckedServiceRegistryReader checkedServiceReader;

    private static final String ROOT_ELEMENT_NAME = "checkedService"; //$NON-NLS-1$

    private static final String SERVICE_NAME_ELEMENT_NAME = "serviceName"; //$NON-NLS-1$

    private static final String SERVICE_TYPE_ELEMENT_NAME = "serviceType"; //$NON-NLS-1$

    private static final String TIMEOUT_ELEMENT_NAME = "timeout"; //$NON-NLS-1$

    private static final String CHECKED_SERVICE_PROVIDER_ELEMENT_NAME = "checkedServiceProvider"; //$NON-NLS-1$

    private static final int DEFAULT_TIMEOUT = 30; // 30 seconds

    private List<HadoopServiceBean> serviceBeans = new ArrayList<HadoopServiceBean>();

    private CheckedServiceRegistryReader() {
        super(FrameworkUtil.getBundle(CheckedServiceRegistryReader.class).getSymbolicName(), CHECKED_SERVICE_EXTENSION_POINT);
        readRegistry();
    }

    public static CheckedServiceRegistryReader getInstance() {
        if (checkedServiceReader == null) {
            checkedServiceReader = new CheckedServiceRegistryReader();
        }

        return checkedServiceReader;
    }

    public List<HadoopServiceBean> getCheckedServiceBeans() {
        return this.serviceBeans;
    }

    @Override
    protected boolean readElement(final IConfigurationElement element) {
        if (ROOT_ELEMENT_NAME.equals(element.getName())) {
            final List<String> serviceTypes = new ArrayList<String>();
            SafeRunner.run(new RegistryReader.RegistrySafeRunnable() {

                @Override
                public void run() throws Exception {
                    String serviceName = element.getAttribute(SERVICE_NAME_ELEMENT_NAME);
                    String serviceType = element.getAttribute(SERVICE_TYPE_ELEMENT_NAME);
                    String timeout = element.getAttribute(TIMEOUT_ELEMENT_NAME);
                    if (serviceTypes.contains(serviceType)) {
                        return;
                    }
                    ICheckedServiceProvider checkedServiceProvider = (ICheckedServiceProvider) element
                            .createExecutableExtension(CHECKED_SERVICE_PROVIDER_ELEMENT_NAME);
                    HadoopServiceBean serviceBean = new HadoopServiceBean();
                    serviceBean.setServiceName(serviceName);
                    serviceBean.setServiceType(EHadoopServiceType.valueOf(serviceType));
                    serviceBean.setTimeout(getTimeout(timeout));
                    serviceBean.setCheckedServiceProvider(checkedServiceProvider);
                    serviceBeans.add(serviceBean);
                    serviceTypes.add(serviceType);
                }
            });
            return true;
        }// else return false

        return false;
    }

    private int getTimeout(String timeout) {
        int to = DEFAULT_TIMEOUT;
        try {
            to = Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            to = DEFAULT_TIMEOUT;
        }

        return to;
    }

}
