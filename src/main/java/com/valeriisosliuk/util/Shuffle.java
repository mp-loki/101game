package com.valeriisosliuk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shuffle {
	
	/**
	 * Returns A shuffled copy of an input List
	 * @param originalList Input List
	 * @return a new list, which contains all the items from <code>originalList</code>  in random order
	 */
	public static <T> List<T> shuffle(List<T> originalList) {
		List<T> shuffled = new ArrayList<>(originalList.size());
		Collections.copy(shuffled, originalList);
		Collections.shuffle(shuffled);
		return shuffled;
	}

}
