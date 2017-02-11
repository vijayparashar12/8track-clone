package com.vp.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.vp.demo.data.Playlist;
import com.vp.demo.data.datastore.DataStore;
import com.vp.demo.data.datastore.InMemoryDataStore;

public class Service {
	private DataStore dataStore;

	public Service() {
		dataStore = new InMemoryDataStore();
		dataStore.loadFromFile();
	}

	public void addToDataStore(Playlist playlist) {
		dataStore.insert(playlist);
	}

	public Playlist get(int id) {
		return dataStore.get(id);
	}

	public List<Playlist> getList() {
		List<Playlist> all = dataStore.getAll();
		all.sort(relevancCamparator(null));
		return all;
	}

	private Comparator<? super Playlist> relevancCamparator(final Set<String> sTags) {
		return (pl1, pl2) -> {
			return -1 * pl1.getReleVancScore(sTags).compareTo(pl2.getReleVancScore(sTags));
		};
	}

	public Playlist like(int id) {
		Playlist playlist = this.get(id);
		playlist.incrLikes();
		return playlist;
	}

	public Playlist play(int id) {
		Playlist playlist = this.get(id);
		playlist.incrPlays();
		return playlist;
	}

	public void remove(int id) {
		dataStore.remove(id);
	}

	public List<Playlist> search(Set<String> searchTags) {
		List<Playlist> searchedList = dataStore.search(searchTags);
		searchedList.sort(relevancCamparator(searchTags));
		return searchedList;
	}

}
