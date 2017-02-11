package com.vp.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.vp.demo.data.Playlist;
import com.vp.demo.service.Service;

@SpringBootApplication
@RestController
public class WebService {

	private Service service;

	public WebService() {
		service = new Service();
	}

	private static Log log = LogFactory.getLog(WebService.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Map<String, String> index() {
		Map<String, String> res = new HashMap<String, String>();
		res.put("msg", "Service is up and running");
		return res;
	}

	@RequestMapping(value = "/playlist/create", method = RequestMethod.POST)
	public Map<String, String> create(@RequestBody String json) {
		Gson gson = new Gson();
		Playlist playlist = gson.fromJson(json, Playlist.class);
		playlist.updateId();
		service.addToDataStore(playlist);
		Map<String, String> res = new HashMap<String, String>();
		res.put("msg", "created with id " + playlist.getId());
		res.put("url", "/playlist/" + playlist.getId());
		res.put("code", "201");
		return res;
	}

	@RequestMapping(value = "/playlist/{id}/like", method = RequestMethod.PUT)
	public Playlist like(@PathVariable int id) {
		return service.like(id);
	}

	@RequestMapping(value = "playlist/{id}/play", method = RequestMethod.PUT)
	public Playlist play(@PathVariable int id) {
		return service.play(id);
	}

	@RequestMapping(value = "playlist/{id}", method = RequestMethod.GET)
	public Playlist get(@PathVariable int id) {
		return service.get(id);
	}

	@RequestMapping(value = "playlist/{id}", method = RequestMethod.DELETE)
	public Map<String, String> delete(@PathVariable int id) {
		service.remove(id);
		Map<String, String> res = new HashMap<String, String>();
		res.put("msg", "removed successfully");
		res.put("code", "200");
		return res;
	}

	@RequestMapping(value = "playlist/list", method = RequestMethod.GET)
	public List<Playlist> list() {
		return service.getList();
	}

	@RequestMapping(value = "playlist/search", method = RequestMethod.GET)
	public List<Playlist> search(@RequestParam(value = "q") String query) {
		return service.search(getSearchTags(query));
	}

	private Set<String> getSearchTags(String query) {
		Set<String> searchTags = new HashSet<String>();
		if (query != null && query.trim() != "") {
			String[] tags = query.split(",");
			for (String tag : tags) {
				try {
					String decodeTag = URLDecoder.decode(tag, "UTF-8");
					decodeTag = decodeTag.trim().toLowerCase();
					searchTags.add(decodeTag);
				} catch (UnsupportedEncodingException e) {
					log.error("unaable to decode tag " + tag);
				}
			}
		}
		return searchTags;
	}

	public static void main(String[] args) {
		log.info("Starting up..");
		SpringApplication.run(WebService.class, args);
	}
}
