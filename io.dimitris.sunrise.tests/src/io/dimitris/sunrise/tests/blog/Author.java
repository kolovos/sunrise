
package io.dimitris.sunrise.tests.blog;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public  class Author extends Person {

	
	public Author(OrientBaseGraph graph, Vertex vertex) {
		super(graph, vertex);
	}
	
	public static Author create(OrientBaseGraph graph, Vertex vertex) {
		OrientVertex orientVertex = (OrientVertex) vertex;
		return new Author(graph, vertex);
	}
	
	public String getGenre() {
		return vertex.getProperty("genre");
	}
	
	public Author setGenre(String genre) {
		vertex.setProperty("genre", genre);
		return this;
	}
	
	

	
	public Vertex getVertex() {
		return vertex;
	}
	
	
	public boolean equals(Object o) {
		return (o instanceof Author) && (vertex.equals(((Author)o).getVertex()));
	}
	
	public static class Iterable implements java.lang.Iterable<Author> {
		
		protected java.lang.Iterable<Vertex> iterable = null;
		protected OrientBaseGraph graph = null;
		
		public Iterable(OrientBaseGraph graph, java.lang.Iterable<Vertex> iterable) {
			this.iterable = iterable;
			this.graph = graph;
		}
		
		@Override
		public java.util.Iterator<Author> iterator() {
			return new Author.Iterator(graph, iterable.iterator());
		}
		
		public int size() {
			int size = 0;
			java.util.Iterator<?> iterator = iterator();
			while (iterator.hasNext()) { size++; iterator.next(); }
			return size;
		}
		
		public Author get(int i) {
			int index = 0;
			for (Author o : this) {
				if (index == i) return o;
				else index++;
			}
			return null;
		}
		
		public Author first() {
			return get(0);
		}
		
		public boolean contains(Author author) {
			for (Author it : this) {
				if (it.equals(author)) return true;
			}
			return false;
		}
		
	}
	
	public static class Iterator implements java.util.Iterator<Author> {
		
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
		public Author next() {
			return create(graph, iterator.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}