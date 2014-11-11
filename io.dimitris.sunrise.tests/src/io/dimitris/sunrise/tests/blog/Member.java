
package io.dimitris.sunrise.tests.blog;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public  class Member extends Person {

	
	public Member(OrientBaseGraph graph, Vertex vertex) {
		super(graph, vertex);
	}
	
	public static Member create(OrientBaseGraph graph, Vertex vertex) {
		OrientVertex orientVertex = (OrientVertex) vertex;
		return new Member(graph, vertex);
	}
	
	

	
	public Vertex getVertex() {
		return vertex;
	}
	
	
	public boolean equals(Object o) {
		return (o instanceof Member) && (vertex.equals(((Member)o).getVertex()));
	}
	
	public static class Iterable implements java.lang.Iterable<Member> {
		
		protected java.lang.Iterable<Vertex> iterable = null;
		protected OrientBaseGraph graph = null;
		
		public Iterable(OrientBaseGraph graph, java.lang.Iterable<Vertex> iterable) {
			this.iterable = iterable;
			this.graph = graph;
		}
		
		@Override
		public java.util.Iterator<Member> iterator() {
			return new Member.Iterator(graph, iterable.iterator());
		}
		
		public int size() {
			int size = 0;
			java.util.Iterator<?> iterator = iterator();
			while (iterator.hasNext()) { size++; iterator.next(); }
			return size;
		}
		
		public Member get(int i) {
			int index = 0;
			for (Member o : this) {
				if (index == i) return o;
				else index++;
			}
			return null;
		}
		
		public Member first() {
			return get(0);
		}
		
		public boolean contains(Member member) {
			for (Member it : this) {
				if (it.equals(member)) return true;
			}
			return false;
		}
		
	}
	
	public static class Iterator implements java.util.Iterator<Member> {
		
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
		public Member next() {
			return create(graph, iterator.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}