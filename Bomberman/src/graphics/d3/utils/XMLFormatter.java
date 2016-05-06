package graphics.d3.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Jama.Matrix;
import graphics.d3.data.Vector4;
import graphics.d3.objetos.Caja;
import graphics.d3.objetos.Figura;
import graphics.d3.objetos.Material;
import graphics.d3.objetos.Objeto;
import graphics.d3.objetos.Plano;
import graphics.d3.trazador.Camara;
import graphics.d3.trazador.Foco;
import utils.FileUtils;

/**
 * <h1>XMLFormatter</h1> Clase para parsear el fichero XML que contiene la
 * escena
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class XMLFormatter {

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return documento XML del fichero
	 */
	private static Document setup(String xml) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			return db.parse(FileUtils.getFile(xml));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return numero de threads o hilos
	 */
	public static int setMultiThreading(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();
		return Integer.parseInt(doc.getDocumentElement().getAttribute("multithreading"));
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return <b>true</b> si se usan cajas envolventes, <b>false</b> en caso
	 *         contrario
	 */
	public static boolean setCajas(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();
		return Integer.parseInt(doc.getDocumentElement().getAttribute("cajas")) == 1;
	}

	/**
	 * @param xml
	 *            path del fichero MXL
	 * @return una tabla de booleanos con los flags a aplicar. En concreto:
	 *         <p>
	 *         <ol>
	 *         <li>Componente ambiental</li>
	 *         <li>Componente difusa</li>
	 *         <li>Componente especular</li>
	 *         <li>Componente de reflexion</li>
	 *         <li>Componente de refraccion</li>
	 *         </ol>
	 */
	public static boolean[] setFlags(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		NodeList nl = doc.getElementsByTagName("debug");
		if (nl != null && nl.getLength() > 0) {
			Element e = (Element) nl.item(0);

			int ambiente = Integer.parseInt(e.getElementsByTagName("ambiente").item(0).getTextContent());
			int difusa = Integer.parseInt(e.getElementsByTagName("difusa").item(0).getTextContent());
			int especular = Integer.parseInt(e.getElementsByTagName("especular").item(0).getTextContent());

			boolean returned[] = new boolean[5];
			returned[0] = (ambiente == 1);
			returned[1] = (difusa == 1);
			returned[2] = (especular == 1);

			return returned;
		} else {
			return null;
		}
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return numero de rayos a lanzar por pixel
	 */
	public static int getAntialiasing(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		return Integer.parseInt(doc.getDocumentElement().getAttribute("antialiasing"));
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return numero de rebotes de los rayos
	 */
	public static int getRebotes(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		return Integer.parseInt(doc.getDocumentElement().getAttribute("rebotes"));
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return componente de luz ambiental
	 */
	public static double getLuzAmbiente(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		return Double.parseDouble(doc.getDocumentElement().getAttribute("ambiente"));
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return epsilon
	 */
	public static double getEpsilon(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		return Double.parseDouble(doc.getDocumentElement().getAttribute("epsilon"));
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return lista de focos
	 */
	public static List<Foco> getFocos(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		List<Foco> focos = new ArrayList<Foco>();

		NodeList nl = doc.getElementsByTagName("focos");
		if (nl != null && nl.getLength() > 0) {
			Element e = (Element) nl.item(0);
			NodeList nl2 = e.getElementsByTagName("foco");
			for (int i = 0; i < nl2.getLength(); i++) {
				e = (Element) nl2.item(i);

				// posicion
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				double z = Double.parseDouble(e.getAttribute("z"));
				Vector4 posicion = new Vector4(x, y, z, 1);

				// intensidad
				int intensidad = Integer.parseInt(e.getElementsByTagName("intensidad").item(0).getTextContent());

				// color
				int r = Integer.parseInt(((Element) e.getElementsByTagName("color").item(0)).getAttribute("r"));
				int g = Integer.parseInt(((Element) e.getElementsByTagName("color").item(0)).getAttribute("g"));
				int b = Integer.parseInt(((Element) e.getElementsByTagName("color").item(0)).getAttribute("b"));
				Color c = new Color(r, g, b);

				focos.add(new Foco(posicion, c, intensidad));
			}
		}

		return focos;
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return camara
	 */
	public static Camara getCamara(String xml) {
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		NodeList nl = doc.getElementsByTagName("pov");
		if (nl != null && nl.getLength() > 0) {
			Element e = (Element) nl.item(0);

			double x = Double.parseDouble(e.getAttribute("x"));
			double y = Double.parseDouble(e.getAttribute("y"));
			double z = Double.parseDouble(e.getAttribute("z"));
			Vector4 posicion = new Vector4(x, y, z, 1);

			int columnas = Integer.parseInt(((Element) e.getElementsByTagName("columnas").item(0)).getTextContent());
			int filas = Integer.parseInt(((Element) e.getElementsByTagName("filas").item(0)).getTextContent());
			int anchura = Integer.parseInt(((Element) e.getElementsByTagName("anchura").item(0)).getTextContent());
			int altura = Integer.parseInt(((Element) e.getElementsByTagName("altura").item(0)).getTextContent());

			int f = Integer.parseInt(((Element) e.getElementsByTagName("focal").item(0)).getTextContent());

			Vector4 g = Vector4.sub(new Vector4(0, 0, 0, 1), posicion).normalise();

			return new Camara(posicion, g, f, columnas, filas, anchura, altura);
		}
		return null;
	}

	/**
	 * @param xml
	 *            path del fichero XML
	 * @return bomberman
	 */
	public static Objeto getBomberman(String xml) {
		boolean cajas = setCajas(xml);
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		Objeto bomberman = null;

		NodeList nl = doc.getElementsByTagName("cajas");
		if (nl != null && nl.getLength() > 0) {
			NodeList nl2 = ((Element) nl.item(0)).getElementsByTagName("caja");
			if (nl2 != null && nl2.getLength() > 0) {
				Caja caja = new Caja();

				NodeList nl3_figuras = ((Element) nl2.item(0)).getElementsByTagName("bomberman");

				Element e = (Element) nl3_figuras.item(0);
				String path = e.getAttribute("path");

				//////////////////////////////////////////////////////////
				// traslacion
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				double z = Double.parseDouble(e.getAttribute("z"));

				// escala global
				Element ee = (Element) e.getElementsByTagName("global").item(0);
				double global = Double.parseDouble(ee.getTextContent());

				// escala local
				ee = (Element) e.getElementsByTagName("escala").item(0);
				double escalaX = Double.parseDouble(ee.getAttribute("x"));
				double escalaY = Double.parseDouble(ee.getAttribute("y"));
				double escalaZ = Double.parseDouble(ee.getAttribute("z"));

				// rotacion
				ee = (Element) e.getElementsByTagName("rotacion").item(0);
				double rotacionX = Double.parseDouble(ee.getAttribute("x"));
				double rotacionY = Double.parseDouble(ee.getAttribute("y"));
				double rotacionZ = Double.parseDouble(ee.getAttribute("z"));

				// simetria
				ee = (Element) e.getElementsByTagName("simetria").item(0);
				int simetriaX = Integer.parseInt(ee.getAttribute("x"));
				int simetriaY = Integer.parseInt(ee.getAttribute("y"));
				int simetriaZ = Integer.parseInt(ee.getAttribute("z"));

				// cizalla
				ee = (Element) e.getElementsByTagName("cizalla").item(0);
				double cizallaX = Double.parseDouble(ee.getAttribute("x"));
				double cizallaY = Double.parseDouble(ee.getAttribute("y"));
				double cizallaZ = Double.parseDouble(ee.getAttribute("z"));

				// order
				ee = (Element) e.getElementsByTagName("order").item(0);
				int order = Integer.parseInt(ee.getTextContent());
				///////////////////////////////////////////////////////////////////

				//////////////////////////////////////////////////////////////////
				// material
				ee = (Element) e.getElementsByTagName("material").item(0);

				// difusa
				double difusa = Double.parseDouble(ee.getElementsByTagName("difusa").item(0).getTextContent());

				// especular
				double especular = Double.parseDouble(ee.getElementsByTagName("especular").item(0).getTextContent());

				// reflectante
				double reflectante = Double
						.parseDouble(ee.getElementsByTagName("reflectante").item(0).getTextContent());

				// transparente
				double transparente = Double
						.parseDouble(ee.getElementsByTagName("transparente").item(0).getTextContent());

				// shiny
				int shiny = Integer.parseInt(ee.getElementsByTagName("shiny").item(0).getTextContent());

				// indiceRefraccion
				double indRef = Double
						.parseDouble(ee.getElementsByTagName("indiceRefraccion").item(0).getTextContent());
				//////////////////////////////////////////////////////////////////////////////

				Material m = new Material(null, difusa, especular, reflectante, transparente, indRef, shiny);
				Figura f = PLYConverter.getFigura(path, m);

				Matrix T = TransformacionesAfines.affineMatrix(x, y, z, escalaX, escalaY, escalaZ, global,
						(simetriaX > 0), (simetriaY > 0), (simetriaZ > 0), rotacionX, rotacionY, rotacionZ, cizallaX,
						cizallaY, cizallaZ, order);

				f.setT(T);

				if (cajas) {
					caja.addObjeto(f);
					bomberman = caja;
				} else {
					bomberman = f;
				}
			}
		}
		return bomberman;
	}
	
	/**
	 * @param xml
	 *            path del fichero XML
	 * @return bomberman
	 */
	public static Objeto getEnemigo(String xml) {
		boolean cajas = setCajas(xml);
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		Objeto enemigo = null;

		NodeList nl = doc.getElementsByTagName("cajas");
		if (nl != null && nl.getLength() > 0) {
			NodeList nl2 = ((Element) nl.item(0)).getElementsByTagName("caja");
			if (nl2 != null && nl2.getLength() > 0) {
				Caja caja = new Caja();

				NodeList nl3_figuras = ((Element) nl2.item(0)).getElementsByTagName("enemigo");

				Element e = (Element) nl3_figuras.item(0);
				String path = e.getAttribute("path");

				//////////////////////////////////////////////////////////
				// traslacion
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				double z = Double.parseDouble(e.getAttribute("z"));

				// escala global
				Element ee = (Element) e.getElementsByTagName("global").item(0);
				double global = Double.parseDouble(ee.getTextContent());

				// escala local
				ee = (Element) e.getElementsByTagName("escala").item(0);
				double escalaX = Double.parseDouble(ee.getAttribute("x"));
				double escalaY = Double.parseDouble(ee.getAttribute("y"));
				double escalaZ = Double.parseDouble(ee.getAttribute("z"));

				// rotacion
				ee = (Element) e.getElementsByTagName("rotacion").item(0);
				double rotacionX = Double.parseDouble(ee.getAttribute("x"));
				double rotacionY = Double.parseDouble(ee.getAttribute("y"));
				double rotacionZ = Double.parseDouble(ee.getAttribute("z"));

				// simetria
				ee = (Element) e.getElementsByTagName("simetria").item(0);
				int simetriaX = Integer.parseInt(ee.getAttribute("x"));
				int simetriaY = Integer.parseInt(ee.getAttribute("y"));
				int simetriaZ = Integer.parseInt(ee.getAttribute("z"));

				// cizalla
				ee = (Element) e.getElementsByTagName("cizalla").item(0);
				double cizallaX = Double.parseDouble(ee.getAttribute("x"));
				double cizallaY = Double.parseDouble(ee.getAttribute("y"));
				double cizallaZ = Double.parseDouble(ee.getAttribute("z"));

				// order
				ee = (Element) e.getElementsByTagName("order").item(0);
				int order = Integer.parseInt(ee.getTextContent());
				///////////////////////////////////////////////////////////////////

				//////////////////////////////////////////////////////////////////
				// material
				ee = (Element) e.getElementsByTagName("material").item(0);

				// difusa
				double difusa = Double.parseDouble(ee.getElementsByTagName("difusa").item(0).getTextContent());

				// especular
				double especular = Double.parseDouble(ee.getElementsByTagName("especular").item(0).getTextContent());

				// reflectante
				double reflectante = Double
						.parseDouble(ee.getElementsByTagName("reflectante").item(0).getTextContent());

				// transparente
				double transparente = Double
						.parseDouble(ee.getElementsByTagName("transparente").item(0).getTextContent());

				// shiny
				int shiny = Integer.parseInt(ee.getElementsByTagName("shiny").item(0).getTextContent());

				// indiceRefraccion
				double indRef = Double
						.parseDouble(ee.getElementsByTagName("indiceRefraccion").item(0).getTextContent());
				//////////////////////////////////////////////////////////////////////////////

				Material m = new Material(null, difusa, especular, reflectante, transparente, indRef, shiny);
				Figura f = PLYConverter.getFigura(path, m);

				Matrix T = TransformacionesAfines.affineMatrix(x, y, z, escalaX, escalaY, escalaZ, global,
						(simetriaX > 0), (simetriaY > 0), (simetriaZ > 0), rotacionX, rotacionY, rotacionZ, cizallaX,
						cizallaY, cizallaZ, order);

				f.setT(T);

				if (cajas) {
					caja.addObjeto(f);
					enemigo = caja;
				} else {
					enemigo = f;
				}
			}
		}
		return enemigo;
	}
	
	/**
	 * @param xml
	 *            path del fichero XML
	 * @return bomberman
	 */
	public static Objeto getBloque(String xml) {
		boolean cajas = setCajas(xml);
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();

		Objeto bloque = null;

		NodeList nl = doc.getElementsByTagName("cajas");
		if (nl != null && nl.getLength() > 0) {
			NodeList nl2 = ((Element) nl.item(0)).getElementsByTagName("caja");
			if (nl2 != null && nl2.getLength() > 0) {
				Caja caja = new Caja();

				NodeList nl3_figuras = ((Element) nl2.item(0)).getElementsByTagName("bloque");

				Element e = (Element) nl3_figuras.item(0);
				String path = e.getAttribute("path");

				//////////////////////////////////////////////////////////
				// traslacion
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				double z = Double.parseDouble(e.getAttribute("z"));

				// escala global
				Element ee = (Element) e.getElementsByTagName("global").item(0);
				double global = Double.parseDouble(ee.getTextContent());

				// escala local
				ee = (Element) e.getElementsByTagName("escala").item(0);
				double escalaX = Double.parseDouble(ee.getAttribute("x"));
				double escalaY = Double.parseDouble(ee.getAttribute("y"));
				double escalaZ = Double.parseDouble(ee.getAttribute("z"));

				// rotacion
				ee = (Element) e.getElementsByTagName("rotacion").item(0);
				double rotacionX = Double.parseDouble(ee.getAttribute("x"));
				double rotacionY = Double.parseDouble(ee.getAttribute("y"));
				double rotacionZ = Double.parseDouble(ee.getAttribute("z"));

				// simetria
				ee = (Element) e.getElementsByTagName("simetria").item(0);
				int simetriaX = Integer.parseInt(ee.getAttribute("x"));
				int simetriaY = Integer.parseInt(ee.getAttribute("y"));
				int simetriaZ = Integer.parseInt(ee.getAttribute("z"));

				// cizalla
				ee = (Element) e.getElementsByTagName("cizalla").item(0);
				double cizallaX = Double.parseDouble(ee.getAttribute("x"));
				double cizallaY = Double.parseDouble(ee.getAttribute("y"));
				double cizallaZ = Double.parseDouble(ee.getAttribute("z"));

				// order
				ee = (Element) e.getElementsByTagName("order").item(0);
				int order = Integer.parseInt(ee.getTextContent());
				///////////////////////////////////////////////////////////////////

				//////////////////////////////////////////////////////////////////
				// material
				ee = (Element) e.getElementsByTagName("material").item(0);

				// difusa
				double difusa = Double.parseDouble(ee.getElementsByTagName("difusa").item(0).getTextContent());

				// especular
				double especular = Double.parseDouble(ee.getElementsByTagName("especular").item(0).getTextContent());

				// reflectante
				double reflectante = Double
						.parseDouble(ee.getElementsByTagName("reflectante").item(0).getTextContent());

				// transparente
				double transparente = Double
						.parseDouble(ee.getElementsByTagName("transparente").item(0).getTextContent());

				// shiny
				int shiny = Integer.parseInt(ee.getElementsByTagName("shiny").item(0).getTextContent());

				// indiceRefraccion
				double indRef = Double
						.parseDouble(ee.getElementsByTagName("indiceRefraccion").item(0).getTextContent());
				//////////////////////////////////////////////////////////////////////////////

				Material m = new Material(null, difusa, especular, reflectante, transparente, indRef, shiny);
				Figura f = PLYConverter.getFigura(path, m);

				Matrix T = TransformacionesAfines.affineMatrix(x, y, z, escalaX, escalaY, escalaZ, global,
						(simetriaX > 0), (simetriaY > 0), (simetriaZ > 0), rotacionX, rotacionY, rotacionZ, cizallaX,
						cizallaY, cizallaZ, order);

				f.setT(T);

				if (cajas) {
					caja.addObjeto(f);
					bloque = caja;
				} else {
					bloque = f;
				}
			}
		}
		return bloque;
	}
	
	/**
	 * @param xml
	 *            path del fichero XML
	 * @return lista de objetos ordenados jerarquicamente
	 */
	public static Objeto getPlano(String xml) {
		boolean cajas = setCajas(xml);
		Document doc = setup(xml);
		doc.getDocumentElement().normalize();
		
		Objeto plano = null;

		NodeList nl = doc.getElementsByTagName("cajas");
		if (nl != null && nl.getLength() > 0) {
			NodeList nl2 = ((Element) nl.item(0)).getElementsByTagName("caja");
			for (int i = 0; i < nl2.getLength(); i++) {
				Caja caja = new Caja();

				NodeList nl3_planos = ((Element) nl2.item(i)).getElementsByTagName("plano");

				// Planos
				for (int j = 0; j < nl3_planos.getLength(); j++) {
					Element e = (Element) nl3_planos.item(j);

					/////////////////////////////////////////////////////////
					Element ee = (Element) e.getElementsByTagName("topleft").item(0);
					Vector4 topleft = new Vector4(Double.parseDouble(ee.getAttribute("x")),
							Double.parseDouble(ee.getAttribute("y")), Double.parseDouble(ee.getAttribute("z")), 1);

					ee = (Element) e.getElementsByTagName("topright").item(0);
					Vector4 topright = new Vector4(Double.parseDouble(ee.getAttribute("x")),
							Double.parseDouble(ee.getAttribute("y")), Double.parseDouble(ee.getAttribute("z")), 1);

					ee = (Element) e.getElementsByTagName("bottomleft").item(0);
					Vector4 bottomleft = new Vector4(Double.parseDouble(ee.getAttribute("x")),
							Double.parseDouble(ee.getAttribute("y")), Double.parseDouble(ee.getAttribute("z")), 1);

					ee = (Element) e.getElementsByTagName("bottomright").item(0);
					Vector4 bottomright = new Vector4(Double.parseDouble(ee.getAttribute("x")),
							Double.parseDouble(ee.getAttribute("y")), Double.parseDouble(ee.getAttribute("z")), 1);
							////////////////////////////////////////////////////////

					//////////////////////////////////////////////////////////
					// traslacion
					double x = Double.parseDouble(e.getAttribute("x"));
					double y = Double.parseDouble(e.getAttribute("y"));
					double z = Double.parseDouble(e.getAttribute("z"));

					// escala global
					ee = (Element) e.getElementsByTagName("global").item(0);
					double global = Double.parseDouble(ee.getTextContent());

					// escala local
					ee = (Element) e.getElementsByTagName("escala").item(0);
					double escalaX = Double.parseDouble(ee.getAttribute("x"));
					double escalaY = Double.parseDouble(ee.getAttribute("y"));
					double escalaZ = Double.parseDouble(ee.getAttribute("z"));

					// rotacion
					ee = (Element) e.getElementsByTagName("rotacion").item(0);
					double rotacionX = Double.parseDouble(ee.getAttribute("x"));
					double rotacionY = Double.parseDouble(ee.getAttribute("y"));
					double rotacionZ = Double.parseDouble(ee.getAttribute("z"));

					// simetria
					ee = (Element) e.getElementsByTagName("simetria").item(0);
					int simetriaX = Integer.parseInt(ee.getAttribute("x"));
					int simetriaY = Integer.parseInt(ee.getAttribute("y"));
					int simetriaZ = Integer.parseInt(ee.getAttribute("z"));

					// cizalla
					ee = (Element) e.getElementsByTagName("cizalla").item(0);
					int cizallaX = Integer.parseInt(ee.getAttribute("x"));
					int cizallaY = Integer.parseInt(ee.getAttribute("y"));
					int cizallaZ = Integer.parseInt(ee.getAttribute("z"));

					// order
					ee = (Element) e.getElementsByTagName("order").item(0);
					int order = Integer.parseInt(ee.getTextContent());
					///////////////////////////////////////////////////////////////////

					//////////////////////////////////////////////////////////////////
					// material
					ee = (Element) e.getElementsByTagName("material").item(0);

					// material -> color
					Element eee = (Element) e.getElementsByTagName("color").item(0);
					Color c = new Color(Integer.parseInt(eee.getAttribute("r")),
							Integer.parseInt(eee.getAttribute("g")), Integer.parseInt(eee.getAttribute("b")));

					// difusa
					double difusa = Double.parseDouble(ee.getElementsByTagName("difusa").item(0).getTextContent());

					// especular
					double especular = Double
							.parseDouble(ee.getElementsByTagName("especular").item(0).getTextContent());

					// reflectante
					double reflectante = Double
							.parseDouble(ee.getElementsByTagName("reflectante").item(0).getTextContent());

					// transparente
					double transparente = Double
							.parseDouble(ee.getElementsByTagName("transparente").item(0).getTextContent());

					// shiny
					int shiny = Integer.parseInt(ee.getElementsByTagName("shiny").item(0).getTextContent());

					// indiceRefraccion
					double indRef = Double
							.parseDouble(ee.getElementsByTagName("indiceRefraccion").item(0).getTextContent());
					//////////////////////////////////////////////////////////////////////////////

					Material m = new Material(c, difusa, especular, reflectante, transparente, indRef, shiny);
					Matrix T = TransformacionesAfines.affineMatrix(x, y, z, escalaX, escalaY, escalaZ, global,
							(simetriaX > 0), (simetriaY > 0), (simetriaZ > 0), rotacionX, rotacionY, rotacionZ,
							cizallaX, cizallaY, cizallaZ, order);

					Plano plane = new Plano(topleft, topright, bottomleft, bottomright, m, T);
					if (cajas) {
						caja.addObjeto(plane);
						plano = caja;
					} else {
						plano = plane;
					}
				}
			}
		}
		return plano;
	}
}
