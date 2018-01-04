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
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.runtime.hd.IDistributionsManager;
import org.talend.designer.maven.aether.DummyDynamicMonitor;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;

public final class DistributionsManager implements IDistributionsManager {

    private final String serviceName;

    private final ComponentType componentType;

    private Map<String, DistributionBean> distributionsMap;

    private DistributionBean[] distributionsCache;

    private ServiceListener serviceListener;

    static {
        try {

            DynamicDistributionManager dynamicDistributionManager = DynamicDistributionManager.getInstance();
            try {
                IDynamicMonitor monitor = new DummyDynamicMonitor();
                dynamicDistributionManager.registAll(monitor, true);
                dynamicDistributionManager.setLoaded(true);
            } catch (Throwable e) {
                ExceptionHandler.process(e);
            }

        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     * Service can't be null. if the service is HadoopComponent directly, the componentType will be null.
     */
    public DistributionsManager(String serviceName, ComponentType componentType) {
        this(serviceName, componentType, false);
    }

    public DistributionsManager(String serviceName, ComponentType compType, boolean registListener) {
        super();
        this.serviceName = serviceName;
        this.componentType = compType;
        this.distributionsMap = new HashMap<String, DistributionBean>();
        if (registListener) {
            this.serviceListener = new ServiceListener() {

                @Override
                public void serviceChanged(ServiceEvent event) {
                    if (event.getType() == ServiceEvent.REGISTERED) {
                        ServiceReference<? extends Object> sr = event.getServiceReference();
                        if (sr != null) {
                            BundleContext bc = getBundleContext();
                            Object obj = bc.getService(sr);
                            if (obj instanceof HadoopComponent) {
                                if (!distributionsMap.isEmpty()) {
                                    addDistribution(bc, distributionsMap, componentType,
                                            (ServiceReference<? extends HadoopComponent>) sr);
                                }
                            }
                        }
                    } else if (event.getType() == ServiceEvent.UNREGISTERING) {
                        ServiceReference<? extends Object> sr = event.getServiceReference();
                        if (sr != null) {
                            BundleContext bc = getBundleContext();
                            Object obj = bc.getService(sr);
                            if (obj instanceof HadoopComponent) {
                                if (!distributionsMap.isEmpty()) {
                                    removeDistribution(bc, distributionsMap, componentType,
                                            (ServiceReference<? extends HadoopComponent>) sr);
                                }
                            }
                        }
                    }
                }
            };
            try {
                getBundleContext().addServiceListener(this.serviceListener, "(" //$NON-NLS-1$
                        + Constants.OBJECTCLASS + "=" //$NON-NLS-1$
                        + serviceName + ")"); //$NON-NLS-1$
            } catch (InvalidSyntaxException e) {
                ExceptionHandler.process(e);
            }
        }
    }

    public DistributionsManager(String serviceName) {
        this(serviceName, null);
    }

    public DistributionsManager(ComponentType componentType) {
        this(componentType.getService(), componentType);
    }

    public void dispose() {
        if (this.serviceListener != null) {
            getBundleContext().removeServiceListener(this.serviceListener);
        }
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
        if (distributionsCache == null) {
            if (distributionsMap.isEmpty()) {
                synchronized (DistributionsManager.class) {
                    if (distributionsMap.isEmpty()) {
                        distributionsMap = getDistributionsDelegate();
                    }
                }
            }
            List<DistributionBean> distributionsList = new ArrayList<>(distributionsMap.values());
            sortDistributionBeans(distributionsList);
            distributionsCache = distributionsList.toArray(new DistributionBean[0]);
        }
        return distributionsCache;
    }

    private void sortDistributionBeans(List<DistributionBean> distributionsList) {
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
    }

    Map<String, DistributionBean> getDistributionsDelegate() {
        if (getServiceName() == null) {
            return new HashMap<String, DistributionBean>();
        }
        final ComponentType type = getComponentType();

        // We retrieve all the implementations of the HadoopComponent service.
        BundleContext bc = getBundleContext();
        Collection<ServiceReference<? extends HadoopComponent>> distributions = new LinkedList<>();
        try {
            Class<? extends HadoopComponent> clazz = (Class<? extends HadoopComponent>) Class.forName(getServiceName());
            distributions.addAll(bc.getServiceReferences(clazz, null));
        } catch (InvalidSyntaxException | ClassNotFoundException e) {
            CommonExceptionHandler.process(e);
            return new HashMap<String, DistributionBean>();
        }
        Map<String, DistributionBean> disctributionsMap = new HashMap<>();

        for (ServiceReference<? extends HadoopComponent> sr : distributions) {
            addDistribution(bc, disctributionsMap, type, sr);
        }

        return disctributionsMap;
    }

    private static BundleContext getBundleContext() {
        return FrameworkUtil.getBundle(DistributionFactory.class).getBundleContext();
    }

    private void addDistribution(BundleContext bc, Map<String, DistributionBean> disctributionsMap, ComponentType type,
            ServiceReference<? extends HadoopComponent> sr) {
        HadoopComponent hc = bc.getService(sr);
        if (hc.isActivated()) {
            final String distribution = hc.getDistribution();
            final String distributionName = hc.getDistributionName();

            String key = getKey(hc);
            DistributionBean distributionBean = disctributionsMap.get(key);
            if (distributionBean == null) {
                distributionBean = new DistributionBean(type, distribution, distributionName);
                disctributionsMap.put(key, distributionBean);
            } else {// check the name and displayName
                if (!distribution.equals(distributionBean.name) || !distributionName.equals(distributionBean.displayName)) {
                    CommonExceptionHandler.warn(" There are different distribution name for " + distributionBean); //$NON-NLS-1$
                    return;
                }
            }
            clearCache();

            final String version = hc.getVersion();
            // if (version!=null){ //sometimes, will be null, like Custom. but still need add the null version.
            DistributionVersion versionBean = new DistributionVersion(hc, distributionBean, version, hc.getVersionName(type));
            versionBean.addModuleGroups(hc.getModuleGroups(type));
            // special condition for current version
            versionBean.displayCondition = hc.getDisplayCondition(type);
            distributionBean.addVersion(versionBean);

            // add all version conditions ?
            distributionBean.addCondition(hc.getDisplayCondition(type));
        }
    }

    private void clearCache() {
        distributionsCache = null;
    }

    private void removeDistribution(BundleContext bc, Map<String, DistributionBean> distributionsMap, ComponentType type,
            ServiceReference<? extends HadoopComponent> sr) {
        HadoopComponent hc = bc.getService(sr);
        final String distribution = hc.getDistribution();
        final String distributionName = hc.getDistributionName();

        String key = getKey(hc);
        DistributionBean distributionBean = distributionsMap.get(key);
        if (distributionBean == null) {
            // not exsit, no need to remove
            return;
        } else {// check the name and displayName
            if (!distribution.equals(distributionBean.name) || !distributionName.equals(distributionBean.displayName)) {
                CommonExceptionHandler.warn(" There are different distribution name for " + distributionBean); //$NON-NLS-1$
                // return;
            }
        }
        clearCache();

        final String version = hc.getVersion();
        // if (version!=null){ //sometimes, will be null, like Custom. but still need add the null version.
        DistributionVersion versionBean = new DistributionVersion(hc, distributionBean, version, hc.getVersionName(type));
        versionBean.addModuleGroups(hc.getModuleGroups(type));
        // special condition for current version
        versionBean.displayCondition = hc.getDisplayCondition(type);
        distributionBean.removeVersion(versionBean);
        distributionBean.setDefaultVersion(null);

        DistributionVersion[] versions = distributionBean.getVersions();
        if (versions == null || versions.length <= 0) {
            distributionsMap.remove(key);
        }
    }

    private String getKey(HadoopComponent hc) {
        return hc.getDistribution();
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
