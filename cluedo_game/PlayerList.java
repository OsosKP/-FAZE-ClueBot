// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public class PlayerList {

	//Changed to Circular Array because there's always gonna only be 8 players
    private Token[] players = new Token[8];//You can only have a max of 8
    private int turn = 0; //Starts at 0, but you can change who goes first by changing this int
	private int rear = 0;

	public void addPlayer(Token player){
        if (size()==7){//If it makes the array full by adding another element (breaking the circle)
            System.out.println("Something's wrong, array's full");
        }
        else{
            players[rear]=player;//Adds object
            rear++;//Move rear index forward
        }
    }

	
//	public Token removePlayer(int playerIndex){
//		if (playerIndex==7){
//			players[7]=null;
//		}
//		else{
//			for (int x=playerIndex; x<rear-1; x++){//Start at index, move everything after down
//				players[x]=players[x+1];
//			}
//		}
//		rear--;
//	}

	public Token getNext(Token current) {return current;}
	public String toString(){
        String printstring = "";//Set up the print string
        for (int i=0;i<size()+1;i++) printstring += players[i];//No need to say .toString because most objects have that property
        return printstring;
    }
	public boolean isEmpty(){return rear!=0;}
	public int size(){ return rear;}
}
