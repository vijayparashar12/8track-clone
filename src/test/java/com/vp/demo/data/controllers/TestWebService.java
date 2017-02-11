package com.vp.demo.data.controllers;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.google.gson.Gson;
import com.vp.demo.data.Playlist;
import com.vp.demo.service.Service;

public class TestWebService {

	@Test
	public void test() {
		String json = "{\"name\" :\"Test play list\",\"likes\": 10,\"plays\": 30,\"tags\" :[\"Rock\",\"HipHop\"]}";
		Gson gson = new Gson();
		Playlist fromJson = gson.fromJson(json, Playlist.class);
		assertTrue(fromJson != null);
		assertEquals(fromJson.getLikes(), 10);
		assertEquals(fromJson.getPlays(), 30);
	}
	
	@Test
	public void testSearch() throws Exception {
		Service service = new Service();
		Set<String> searchTags = null;
		service.search(searchTags);
	}

}
