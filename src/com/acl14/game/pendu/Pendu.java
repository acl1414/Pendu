package com.acl14.game.pendu;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Pendu extends JFrame  implements ActionListener{

	
	private static final long serialVersionUID = 6915033733326541759L;
	
	private static final String LIBELLE_NB_ERROR ="Nb errors : ";
	private static final String LIBELLE_NB_ESSAI ="Nb essais : ";
	
	JButton buttonNew;
	ArrayList<JButton> buttonList;
	List<String> motList;
	
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String textAffiche, textATrouver, textTrouve;
	
	JLabel labelTitle, labelMot, labelEssai,labelError;
	JPanel panelMain, panelTop, panelCenter, panelSouth, panelRight ;
	
	JFrame frame;
	
	boolean isGameOver=false, isSuccess=false; 
	
	DessinPanel dessinPanel;
	
	int nbEssai = 0;
	int nbError=0;
	int nbFind;
	
	
	public Pendu(){
		super();
 
		frame =  new JFrame();
		build();
	}

	 public static void main(String [] args){
		 
	      new Pendu();
	      
	      
	   }
	
	private void build(){
		
		motList =  Arrays.asList( "BELGIQUE", "FRANCE","ALLEMAGNE","ITALIE");
		buttonList = getButtonList(alphabet);
		
		setTitle("Jeu du Pendu"); //On donne un titre à l'application
		setSize(640,480); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(true); //On permet le redimensionnement
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		
		setContentPane(buidMainPanel());
		setVisible(true);
		
		initGame();
	}
	
	
	private JPanel buidMainPanel() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		
		panelTop = buidTopPanel();
		panelCenter= buildCenterPane();
		panelSouth= buildSouthPane();
		
		panel.add(BorderLayout.NORTH,panelTop);
		panel.add(BorderLayout.CENTER,panelCenter);
		panel.add(BorderLayout.SOUTH,panelSouth);
		
		
		return panel;
	}
	
	
	private JPanel buidTopPanel() 
	{
		
		panelTop = new JPanel();
		panelTop.setLayout(new BoxLayout(panelTop,BoxLayout.Y_AXIS));
		panelTop.setBackground(Color.white);

		buttonNew = new JButton("New Game");
		buttonNew.addActionListener(this);
		buttonNew.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelTop.add(buttonNew);
		
		panelTop.add(Box.createRigidArea(new Dimension(0,10))); // ESPACE
		
		labelTitle = new JLabel("LE JEU DU PENDU");
		labelTitle.setFont(new Font("Calibri", Font.BOLD, 40));
		labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelTop.add(labelTitle);
		
		return panelTop;
	}
	
	
	
	
	private JPanel buildCenterPane() 
	{
	
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
		panel.setBackground(Color.WHITE);
 

		
		dessinPanel = new DessinPanel();
		panel.add(dessinPanel);
		
		
		labelMot = new JLabel(textAffiche);
		labelMot.setFont(new Font("Calibri", Font.BOLD, 40));
		labelMot.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labelMot);
		
		
		labelEssai = new JLabel(LIBELLE_NB_ESSAI+ nbEssai);
		labelEssai.setFont(new Font("Calibri", Font.BOLD, 15));
		labelEssai.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		panel.add(labelEssai);

		labelError = new JLabel(LIBELLE_NB_ERROR + nbError);
		labelError.setFont(new Font("Calibri", Font.BOLD, 15));
		labelError.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(labelError);


		
		
		
		
		return panel;
	}
	
	private JPanel buildSouthPane(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,10));
		panel.setBackground(Color.white);
		
		for (JButton bouton : buttonList ) {
			panel.add(bouton);
		}
 	

		
		
 
		return panel;
	}
 

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		System.out.println("action event");
		
		JButton j= (JButton) source;

 
		if(source == buttonNew){
			System.out.println("New Game");
			initGame();
			
		} 
		
		for (JButton button : buttonList) {
			if(source == button){
				button.setEnabled(false);
				this.checkLetter(button.getText());
				break;
			}
		}
		
		this.repaint();
		

	
	}
	
	private ArrayList<JButton> getButtonList(String text){
		
		ArrayList<JButton> result = new ArrayList<JButton>();  
		
		
		 
		for (int i = 0 ; i < alphabet.length() ;i++) 
		{
		 
		    String lettre=alphabet.substring(i, i+1); 
		    JButton bouton = new JButton(lettre);
			bouton.addActionListener(this);
			result.add(bouton);
		}

		
		return result;
		
		
	}
	
	private void initGame() {
		
		this.nbEssai=0;
		this.nbFind=0;
		this.nbError=0;
	
		for (JButton button : buttonList) {
		
			button.setEnabled(true);
		}
		
		int randomIndex= (int) (Math.random() * ( motList.size() ));
		textATrouver = motList.get(randomIndex);
		textTrouve= this.initTextTouve(textATrouver);
		textAffiche = this.addEspaceToWord(textTrouve);
	
		labelMot.setText(textAffiche);
		
		repaint();
		//dessinPanel.repaint();
		
	}
	
	private void checkLetter(String letter) {
		
		
		System.out.println("check Letter:"+letter);
		
		this.nbEssai++;		
		
		boolean success= false;
		
		for (int i = 0; i < textATrouver.length(); i++) {
			
			if (textATrouver.substring(i, i+1).equals(letter)) {
				
				System.out.println("FIND at position"+i);
				textTrouve=textTrouve.substring(0,i)+letter+textTrouve.substring(i+1);
				success=true;
				this.nbFind++;
			}
			
		}
		
		if (!success) {
			this.nbError++;
		}
		
		
		
		labelMot.setText(this.addEspaceToWord(textTrouve));
		
		
	}
	
	private String addEspaceToWord(String text) {
		
		String result="";
		
		for (int i = 0; i < text.length(); i++) {
			result=result+text.substring(i, i+1)+" ";
		}
		
		return result;
		
	}
	
	private String initTextTouve(String text) {
		
		String result="";
		
		for (int i = 0; i < text.length(); i++) {
			result=result + "_";
		}
		
		return result;
		
	}
	
	

	
	
	
	public class DessinPanel extends JPanel {
		
		private BufferedImage imageGameOver;
		private BufferedImage imageSucces;
	//	new ImageIcon("image.png");
		
		public DessinPanel()  {
			
			try {
				imageGameOver = ImageIO.read(new File("imageGameOver.png"));
				imageSucces = ImageIO.read(new File("imageSucces.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		public void paintComponents(Graphics g) {
			
			g.setColor(Color.orange);
			g.fillRect(20, 50, 500, 500);
			
			System.out.println("Paint");
		
			
		}
		
		public void paint(Graphics g) {
			
			System.out.println("Paint");
			
			labelEssai.setText(LIBELLE_NB_ESSAI+ nbEssai);
			labelError.setText(LIBELLE_NB_ERROR+ nbError);
			
			int widthMax = getSize().width;
			int heightMax = getSize().height;
		
			int startPosX, startPosY, endPosX, endPosY, startPosyOval=0;
			
			int lineWidth=10;
			
			BasicStroke line = new BasicStroke(lineWidth);
		    Graphics2D g2D = (Graphics2D) g;
			g2D.setStroke(line);
			
			
			
			
		
			g.setColor( new Color(153, 76, 0));
			
			if (nbError>=1) {	
			// Step1
			startPosX=widthMax*5/16;
			startPosY=heightMax*15/16;
			endPosX=widthMax*11/16;
			endPosY=heightMax*15/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			if (nbError>=2) {
			// Step2
			startPosX=widthMax*6/16;
			startPosY=heightMax*1/16;
			endPosX=widthMax*6/16;
			endPosY=heightMax*15/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			
			if (nbError>=3) {
			// Step3
			startPosX=widthMax*6/16;
			startPosY=heightMax*1/16;
			endPosX=widthMax*8/16;
			endPosY=heightMax*1/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			if (nbError>=4) {
			// Step4
			startPosX=widthMax*6/16+5;
			startPosY=heightMax*4/16;
			endPosX=widthMax*7/16;
			endPosY=heightMax*1/16+5;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			
			if (nbError>=5) {
			// Step5
			startPosX=widthMax*8/16;
			startPosY=heightMax*1/16;
			endPosX=widthMax*8/16;
			endPosY=heightMax*4/16;
			g.setColor( Color.GRAY);
			
			g2D.drawLine(startPosX,startPosY+lineWidth, endPosX, endPosY);
			}
			
			
			if (nbError>=6) {
			// Step6
			startPosX=widthMax*15/32;
			startPosyOval=heightMax*4/16;
			g.setColor( Color.pink);
			g.fillOval(startPosX, startPosyOval, widthMax/16,widthMax/16);
			}
			

			
			if (nbError>=7) {
			// Step7
			g.setColor( Color.black);
			startPosX=widthMax*8/16;
			startPosY=startPosyOval+widthMax/16;
			endPosX=widthMax*8/16;
			endPosY=heightMax*10/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			
			if (nbError>=8) {
			// Step8
			g.setColor( Color.black);
			startPosX=widthMax*7/16;
			startPosY=heightMax*7/16;
			endPosX=widthMax*8/16;
			endPosY=heightMax*8/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			
			if (nbError>=9) {
			// Step9
			startPosX=widthMax*8/16;
			startPosY=heightMax*8/16;
			endPosX=widthMax*9/16;
			endPosY=heightMax*7/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			
			if (nbError>=10) {
			// Step10
			startPosX=widthMax*8/16;
			startPosY=heightMax*10/16;
			endPosX=widthMax*17/32;
			endPosY=heightMax*12/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			}
			
			if (nbError>=11) {
			// Step11
			startPosX=widthMax*8/16;
			startPosY=heightMax*10/16;
			endPosX=widthMax*15/32;
			endPosY=heightMax*12/16;
			
			g2D.drawLine(startPosX,startPosY, endPosX, endPosY);
			
			
			
			g.drawImage(imageGameOver, widthMax/2-150, 0,300,100, null );
			}
			
			if (nbFind>=textATrouver.length()) {
				g.drawImage(imageSucces, widthMax/2-150, 0,300,100, null );	
			}
			
			
			}
		
	

	}

	
	
}