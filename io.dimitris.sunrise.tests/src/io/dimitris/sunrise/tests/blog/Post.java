
package io.dimitris.sunrise.tests.blog;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public  class Post extends Object {

	
	protected Vertex vertex = null;
	protected OrientBaseGraph graph = null;
	
	public Post(OrientBaseGraph graph, Vertex vertex) {
		this.vertex = vertex;
		this.graph = graph;
	}
	
	public static Post create(OrientBaseGraph graph, Vertex vertex) {
		OrientVertex orientVertex = (OrientVertex) vertex;
		return new Post(graph, vertex);
	}
	
	public String getTitle() {
		return vertex.getProperty("title");
	}
	
	public Post setTitle(String title) {
		vertex.setProperty("title", title);
		return this;
	}
	
	public String[] getTags() {
		return vertex.getProperty("tags");
	}
	
	public Post setTags(String[] tags) {
		vertex.setProperty("tags", tags);
		return this;
	}
	
	public int[] getRatings() {
		return vertex.getProperty("ratings");
	}
	
	public Post setRatings(int[] ratings) {
		vertex.setProperty("ratings", ratings);
		return this;
	}
	
	
	public Comment.Iterable getComments() {
		return new Comment.Iterable(graph, vertex.getVertices(Direction.OUT, "PostComments"));
	}
	
	public Post addComments(Comment... comments) {
		for (Comment o : comments) {
			vertex.addEdge("PostComments", o.getVertex());
		}
		return this;
	}
	
	public Post removeComments(Comment... comments) {
		for (Comment o : comments) {
			for (Edge e : vertex.getEdges(Direction.OUT, "PostComments")) {
				if (e.getVertex(Direction.IN).equals(o.getVertex())) graph.removeEdge(e);
			}
		}
		return this;
	}
	
	public Post clearComments() {
		for (Comment o : getComments()) {
			removeComments(o);
		}
		return this;
	}

	public Author getAuthor() {
		java.util.Iterator<Vertex> iterator = vertex.getVertices(Direction.OUT, "PostAuthor").iterator();
		if (iterator.hasNext()) return Author.create(graph, iterator.next());
		else return null;
	}
	
	public Post setAuthor(Author author) {
		unsetAuthor();
		vertex.addEdge("PostAuthor", author.getVertex());
		return this;
	}
	
	public Post unsetAuthor() {
		Author temp = getAuthor();
		for (Edge e : vertex.getEdges(Direction.OUT, "PostAuthor")) {
			graph.removeEdge(e);
		}
		return this;
	}
	
	public Stats getStats() {
		java.util.Iterator<Vertex> iterator = vertex.getVertices(Direction.OUT, "PostStats").iterator();
		if (iterator.hasNext()) return Stats.create(graph, iterator.next());
		else return null;
	}
	
	public Post setStats(Stats stats) {
		unsetStats();
		vertex.addEdge("PostStats", stats.getVertex());
		return this;
	}
	
	public Post unsetStats() {
		Stats temp = getStats();
		for (Edge e : vertex.getEdges(Direction.OUT, "PostStats")) {
			graph.removeEdge(e);
		}
		return this;
	}
	
	
	public Vertex getVertex() {
		return vertex;
	}
	
	public void delete() {
		graph.removeVertex(this.vertex);
	}
	
	public boolean equals(Object o) {
		return (o instanceof Post) && (vertex.equals(((Post)o).getVertex()));
	}
	
	public static class Iterable implements java.lang.Iterable<Post> {
		
		protected java.lang.Iterable<Vertex> iterable = null;
		protected OrientBaseGraph graph = null;
		
		public Iterable(OrientBaseGraph graph, java.lang.Iterable<Vertex> iterable) {
			this.iterable = iterable;
			this.graph = graph;
		}
		
		@Override
		public java.util.Iterator<Post> iterator() {
			return new Post.Iterator(graph, iterable.iterator());
		}
		
		public int size() {
			int size = 0;
			java.util.Iterator<?> iterator = iterator();
			while (iterator.hasNext()) { size++; iterator.next(); }
			return size;
		}
		
		public Post get(int i) {
			int index = 0;
			for (Post o : this) {
				if (index == i) return o;
				else index++;
			}
			return null;
		}
		
		public Post first() {
			return get(0);
		}
		
		public boolean contains(Post post) {
			for (Post it : this) {
				if (it.equals(post)) return true;
			}
			return false;
		}
		
	}
	
	public static class Iterator implements java.util.Iterator<Post> {
		
		protected java.util.Iterator<Vertex> iterator = null;
		protected OrientBaseGraph graph = null;
		
		public Iterator(OrientBaseGraph graph, java.util.Iterator<Vertex> iterator) {
			this.iterator = iterator;
			this.graph = graph;
		}
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Post next() {
			return create(graph, iterator.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}