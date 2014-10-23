package com.klm.tcs.kiosk;

import java.util.HashSet;
import java.util.Set;

public class Storage {
	private static Storage instance = null;

	private Set<String> id = null;

	private Storage() {
		id = new HashSet<String>();
	}

	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage();
		}
		return instance;
	}

	public Set<String> getId() {
		return id;
	}

	public void setId(Set<String> id) {
		this.id = id;
	}
}