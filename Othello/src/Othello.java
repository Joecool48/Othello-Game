import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.*;
import javax.swing.*;
public class Othello extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
	private JButton newgame, setpiece, skipturn;//For a button that says New Game to add later
	private JTextField field1, field2, field3, fieldend;
	private JFrame frame;    
	private int width;
	private int height;
	private Timer t;
	private OthelloPiece[][] pieces;
	private int diameter = 68;
	private int time = 0;
	private OthelloPiece p;
	private int turn, skip;
	private boolean isBlack=true;
	private boolean game = false;
	private int row, column; //The row and column selected by the user
	private String player1="Black", player2="White";
	public Othello(){//A constructor to create all the GUI components put into two methods for neatness I guess
		initialize();
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		
		}
	public void initialize(){//Initializes all the components in the constructor, and sets some of their functions
		pieces = new OthelloPiece[8][8];
		frame = new JFrame("Othello");
		setLayout(new BorderLayout());
		newgame = new JButton("New Game");
		setpiece = new JButton("Set Piece");
		skipturn =  new JButton("Skip Turn");
		newgame.setPreferredSize(new Dimension(100, 50));
		setpiece.setPreferredSize(new Dimension(100, 50));
		skipturn.setPreferredSize(new Dimension(100, 50));
		t = new Timer(20, this);
		JPanel panel = new JPanel();
		panel.add(newgame);
		panel.add(setpiece);
		panel.add(skipturn);
		add(panel, BorderLayout.NORTH);
		setpiece.addActionListener(this);
		newgame.addActionListener(this);
		skipturn.addActionListener(this);
		frame.setLocation(0, 0);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		width = frame.getWidth();
		height = frame.getHeight();
		frame.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		frame.setSize(width, height);
	}
	public static void main(String[] args){
		new Othello();
}
		public void popupWindow(){
		final JOptionPane popup = new JOptionPane("Player Name");
		field1 = new JTextField();
		field2 = new JTextField();
		field3 = new JTextField();
		field3.setEditable(false);
		field1.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (field1.getText().length() >= 10 ) // limit textfield to 10 characters
		            e.consume(); 
		    }  
		});
		field2 = new JTextField();
		field2.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (field2.getText().length() >= 10) // limit textfield to 10 characters
		            e.consume(); 
		    }  
		});
		field3.setText("The Default option is \"Black\" for Player 1 and \"White\" for Player 2");
		Object[] messages = {
				field3, "Player 1:", field1, "Player 2:", field2
		};
		popup.setPreferredSize(new Dimension(300, 200));
		popup.setLocation(500, 500);
		int option = JOptionPane.showConfirmDialog(frame, messages, "Choose Names", JOptionPane.OK_CANCEL_OPTION);
		if(option == JOptionPane.OK_OPTION){
			if(field1.getText().equals(""))
				player1 = "Black";
			if(field2.getText().equals(""))
				player2 = "White";
			
			if(!field1.getText().equals(""))
				player1 = field1.getText();
			if(!field2.getText().equals(""))
				player2 = field2.getText();
		}}
			

		
		
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawBoard(g); //drawBoard(g,0,0);
		layoutBoard(g);
		playerTurn(g);
		g.setColor(Color.BLUE);
		if(row < 8 && row >= 0 && column < 8 && column >= 0){
		g.drawRect(getx() -  1, gety() - 1, 69, 69);
		g.drawRect(getx(), gety(), 68, 68 );
		}
		}
