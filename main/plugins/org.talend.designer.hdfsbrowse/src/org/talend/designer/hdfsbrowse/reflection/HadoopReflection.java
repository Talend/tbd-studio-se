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
package org.talend.designer.hdfsbrowse.reflection;

import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopReflectionException;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopReflection {

    public static Object newInstance(final String className, final ClassLoader loader) throws HadoopReflectionException {
        return newInstance(className, null, loader);
    }

    public static Object newInstance(final String className, final Object[] args, final ClassLoader loader,
            final Class... argTypes) throws HadoopReflectionException {
        return new WrapException<Object>() {

            @Override
            public Object toDo() throws Exception {
                if (args == null || args.length == 0) {
                    return Class.forName(className, true, loader).newInstance();
                } else {
                    return ReflectionUtils.newInstance(className, loader, args, argTypes); //$NON-NLS-1$
                }
            }
        }.wrap();
    }

    public static Object invokeMethod(final Object obj, final String methodName) throws HadoopReflectionException {
        return invokeMethod(obj, methodName, null);
    }

    public static Object invokeMethod(final Object obj, final String methodName, final Object[] args, final Class... argTypes)
            throws HadoopReflectionException {
        return new WrapException<Object>() {

            @Override
            public Object toDo() throws Exception {
                if (args == null) {
                    return ReflectionUtils.invokeMethod(obj, methodName, new Object[0], argTypes); //$NON-NLS-1$
                } else {
                    return ReflectionUtils.invokeMethod(obj, methodName, args, argTypes); //$NON-NLS-1$
                }
            }
        }.wrap();
    }

    public static Object invokeStaticMethod(final String className, final String methodName, final ClassLoader loader,
            final Class... argTypes) throws HadoopReflectionException {
        return invokeStaticMethod(className, methodName, null, loader, argTypes);
    }

    public static Object invokeStaticMethod(final String className, final String methodName, final Object[] args,
            final ClassLoader loader, final Class... argTypes) throws HadoopReflectionException {
        return new WrapException<Object>() {

            @Override
            public Object toDo() throws Exception {
                if (args == null) {
                    return ReflectionUtils.invokeStaticMethod(className, loader, methodName, new Object[0], argTypes); //$NON-NLS-1$ 
                } else {
                    return ReflectionUtils.invokeStaticMethod(className, loader, methodName, args, argTypes); //$NON-NLS-1$ 
                }
            }
        }.wrap();
    }

    abstract static class WrapException<T> {

        public T wrap() throws HadoopReflectionException {
            try {
                return toDo();
            } catch (Exception e) {
                throw new HadoopReflectionException(e);
            }
        }

        public abstract T toDo() throws Exception;
    }

}
