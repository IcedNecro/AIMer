package com.checkpoint.aimer.utils;

import java.lang.reflect.Field;

/**
 * Utility class that performs merge of Entities. Used to modificate an existing entity
 * @author roman
 *
 * @param <E>
 */
public class EntityMerge<E> {

	/**
	 * Merges two Entities. It checks if another's field isn't null and 
	 * not equal to original's one
	 * 
	 * @param original
	 * @param another
	 * @return
	 */
	public E merge(E original, E another) {
		try {
			for(Field f: original.getClass().getDeclaredFields()){
				f.setAccessible(true);
				if(f.get(another)!=null && !f.get(original).equals(f.get(another))) {
					f.set(original, f.get(another));
				}
				f.setAccessible(false);
			}
			return original;
		} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}
