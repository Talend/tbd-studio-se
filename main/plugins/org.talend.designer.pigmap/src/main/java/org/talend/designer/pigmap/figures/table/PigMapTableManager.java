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
package org.talend.designer.pigmap.figures.table;

import org.talend.designer.gefabstractmap.figures.manager.TableManager;
import org.talend.designer.gefabstractmap.model.abstractmap.MapperTable;
import org.talend.designer.gefabstractmap.part.MapperTablePart;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapTableManager extends TableManager {

    public PigMapTableManager(MapperTable mapperTable, MapperTablePart tablePart) {
        super(mapperTable, tablePart);
    }

    @Override
    public AbstractInOutTable getModel() {
        return (AbstractInOutTable) super.getModel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#isActivateCondensedTool()
     */
    @Override
    public boolean isActivateCondensedTool() {
        return getModel().isActivateCondensedTool();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#isActivateExpressionFilter()
     */
    @Override
    public boolean isActivateExpressionFilter() {
        return getModel().isActivateExpressionFilter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#getExpressionFilter()
     */
    @Override
    public String getExpressionFilter() {
        return getModel().getExpressionFilter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#setExpressionFilter(java.lang.String)
     */
    @Override
    public void setExpressionFilter(String filter) {
        getModel().setExpressionFilter(filter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#isMinimized()
     */
    @Override
    public boolean isMinimized() {
        return getModel().isMinimized();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#setActivateCondensedTool(boolean)
     */
    @Override
    public void setActivateCondensedTool(boolean active) {
        getModel().setActivateCondensedTool(active);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#setActivateExpressionFilter(boolean)
     */
    @Override
    public void setActivateExpressionFilter(boolean active) {
        getModel().setActivateExpressionFilter(active);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#setMinimized(boolean)
     */
    @Override
    public void setMinimized(boolean minisized) {
        getModel().setMinimized(minisized);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#isActivateGlobalMap()
     */
    @Override
    public boolean isActivateGlobalMap() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.TableManager#setActivateGlobalMap(boolean)
     */
    @Override
    public void setActivateGlobalMap(boolean active) {
        // TODO Auto-generated method stub

    }

}
