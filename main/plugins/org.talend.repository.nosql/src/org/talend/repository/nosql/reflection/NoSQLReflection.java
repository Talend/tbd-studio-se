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
package org.talend.repository.nosql.reflection;

import org.talend.core.utils.ReflectionUtils;
import org.talend.repository.nosql.exceptions.NoSQLReflectionException;

/**
 * DOC PLV class global comment. Detailled comment
 */
public class NoSQLReflection {

    public static Object newInstance(final String className, final ClassLoader loader) throws NoSQLReflectionException {
        return newInstance(className, null, loader);
    }

    public static Object newInstance(final String className, final Object[] args, final ClassLoader loader,
            final Class... argTypes) throws NoSQLReflectionException {
        return new WrapException<Object>() {

            @Override
            public Object toDo() throws Exception {
                if (args == null || args.length == 0) {
                    return Class.forName(className, true, loader).newInstance();
                } else {
                    return ReflectionUtils.newInstance(className, loader, args, argTypes);
                }
            }
        }.wrap();
    }

    public static Boolean isInstance(final Object obj, final Class cls) throws NoSQLReflectionException {
        return new WrapException<Boolean>() {

            @Override
            public Boolean toDo() throws Exception {
                return ReflectionUtils.isInstance(obj, cls);
            }
        }.wrap();
    }

    public static Object invokeMethod(final Object obj, final String methodName) throws NoSQLReflectionException {
        return invokeMethod(obj, methodName, null);
    }

    public static Object invokeMethod(final Object obj, final String methodName, final Object[] args, final Class... argTypes)
            throws NoSQLReflectionException {
        return new WrapException<Object>() {

            @Override
            public Object toDo() throws Exception {
                if (args == null) {
                    return ReflectionUtils.invokeMethod(obj, methodName, new Object[0], argTypes);
                } else {
                    return ReflectionUtils.invokeMethod(obj, methodName, args, argTypes);
                }
            }
        }.wrap();
    }

    public static Object invokeStaticMethod(final String className, final String methodName, final ClassLoader loader,
            final Class... argTypes) throws NoSQLReflectionException {
        return invokeStaticMethod(className, methodName, null, loader, argTypes);
    }

    public static Object invokeStaticMethod(final String className, final String methodName, final Object[] args,
            final ClassLoader loader, final Class... argTypes) throws NoSQLReflectionException {
        return new WrapException<Object>() {

            @Override
            public Object toDo() throws Exception {
                if (args == null) {
                    return ReflectionUtils.invokeStaticMethod(className, loader, methodName, new Object[0], argTypes);
                } else {
                    return ReflectionUtils.invokeStaticMethod(className, loader, methodName, args, argTypes);
                }
            }
        }.wrap();
    }

    abstract static class WrapException<T> {

        public T wrap() throws NoSQLReflectionException {
            try {
                return toDo();
            } catch (Exception e) {
                throw new NoSQLReflectionException(e);
            }
        }

        public abstract T toDo() throws Exception;
    }

}
