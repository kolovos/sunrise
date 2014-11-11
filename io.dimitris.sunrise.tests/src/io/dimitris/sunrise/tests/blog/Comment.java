
package io.dimitris.sunrise.tests.blog;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public  class Comment extends Object {

	
	protected Vertex vertex = null;
	protected OrientBaseGraph graph = null;
	
	public Comment(OrientBaseGraph graph, Vertex vertex) {
		this.vertex = vertex;
		this.graph = graph;
	}
	
	public static Comment create(OrientBaseGraph graph, Vertex vertex) {
		OrientVertex orientVertex = (OrientVertex) vertex;
		return new Comment(graph, vertex);
	}
	
	public String getText() {
		return vertex.getProperty("text");
	}
	
	public Comment setText(String text) {
		vertex.setProperty("text", text);
		return this;
	}
	
	
	public Comment.Iterable getReplies() {
		return new Comment.Iterable(graph, vertex.getVertices(Direction.OUT, "CommentReplies"));
	}
	
	public Comment addReplies(Comment... replies) {
		for (Comment o : replies) {
			vertex.addEdge("CommentReplies", o.getVertex());
		}
		return this;
	}
	
	public Comment removeReplies(Comment... replies) {
		for (Comment o : replies) {
			for (Edge e : vertex.getEdges(Direction.OUT, "CommentReplies")) {
				if (e.getVertex(Direction.IN).equals(o.getVertex())) graph.removeEdge(e);
			}
		}
		return this;
	}
	
	public Comment clearReplies() {
		for (Comment o : getReplies()) {
			removeReplies(o);
		}
		return this;
	}
	public Member.Iterable getLiked() {
		return new Member.Iterable(graph, vertex.getVertices(Direction.OUT, "CommentLiked"));
	}
	
	public Comment addLiked(Member... liked) {
		for (Member o : liked) {
			vertex.addEdge("CommentLiked", o.getVertex());
		}
		return this;
	}
	
	public Comment removeLiked(Member... liked) {
		for (Member o : liked) {
			for (Edge e : vertex.getEdges(Direction.OUT, "CommentLiked")) {
				if (e.getVertex(Direction.IN).equals(o.getVertex())) graph.removeEdge(e);
			}
		}
		return this;
	}
	
	public Comment clearLiked() {
		for (Member o : getLiked()) {
			removeLiked(o);
		}
		return this;
	}
	public Member.Iterable getDisliked() {
		return new Member.Iterable(graph, vertex.getVertices(Direction.OUT, "CommentDisliked"));
	}
	
	public Comment addDisliked(Member... disliked) {
		for (Member o : disliked) {
			vertex.addEdge("CommentDisliked", o.getVertex());
		}
		return this;
	}
	
	public Comment removeDisliked(Member... disliked) {
		for (Member o : disliked) {
			for (Edge e : vertex.getEdges(Direction.OUT, "CommentDisliked")) {
				if (e.getVertex(Direction.IN).equals(o.getVertex())) graph.removeEdge(e);
			}
		}
		return this;
	}
	
	public Comment clearDisliked() {
		for (Member o : getDisliked()) {
			removeDisliked(o);
		}
		return this;
	}

	public Author getAuthor() {
		java.util.Iterator<Vertex> iterator = vertex.getVertices(Direction.OUT, "CommentAuthor").iterator();
		if (iterator.hasNext()) return Author.create(graph, iterator.next());
		else return null;
	}
	
	public Comment setAuthor(Author author) {
		unsetAuthor();
		vertex.addEdge("CommentAuthor", author.getVertex());
		return this;
	}
	
	public Comment unsetAuthor() {
		Author temp = getAuthor();
		for (Edge e : vertex.getEdges(Direction.OUT, "CommentAuthor")) {
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
		return (o instanceof Comment) && (vertex.equals(((Comment)o).getVertex()));
	}
	
	public static class Iterable implements java.lang.Iterable<Comment> {
		
		protected java.lang.Iterable<Vertex> iterable = null;
		protected OrientBaseGraph graph = null;
		
		public Iterable(OrientBaseGraph graph, java.lang.Iterable<Vertex> iterable) {
			this.iterable = iterable;
			this.graph = graph;
		}
		
		@Override
		public java.util.Iterator<Comment> iterator() {
			return new Comment.Iterator(graph, iterable.iterator());
		}
		
		public int size() {
			int size = 0;
			java.util.Iterator<?> iterator = iterator();
			while (iterator.hasNext()) { size++; iterator.next(); }
			return size;
		}
		
		public Comment get(int i) {
			int index = 0;
			for (Comment o : this) {
				if (index == i) return o;
				else index++;
			}
			return null;
		}
		
		public Comment first() {
			return get(0);
		}
		
		public boolean contains(Comment comment) {
			for (Comment it : this) {
				if (it.equals(comment)) return true;
			}
			return false;
		}
		
	}
	
	public static class Iterator implements java.util.Iterator<Comment> {
		
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
		public Comment next() {
			return create(graph, iterator.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}