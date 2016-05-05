package graphics.d3.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import graphics.d3.data.Vector4;
import graphics.d3.objetos.Figura;
import graphics.d3.objetos.Material;
import graphics.d3.objetos.Triangulo;

/**
 * <h1>PLYConverter</h1>
 * CLase para recuperar de un archivo PLY los vertices
 * y caras de una figura
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class PLYConverter {

	/**
	 * @param plyFile path del fichero con extension PLY
	 * @param m material que aplicar a la figura contenida en 
	 * @param plyFile
	 * 
	 * @return figura contenida en @param plyFile, aplicando el 
	 * material 2param m
	 */
	public static Figura getFigura(String plyFile, Material m) {
		Figura returned = new Figura();
		boolean plyfile = false;
		
		boolean data = false;
		boolean fin = false;
		
		ArrayList<Vector4> vertices = new ArrayList<Vector4>();
		ArrayList<Vector4> colors = new ArrayList<Vector4>();
		
		int index = 0;
		int vertexNumber = -1;
		int faceNumber = -1;
		
		int indexX = -1;
		int indexY = -1;
		int indexZ = -1;
		
		int indexRed = -1;
		int indexGreen = -1;
		int indexBlue = -1;
		
		int count = 0;
		
		try {
			Scanner s = new Scanner(new File(plyFile));
			while (s.hasNextLine() && !fin) {
				String line = s.nextLine();
				Scanner lineScanner = new Scanner(line);
				String actual = lineScanner.next();

				// Check ply file
				if (!plyfile) {
					if (actual.equals("ply")) {
						// Yes, its a ply file
						plyfile = true;
					} else {
						s.close();
						lineScanner.close();
						throw new Exception("Thats not a valid ply file!");
					}
				} // End of checking ply file

				else {
					if (!data) {
						// Element line
						if (actual.equals("element")) {
							actual = lineScanner.next();
							// Vertices definition
							if (actual.equals("vertex")) {
								actual = lineScanner.next();
								vertexNumber = Integer.parseInt(actual);
							}

							// Faces definition
							else if (actual.equals("face")) {
								actual = lineScanner.next();
								faceNumber = Integer.parseInt(actual);
							}
							index = 0;
						} // End of element line

						// Property line
						else if (actual.equals("property")) {
							actual = line.substring(line.lastIndexOf(" ") + 1);

							// Check x
							if (actual.equals("x")) {
								indexX = index;
							}

							// Check y
							else if (actual.equals("y")) {
								indexY = index;
							}

							// Check z
							else if (actual.equals("z")) {
								indexZ = index;
							}

							// Check red
							else if (actual.equals("red")) {
								indexRed = index;
							}

							// Check green
							else if (actual.equals("green")) {
								indexGreen = index;
							}

							// Check blue
							else if (actual.equals("blue")) {
								indexBlue = index;
							}

							index++;
						} // End of property line

						// End header line
						else if (actual.equals("end_header")) {
							data = true;
							count = 0;
						}
					} else {
						line.replace("\n", "");
						String[] array = line.split(" ");
						// Vertices
						if (count < vertexNumber) {
							float tempX = Float.parseFloat(array[indexX]) * 10;
							float tempY = Float.parseFloat(array[indexY]) * 10;
							float tempZ = Float.parseFloat(array[indexZ]) * 10;

							float tempR = Float.parseFloat(array[indexRed]);
							float tempG = Float.parseFloat(array[indexGreen]);
							float tempB = Float.parseFloat(array[indexBlue]);

							Vector4 tempV = new Vector4(tempX, tempY, tempZ, 1);
							Vector4 tempC = new Vector4(tempR, tempG, tempB, 1);

							vertices.add(tempV);
							colors.add(tempC);
							
						}
						// Faces
						else if (count<vertexNumber+faceNumber){
							if(Integer.parseInt(array[0]) == 3){
								// Son triangulos
								Vector4 p1 = vertices.get(Integer.parseInt(array[1]));
								Vector4 p2 = vertices.get(Integer.parseInt(array[2]));
								Vector4 p3 = vertices.get(Integer.parseInt(array[3]));
								
								float r = (float) colors.get(Integer.parseInt(array[1])).getX();
								r = r + (float) colors.get(Integer.parseInt(array[2])).getX();
								r = r + (float) colors.get(Integer.parseInt(array[3])).getX();
								r = (float) (r/3.0);
								
								float g = (float) colors.get(Integer.parseInt(array[1])).getY();
								g = g + (float) colors.get(Integer.parseInt(array[2])).getY();
								g = g + (float) colors.get(Integer.parseInt(array[3])).getY();
								g = (float) (g/3.0);
								
								float b = (float) colors.get(Integer.parseInt(array[1])).getZ();
								b = b + (float) colors.get(Integer.parseInt(array[2])).getZ();
								b = b + (float) colors.get(Integer.parseInt(array[3])).getZ();
								b = (float) (b/3.0);
								
								int ir = (int) (r);
								int ig = (int) (g);
								int ib = (int) (b);
								
								Color c = new Color(ir,ig,ib);
								Triangulo t = null;
								if(m == null){
									t = new Triangulo(p1,p2,p3,new Material(c,1,0,0,0,1,0));
								}
								else{
									m.setColor(c);
									t = new Triangulo(p1,p2,p3,m);
								}
								
								returned.addObjeto(t);
							}
							else if(Integer.parseInt(array[0]) != 3){
								// Only triangles!
								lineScanner.close();
								throw new Exception("Only triangles are accepted in your ply file!");
							}
						}
						else{
							fin = true;
						}
						count++;
					}
				}
				lineScanner.close();
			}
			s.close();
			return returned;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
}
