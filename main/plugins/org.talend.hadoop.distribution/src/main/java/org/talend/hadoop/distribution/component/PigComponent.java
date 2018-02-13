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
package org.talend.hadoop.distribution.component;

/**
 * Interface that exposes specific Pig methods.
 *
 */
public interface PigComponent extends MRComponent {

    /**
     * @return true if the distribution does support the Pig HCatLoader/HCatStorer.
     */
    public boolean doSupportHCatalog();

    /**
     * @return true if the distribution does support the Pig HBaseLoader/HBaseStorer.
     */
    public boolean doSupportHBase();

    /**
     * @return true if the distribution embeds a version of Pig prior to 0.12. This parameter is used to define the
     * package of the HCatLoader and HCatStorer.
     */
    public boolean pigVersionPriorTo_0_12();

    /**
     * @return true if the distribution supports TEZ for Pig.
     */
    public boolean doSupportTezForPig();
}
