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
package org.apache.ambari.api.model;

import java.util.Iterator;
import java.util.List;

import org.apache.ambari.api.ApiUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * created by bchen on Jun 2, 2015 Detailled comment
 *
 */
/**
 * A generic list.
 */
abstract class ApiListBase<T> implements Iterable<T> {

    protected List<T> values;

    public ApiListBase() {
        values = Lists.newArrayList();
    }

    public ApiListBase(List<T> values) {
        this.values = values;
    }

    public boolean add(T item) {
        return values.add(item);
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    public int size() {
        return values.size();
    }

    public T get(int index) {
        return values.get(index);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("values", values).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    @Override
    public boolean equals(Object o) {
        ApiListBase that = ApiUtils.baseEquals(this, o);
        return this == that || (that != null && Objects.equal(values, that.values));
    }
}