
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
		Line t = new Line();
		Integer c=0;
		
		System.out.print("const s32 obj[] = {");
		System.out.print((lines.size()*6) + ", //total number of vertex points\n");
		System.out.print("\t2,\n");
		//faces
		while(i.hasNext()){
			c+=1;
			t = i.next();
			
			System.out.print("\t" + t.printLineCoordinates());
			
			if(i.hasNext()) System.out.print(",\n");
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
					int v1,v2,v3,v4,v5,v6,vt;
					
					String v = (pieces[1].indexOf("/")>-1)?(pieces[1].split("/")[0]):(pieces[1]);
					vt = (Integer.parseInt(v)-1)*3;
					v1 = vertices.get(vt);
					v2 = vertices.get(vt+1);
					v3 = vertices.get(vt+2);
					
					for(int i=2; i<pieces.length; i++){
						v = (pieces[i].indexOf("/")>-1)?(pieces[i].split("/")[0]):(pieces[i]);
						vt = (Integer.parseInt(v)-1)*3;
						v4 = vertices.get(vt);
						v5 = vertices.get(vt+1);
						v6 = vertices.get(vt+2);
						
						Line l = new Line(new int[]{v1,v2,v3}, new int[]{v4,v5,v6});
						
						boolean add = true;
						for(int j=0; j<lines.size(); j++){
							if(lines.get(j).compare(l)) {
								add = false;
								break;
							}
						}
						
						if(add) lines.add(l);
						
						v1 = v4;
						v2 = v5;
						v3 = v6;
						
						//The last vertex of a face connects to the first vertex
						//with a line so this creates that final line
						if(i==pieces.length-1){
							v = (pieces[1].indexOf("/")>-1)?(pieces[1].split("/")[0]):(pieces[1]);
							vt = (Integer.parseInt(v)-1)*3;
							v4 = vertices.get(vt);
							v5 = vertices.get(vt+1);
							v6 = vertices.get(vt+2);
							
							l = new Line(new int[]{v1,v2,v3}, new int[]{v4,v5,v6});
							
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
