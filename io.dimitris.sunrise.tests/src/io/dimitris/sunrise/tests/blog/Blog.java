package io.dimitris.sunrise.tests.blog;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;

public class Blog {
	
	protected OrientBaseGraph graph = null;
	
	public Blog(OrientBaseGraph graph) {
		this.graph = graph;
	}
	
	public void createSchema() {
		OrientVertexType postVertexType = graph.createVertexType("Post");
		postVertexType.createProperty("title", OType.STRING);
		postVertexType.createEdgeProperty(Direction.OUT, "PostComments").setOrdered(true);
		postVertexType.createEdgeProperty(Direction.OUT, "PostAuthor").setOrdered(true);
		postVertexType.createEdgeProperty(Direction.OUT, "PostStats").setOrdered(true);
		OrientVertexType statsVertexType = graph.createVertexType("Stats");
		statsVertexType.createProperty("pageloads", OType.INTEGER);
		statsVertexType.createProperty("visitors", OType.INTEGER);
		OrientVertexType commentVertexType = graph.createVertexType("Comment");
		commentVertexType.createProperty("text", OType.STRING);
		commentVertexType.createEdgeProperty(Direction.OUT, "CommentAuthor").setOrdered(true);
		commentVertexType.createEdgeProperty(Direction.OUT, "CommentReplies").setOrdered(true);
		commentVertexType.createEdgeProperty(Direction.OUT, "CommentLiked").setOrdered(true);
		commentVertexType.createEdgeProperty(Direction.OUT, "CommentDisliked").setOrdered(true);
		OrientVertexType personVertexType = graph.createVertexType("Person");
		personVertexType.createProperty("name", OType.STRING);
		OrientVertexType authorVertexType = graph.createVertexType("Author");
		authorVertexType.createProperty("genre", OType.STRING);
		OrientVertexType memberVertexType = graph.createVertexType("Member");
		authorVertexType.setSuperClass(personVertexType);
		memberVertexType.setSuperClass(personVertexType);
		authorVertexType.createIndex("authorByName", OClass.INDEX_TYPE.UNIQUE, "name");
		authorVertexType.createIndex("authorByNameAndGenre", OClass.INDEX_TYPE.UNIQUE, "name", "genre");
	}
	
	public Post createPost() {
		return new Post(graph, graph.addVertex("class:Post"));
	}
	
	public Post.Iterable getPosts() {
		return new Post.Iterable(graph, graph.getVerticesOfClass("Post"));
	}
	
	public Stats createStats() {
		return new Stats(graph, graph.addVertex("class:Stats"));
	}
	
	public Stats.Iterable getStatss() {
		return new Stats.Iterable(graph, graph.getVerticesOfClass("Stats"));
	}
	
	public Comment createComment() {
		return new Comment(graph, graph.addVertex("class:Comment"));
	}
	
	public Comment.Iterable getComments() {
		return new Comment.Iterable(graph, graph.getVerticesOfClass("Comment"));
	}
	
	public Person createPerson() {
		return new Person(graph, graph.addVertex("class:Person"));
	}
	
	public Person.Iterable getPersons() {
		return new Person.Iterable(graph, graph.getVerticesOfClass("Person"));
	}
	
	public Author createAuthor() {
		return new Author(graph, graph.addVertex("class:Author"));
	}
	
	public Author.Iterable getAuthors() {
		return new Author.Iterable(graph, graph.getVerticesOfClass("Author"));
	}
	
	public Member createMember() {
		return new Member(graph, graph.addVertex("class:Member"));
	}
	
	public Member.Iterable getMembers() {
		return new Member.Iterable(graph, graph.getVerticesOfClass("Member"));
	}
	
	public Author findAuthorByName(String name) {
		java.lang.Iterable<Vertex> iterable = graph.command(new OCommandSQL("select from Author where name = ?")).execute(name);
		java.util.Iterator<Vertex> iterator = iterable.iterator();
		if (iterator.hasNext()) return new Author(graph, iterator.next());
		else return null;
	}
	public Author findAuthorByNameAndGenre(String name, String genre) {
		java.lang.Iterable<Vertex> iterable = graph.command(new OCommandSQL("select from Author where name = ? and genre = ?")).execute(name, genre);
		java.util.Iterator<Vertex> iterator = iterable.iterator();
		if (iterator.hasNext()) return new Author(graph, iterator.next());
		else return null;
	}
	
	
	public OrientBaseGraph getGraph() {
		return graph;
	}
}
