package cluedo_game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CardImages {
    // Arrays to hold cards indexed to the same as strings below
    private static ImageIcon[] characters;
    private static ImageIcon[] weapons;
    private static ImageIcon[] rooms;
    //Arrays to hold cards with type BufferedImage
    private static BufferedImage[] characterBuffer;
    private static BufferedImage[] weaponsBuffer;
    private static BufferedImage[] roomsBuffer;
    
    // Lookup strings
    private static String[] characterStrings =
            {"Green", "Mustard", "Peacock", "Plum", "Scarlet", "White"};
    private static String[] weaponStrings =
            {"Candlestick", "Dagger", "Pipe", "Pistol", "Rope", "Wrench"};
    private static String[] roomStrings =
            {"ballroom", "billiardroom", "conservatory", "diningroom",
                    "hall", "kitchen", "library", "lounge", "study"};

    public CardImages() {
        characters = new ImageIcon[12];
        weapons = new ImageIcon[12];
        rooms = new ImageIcon[18];
        
        characterBuffer = new BufferedImage[12];
        weaponsBuffer = new BufferedImage[12];
        roomsBuffer = new BufferedImage[12];
        try {
            loadImages();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void loadImages() throws Exception {
        BufferedImage charTemp;
        BufferedImage wpnTemp;
        BufferedImage rmTemp;

        for (int i=0; i<9; i++) {
            // First, load color images
        	rmTemp = ImageIO.read(CardImages.class.getResource("/roomCards/" + roomStrings[i] + ".jpeg"));
            rooms[i] = new ImageIcon(rmTemp);
            roomsBuffer[i] = rmTemp;
            
            if (i < 6) {
                charTemp = ImageIO.read(CardImages.class.getResource("/characterCards/" + characterStrings[i] + ".png"));
                characters[i] = new ImageIcon(charTemp);
                characterBuffer[i] = charTemp;
                
                wpnTemp = ImageIO.read(CardImages.class.getResource("/weaponCards/" + weaponStrings[i] + ".png"));
                weapons[i] = new ImageIcon(wpnTemp);
                weaponsBuffer[i] = wpnTemp;
            }
            
            // Then load black-and-white images
            int j = i + 6;
            rmTemp = ImageIO.read(CardImages.class.getResource("/roomCards/" + roomStrings[i] + "b&w.jpeg"));
            rooms[j + 3] = new ImageIcon(rmTemp);
            roomsBuffer[j+ 3] = rmTemp;
            
            if (i < 6) {
                charTemp = ImageIO.read(CardImages.class.getResource("/characterCards/" + characterStrings[i] + "B&W.png"));
                characters[j] = new ImageIcon(charTemp);
                characterBuffer[j] = charTemp;
                
                wpnTemp = ImageIO.read(CardImages.class.getResource("/weaponCards/" + weaponStrings[i] + "B&W.png"));
                weapons[j] = new ImageIcon(wpnTemp);
                weaponsBuffer[j] = wpnTemp;
              
            }
        }
    }
    
    //TODO: Kelsey add the NA cards to this -- otherwise they just wont load

    public static ImageIcon[] getCharacters() {
        return characters;
    }

    public static ImageIcon[] getWeapons() {
        return weapons;
    }

    public static ImageIcon[] getRooms() {
        return rooms;
    }

    public static ImageIcon getCharacterByIndex(int i) {
        return characters[i];
    }

    public static ImageIcon getWeaponsByIndex(int i) {
        return weapons[i];
    }

    public static ImageIcon getRoomsByIndex(int i) {
        return rooms[i];
    }

    public static ImageIcon getGreen() {
        return characters[0];
    }
    
    public static BufferedImage getBufferedGreen() {
    	return characterBuffer[0];
    }

    public static ImageIcon getMustard() {
        return characters[1];
    }
    
    public static BufferedImage getBufferedMustard() {
    	return characterBuffer[1];
    }

    public static ImageIcon getPeacock() {
        return characters[2];
    }
    
    public static BufferedImage getBufferedPeacock() {
    	return characterBuffer[2];
    }

    public static ImageIcon getPlum() {
        return characters[3];
    }
    
    public static BufferedImage getBufferedPlum() {
    	return characterBuffer[3];
    }

    public static ImageIcon getScarlet() {
        return characters[4];
    }
    
    public static BufferedImage getBufferedScarlet() {
    	return characterBuffer[4];
    }

    public static ImageIcon getWhite() {
        return characters[5];
    }
    
    public static BufferedImage getBufferedWhite() {
    	return characterBuffer[5];
    }

    public static ImageIcon getGreenbw() {
        return characters[6];
    }
    
    public static BufferedImage getBufferedGreenbw() {
    	return characterBuffer[6];
    }

    public static ImageIcon getMustardbw() {
        return characters[7];
    }
    
    public static BufferedImage getBufferedMustardbw() {
    	return characterBuffer[7];
    }

    public static ImageIcon getPeacockbw() {
        return characters[8];
    }
    
    public static BufferedImage getBufferedPeacockbw() {
    	return characterBuffer[8];
    }

    public static ImageIcon getPlumbw() {
        return characters[9];
    }
    
    public static BufferedImage getBufferedPlumbw() {
    	return characterBuffer[9];
    }

    public static ImageIcon getScarletbw() {
        return characters[10];
    }
    
    public static BufferedImage getBufferedScarletbw() {
    	return characterBuffer[10];
    }

    public static ImageIcon getWhitebw() {
        return characters[11];
    }
    
    public static BufferedImage getBufferedWhitebw() {
    	return characterBuffer[11];
    }

    public static ImageIcon getCandlestick() {
        return weapons[0];
    }
    
    public static BufferedImage getBufferedCandlestick() {
    	return weaponsBuffer[0];
    }

    public static ImageIcon getDagger() {
        return weapons[1];
    }
    
    public static BufferedImage getBufferedDagger() {
    	return weaponsBuffer[1];
    }

    public static ImageIcon getPipe() {
        return weapons[2];
    }
    
    public static BufferedImage getBufferedPipe() {
    	return weaponsBuffer[2];
    }

    public static ImageIcon getPistol() {
        return weapons[3];
    }
    
    public static BufferedImage getBufferedPistol() {
    	return weaponsBuffer[3];
    }

    public static ImageIcon getRope() {
        return weapons[4];
    }
    
    public static BufferedImage getBufferedRope() {
    	return weaponsBuffer[4];
    }

    public static ImageIcon getWrench() {
        return weapons[5];
    }
    
    public static BufferedImage getBufferedWrench() {
    	return weaponsBuffer[5];
    }

    public static ImageIcon getCandlestickbw() {
        return weapons[6];
    }
    
    public static BufferedImage getBufferedCandlestickbw() {
    	return weaponsBuffer[6];
    }

    public static ImageIcon getDaggerbw() {
        return weapons[7];
    }
    
    public static BufferedImage getBufferedDaggerbw() {
    	return weaponsBuffer[7];
    }
    
    public static ImageIcon getPipebw() {
        return weapons[8];
    }
    
    public static BufferedImage getBufferedPipebw() {
    	return weaponsBuffer[8];
    }

    public static ImageIcon getPistolbw() {
        return weapons[9];
    }
    
    public static BufferedImage getBufferedPistolbw() {
    	return weaponsBuffer[9];
    }

    public static ImageIcon getRopebw() {
        return weapons[10];
    }
    
    public static BufferedImage getBufferedRopebw() {
    	return weaponsBuffer[10];
    }

    public static ImageIcon getWrenchbw() {
        return weapons[11];
    }
    
    public static BufferedImage getBufferedWrenchbw() {
    	return weaponsBuffer[11];
    }

    public static ImageIcon getBallroom() {
        return rooms[0];
    }
    
    public static BufferedImage getBufferedBallroom() {
    	return roomsBuffer[0];
    }

    public static ImageIcon getBilliardroom() {
        return rooms[1];
    }
    
    public static BufferedImage getBufferedBilliardroom() {
    	return roomsBuffer[1];
    }

    public static ImageIcon getConservatory() {
        return rooms[2];
    }
    
    public static BufferedImage getBufferedConservatory() {
    	return roomsBuffer[2];
    }

    public static ImageIcon getDiningroom() {
        return rooms[3];
    }
    
    public static BufferedImage getBufferedDiningroom() {
    	return roomsBuffer[3];
    }

    public static ImageIcon getHall() {
        return rooms[4];
    }
    
    public static BufferedImage getBufferedHall() {
    	return roomsBuffer[4];
    }

    public static ImageIcon getKitchen() {
        return rooms[5];
    }
    
    public static BufferedImage getBufferedKitchen() {
    	return roomsBuffer[5];
    }

    public static ImageIcon getLibrary() {
        return rooms[6];
    }
    
    public static BufferedImage getBufferedLibrary() {
    	return roomsBuffer[6];
    }

    public static ImageIcon getLounge() {
        return rooms[7];
    }
    
    public static BufferedImage getBufferedLounge() {
    	return roomsBuffer[7];
    }

    public static ImageIcon getStudy() {
        return rooms[8];
    }
    
    public static BufferedImage getBufferedStudy() {
    	return roomsBuffer[8];
    }

    public static ImageIcon getBallroombw() {
        return rooms[9];
    }
    
    public static BufferedImage getBufferedBallroombw() {
    	return roomsBuffer[9];
    }

    public static ImageIcon getBilliardroombw() {
        return rooms[10];
    }
    
    public static BufferedImage getBufferedBiilliardRoombw() {
    	return roomsBuffer[10];
    }

    public static ImageIcon getConservatorybw() {
        return rooms[11];
    }
    
    public static BufferedImage getBufferedConservatorybw() {
    	return roomsBuffer[11];
    }

    public static ImageIcon getDiningroombw() {
        return rooms[12];
    }
    
    public static BufferedImage getBufferedDiningroombw() {
    	return roomsBuffer[12];
    }

    public static ImageIcon getHallbw() {
        return rooms[13];
    }
    
    public static BufferedImage getBufferedHallbw() {
    	return roomsBuffer[13];
    }

    public static ImageIcon getKitchenbw() {
        return rooms[14];
    }
    
    public static BufferedImage getBufferedKitchenbw() {
    	return roomsBuffer[14];
    }

    public static ImageIcon getLibrarybw() {
        return rooms[15];
    }
    
    public static BufferedImage getBufferedLibrarybw() {
    	return roomsBuffer[15];
    }

    public static ImageIcon getLoungebw() {
        return rooms[16];
    }
    
    public static BufferedImage getBufferedLoungebw() {
    	return roomsBuffer[16];
    }

    public static ImageIcon getStudybw() {
        return rooms[17];
    }
    
    public static BufferedImage getBufferedStudybw() {
    	return roomsBuffer[17];
    }
}
