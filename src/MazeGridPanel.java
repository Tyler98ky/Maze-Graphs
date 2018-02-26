import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JPanel;

public class MazeGridPanel extends JPanel{
	private int rows;
	private int cols;
	private Cell[][] maze;



	// extra credit
	public void genDFSMaze() {
		boolean[][] visited = new boolean[this.rows][this.cols];
		int count = 1;
		Stack<Cell> stack  = new Stack<Cell>();
		Cell start = maze[0][0];
		start.setBackground(Color.GREEN);
		visited[start.row][start.row] = true;

		Cell current = start;
		while(count < this.rows * this.cols){
			if (hasUnvisitedNeighbors(visited, current)){
				stack.push(current);
				switch (pickRandomNeighbor(visited, current)){
					case "UP":
						current.northWall = false;
						current = getUp(current);
						current.southWall = false;
						break;

					case "LEFT":
						current.westWall = false;
						current = getLeft(current);
						current.eastWall = false;
						break;

					case "DOWN":
						current.southWall = false;
						current = getDown(current);
						current.northWall = false;
						break;

					case "RIGHT":
						current.eastWall = false;
						current = getRight(current);
						current.westWall = false;
						break;
				}
				visited[current.row][current.col] = true;
				count++;
			}
			else if(!stack.isEmpty()){
				current = stack.pop();
			}
		}
		stack.push(start);
	}

	public Cell getUp(Cell e){
		return maze[e.row-1][e.col];
	}

	public Cell getLeft(Cell e){
		return maze[e.row][e.col-1];
	}

	public Cell getDown(Cell e){
		return maze[e.row+1][e.col];
	}

	public Cell getRight(Cell e){
		return maze[e.row][e.col+1];
	}

	public boolean hasUnvisitedNeighbors(boolean[][] table, Cell target){
		int r = target.row;
		int c = target.col;

		if(r >= 1 && table[r-1][c] == false){ //if there's room above AND it hasnt been visited
			return true;
		}
		if(c >= 1 && table[r][c-1] == false){ // if theres room left AND it hasnt been visited
			return true;
		}
		if(r < this.rows-1 && table[r+1][c] == false){ //if theres room below AND it hasnt been visited
			return true;
		}
		if(c < this.cols-1 && table[r][c+1] == false){ //if theres room right AND it hasnt been visited
			return true;
		}
		return false;
	}

	public String pickRandomNeighbor(boolean[][] table, Cell target){
		int r = target.row;
		int c = target.col;
		ArrayList<String> choices = new ArrayList<>();

		if(r >= 1 && table[r-1][c] == false){ //if there's room above AND it hasnt been visited
			choices.add("UP");
		}
		if(c >= 1 && table[r][c-1] == false){ // if theres room left AND it hasnt been visited
			choices.add("LEFT");
		}
		if(r < this.rows-1 && table[r+1][c] == false){ //if theres room below AND it hasnt been visited
			choices.add("DOWN");
		}
		if(c < this.cols-1 && table[r][c+1] == false){ //if theres room right AND it hasnt been visited
			choices.add("RIGHT");
		}

		int index = (int) (Math.random() * choices.size());
		return choices.get(index);
	}
	//homework
	public void solveMaze() {
		Stack<Cell> stack  = new Stack<Cell>();

		Cell start = maze[0][0];
		start.setBackground(Color.GREEN);

		Cell finish = maze[rows-1][cols-1];
		finish.setBackground(Color.RED);

		stack.push(start);

		while(stack.peek().getBackground() != Color.RED && !stack.empty()){
			Cell current = stack.peek();

			if(!current.northWall && !visited(current.row-1, current.col)){
				stack.push(maze[current.row-1][current.col]);
				current.setBackground(Color.BLUE);
			}
			else if(!current.eastWall && !visited(current.row, current.col+1)){
				stack.push(maze[current.row][current.col+1]);
				current.setBackground(Color.BLUE);
			}
			else if(!current.westWall && !visited(current.row, current.col-1)){
				stack.push(maze[current.row][current.col-1]);
				current.setBackground(Color.BLUE);
			}
			else if(!current.southWall && !visited(current.row+1, current.col)){
				stack.push(maze[current.row+1][current.col]);
				current.setBackground(Color.BLUE);
			}
			else {
				current.setBackground(Color.ORANGE);
				stack.pop();
			}
		}
		maze[0][0].setBackground(Color.GREEN);
	}


	

	public void solveMazeQueue() {
		Queue<Cell> toVisitNext = new LinkedList<Cell>();
		
		Cell start = maze[0][0];
		start.setBackground(Color.CYAN);
		
		
		Cell finish = maze[rows -5][cols - 5];
		finish.setBackground(Color.RED);
		
		
		toVisitNext.offer(start);
		boolean done = false;
		while(!done && !toVisitNext.isEmpty()) {
			Cell current = toVisitNext.poll();
			current.setBackground(Color.BLUE);
			if(current == finish) {
				done = true;
			} else {
				
				if( !current.northWall  &&  !this.visited(current.row-1, current.col) ) {
					toVisitNext.offer(maze[current.row-1][current.col]);
					maze[current.row-1][current.col].setBackground(Color.GREEN);
				}
				if( !current.southWall  &&  !this.visited(current.row+1, current.col) ) {
					toVisitNext.offer(maze[current.row+1][current.col]);
					maze[current.row+1][current.col].setBackground(Color.GREEN);
				}
				if( !current.eastWall  &&  !this.visited(current.row, current.col+1) ) {
					toVisitNext.offer(maze[current.row][current.col+1]);
					maze[current.row][current.col+1].setBackground(Color.GREEN);
				}
				if( !current.westWall  &&  !this.visited(current.row, current.col-1) ) {
					toVisitNext.offer(maze[current.row][current.col-1]);
					maze[current.row][current.col-1].setBackground(Color.GREEN);
				}
				
				
			}
			
			
			
		}
		
		
	}



	public boolean visited(int row, int col) {
		Cell c = maze[row][col];
		Color status = c.getBackground();
		if(status.equals(Color.WHITE)  || status.equals(Color.RED)  ) {
			return false;
		}


		return true;


	}


	public void genNWMaze() {
		
		for(int row = 0; row  < rows; row++) {
			for(int col = 0; col < cols; col++) {

				if(row == 0 && col ==0) {
					continue;
				}

				else if(row ==0) {
					maze[row][col].westWall = false;
					maze[row][col-1].eastWall = false;
				} else if(col == 0) {
					maze[row][col].northWall = false;
					maze[row-1][col].southWall = false;
				}else {
					boolean north = Math.random()  < 0.5;
					if(north ) {
						maze[row][col].northWall = false;
						maze[row-1][col].southWall = false;
					} else {  // remove west
						maze[row][col].westWall = false;
						maze[row][col-1].eastWall = false;
					}
					maze[row][col].repaint();
				}
			}
		}
		this.repaint();
		
	}

	public MazeGridPanel(int rows, int cols) {
		this.setPreferredSize(new Dimension(800,800));
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows,cols));
		this.maze =  new Cell[rows][cols];
		for(int row = 0 ; row  < rows ; row++) {
			for(int col = 0; col < cols; col++) {

				maze[row][col] = new Cell(row,col);

				this.add(maze[row][col]);
			}

		}

		this.genDFSMaze();
//		this.genNWMaze();
		this.solveMaze();
//		this.solveMazeQueue();
	}




}
