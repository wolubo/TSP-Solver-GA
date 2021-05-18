package de.wbongartz.tsp_solver_ga.genetic_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class IndividualTest {
	
	@Test
	public void testIndividualConstructor() {
		Individual i;
		try {
			String[] s = {};
			i = new Individual(s);
			fail("Allowed to construct an empty object! IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}

		try {
			String[] s = {"a", "b", "c"};
			i = new Individual(s);
			if(i.toString().compareTo("a --> b --> c")!=0) fail();
		} catch(Exception ex) {
			fail();
		}
	}

	@Test
	public void testHashCode() {
		Individual i;
		String[] s = {"a", "b", "c"};
		i = new Individual(s);
		try {
			i.hashCode();
		} catch(Exception ex) {
			fail();
		}
	}

	@Test
	public void testSize() {
		Individual i;
		String[] s = {"a", "b", "c"};
		i = new Individual(s);
		try {
			if(i.size()!=3) fail();
		} catch(Exception ex) {
			fail();
		}
	}

	@Test
	public void testGetGene() {
		Individual i;
		String[] s = {"a", "b", "c"};
		i = new Individual(s);

		try {
			if(i.getGene(0).compareTo("a")!=0) fail();
			if(i.getGene(1).compareTo("b")!=0) fail();
			if(i.getGene(2).compareTo("c")!=0) fail();
		} catch(Exception ex) {
			fail();
		}
		
		try {
			i.getGene(-1);
			fail("IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}
		
		try {
			i.getGene(3);
			fail("IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}
	}

	@Test
	public void testSetGene() {
		String[] s = {"a", "b", "c"};

		try {
			Individual i=new Individual(s);
			i.setGene(0, "x");
			if(i.toString().compareTo("x --> b --> c")!=0) fail();
			
			i.setGene(1, "y");
			if(i.toString().compareTo("x --> y --> c")!=0) fail();

			i.setGene(2, "z");
			if(i.toString().compareTo("x --> y --> z")!=0) fail();
		} catch(Exception ex) {
			fail();
		}
		
		try {
			Individual i=new Individual(s);
			i.setGene(-1, "x");
			fail("IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}
		
		try {
			Individual i=new Individual(s);
			i.setGene(4, "x");
			fail("IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}
	}

	@Test
	public void testToString() {
		Individual i;
		String[] s = {"a", "b", "c"};
		i = new Individual(s);
		try {
			if(i.toString().compareTo("a --> b --> c")!=0) fail();
		} catch(Exception ex) {
			fail();
		}
	}

	@Test
	public void testIterator() {
		Individual i;
		String[] s = {"a", "b", "c"};
		String o = "";
		i = new Individual(s);
		try {
			for(String gene: i) {
				o += gene;
			}
			if(o.compareTo("abc")!=0) fail();
		} catch(Exception ex) {
			fail();
		}
	}

	@Test
	public void testEqualsObject() {
		String[] s1 = {"a", "b", "c"};
		String[] s2 = {"a", "b", "c"};
		String[] s3 = {"x", "y", "z"};
		String[] s4 = {"a", "b", "c", "d"};
		
		Individual i1, i2;
		
		try {
			i1 = new Individual(s1);
			i2 = new Individual(s2);
			if(!i1.equals(i2)) fail();
			if(!i2.equals(i1)) fail();
		} catch(Exception ex) {
			fail();
		}

		try {
			i1 = new Individual(s1);
			i2 = new Individual(s3);
			if(i1.equals(i2)) fail();
			if(i2.equals(i1)) fail();
		} catch(Exception ex) {
			fail();
		}

		try {
			i1 = new Individual(s1);
			i2 = new Individual(s4);
			if(i1.equals(i2)) fail();
			if(i2.equals(i1)) fail();
		} catch(Exception ex) {
			fail();
		}
	}

	@Test
	public void testExchangeGeneSequence() {
		String[] s = {"a", "b", "c", "d", "e", "f"};
		String[] x = {"x", "y", "z"};

		try {
			Individual i = new Individual(s);
			i.exchangeGeneSequence(0, x);
			if(i.toString().compareTo("x --> y --> z --> d --> e --> f")!=0) fail();
		} catch(Exception ex) {
			fail();
		}

		try {
			Individual i = new Individual(s);
			i.exchangeGeneSequence(3, x);
			System.out.println(i.toString());
			if(i.toString().compareTo("a --> b --> c --> x --> y --> z")!=0) fail();
		} catch(Exception ex) {
			fail();
		}

		try {
			Individual i = new Individual(s);
			i.exchangeGeneSequence(2, x);
			if(i.toString().compareTo("a --> b --> x --> y --> z --> f")!=0) fail();
		} catch(Exception ex) {
			fail();
		}

		try {
			Individual i = new Individual(s);
			i.exchangeGeneSequence(-1, x);
			fail("IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}

		try {
			Individual i = new Individual(s);
			i.exchangeGeneSequence(4, x);
			fail("IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}

		try {
			String[] empty = {};
			Individual i = new Individual(s);
			i.exchangeGeneSequence(2, empty);
			fail("IllegalArgumentException expected!");
		} catch(IllegalArgumentException ex) {
		} catch(Exception ex) {
			fail("IllegalArgumentException expected!");
		}
	}

}
