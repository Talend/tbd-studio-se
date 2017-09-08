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
package org.talend.designer.neo4j.ui.tabs;

import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.CopyPushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.ImportPushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.PastePushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.control.ExtendedPushButton;
import org.talend.commons.ui.swt.extended.table.AbstractExtendedTableViewer;

public abstract class ParameterToolbarView<T> extends ExtendedToolbarView {

    public ParameterToolbarView(Composite parent, int style, AbstractExtendedTableViewer<T> extendedTableViewer) {
        super(parent, style, extendedTableViewer);

    }

    @Override
    protected abstract AddPushButton createAddPushButton();

    @Override
    protected CopyPushButton createCopyPushButton() {
        return null;
    }

    @Override
    public PastePushButton createPastePushButton() {
        return null;
    }

    @Override
    public ImportPushButton createImportPushButton() {
        return null;
    }

    public void setEnable(boolean enabled) {
        for (ExtendedPushButton button : getButtons()) {
            if (button != null && button.getButton() != null) {
                button.getButton().setEnabled(enabled);
            }
        }
    }
}
