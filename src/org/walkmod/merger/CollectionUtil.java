package org.walkmod.merger;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class CollectionUtil {

	public static Object findObject(Collection<?> c, Object o, Comparator cmp) {
		if (c != null && o != null && cmp != null) {
			Iterator<?> it = c.iterator();
			while (it.hasNext()) {
				Object current = it.next();
				if (o.getClass().equals(current.getClass())) {
					if (cmp.compare(current, o) == 0) {
						return current;
					}
				}
			}
		}
		return null;
	}

}
