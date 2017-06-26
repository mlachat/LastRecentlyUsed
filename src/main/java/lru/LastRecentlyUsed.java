package lru;

import java.util.ArrayList;
import java.util.List;

public class LastRecentlyUsed {

	private class Element {
		private String text;
		private Element next;
		private Element previous;
		private Element getPrevious() {
			return previous;
		}
		private void setPrevious(Element previous) {
			this.previous = previous;
		}
		private Element getNext() {
			return next;
		}
		private void setNext(Element next) {
			this.next = next;
		}
		private String getText() {
			return text;
		}
		public Element(String text) {
			super();
			this.text = text;
		}
	}
	
	private Element first;
	
	private List<String> theStrings = new ArrayList<String>();
	private int capacity = Integer.MAX_VALUE;

	public String getLastString() {
		return first != null? findLastElement().getText() : null;
	}

	public void addString(String string) throws NullNotAllowedException {
		if(string == null) throw new NullNotAllowedException();
		Element element = new Element(string);
		if(first == null) {
			first = element;
		} else {
			Element last = findLastElement();
			last.setNext(element);
			element.setPrevious(last);
		}
		
	}

	private Element findLastElement() {
		Element akuellesElement = first;
		while (akuellesElement != null && akuellesElement.getNext() != null){
			akuellesElement = akuellesElement.getNext();
		}
		return akuellesElement;
	}

	public List<String> getAllStrings() {
		
		List<String> result = new ArrayList<String>();
		if(first == null) return result;
		Element lastElement = findLastElement();
		while(lastElement != null){
			result.add(lastElement.getText());
			lastElement = lastElement.getPrevious();
		} 
		return result;
	}

	public String getAtPosition(int i) {
		int position = 1;
		if(first != null){
			Element akt = first;
			if (position == i) {
				return akt.getText();
			} 			
			do {
				if (position == i) {
					return akt.getText();
				} else {
					akt = akt.getNext();
					position++;
				}
			} while (akt != null);
		}
		return "";
	}

	public void setCapacity(int i) {
		this.capacity  = i;
	}

}