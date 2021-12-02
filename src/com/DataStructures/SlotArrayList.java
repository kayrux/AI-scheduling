package com.DataStructures;

import java.util.ArrayList;

public class SlotArrayList extends ArrayList {
	
	@Override
	public int indexOf(Object o) {
		return modCount;
		
	}
}
