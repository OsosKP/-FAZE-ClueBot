package cluedo_game;

public class PlayerList {

	//Changed to Circular Array because there's always gonna only be 8 players
    private Token[] players = new Token[8];//You can only have a max of 8
    private int turn = 0; //Starts at 0, but you can change who goes first by changing this int
	private int rear = 0;

	public void addPlayer(Token player){
        if (size()==7){//If it makes the array full by adding another elemnt (breaking the circle)
            System.out.println("Something's wrong, array's full");
        }
        else{
            players[rear]=player;//Adds object
            rear++;//Move rear index foward
        }
    }

	public Token removePlayer(int playerIndex){
		if (playerIndex==7){
			players[7]=null;
		}
		else{
			for (int x=playerIndex;x<rear-1;x++){//Start at index, move everything after down
				players[x]=players[x+1];
			}
		}
		rear--;
	}

	public Token getNext(Token current) {return current;}
	public String toString(){
        String printstring = "";//Set up the print string
        for (int i=0;i<size()+1;i++) printstring += players[i];//No need to say .toString because most objects have that property
        return printstring;
    }
	public boolean isEmpty(){return rear!=0;}
	public int size(){ return rear;}

	//

	//
	// private node firstNode = null;
	// private node lastNode = null;
	// private node currentNode;
	//
	// public void addFirst(Token player) {
	// 	node exampleNode = new node(player);
	// 	if (firstNode == null) {
	// 		firstNode = exampleNode;
	// 		lastNode = exampleNode;
	// 		lastNode.nextNode = firstNode;
	//
	// 		firstNode.setNext(null);
	// 	}
	// 	else {
	// 		exampleNode.nextNode = firstNode;
	// 		firstNode = exampleNode;
	// 		lastNode.nextNode = firstNode;
	// 	}
	// }
	//
	// public void insertRear(Token player) {
	// 	node rearNode = new node(player);
	// 	node currentNode = firstNode;
	// 	if (lastNode == null) {
	// 		lastNode = rearNode;
	// 		firstNode = rearNode;
	// 		firstNode.nextNode = lastNode;
	// 		lastNode.nextNode = firstNode;
	// 	}
	// 	else {
	// 		while (currentNode != null) {
	// 			currentNode = currentNode.nextNode;
	// 		}
	// 		rearNode.nextNode = null;
	// 		currentNode.nextNode = rearNode;
	//
	// 		lastNode = rearNode; //re-setting the last link to our new node
	// 	}
	// }
	//
	// public void removeFront() {
	// 	if (firstNode.nextNode != lastNode && firstNode.nextNode != null) {
	// 		firstNode = firstNode.nextNode;
	// 	}
	// 	else if (firstNode.nextNode == lastNode) {
	// 		firstNode = lastNode;
	// 	}
	// 	else if (firstNode == lastNode) {
	// 		firstNode = null;
	// 		lastNode = null;
	// 	}
	// 	else {
	// 		System.out.println("Unable to remove link! List is currently empty!");
	// 	}
	// }
	//
	// public void removeRear() {
	// 	if (lastNode == firstNode) {
	// 		lastNode = null;
	// 		firstNode = null;
	// 	}
	// 	else if (lastNode != null) {
	//
	// 		node currentNode = firstNode;
	//
	// 		while (currentNode.nextNode != lastNode) {
	// 			currentNode = currentNode.nextNode;
	// 		}
	// 		lastNode = currentNode;
	// 	}
	// 	else {
	// 		System.out.println("Unable to remove link! List is currently emtpy");
	// 	}
	// }
	//
	// public Token moveToNext() {
	// 	 currentNode = currentNode.nextNode;
	// 	 return currentNode.player;
	// }
	//
	// playerList(){
	// 	//nothing
	// }
	//
	// class node{
	// 	private Token player;
	// 	private node nextNode;
	//
	// 	node(Token tmp){
	// 		tmp = this.player;
	// 	}
	// 	public void setNext(node nextNode) {
	// 		this.nextNode = nextNode;
	// 	}
	// 	public node getNext() {
	// 		return nextNode;
	// 	}
	// }

}
