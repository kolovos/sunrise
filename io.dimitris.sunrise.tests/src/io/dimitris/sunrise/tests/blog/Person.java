
package io.dimitris.sunrise.tests.blog;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public  class Person extends Object {

	
	protected Vertex vertex = null;
	protected OrientBaseGraph graph = null;
	
	public Person(OrientBaseGraph graph, Vertex vertex) {
		this.vertex = vertex;
		this.graph = graph;
	}
	
	public static Person create(OrientBaseGraph graph, Vertex vertex) {
		OrientVertex orientVertex = (OrientVertex) vertex;
		if (orientVertex.getRecord().getClassName().equals("Author")) return new Author(graph, vertex);
		if (orientVertex.getRecord().getClassName().equals("Member")) return new Member(graph, vertex);
		return new Person(graph, vertex);
	}
	
	public String getName() {
		return vertex.getProperty("name");
	}
	
	public Person setName(String name) {
		vertex.setProperty("name", name);
		return this;
	}
	
	

	
	public Vertex getVertex() {
		return vertex;
	}
	
	public void delete() {
		graph.removeVertex(this.vertex);
	}
	
	public boolean equals(Object o) {
		return (o instanceof Person) && (vertex.equals(((Person)o).getVertex()));
	}
	
	public static class Iterable implements java.lang.Iterable<Person> {
		
		protected java.lang.Iterable<Vertex> iterable = null;
		protected OrientBaseGraph graph = null;
		
		public Iterable(OrientBaseGraph graph, java.lang.Iterable<Vertex> iterable) {
			this.iterable = iterable;
			this.graph = graph;
		}
		
		@Override
		public java.util.Iterator<Person> iterator() {
			return new Person.Iterator(graph, iterable.iterator());
		}
		
		public int size() {
			int size = 0;
			java.util.Iterator<?> iterator = iterator();
			while (iterator.hasNext()) { size++; iterator.next(); }
			return size;
		}
		
		public Person get(int i) {
			int index = 0;
			for (Person o : this) {
				if (index == i) return o;
				else index++;
			}
			return null;
		}
		
		public Person first() {
			return get(0);
		}
		
		public boolean contains(Person person) {
			for (Person it : this) {
				if (it.equals(person)) return true;
			}
			return false;
		}
		
	}
	
	public static class Iterator implements java.util.Iterator<Person> {
		
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
		public Person next() {
			return create(graph, iterator.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}