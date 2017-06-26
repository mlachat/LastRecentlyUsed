package lru;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LastRecentlyUsedTest {

	
	private LastRecentlyUsed lru;

	@Before
	public void setup() {
		lru = new LastRecentlyUsed();
	}
	
	@Test
	public void initialCheckTest() throws Exception {
		assertNull(lru.getLastString());
	}
	
	@Test
	public void initialListIsEmptyCheck() throws Exception {
		assertTrue(lru.getAllStrings().isEmpty());
		assertThat(lru.getAllStrings(), org.hamcrest.collection.IsEmptyCollection.empty());
	}

	@Test
	public void lastStringIsReturned() throws Exception {
		lru.addString("blafasel");
		assertEquals("blafasel", lru.getLastString());
	}
	
	@Test
	public void lastStringComesFirstWhen2inserted() throws Exception {
		String lastStringInserted = "BartSimpson";
		lru.addString("HomerSimpson");
		lru.addString(lastStringInserted);
		assertEquals(lru.getLastString(), lastStringInserted);
	}
	
	@Test
	public void allStringsReturnedInCorrectOrder() throws Exception {
		String lastString = "BartSimpson";
		String firstString = "HomerSimpson";
		lru.addString(firstString);
		lru.addString(lastString);
		assertThat(lru.getAllStrings(), org.hamcrest.Matchers.contains(lastString, firstString));
	}
	
	@Test
	public void stringReturnedByPosition() throws Exception {
		String lastString = "BartSimpson";
		String firstString = "HomerSimpson";
		lru.addString(firstString);
		lru.addString(lastString);
		assertEquals(firstString, lru.getAtPosition(1));
		assertEquals(lastString, lru.getAtPosition(2));
	}
	
	@Test
	public void stringReturnedByPositionNullSafe() throws Exception {
		assertEquals(lru.getAtPosition(1), "");
	}
	
	@Test
	public void duplicateStringPushesOriginal() throws Exception {
		String lastString = "BartSimpson";
		String firstString = "HomerSimpson";
		lru.addString(firstString);
		lru.addString(lastString);
		lru.addString(firstString);

		assertThat(lru.getAllStrings(), org.hamcrest.Matchers.contains(firstString, lastString));
	}
	
	@Test(expected=NullNotAllowedException.class)
	public void addingNullValueIsNotSupported() throws Exception {
		lru.addString(null);
	}
	
	@Test
	public void maxCapacityRemovesOldestEntry() throws Exception {
		lru.setCapacity(10);
		String firstString = "HomerSimpson";
		for(int i = 0; i < 11; i++){
			lru.addString(firstString + " " + i);
		}
		assertEquals(firstString + " 1", lru.getAtPosition(1));
	} 
	
	@Test
	public void whenAddingADuplicateValueAtMaxCapacityItsFine() throws Exception {
		lru.setCapacity(10);
		String firstString = "HomerSimpson";
		for(int i = 0; i < 10; i++){
			lru.addString(firstString + " "+i);
		}
		lru.addString(firstString + " "+0);
		
		assertEquals(10, lru.getAllStrings().size());
	} 
}