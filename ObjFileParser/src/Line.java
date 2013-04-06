/**
 * Line class to hold start and end points of an arbitrary line in 3d space
 * @author user
 *
 */
public class Line {
	int[] startVertex;
	int[] endVertex;
	
	/**
	 * Constructor
	 * @param s start vertex as array {x,y,z}
	 * @param e end vertex as array {x,y,z}
	 */
	public Line(int[] s, int[] e){
		startVertex = new int[3];
		endVertex = new int[3];
		
		startVertex[0] = s[0];
		startVertex[1] = s[1];
		startVertex[2] = s[2];
		
		endVertex[0] = e[0];
		endVertex[1] = e[1];
		endVertex[2] = e[2];
	}
	
	public Line(){
		startVertex = new int[3];
		endVertex = new int[3];
	}
	
	/**
	 * Returns the starting vertex
	 * @return
	 */
	public int[] getStart(){
		return startVertex;
	}
	
	public void printLine(){
		System.out.print(String.format("sx: %d sy: %d sz: %d ex: %d ey: %d ez: %d ",
				startVertex[0],
				startVertex[1],
				startVertex[2],
				endVertex[0],
				endVertex[1],
				endVertex[2]
				));
	}
	
	public String printLineCoordinates(){
		return String.format("%d,%d,%d,%d,%d,%d",
				startVertex[0],
				startVertex[1],
				startVertex[2],
				endVertex[0],
				endVertex[1],
				endVertex[2]
				);
	}
	
	/**
	 * Returns the ending vertex
	 * @return
	 */
	public int[] getEnd(){
		return endVertex;
	}
	
	/**
	 * Compares two lines by looking at the start and end vertexes and seeing if they are equal
	 * @param l The line to compare this line to.
	 * @return
	 */
	public boolean compare(Line l){
		boolean b = false;
		
		if(startVertex[0] == l.getStart()[0] &&
		   startVertex[1] == l.getStart()[1] &&
		   startVertex[2] == l.getStart()[2] &&
		   endVertex[0] == l.getEnd()[0] &&
		   endVertex[1] == l.getEnd()[1] &&
		   endVertex[2] == l.getEnd()[2]
				) b=true;
		
		if(startVertex[0] == l.getEnd()[0] &&
				   startVertex[1] == l.getEnd()[1] &&
				   startVertex[2] == l.getEnd()[2] &&
				   endVertex[0] == l.getStart()[0] &&
				   endVertex[1] == l.getStart()[1] &&
				   endVertex[2] == l.getStart()[2]
						) b=true;
		
		return b;
	}
}
