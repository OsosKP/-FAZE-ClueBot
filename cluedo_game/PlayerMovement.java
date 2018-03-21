package cluedo_game;

/**
 * This method receives instructions from the user input and moves the player's token accordingly
 */

//public class PlayerMovement {
//    public static boolean movementCheck(Board board, Token player, String move){
//        BoardSquare square = player.getSquare(board);
//        switch(move){
//            case "up":
//                /*
//                Checks:
//                Player isn't at the top of the board
//                The square above the player is traversable (a FloorSquare or EntrySquare)
//                There is no player on the above square
//                 */
//                if((square.getAbove() instanceof FloorSquare || square.getAbove() instanceof EntrySquare)
//                        && !(square.isPlayerOn())){
//                    square.getAbove().setPlayerOn(player);
//                    square.removePlayerOn();
//                    return true;
//            }
//            default:
//                System.out.println("Error");
//        }
//        return false;
//    }
//
//}
