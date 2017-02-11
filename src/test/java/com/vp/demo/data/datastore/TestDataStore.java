package com.vp.demo.data.datastore;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.vp.demo.data.Playlist;

public class TestDataStore {

	@Test
	public void test() {
		DataStore d = new InMemoryDataStore();
		d.loadFromFile();
		System.out.println(d.getAll());
	}
	
	@Test
	public void testSearch() throws Exception {
		DataStore d = new InMemoryDataStore();
		d.loadFromFile();
		Set<String> s = new HashSet<String>();
		s.add("Bollywood".trim().toLowerCase());
		List<Playlist> search = d.search(s);
		search.forEach(k -> System.out.println(k.getName()));
	}
	
	@Test
	public void testRemove() throws Exception {
		DataStore d = new InMemoryDataStore();
		d.loadFromFile();
		d.remove(1);
		assertNull(d.get(1));
	}

}