public void flip(OthelloPiece piece, boolean horizontally){//A method to invoke a animation to flip pieces
		Timer t = new Timer(20, this);
		p = piece;
		p.setFlipping(true);
		t.start();
	}



		

		public int getx(){
			int locX = width / 2 - 300 + column * 75 + 7;
			return locX;
		}
		public int gety(){
			int locY = height / 2 - 300 + row * 75 + 7;
			return locY;
		}
		public void playerTurn(Graphics g){
			g.setFont(new Font("SansSerif", Font.BOLD, 25));
			g.setColor(new Color(255,204,0));
			g.drawString("Turn: " + turn, width/2-45, height/2-320); //Font Characteristics for "Turn: __"
			g.setFont(new Font("SansSerif", Font.BOLD, 40));
			if((turn+skip) % 2 == 0 && player1.equals("")){}   
			else if ((turn+skip) % 2 == 1){
				setBackground(new Color(135,206,250));
				g.setColor(Color.WHITE);
				g.drawString(player2+"'s Turn", width/2+(300+10+width/15), height/6);
				isBlack=false;
			} 									//center-(board+border+distance_from_board+text_length)
			else{
				setBackground(new Color(30,144,255));
				g.setColor(Color.BLACK);
				g.drawString(player1+"'s Turn", width/2-(300+10+width/15+230), height/6);
				isBlack=true;

			}
			
		}
		public void drawBoard(Graphics g){ 
		Graphics2D g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(new Color(0, 115, 0));
		 
			for(int i = width/2-300; i < width/2+300; i += 75){
			for(int j = height/2-300; j < height/2+300; j += 75){
				g2D.fill3DRect(i,j, 75, 75, false);
				}
			}
			for(int i = 0; i <= 600; i += 75){
			g2D.fill3DRect(i+width/2-300, height/2-300, 7, 600, true);//column borders
			g2D.fill3DRect(width/2-300, i+height/2-300, 607, 7, true);//row borders
			}
			g.setColor(Color.BLACK);
			g2D.fillRect(width / 2 - 310, height /2 - 310, 10, 627 );
			g2D.fillRect(width / 2 - 310, height /2 - 310 + 617, 627, 10);
			g2D.fillRect(width / 2 - 310, height /2 - 310, 627, 10);
			g2D.fillRect(width / 2 - 310 + 617, height /2 - 310, 10, 627);
			g2D.fillArc(width / 2 - 310, height /2 - 360, 627, 100, 0, 180);
			g2D.fillArc(width / 2 - 310, height /2 - 350 + 617, 627, 100, 180, 180);

			g.setFont(new Font("SansSerif", Font.BOLD, 40));
			g.setColor(Color.WHITE);
			g.drawString("OTHELLO",width/2-95, height/2+350);
			setBackground(new Color(30,144,255));

		}
		
		public int endGame(){
			int blackcount = 0;
			int whitecount = 0;
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(pieces[i][j]!=null && pieces[i][j].isBlack()) {
					blackcount++;	
					}
					if(pieces[i][j]!=null && !pieces[i][j].isBlack()) {
						whitecount++;	
					}
				}}
			if (blackcount>whitecount) {
					return 1;}
			else if (blackcount<whitecount) {
					return 2;}
			
			return 3;}

		public void clearBoard(){//A method to remove all the pieces from the board
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				pieces[i][j] = null;
			}
		}
		repaint();
		}
		public void setGame(){//A method to set the board with the four starting pieces
			pieces[3][3] = new OthelloPiece(width/2-70,height/2-70,false);	
			pieces[3][4] = new OthelloPiece(width/2+5,height/2-70,true);	
			pieces[4][3] = new OthelloPiece(width/2-70,height/2+5,true);	
			pieces[4][4] = new OthelloPiece(width/2+5,height/2+5,false);
		}
		public void layoutBoard(Graphics g){//Draws all the pieces on the board
			if(p != null && p.isFlipping())
				p.drawPiece(g, diameter, true);

for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(pieces[i][j] != null)
						pieces[i][j].drawPiece(g);
				}
			}
		}

		
