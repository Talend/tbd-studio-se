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
public enum PIG_MAP_JOIN_OPTIMIZATION implements IUIJoinOptimization {

    NONE(JOIN_OPTIMIZATION.NONE, "None", "one", ImageInfo.ARROW_RIGHT),

    REPLICATED(JOIN_OPTIMIZATION.REPLICATED, "Replicated", "replicated", ImageInfo.ARROW_RIGHT_EDIT),

    SKEWED(JOIN_OPTIMIZATION.SKEWED, "Skewed", "skewed", ImageInfo.REFRESH),

    MERGE(JOIN_OPTIMIZATION.MERGE, "Merge", "merge", ImageInfo.REFRESH_CACHE), ;

    private String label;

    private MENU_TYPE menuType;

    private JOIN_OPTIMIZATION multipleMatchingMode;

    private String tooltipText;

    private ImageInfo imageInfo;

    PIG_MAP_JOIN_OPTIMIZATION(JOIN_OPTIMIZATION multipleMatchingMode, String labelKey, String tooltipKey, ImageInfo imageInfo) {
        this.multipleMatchingMode = multipleMatchingMode;
        this.label = labelKey;
        this.tooltipText = tooltipKey;
        this.imageInfo = imageInfo;
        this.menuType = MENU_TYPE.ITEM;
    }

    /**
     * Getter for label.
     * 
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.mapper.model.table.IUITest#getImageInfo()
     */
    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    /**
     * Getter for menuType.
     * 
     * @return the menuType
     */
    public MENU_TYPE getMenuType() {
        return menuType;
    }

    /**
     * Getter for multipleMatchingMode.
     * 
     * @return the multipleMatchingMode
     */
    public JOIN_OPTIMIZATION getJoinOptimization() {
        return this.multipleMatchingMode;
    }

    public String getTooltipText() {
        return this.tooltipText;
    }

    public static IUIJoinOptimization parse(String matchingMode) {
        PIG_MAP_JOIN_OPTIMIZATION multipleMatchingMode = null;
        PIG_MAP_JOIN_OPTIMIZATION[] tmapMultipleMatchingModes = values();
        for (PIG_MAP_JOIN_OPTIMIZATION tmapMultipleMatchingMode : tmapMultipleMatchingModes) {
            if (tmapMultipleMatchingMode.toString().equals(matchingMode)) {
                multipleMatchingMode = tmapMultipleMatchingMode;
                break;
            }
        }
        return multipleMatchingMode;
    }

};
