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
package org.talend.hadoop.distribution.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.core.runtime.hd.IDistributionsManager;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;

public final class DistributionsManager implements IDistributionsManager {

    private final String serviceName;

    private final ComponentType componentType;

    private DistributionBean[] distributionBeans;

    /**
     * Service can't be null. if the service is HadoopComponent directly, the componentType will be null.
     */
    public DistributionsManager(String serviceName, ComponentType componentType) {
        super();
        this.serviceName = serviceName;
        this.componentType = componentType;
    }

    public DistributionsManager(String serviceName) {
        this(serviceName, null);
    }

    public DistributionsManager(ComponentType componentType) {
        this(componentType.getService(), componentType);
    }

    private String getServiceName() {
        return serviceName;
    }

    private ComponentType getComponentType() {
        if (componentType == null) {
            ComponentType componentTypeByService = null;
            for (ComponentType t : ComponentType.values()) {
                if (t.getService().equals(getServiceName())) {
                    componentTypeByService = t;
                    break;
                }
            }
            return componentTypeByService;
        }
        return componentType;
    }

    @Override
    public DistributionBean[] getDistributions() {
        if (distributionBeans == null) {
            synchronized (DistributionsManager.class) {
                if (distributionBeans == null) {
                    distributionBeans = getDistributionsDelegate();
                }
            }
        }
        return distributionBeans;
    }

    DistributionBean[] getDistributionsDelegate() {
        if (getServiceName() == null) {
            return new DistributionBean[0];
        }
        final ComponentType type = getComponentType();

        // We retrieve all the implementations of the HadoopComponent service.
        BundleContext bc = FrameworkUtil.getBundle(DistributionFactory.class).getBundleContext();
        Collection<ServiceReference<? extends HadoopComponent>> distributions = new LinkedList<>();
        try {
            Class<? extends HadoopComponent> clazz = (Class<? extends HadoopComponent>) Class.forName(getServiceName());
            distributions.addAll(bc.getServiceReferences(clazz, null));
        } catch (InvalidSyntaxException | ClassNotFoundException e) {
            CommonExceptionHandler.process(e);
            return new DistributionBean[0];
        }
        Map<String, DistributionBean> disctributionsMap = new HashMap<>();

        for (ServiceReference<? extends HadoopComponent> sr : distributions) {
            HadoopComponent hc = bc.getService(sr);
            final String distribution = hc.getDistribution();
            final String distributionName = hc.getDistributionName();

            DistributionBean disctributionBean = disctributionsMap.get(distribution);
            if (disctributionBean == null) {
                disctributionBean = new DistributionBean(type, distribution, distributionName);
                disctributionsMap.put(distribution, disctributionBean);
            } else {// check the name and displayName
                if (!distribution.equals(disctributionBean.name) || !distributionName.equals(disctributionBean.displayName)) {
                    CommonExceptionHandler.warn(" There are different distribution name for " + disctributionBean); //$NON-NLS-1$
                    continue;
                }
            }

            final String version = hc.getVersion();
            // if (version!=null){ //sometimes, will be null, like Custom. but still need add the null version.
            DistributionVersion versionBean = new DistributionVersion(hc, disctributionBean, version, hc.getVersionName(type));
            versionBean.addModuleGroups(hc.getModuleGroups(type));
            // special condition for current version
            versionBean.displayCondition = hc.getDisplayCondition(type);
            disctributionBean.addVersion(versionBean);

            // add all version conditions ?
            disctributionBean.addCondition(hc.getDisplayCondition(type));
        }

        List<DistributionBean> distributionsList = new ArrayList<>(disctributionsMap.values());
        Collections.sort(distributionsList, new Comparator<DistributionBean>() {

            @Override
            public int compare(DistributionBean b1, DistributionBean b2) {
                if (Constant.DISTRIBUTION_CUSTOM.equals(b1.name)) {
                    return 1;
                }
                if (Constant.DISTRIBUTION_CUSTOM.equals(b2.name)) {
                    return -1;
                }
                return b1.name.compareTo(b2.name);
            }
        });
        return distributionsList.toArray(new DistributionBean[0]);
    }

    @Override
    public String[] getDistributionsDisplay(boolean withCustom) {
        List<String> distributionsDisplay = new ArrayList<>();
        for (DistributionBean bean : getDistributions()) {
            if (!withCustom && bean.useCustom()) {
                continue;
            }
            if ("CLOUDERA_ALTUS".equals(bean.getName())) {
                continue;
            }
            distributionsDisplay.add(bean.displayName);
        }
        return distributionsDisplay.toArray(new String[0]);
    }

    @Override
    public DistributionBean getDistribution(String name, boolean byDisplay) {
        if (name != null) {
            for (DistributionBean bean : getDistributions()) {
                if (byDisplay) {
                    if (name.equals(bean.displayName)) {
                        return bean;
                    }
                } else if (name.equals(bean.name)) {
                    return bean;
                }
            }
        }
        return null;
    }

    @Override
    public DistributionVersion getDistributionVersion(String version, boolean byDisplay) {
        for (DistributionBean bean : getDistributions()) {
            DistributionVersion v = bean.getVersion(version, byDisplay);
            if (v != null) {
                return v;
            }
        }
        return null;
    }
}
