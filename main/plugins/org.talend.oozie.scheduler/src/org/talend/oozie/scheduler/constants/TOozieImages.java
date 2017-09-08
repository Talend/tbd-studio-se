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
package org.talend.oozie.scheduler.constants;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.oozie.scheduler.OozieSchedulerPlugin;

/**
 * DOC Marvin class global comment. Detailled comment
 */
public enum TOozieImages implements IImage {
    IMG_RUN("/icons/oozie_run.gif"),
    IMG_SCHEDULE("/icons/oozie_schedule.gif"),
    IMG_KILL("/icons/oozie_kill.gif"),
    IMG_MONITOING("/icons/oozie_monitoring.gif"),
    IMG_SETTING("/icons/oozie_setting.gif"),
    IMG_DOTS("/icons/oozie_dots.gif");

    private String path;

    TOozieImages(String path) {
        this.path = path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.runtime.image.IImage#getLocation()
     */
    @Override
    public Class<OozieSchedulerPlugin> getLocation() {
        return OozieSchedulerPlugin.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.runtime.image.IImage#getPath()
     */
    @Override
    public String getPath() {
        return path;
    }

}
