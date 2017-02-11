package com.vp.demo.data.datastore;

import java.util.List;
import java.util.Set;

import com.vp.demo.data.Playlist;

public class FileDataStore implements DataStore {

	private List<Playlist> data;

	public FileDataStore(List<Playlist> data) {
		this.data = data;
	}

	@Override
	public Playlist get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Playlist playlist) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Playlist> search(Set<String> tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Playlist> getAll() {
		return data;
	}

	@Override
	public void remove(int id) {
		// TODO Auto-generated method stub
		
	}

}
