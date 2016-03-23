package com.valeriisosliuk.game.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Shuffle {
	
	/**
	 * Returns A shuffled copy of an input List
	 * @param originalList Input List
	 * @return a new list, which contains all the items from <code>originalList</code>  in random order
	 */
	public static <T> List<T> shuffle(List<T> originalList) {
		List<T> shuffled = new ArrayList<>(originalList);
		Collections.shuffle(shuffled, new Random(System.nanoTime()));
		return shuffled;
	}

}
