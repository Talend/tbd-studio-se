// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
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
 * Interface that exposes specific Impala methods.
 *
 */
public interface ImpalaComponent extends HadoopComponent {

    /**
     * indicate if Impala component support SSL
     *
     * Warning: Prior to CDH 5.5.2 / Impala 2.3.2, user could enable Kerberos authentication
     * between Impala internal components, or SSL encryption between Impala internal components,
     * but not both at the same time. This restriction has now been lifted.
     * See IMPALA-2598 to see the maintenance releases for different levels of CDH
     * where the fix has been published.
     *
     * @return support for SSL
     */
    default boolean doImpalaSupportSSL() {
        return false;
    }
}
