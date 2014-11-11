package io.dimitris.sunrise.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.dimitris.sunrise.tests.blog.Author;
import io.dimitris.sunrise.tests.blog.Blog;
import io.dimitris.sunrise.tests.blog.Comment;
import io.dimitris.sunrise.tests.blog.Post;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLQuery;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import static org.junit.Assert.*;

public class SunriseTests {
	
	protected static Blog blog = null;
	protected static OrientGraph graph = null;
	
	@BeforeClass
	public static void setup() throws Exception {
		File file = new File("blog-db");
		if (file.exists()) delete(file);
		graph = new OrientGraph("plocal:" + file.getAbsolutePath(), false);
		blog = new Blog(graph);
		blog.createSchema();
		graph.getRawGraph().begin();
	}
	
	@Test
	public void testPost() {
		Post post = blog.createPost();
		post.setTitle("Post 1");
		assertEquals(post.getTitle(), blog.getPosts().iterator().next().getTitle());
	}
	
	@Test
	public void testSingleKeyIndex() {
		for (int i=0;i<1000;i++) {
			Author author = blog.createAuthor();
			author.setName("Author" + i);
			author.setGenre("SciFi");
		}
		assertNotNull(blog.findAuthorByName("Author400"));
	}
	
	@Test
	public void testMultiKeyIndex() {
		for (int i=0;i<1000;i++) {
			Author author = blog.createAuthor();
			author.setName("Author" + i);
			author.setGenre("SciFi");
		}
		System.out.println(blog.findAuthorByNameAndGenre("Author400", "SciFi").getName());
		assertNotNull(blog.findAuthorByNameAndGenre("Author400", "SciFi"));		
	}
	
	@Test
	public void testRollback() {
		assertFalse(blog.getPosts().iterator().hasNext());
	}
	
	@Test
	public void testComment() {
		Post post = blog.createPost();
		Comment comment = blog.createComment();
		post.setTitle("Post 1");
		comment.setText("Comment 1");
		post.addComments(comment);
		assertEquals(comment.getText(), blog.getPosts().first().getComments().first().getText());
		//post.removeComments(comment);
	}
	
	@Test
	public void testAuthor() {
		Post post = blog.createPost();
		Author author = blog.createAuthor();
		post.setAuthor(author);
		assertEquals(author, post.getAuthor());
		Author author2 = blog.createAuthor();
		post.setAuthor(author2);
		assertEquals(author2, post.getAuthor());
		post.unsetAuthor();
		assertNull(post.getAuthor());
	}
	
	@Test
	public void testTags() {
		Post post = blog.createPost();
		String[] tags = new String[]{"tag1", "tag2"};
		post.setTags(tags);
		assertArrayEquals(tags, post.getTags());
	}
	
	@After
	public void teardown() {
		graph.rollback();
		graph.getRawGraph().begin();
	}
	
	static void delete(File f) throws IOException {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  if (!f.delete())
		    throw new FileNotFoundException("Failed to delete file: " + f);
		}
	
}
