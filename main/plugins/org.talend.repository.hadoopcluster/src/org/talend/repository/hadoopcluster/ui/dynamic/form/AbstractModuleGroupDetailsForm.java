// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.ui.dynamic.form;

import java.util.EventListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicModuleGroupData;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractModuleGroupDetailsForm extends Composite {

    private ICheckListener checkListener;

    private DynamicModuleGroupData groupData;

    public AbstractModuleGroupDetailsForm(Composite parent, int style, DynamicModuleGroupData moduleGroupData) {
        super(parent, style);
        this.groupData = moduleGroupData;
        this.setLayout(new FillLayout());
    }

    abstract public boolean isComplete();

    abstract public boolean canFinish();

    abstract public boolean canFlipToNextPage();

    public DynamicModuleGroupData getModuleGroupData() {
        return this.groupData;
    }

    public static Point getNewButtonSize(Button btn) {
        return getNewButtonSize(btn, 6);
    }

    public static Point getNewButtonSize(Button btn, int hPadding) {
        Point btnSize = btn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        btnSize.x += hPadding * 2;
        return btnSize;
    }

    protected Composite createFormContainer(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        FormLayout formLayout = new FormLayout();
        formLayout.marginTop = 10;
        formLayout.marginBottom = 10;
        formLayout.marginLeft = 10;
        formLayout.marginRight = 10;
        container.setLayout(formLayout);
        return container;
    }

    protected int getAlignVertical() {
        return 12;
    }

    protected int getAlignVerticalInner() {
        return 7;
    }

    protected int getAlignHorizon() {
        return 3;
    }

    protected int getHorizonWidth() {
        return 100;
    }

    public ICheckListener getCheckListener() {
        return this.checkListener;
    }

    public void setCheckListener(ICheckListener checkListener) {
        this.checkListener = checkListener;
    }

    protected void showMessage(String message, int level) {
        this.checkListener.showMessage(message, level);
    }

    protected void updateButtons() {
        this.checkListener.updateButtons();
    }

    public static interface ICheckListener extends EventListener {

        public void showMessage(String message, int level);

        public void updateButtons();

    }

}
