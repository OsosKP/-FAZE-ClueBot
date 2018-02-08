package cluedo_game;

public class playerList {
	
	private node firstNode = null;
	private node lastNode = null;
	private node currentNode;
	
	public void addFirst(Token player) {
		node exampleNode = new node(player);
		if (firstNode == null) {
			firstNode = exampleNode;
			lastNode = exampleNode;
			lastNode.nextNode = firstNode;
			
			firstNode.setNext(null);
		}
		else {
			exampleNode.nextNode = firstNode;
			firstNode = exampleNode;
			lastNode.nextNode = firstNode;
		}
	}
	
	public void insertRear(Token player) {
		node rearNode = new node(player);
		node currentNode = firstNode;
		if (lastNode == null) {
			lastNode = rearNode;
			firstNode = rearNode;
			firstNode.nextNode = lastNode;
			lastNode.nextNode = firstNode;
		}
		else {
			while (currentNode != null) {
				currentNode = currentNode.nextNode;
			}	
			rearNode.nextNode = null;
			currentNode.nextNode = rearNode;

			lastNode = rearNode; //re-setting the last link to our new node
		}
	}
	
	public void removeFrount() {
		if (firstNode.nextNode != lastNode && firstNode.nextNode != null) {
			firstNode = firstNode.nextNode;
		}
		else if (firstNode.nextNode == lastNode) {
			firstNode = lastNode;
		}
		else if (firstNode == lastNode) {
			firstNode = null;
			lastNode = null;
		}
		else {
			System.out.println("Unable to remove link! List is currently empty!");
		}
	}
	
	public void removeRear() {
		if (lastNode == firstNode) {
			lastNode = null;
			firstNode = null;
		}
		else if (lastNode != null) {
			
			node currentNode = firstNode;
			
			while (currentNode.nextNode != lastNode) {
				currentNode = currentNode.nextNode;
			}
			lastNode = currentNode;
		}
		else {
			System.out.println("Unable to remove link! List is currently emtpy");
		}
	}
	
	public Token moveToNext() {
		 currentNode = currentNode.nextNode;
		 return currentNode.player;
	}
	
	playerList(){
		//nothing
	}
	
	class node{
		private Token player;
		private node nextNode;
		
		node(Token tmp){
			tmp = this.player;
		}
		public void setNext(node nextNode) {
			this.nextNode = nextNode;
		}
		public node getNext() {
			return nextNode;
		}
	}
	
}
