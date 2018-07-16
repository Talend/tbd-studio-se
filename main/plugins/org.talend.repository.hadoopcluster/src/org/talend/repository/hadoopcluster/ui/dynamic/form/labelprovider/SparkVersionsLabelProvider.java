// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.ui.dynamic.form.labelprovider;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.talend.repository.ui.login.LoginDialogV2;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class SparkVersionsLabelProvider extends LabelProvider
        implements ITableLabelProvider, ITableColorProvider, ITableFontProvider {

    @Override
    public Font getFont(Object element, int columnIndex) {
        return null;
    }

    @Override
    public Color getForeground(Object element, int columnIndex) {
        return null;
    }

    @Override
    public Color getBackground(Object element, int columnIndex) {
        if (isSelected(element)) {
            return LoginDialogV2.YELLOW_GREEN_COLOR;
        }
        return null;
    }

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        String text = getText(element);
        if (isSelected(element)) {
            text = text + " √"; //$NON-NLS-1$
        }
        return text;
    }

    abstract protected boolean isSelected(Object element);
}
