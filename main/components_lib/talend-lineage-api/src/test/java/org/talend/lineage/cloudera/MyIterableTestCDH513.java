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