public boolean isFlanking(){
			
			if(isBlack){
			//check left and right for other non-adjacent black pieces
			
			////not on left/right edge
				if(row!=0 && row!=7){ 
					for(int r = row+1; r < 8; r++){
						if(pieces[r][column]==null || pieces[row+1][column].isBlack()){
							break;
						}
						if(pieces[r][column].isBlack() && r!=row-1 && r!=row+1){
							return true;}}
					for(int r = row-1; r >= 0; r--){
						if(pieces[r][column]==null || pieces[row-1][column].isBlack()){
							break;
						}
						if(pieces[r][column].isBlack() && r!=row-1 && r!=row+1){
							return true;}}}
			////on right edge
					if(row == 7){ 
					for(int r = row-1; r >= 0; r--){
						if(pieces[r][column]==null || pieces[row-1][column].isBlack()){
							break;
						}
						if(pieces[r][column].isBlack()){
							return true;}}}
					
			////on left edge
					if(row==0){ for(int r = row+1; r < 8; r++){
						if(pieces[r][column]==null || pieces[row+1][column].isBlack()){
							break;
						}
						if(pieces[r][column].isBlack()){
							return true;}}}
				
			//check top and bottom for other non-adjacent black pieces
			if(column!=0 && column!=7){
			for(int c = column+1; c < 8; c++){
				if(pieces[row][c]==null || pieces[row][column+1].isBlack()){
					break;
				}
					if(pieces[row][c].isBlack() && c!=column-1 && c!=column+1){
						return true;
					}}
			for(int c = column-1; c >= 0; c--){
				if(pieces[row][c]==null || pieces[row][column-1].isBlack()){
					break;
				}
					if(pieces[row][c].isBlack() && c!=column-1 && c!=column+1){
						return true;
				}}}
			////on bottom edge
			if(column == 7){ 
				for(int c = column-1; c >= 0; c--){
					if(pieces[row][c]==null || pieces[row][column-1].isBlack()){
						break;
					}
					if(pieces[row][c].isBlack()){
						return true;
				}}}
			
			////on top edge
			if(column==0){ for(int c = column+1; c < 8; c++){
				if(pieces[row][c]==null || pieces[row][column+1].isBlack()){
					break;
				}
				if(pieces[row][c].isBlack()){
					return true;
				}}}
			
			
			//check diagonaltopleft for other non-adjacent black pieces
			int checkrow = row-1;
			int checkcol = column-1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 &&pieces[checkrow][checkcol]!=null && !pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow--; checkcol--;
				if(pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack()){
					return true;
				}}}	
			//check diagonalbottomright for other non-adjacent black pieces
			checkrow = row+1;
			checkcol = column+1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 &&pieces[checkrow][checkcol]!=null &&!pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow++; checkcol++;
				if(pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack() ){
					return true;
				}}}	 
			//check diagonalbottomleft for other non-adjacent black pieces
			checkrow = row+1;
			checkcol = column-1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 &&pieces[checkrow][checkcol]!=null && !pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow++; checkcol--;
				if(pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack()){
					return true;
				}}}	  
			//check diagonaltopright for other non-adjacent black pieces 	    
			checkrow = row-1;
			checkcol = column+1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 && pieces[checkrow][checkcol]!=null && !pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow--; checkcol++;
				if(pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack()){
					return true;
				}}}	
			return false;
			}
					if(!isBlack){
			//check left and right for other non-adjacent black pieces
			
			////not on left/right edge
				if(row!=0 && row!=7){ 
					for(int r = row+1; r < 8; r++){
						if(pieces[r][column]==null || !pieces[row+1][column].isBlack()){
							break;
						}
						if(!pieces[r][column].isBlack() && r!=row-1 && r!=row+1){
							return true;}}
					for(int r = row-1; r >= 0; r--){
						if(pieces[r][column]==null || !pieces[row-1][column].isBlack()){
							break;
						}
						if(!pieces[r][column].isBlack() && r!=row-1 && r!=row+1){
							return true;}}}
			////on right edge
					if(row == 7){ 
					for(int r = row-1; r >= 0; r--){
						if(pieces[r][column]==null || !pieces[row-1][column].isBlack()){
							break;
						}
						if(!pieces[r][column].isBlack()){
							return true;}}}
					
			////on left edge
					if(row==0){ for(int r = row+1; r < 8; r++){
						if(pieces[r][column]==null || !pieces[row+1][column].isBlack()){
							break;
						}
						if(!pieces[r][column].isBlack()){
							return true;}}}
				
			//check top and bottom for other non-adjacent black pieces
			if(column!=0 && column!=7){
			for(int c = column+1; c < 8; c++){
				if(pieces[row][c]==null || !pieces[row][column+1].isBlack()){
					break;
				}
					if(!pieces[row][c].isBlack() && c!=column-1 && c!=column+1){
						return true;
					}}
			for(int c = column-1; c >= 0; c--){
				if(pieces[row][c]==null || !pieces[row][column-1].isBlack()){
					break;
				}
					if(!pieces[row][c].isBlack() && c!=column-1 && c!=column+1){
						return true;
				}}}
			////on bottom edge
			if(column == 7){ 
				for(int c = column-1; c >= 0; c--){
					if(pieces[row][c]==null || !pieces[row][column-1].isBlack()){
						break;
					}
					if(!pieces[row][c].isBlack()){
						return true;
				}}}
			
			////on top edge
			if(column==0){ for(int c = column+1; c < 8; c++){
				if(pieces[row][c]==null || !pieces[row][column+1].isBlack()){
					break;
				}
				if(!pieces[row][c].isBlack()){
					return true;
				}}}
			
			
			//check diagonaltopleft for other non-adjacent black pieces
			int checkrow = row-1;
			int checkcol = column-1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 && pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow--; checkcol--;
				if(pieces[checkrow][checkcol]!=null && !pieces[checkrow][checkcol].isBlack()){
					return true;
				}}}	
			//check diagonalbottomright for other non-adjacent black pieces
			checkrow = row+1;
			checkcol = column+1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 &&pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow++; checkcol++;
				if(pieces[checkrow][checkcol]!=null && !pieces[checkrow][checkcol].isBlack() ){
					return true;
				}}}	 
			//check diagonalbottomleft for other non-adjacent black pieces
			checkrow = row+1;
			checkcol = column-1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 &&pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow++; checkcol--;
				if(pieces[checkrow][checkcol]!=null && !pieces[checkrow][checkcol].isBlack()){
					return true;
				}}}	  
			//check diagonaltopright for other non-adjacent black pieces 	    
			checkrow = row-1;
			checkcol = column+1;
			if(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7 &&pieces[checkrow][checkcol]!=null && pieces[checkrow][checkcol].isBlack()){
			while(checkrow>0 && checkrow<7 && checkcol>0 && checkcol<7){
				if(pieces[checkrow][checkcol]==null){
					break;
				}
				checkrow--; checkcol++;
				if(pieces[checkrow][checkcol]!=null && !pieces[checkrow][checkcol].isBlack()){
					return true;
				}}}	
			return false;
			}
			return false;
		}

		public void mouseReleased(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void actionPerformed(ActionEvent e) {
				if(e.getSource() == t){//Test Timer?
				time += 20;
				if(time <= 680){
				p.setX(getX() + 1);
				diameter -= 2;
				repaint();
				}
				if(time > 680){
				if(time == 700)
				p.changeColor();
				p.setX(getX() - 1);
				diameter += 2;
				repaint();
				}
				if(time == 1360){
					t.stop();
					time = 0;
					p.setFlipping(false);
				}
			}

			if(e.getSource() == newgame){
				popupWindow();
				turn = 0;
				skip = 0;
				game = true;
				clearBoard();
				setGame();
				repaint();
			}
			else if(e.getSource() == setpiece){
				if(game){
				boolean color = false;
				if((turn+skip) % 2 == 0)
					color = true;
				try{if(pieces[row][column] == null && isFlanking()){ //////&& isFlanking()
				pieces[row][column] = new OthelloPiece(getx(), gety(), color);
				turn++;
				flipPieces();
				}}catch(IllegalArgumentException g) {}
				repaint();
				System.out.println("Skip: " + skip);
				System.out.println("Turn: " + turn);
			}}
			else if(e.getSource() == skipturn){
				skip++;
				System.out.println("Skip: " + skip);
				System.out.println("Turn: " + turn);
				
			}
			if(turn == 60) {
				fieldend = new JTextField();
				if(endGame() == 1) {
					fieldend.setText(player1 + " Wins"); 
				}
				else if (endGame() == 2) { 
					fieldend.setText(player2 + " Wins!");
				}
				else {
					fieldend.setText("Tie Game");
					}
				
			final JOptionPane popup1 = new JOptionPane("Game Over");
			
			fieldend.setEditable(false);
			Object[] messages1 = {
					fieldend
			};
			popup1.setPreferredSize(new Dimension(300, 200));
			popup1.setLocation(500, 500);
			int option1 = JOptionPane.showConfirmDialog(frame, messages1, "Game Over", JOptionPane.OK_CANCEL_OPTION);
			}
			}
		

		public void flipPieces(){//Add in checks for close proximity and check for loop logic
			boolean black = pieces[row][column].isBlack(); //Checks to see the color of the current	
			boolean right = false, left = false, top = false, bottom = false, topright = false, topleft = false, bottomright = false, bottomleft = false; //Initializes directional flanking variables
			try{if(row <= 5 && pieces[row + 1][column] != null && pieces[row + 1][column].isBlack() != black){//Then we can check bottom
				for(int r = row + 1; r < 8; r++){
					if(pieces[r][column] == null)
						break;
					if(pieces[r][column].isBlack() == black){
						bottom = true;
						break;
					}
				}
				if(bottom)
				System.out.println("Bottom: " + bottom + " row:" + row + " column:" + column);
			}}catch(IndexOutOfBoundsException e){}
			try{if(row >= 2 && pieces[row - 1][column] != null && pieces[row - 1][column].isBlack() != black){//Then we can check top
				for(int r = row - 1; r >= 0; r--){
					if(pieces[r][column] == null)
						break;
					if(pieces[r][column].isBlack() == black){
						top = true;
						break;
					}
				}
				if(top)
				System.out.println("Top:" + top + " row:" + row + " column:" + column);
			}}catch(IndexOutOfBoundsException e){}
			try{if(column >= 2 && pieces[row][column - 1] != null && pieces[row][column - 1].isBlack() != black){//Then we can check left
				for(int c = column - 1; c >= 0; c--){
					if(pieces[row][c] == null)
						break;
					if(pieces[row][c].isBlack() == black){
						left = true;
						break;
					}
				}
				if(left)
				System.out.println("Left:" + left + " row:" + row + " column:" + column);
			}}catch(IndexOutOfBoundsException e){}
			try{if(column <= 5 && pieces[row][column + 1] != null && pieces[row][column + 1].isBlack() != black){//Then we can check right
				for(int c = column + 1; c < 8; c++){
					if(pieces[row][c] == null)
						break;
					if(pieces[row][c].isBlack() == black){
						right = true;
						break;
					}
				}
				if(right)
				System.out.println("Right:" + right + " row:" + row + " column:" + column);
			}}catch(IndexOutOfBoundsException e){}
			try{if(column <= 5 && row >= 2 && pieces[row - 1][column + 1] != null && pieces[row - 1][column + 1].isBlack() != black){//Then we can check top right
				int r = row - 1, c = column + 1;
				while(r >= 0 && c <= 7){
					if(pieces[r][c] == null)
						break;
					if(pieces[r][c].isBlack() == black){
						topright = true;
						break;
					}
					r--;
					c++;
				}
				if(topright)
				System.out.println("Diagnal topright:" + topright + " row:" + row + " column:" + column);
			}}catch(IndexOutOfBoundsException e){}
			try{if(column >= 2 && row >= 2 && pieces[row - 1][column - 1] != null && pieces[row - 1][column - 1].isBlack() != black){//Then we can check top left
				int r = row - 1, c = column - 1;
				while(r >= 0 && c <= 7){
					if(pieces[r][c] == null)
						break;
					if(pieces[r][c].isBlack() == black){
						topleft = true;
						break;
					}
					r--;
					c--;
				}
				if(topleft)
				System.out.println("Diagnal topleft:" + topleft + " row:" + row + " column:" + column);
			}}catch(IndexOutOfBoundsException e){}
			try{if(column <= 5 && row <= 5 && pieces[row + 1][column + 1] != null && pieces[row + 1][column + 1].isBlack() != black){//Then we can check bottom right
				int r = row + 1, c = column + 1;
				while(r <= 7 && c <= 7){
					if(pieces[r][c] == null)
						break;
					if(pieces[r][c].isBlack() == black){
						bottomright = true;
						break;
					}
					r++;
					c++;
				}
				if(bottomright)
				System.out.println("Diagnal bottomright:" + bottomright + " row:" + row + " column:" + column);
			}
			}catch(IndexOutOfBoundsException e){}
			try{if(column >= 2 && row <= 5 && pieces[row + 1][column - 1] != null && pieces[row + 1][column - 1].isBlack() != black){//Then we can check bottom left
				int r = row + 1, c = column - 1;
				while(r <= 7 && c >= 0){
					if(pieces[r][c] == null)
						break;
					if(pieces[r][c].isBlack() == black){
						bottomleft = true;
						break;
					}
					r++;
					c--;
				}
				if(bottomleft)
				System.out.println("Diagnal bottomleft:" + bottomleft + " row:" + row + " column:" + column);
			}
			}catch(IndexOutOfBoundsException e){}
			if(bottom){
				for(int i = row + 1; pieces[i][column].isBlack() != black; i++){
					pieces[i][column].setColor(black);
				}
			}
			if(top){
				for(int i = row - 1; pieces[i][column].isBlack() != black; i--){
					pieces[i][column].setColor(black);
				}
			}
			if(right){
				for(int i = column + 1; pieces[row][i].isBlack() != black; i++){
					pieces[row][i].setColor(black);
				}
			}
			if(left){
				for(int i = column - 1; pieces[row][i].isBlack() != black; i--){
					pieces[row][i].setColor(black);
				}
			}
			if(topright){
				int r = row - 1, c = column + 1;
				while(pieces[r][c].isBlack() != black){
					pieces[r][c].setColor(black);
					r--;
					c++;
				}
			}
			if(topleft){
				int r = row - 1, c = column - 1;
				while(pieces[r][c].isBlack() != black){
					pieces[r][c].setColor(black);
				r--;
				c--;
				}
			}
			if(bottomright){
				int r = row + 1, c = column + 1;
				while(pieces[r][c].isBlack() != black){
					pieces[r][c].setColor(black);
				r++;
				c++;
				}
			}
			if(bottomleft){
				int r = row + 1, c = column - 1;
				while(pieces[r][c].isBlack() != black){
					pieces[r][c].setColor(black);
				r++;
				c--;
				}
			}
		}

		public void setRow(int posY){
			row = (posY - (height / 2 - 300)) / 75;
		}
		public void setColumn(int posX){
			column = (posX - ( width/ 2 - 300)) / 75;
		}
		public void mouseDragged(MouseEvent e) {
			}
		public void mouseMoved(MouseEvent e) {
			
		}
		public void mouseClicked(MouseEvent evt) {
			
		}
		public void mousePressed(MouseEvent evt) {
			int x = evt.getX();
			int y = evt.getY();
			setColumn(x);
			setRow(y - 20); //FIX: leftmost row selected when left border clicked
			repaint();
		}}
