package com.vp.demo.data.datastore;

import java.util.List;
import java.util.Set;

import com.vp.demo.data.Playlist;

public interface DataStore {

	Playlist get(int id);

	void insert(final Playlist playlist);

	List<Playlist> search(Set<String> tags);

	void loadFromFile();

	List<Playlist> getAll();
	
	void remove(int id);

}
