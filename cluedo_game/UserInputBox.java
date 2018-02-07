package cluedo_game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInputBox {
    final int FIELD_WIDTH = 10;
    JTextField input = new JTextField(FIELD_WIDTH);
    JLabel promptLabel = new JLabel("What would you like to do?");

    class UserInputListener_FloorSquare implements ActionListener {
        public void actionPerformed(ActionEvent event){
            String inputString = input.getText().toLowerCase();
            if((AcceptedUserInputs.getFloorNavigation().contains(inputString))){

            }


        }
    }

}
