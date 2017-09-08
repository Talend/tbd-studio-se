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
package org.talend.designer.pigmap.figures.tablesettings;

import org.talend.designer.gefabstractmap.resource.ImageInfo;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public interface IUIMenuOption {

    public String getLabel();

    public MENU_TYPE getMenuType();

    public String getTooltipText();

    public ImageInfo getImageInfo();

}
