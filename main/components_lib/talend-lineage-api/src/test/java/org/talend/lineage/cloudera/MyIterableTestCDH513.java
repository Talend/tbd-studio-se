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
package org.talend.lineage.cloudera;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collections;

public class MyIterableTestCDH513<T> implements Iterable<T> {

    private List<T> list;

    public MyIterableTestCDH513(T [] t) {
        list = Arrays.asList(t);
        Collections.reverse(list);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
