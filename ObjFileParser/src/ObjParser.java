
/**
 * 
 * @author Greg Stevens
 *This class will parse a wavefront OBJ file and create an array of line data that
 *can be used to display wire frame models on a screen. This is primarily intended for
 *the Virtual Boy system but could be used for any c program.
 */
public class ObjParser {

	private java.util.LinkedList<Line> lines;
	private java.util.LinkedList<Integer> vertices;
	
	public static void main(String[] args) {
		ObjParser o = new ObjParser();
		if(args.length != 3) {
			o.usage();
			return;
		}
		o.parseFile(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
		o.writeOutData();
	}
	
	private void writeOutData(){
		java.util.Iterator<Line>i = lines.iterator();
		java.util.Iterator<Integer>v = vertices.iterator();
		Line t = new Line();
		
		System.out.print("const s32 obj[] = {");
		System.out.print(vertices.size() + ", //total number of vertices\n");
		System.out.print("\t" + (lines.size()*2) + ", //total number of line end points\n");
		System.out.print("\t2,\n\t//Distinct vertices\n");
		//vertices
		boolean bFirst = true;
		int count = 0;
		while(v.hasNext()){
			Integer vert = v.next();
			
			System.out.print((bFirst)?("\t" + vert.toString()):("," + vert.toString()));
			bFirst = false;
			
			count++;
			if(count==12){
				System.out.print("\n\t");
				count = 0;
			}
		}
		//lines
		count = 0;
		bFirst = true;
		while(i.hasNext()){
			t = i.next();
			
			System.out.print((bFirst)?("\n\t//Vertex indexes\n\t," + t.printLineCoordinates()):("," + t.printLineCoordinates()));
			bFirst = false;
			
			count++;
			if(count==8){
				System.out.print("\n\t");
				count = 0;
			}
		}
		System.out.print("};\n");
	}
	
	public ObjParser(){
		lines = new java.util.LinkedList<Line>();
		vertices = new java.util.LinkedList<Integer>();
	}
	
	private void parseFile(int scale, int scaleFactor, String filename){
		try {
			java.io.BufferedReader r = new java.io.BufferedReader(new java.io.FileReader(filename));
			String line;
			String type;
			String[] pieces;
			
			float fx,fy,fz;
			int ix,iy,iz;
			
			while((line=r.readLine()) != null){
				pieces = line.split("[\\s]");
				type = pieces[0].trim();
				if(type.equals("v")){
					fx=Float.parseFloat(pieces[1]) * (float)scale;
					fy=Float.parseFloat(pieces[2]) * (float)scale;
					fz=Float.parseFloat(pieces[3]) * (float)scale;
					
					ix=((int)fx << scaleFactor);
					iy=((int)fy << scaleFactor);
					iz=((int)fz << scaleFactor);
					
					vertices.add(ix);
					vertices.add(iy);
					vertices.add(iz);
				}
				
				if(type.equals("f")){
					int v1,v2;
					
					v1 = Integer.parseInt((pieces[1].indexOf("/")>-1)?(pieces[1].split("/")[0]):(pieces[1]));
					for(int i=2; i<pieces.length; i++){
						v2 = Integer.parseInt((pieces[i].indexOf("/")>-1)?(pieces[i].split("/")[0]):(pieces[i]));
						
						Line l = new Line(v1, v2);
						
						boolean add = true;
						
						for(int j=0; j<lines.size(); j++){
							if(lines.get(j).compare(l)) {
								add = false;
								break;
							}
						}
						
						if(add) lines.add(l);
						
						v1=v2;
						
						//The last vertex of a face connects to the first vertex
						//with a line so this creates that final line
						if(i==pieces.length-1){
							v2 = Integer.parseInt((pieces[i].indexOf("/")>-1)?(pieces[1].split("/")[0]):(pieces[1]));
							
							l = new Line(v1, v2);
							
							add = true;
							
							for(int j=0; j<lines.size(); j++){
								if(lines.get(j).compare(l)) {
									add = false;
									break;
								}
							}
							
							if(add) lines.add(l);
						}
					}
				}
			}
			
			r.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	private void usage(){
		System.out.print("Usage: java ObjParser [scale] [fixed point scale factor] [filename]\n");
		System.out.print("  [scale] is a integer scaling factor that is multiplied to each vertex\n");
		System.out.print("  [fixed point scale factor] is the number of bits to left shift the integer values\n");
		System.out.print("  [filename] is the path to the .obj file\n");
	}

}
