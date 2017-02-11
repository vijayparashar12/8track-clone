package com.vp.demo.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Playlist {
	private static final double TAGRelevanc = 1000d;

	private static final int LIKERelevanc = 1000;

	private static final int PLAYRelevanc = 10000;

	private static AtomicInteger idGenerator = new AtomicInteger();

	private int id;
	private String name;
	private AtomicInteger plays;
	private AtomicInteger likes;
	private Set<String> tags;

	public Playlist(String name) {
		id = idGenerator.incrementAndGet();
		this.name = name;
		tags = new HashSet<String>();
		plays = new AtomicInteger();
		likes = new AtomicInteger();
	}

	public Playlist(String name, int plays, int likes, List<String> pTags) {
		id = idGenerator.incrementAndGet();
		this.name = name;
		this.tags = new HashSet<String>();
		pTags.forEach(k -> tags.add(k.trim().toLowerCase()));
		this.plays = new AtomicInteger(plays);
		this.likes = new AtomicInteger(likes);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean addTag(String tag) {
		boolean added = false;
		if (tag != null && tag.trim() != "") {
			added = tags.add(tag.trim().toLowerCase());
		}
		return added;
	}

	public Set<String> getTags() {
		return tags;
	}

	public boolean removeTag(String tag) {
		return tags.remove(tag);
	}

	public void incrPlays() {
		plays.incrementAndGet();
	}

	public int getPlays() {
		return plays.intValue();
	}

	public void incrLikes() {
		likes.incrementAndGet();
	}

	public int getLikes() {
		return likes.intValue();
	}

	public Double getReleVancScore(Set<String> sTags) {
		double score = 0d;
		if (sTags != null && sTags.size() > 0) {
			for (String tag : sTags) {
				tag = tag.trim().toLowerCase();
				if (tags.contains(tag)) {
					score += TAGRelevanc;
				}
			}
		}
		score += this.likes.intValue() / LIKERelevanc;
		score += this.plays.intValue() / PLAYRelevanc;

		return score;
	}

	public void updateId() {
		id = idGenerator.incrementAndGet();
	}
}
