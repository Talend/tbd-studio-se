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
package org.talend.hadoop.distribution.dynamic;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public interface IDynamicDistributionPreference {

    void initDefaultPreference();

    boolean overrideDefaultSetup();

    boolean getDefaultOverrideDefaultSetup();

    void setOverrideDefaultSetup(boolean override);

    String getRepository();

    String getDefaultRepository();

    void setRepository(String repository);

    boolean isAnonymous();

    boolean getDefaultIsAnonymous();

    void setAnonymous(boolean anonymous);

    String getUsername();

    String getDefaultUsername();

    void setUsername(String username);

    String getPassword();

    String getDefaultPassword();

    void setPassword(String password);

    void save() throws Exception;

    String getPreferencePath();

    void clearCache() throws Exception;

}
