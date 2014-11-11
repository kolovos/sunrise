
package io.dimitris.sunrise.tests.blog;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public  class Stats extends Object {

	
	protected Vertex vertex = null;
	protected OrientBaseGraph graph = null;
	
	public Stats(OrientBaseGraph graph, Vertex vertex) {
		this.vertex = vertex;
		this.graph = graph;
	}
	
	public static Stats create(OrientBaseGraph graph, Vertex vertex) {
		OrientVertex orientVertex = (OrientVertex) vertex;
		return new Stats(graph, vertex);
	}
	
	public int getPageloads() {
		return vertex.getProperty("pageloads");
	}
	
	public Stats setPageloads(int pageloads) {
		vertex.setProperty("pageloads", pageloads);
		return this;
	}
	
	public int getVisitors() {
		return vertex.getProperty("visitors");
	}
	
	public Stats setVisitors(int visitors) {
		vertex.setProperty("visitors", visitors);
		return this;
	}
	
	

	
	public Vertex getVertex() {
		return vertex;
	}
	
	public void delete() {
		graph.removeVertex(this.vertex);
	}
	
	public boolean equals(Object o) {
		return (o instanceof Stats) && (vertex.equals(((Stats)o).getVertex()));
	}
	
	public static class Iterable implements java.lang.Iterable<Stats> {
		
		protected java.lang.Iterable<Vertex> iterable = null;
		protected OrientBaseGraph graph = null;
		
		public Iterable(OrientBaseGraph graph, java.lang.Iterable<Vertex> iterable) {
			this.iterable = iterable;
			this.graph = graph;
		}
		
		@Override
		public java.util.Iterator<Stats> iterator() {
			return new Stats.Iterator(graph, iterable.iterator());
		}
		
		public int size() {
			int size = 0;
			java.util.Iterator<?> iterator = iterator();
			while (iterator.hasNext()) { size++; iterator.next(); }
			return size;
		}
		
		public Stats get(int i) {
			int index = 0;
			for (Stats o : this) {
				if (index == i) return o;
				else index++;
			}
			return null;
		}
		
		public Stats first() {
			return get(0);
		}
		
		public boolean contains(Stats stats) {
			for (Stats it : this) {
				if (it.equals(stats)) return true;
			}
			return false;
		}
		
	}
	
	public static class Iterator implements java.util.Iterator<Stats> {
		
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
		public Stats next() {
			return create(graph, iterator.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}