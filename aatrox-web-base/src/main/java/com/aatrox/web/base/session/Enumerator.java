package com.aatrox.web.base.session;

import java.util.*;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class Enumerator implements Enumeration {
    private Iterator iterator;

    public Enumerator(Collection collection) {
        this(collection.iterator());
    }

    public Enumerator(Collection collection, boolean clone) {
        this(collection.iterator(), clone);
    }

    public Enumerator(Iterator iterator) {
        this.iterator = null;
        this.iterator = iterator;
    }

    public Enumerator(Iterator iterator, boolean clone) {
        this.iterator = null;
        if (!clone) {
            this.iterator = iterator;
        } else {
            ArrayList list = new ArrayList();

            while(iterator.hasNext()) {
                list.add(iterator.next());
            }

            this.iterator = list.iterator();
        }

    }

    public Enumerator(Map map) {
        this(map.values().iterator());
    }

    public Enumerator(Map map, boolean clone) {
        this(map.values().iterator(), clone);
    }

    @Override
    public boolean hasMoreElements() {
        return this.iterator.hasNext();
    }

    @Override
    public Object nextElement() throws NoSuchElementException {
        return this.iterator.next();
    }
}
