package pl.edu.pw.iem.graphics;

import pl.edu.pw.iem.engine.Table;
import pl.edu.pw.iem.gate.GateAdder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class TablePanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	
	private Table tab;
	private int cellSize;
	private int numOfStates;
	private boolean addGateMode = false;
	private String gateName = "AND";
	
	public TablePanel(int numberOfRows, int numberOfCols, int numOfStates) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBounds(0, 0, WIDTH, HEIGHT);
		this.setDoubleBuffered(true);
		
		this.numOfStates = numOfStates;
		
		this.tab = new Table(numberOfRows, numberOfCols);
		
		if(numberOfRows > numberOfCols) {
			this.cellSize = HEIGHT/numberOfRows;
		} else {
			this.cellSize = WIDTH/numberOfCols;
		}
		
		int newBoard[][] = new int[numberOfRows][numberOfCols];
		for(int x=0; x<newBoard.length; x++) {
			for(int y=0; y<newBoard[x].length; y++) {
				newBoard[x][y] = 0;
			}
		}
		this.tab.setBoard(newBoard);
		this.tab.setBoundary("Void");
		this.tab.setNeighbourhood("Moore");
		String rules[] = new String[4];
		rules[0] = "0/9/1/0";
		rules[1] = "1/9/1/2";
		rules[2] = "2/9/1/3";
		rules[3] = "1/12/1/3";
		numOfStates = 4;
		this.tab.setRules(rules);
		
		this.addMouseListener(this);
		repaint();
	}
	
	public void setBoard(int [][]board) {
		tab.setBoard(board);
	}
	
	public int [][]getBoard() {
		return tab.getBoard();
	}
	
	public void setNeighbourhood(String opt) {
		
		switch(opt) {
			case "Moore":
				this.tab.setNeighbourhood("Moore");
				break;
			case "von Neumann":
				this.tab.setNeighbourhood("Neumann");
				break;
			default:
				this.tab.setNeighbourhood("Moore");
		}
		
	}
	
	public void setBoundary(String opt) {
		
		switch(opt) {
		case "Void":
			this.tab.setBoundary("Void");
			break;
		case "Planet":
			this.tab.setBoundary("Planet");
			break;
		default:
			this.tab.setBoundary("Void");
		}
		
	}
	
	public void setRules(String rules[], int numOfStates) {
		this.tab.setRules(rules);
		this.numOfStates = numOfStates;
	}
	
	public void setGateName(String opt) {
		switch(opt) {
		case "AND":
			gateName = opt;
			break;
		case "OR":
			gateName = opt;
			break;
		case "DIODE":
			gateName = opt;
			break;
		case "XOR":
			gateName = opt;
			break;
		default:
			gateName = "AND";
		}
	}
	
	public void setRules(String opt) {
		
		String rules[];
		
		switch(opt) {
			case "Game Of Life":
				rules = new String[2];
				rules[0] = "1/3/1/0";
				rules[1] = "1/23/1/0";
				numOfStates = 2;
				this.tab.setRules(rules);
				break;
			case "Wireworld":
				rules = new String[4];
				rules[0] = "0/9/1/0";
				rules[1] = "1/9/1/2";
				rules[2] = "2/9/1/3";
				rules[3] = "1/12/1/3";
				numOfStates = 4;
				this.tab.setRules(rules);
				break;
			default:
				rules = new String[4];
				rules[0] = "0/9/1/0";
				rules[1] = "1/9/1/2";
				rules[2] = "2/9/1/3";
				rules[3] = "1/12/1/3";
				numOfStates = 4;
				this.tab.setRules(rules);
		}
		
	}
	
	public void nextGeneration() {
		this.tab.makeNextGeneration();
	}
	
	public void clearBoard() {
		int newBoard[][] = new int[tab.getNumberOfRows()][tab.getNumberOfColumns()];
		for(int x=0; x<newBoard.length; x++) {
			for(int y=0; y<newBoard[x].length; y++) {
				newBoard[x][y] = 0;
			}
		}
		this.tab.setBoard(newBoard);
	}
	
	private Color getColorFromState(int state) {
		if(state == 0) {
			return Color.DARK_GRAY;
		}
		if(state == 1 && numOfStates == 2) {
			return Color.WHITE;
		}
		if(state == 1) {
			return Color.BLUE;
		}
		if(state == 2) {
			return Color.RED;
		}
		if(state == 3) {
			return Color.YELLOW;
		}
		float dc = (float)this.numOfStates/state;
		return Color.getHSBColor(dc, 1.0f, 1.0f);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
	    g.setColor(Color.DARK_GRAY);
	    g.fillRect(0, 0, WIDTH, HEIGHT);
	    
	    g.setColor(Color.WHITE);
        for(int x=0; x<tab.getNumberOfRows(); x++) {
            for(int y=0; y<tab.getNumberOfColumns(); y++) {
            	g.setColor(getColorFromState(tab.getCellValue(x, y)));
            	g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
            }
        }
        
        if(tab.getNumberOfColumns() <= 150 && tab.getNumberOfRows() <= 150) {
        
        	g.setColor(Color.LIGHT_GRAY);

        	for(int i=0; i<=cellSize*tab.getNumberOfRows(); i+=cellSize) {
        		g.drawLine(i, 0, i, cellSize*tab.getNumberOfColumns());
        	}
        	for(int i=0; i<=cellSize*tab.getNumberOfColumns(); i+=cellSize) {
            	g.drawLine(0, i, cellSize*tab.getNumberOfRows(), i);
        	}
        }
	}
	
	private void increaseCellState(int x, int y) {
		int state = tab.getCellValue(x, y);
		if(state == this.numOfStates-1) {
			tab.setCellValue(x, y, 0);
		} else {
			tab.setCellValue(x, y, state+1);
		}
		
	}
	
	private void decreaseCellState(int x, int y) {
		int state = tab.getCellValue(x, y);
		if(state == 0) {
			tab.setCellValue(x, y, this.numOfStates-1);
		} else {
			tab.setCellValue(x, y, state-1);
		}
		
	}
	
	public int getNumOfRows() {
		return tab.getNumberOfRows();
	}
	
	public int getNumOfCols() {
		return tab.getNumberOfColumns();
	}
	
	public void setAddGateMode(boolean k) {
		addGateMode = k;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(!addGateMode) {
			if(e.getY() <= cellSize*tab.getNumberOfColumns() && e.getX() <= cellSize*tab.getNumberOfRows() && e.getButton() == MouseEvent.BUTTON1) {
				int x = (int)Math.floor(e.getX()/this.cellSize);
				int y = (int)Math.floor(e.getY()/this.cellSize);
				increaseCellState(x, y);
			}
			if(e.getY() <= cellSize*tab.getNumberOfColumns() && e.getX() <= cellSize*tab.getNumberOfRows() && e.getButton() == MouseEvent.BUTTON3) {
				int x = (int)Math.floor(e.getX()/this.cellSize);
				int y = (int)Math.floor(e.getY()/this.cellSize);
				decreaseCellState(x, y);
			}
		} else {
			if(e.getY() <= cellSize*tab.getNumberOfColumns() && e.getX() <= cellSize*tab.getNumberOfRows()) {
				int x = (int)Math.floor(e.getX()/this.cellSize);
				int y = (int)Math.floor(e.getY()/this.cellSize);
				
				GateAdder gateAdder = new GateAdder(tab, gateName);
				gateAdder.addGateToTable(x, y);
				tab = gateAdder.getTable();
				
				if(!gateAdder.isGatePlaced()) {
					JOptionPane.showMessageDialog(null, "Gate can not be so close to the bottom or the right edge.");
				}
				
				addGateMode = false;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		;
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		;
		
	}
	
}