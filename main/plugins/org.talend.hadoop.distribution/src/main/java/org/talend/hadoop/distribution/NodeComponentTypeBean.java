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
package org.talend.hadoop.distribution;

/**
 * Bean class to be used in HashMap.
 *
 */
public class NodeComponentTypeBean {

    private ComponentType mComponentType;

    private String mComponentName;

    public NodeComponentTypeBean(ComponentType componentType, String componentName) {
        this.mComponentType = componentType;
        this.mComponentName = componentName;
    }

    public ComponentType getComponentType() {
        return this.mComponentType;
    }

    public String getComponentName() {
        return this.mComponentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NodeComponentTypeBean)) {
            return false;
        }

        NodeComponentTypeBean that = (NodeComponentTypeBean) o;

        if (this.mComponentType != that.getComponentType()) {
            return false;
        }
        return this.mComponentName.equals(that.getComponentName());

    }

    @Override
    public int hashCode() {
        int result = this.mComponentType.hashCode();
        result = 31 * result + this.mComponentName.hashCode();
        return result;
    }
}
