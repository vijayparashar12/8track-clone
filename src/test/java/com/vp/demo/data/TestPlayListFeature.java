package com.vp.demo.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPlayListFeature {

	@Test
	public void testIDgeneration() {
		Playlist p = new Playlist("first");
		assertEquals(1, p.getId());

		Playlist q = new Playlist("Second");
		assertEquals(2, q.getId());
	}
	
	@Test
	public void testLikes() throws Exception {
		Playlist p = new Playlist("first");
		p.incrLikes();
		p.incrLikes();
		assertEquals(2, p.getLikes());
	}
	

}
