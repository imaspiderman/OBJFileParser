/**
 * Line class to hold start and end points of an arbitrary line in 3d space
 * @author user
 *
 */
public class Line {
	int startVertex = 0;
	int endVertex = 0;
	
	/**
	 * Constructor
	 * @param s start vertex
	 * @param e end vertex
	 */
	public Line(int s, int e){
		startVertex = s;
		endVertex = e;
	}
	
	public Line(){
	}
	
	/**
	 * Returns the starting vertex
	 * @return
	 */
	public int getStart(){
		return startVertex;
	}
	
	public void printLine(){
		System.out.print(String.format("start: %d end: %d ",
				startVertex,
				endVertex
				));
	}
	
	public String printLineCoordinates(){
		return String.format("%d,%d",
				startVertex-1,
				endVertex-1
				);
	}
	
	/**
	 * Returns the ending vertex
	 * @return
	 */
	public int getEnd(){
		return endVertex;
	}
	
	/**
	 * Compares two lines by looking at the start and end vertexes and seeing if they are equal
	 * @param l The line to compare this line to.
	 * @return
	 */
	public boolean compare(Line l){
		boolean b = false;
		
		if(startVertex == l.getStart() &&
		   endVertex == l.getEnd()
        ) b=true;
		
		if(startVertex == l.getEnd() &&
		   endVertex == l.getStart()
		) b=true;
		
		return b;
	}
}
