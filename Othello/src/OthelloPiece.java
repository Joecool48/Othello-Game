
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class OthelloPiece extends JPanel {
private int posX, posY;
private boolean isBlack;
private boolean flipping;
public OthelloPiece(int posX, int posY, boolean isBlack){
	this.posX = posX;
	this.posY = posY;
	this.isBlack = isBlack;
	flipping = false;
	}
	public OthelloPiece(boolean isBlack){
		this.isBlack = isBlack;
	}
	public void drawPiece(Graphics g){
		if(!isBlack){
			g.setColor(Color.BLACK);
			g.drawOval(posX + 1, posY + 1, 66, 66);
			g.drawOval(posX - 1, posY - 1, 68, 68);
			g.setColor(Color.WHITE);
			g.fillOval(posX, posY, 67, 67);
		}
		if(isBlack){
			g.setColor(Color.WHITE);
			g.drawOval(posX + 1, posY + 1, 66, 66);
			g.drawOval(posX -1, posY - 1, 68, 68);
			g.setColor(Color.BLACK);
			g.fillOval(posX, posY, 67, 67);
		}
	}
	public void drawPiece(Graphics g, int diameter, boolean horizontally){
		if(!isBlack){
			//g.setColor(Color.BLACK);
			//g.drawOval(posX + 1, posY + 1, 66, 66);
			//g.drawOval(posX - 1, posY - 1, 68, 68);
			g.setColor(Color.WHITE);
			g.fillOval(posX, posY, diameter, 68);
		}
		if(isBlack){
			//g.setColor(Color.WHITE);
			//g.drawOval(posX + 1, posY + 1, 66, 66);
			//g.drawOval(posX -1, posY - 1, 68, 68);
			g.setColor(Color.BLACK);
			g.fillOval(posX, posY, diameter, 68);
		}
	}
	public void setLocation(int x, int y){
		posX = x;
		posY = y;
	}
	public void setX(int x){
		posX = x;
	}
	public void setY(int y){
		posY = y;
	}
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	public void changeColor(){
		isBlack = !isBlack;
	}
	public void setColor(boolean b){
		isBlack = b;
	}
	public boolean isBlack(){
		return isBlack;
	}
	public boolean isFlipping(){
		return flipping;
	}
	public void setFlipping(boolean flip){
		flipping = flip;
	}
}

