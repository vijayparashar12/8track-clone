package com.vp.demo.data.datastore;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.vp.demo.data.Playlist;

public class InMemoryDataStore implements DataStore {

	private Map<Integer, Playlist> idIndex;
	private List<Playlist> data;
	private Map<String, List<Playlist>> tagIndex;

	public InMemoryDataStore() {
		idIndex = new HashMap<Integer, Playlist>();
		data = new ArrayList<Playlist>();
		tagIndex = new HashMap<String, List<Playlist>>();
	}

	InMemoryDataStore(List<Playlist> data) {
		this.data = data;
	}

	@Override
	public void insert(final Playlist playlist) {
		if (playlist != null) {
			idIndex.put(playlist.getId(), playlist);
			data.add(playlist);
			playlist.getTags().forEach(d -> {
				String token = d.trim().toLowerCase();
				List<Playlist> indexedList = null;
				if (tagIndex.containsKey(token)) {
					indexedList = tagIndex.get(token);
				} else {
					indexedList = new ArrayList<>();
					tagIndex.put(token, indexedList);
				}
				indexedList.add(playlist);
			});
		}
	}

	@Override
	public Playlist get(int id) {
		return idIndex.get(id);
	}

	@Override
	public List<Playlist> search(Set<String> tags) {
		Set<Playlist> response = new HashSet<Playlist>();
		for (String tag : tags) {
			List<Playlist> list = tagIndex.get(tag);
			if (list != null) {
				response.addAll(list);
			}
		}
		return new ArrayList<Playlist>(response);
	}

	@Override
	public List<Playlist> getAll() {
		return data;
	}

	@Override
	public void loadFromFile() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
		try {
			String json = IOUtils.toString(inputStream);
			Gson gson = new Gson();
			FileDataStore fileDataStore = gson.fromJson(json, FileDataStore.class);
			List<Playlist> playLists = fileDataStore.getAll();
			playLists.forEach(pl -> {
				pl.updateId();
				this.insert(pl);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(int id) {
		Playlist playlist = idIndex.get(id);
		data.remove(playlist);
		for (String tag : playlist.getTags()) {
			String token = tag.trim().toLowerCase();
			List<Playlist> playLists = tagIndex.get(token);
			if (playLists != null && playLists.size() > 0) {
				playLists.remove(playlist);
			}
		}
		idIndex.remove(id);
	}

}
