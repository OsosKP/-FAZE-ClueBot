package cluedo_game;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

public class HelpPageHtml {
		public HelpPageHtml() throws IOException {
		    JFrame frame = new JFrame("Tab Attributes");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    JEditorPane editorPane = new JEditorPane();
		    editorPane.setEditorKit(new HTMLEditorKit());
	    
		    String filename = "index.html";
	    
		    FileReader reader = null;
			try {
				reader = new FileReader(filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    editorPane.read(reader, filename);

		    JScrollPane scrollPane = new JScrollPane(editorPane);
		    frame.add(scrollPane, BorderLayout.CENTER);

		    frame.setSize(300, 150);
		    frame.setVisible(true);		
		}

	    
}