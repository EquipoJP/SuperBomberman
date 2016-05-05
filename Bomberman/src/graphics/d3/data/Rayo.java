package graphics.d3.data;

import graphics.d3.objetos.Objeto;

/**
 * <h1>Rayo</h1>
 * Clase para la creacion de rayos, incluyendo el rayo <i>reflejado</i>
 * y <i>refractado</i>.
 * 
 * @author Patricia Lazaro Tello (554309)
 * @author Alejandro Royo Amondarain (560285)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 * 
 * @version 1.0
 */
public class Rayo {

	/* Atributos de la clase Rayo */
	private Vector4 origin;
	private Vector4 direccion;

	/**
	 * @param origin punto origen del rayo
	 * @param direction vector direccion del rayo
	 */
	public Rayo(Vector4 origin, Vector4 direction) {
		this.origin = origin;
		this.direccion = direction.normalise();
	}

	/**
	 * @return origen del rayo
	 */
	public Vector4 getOrigen() {
		return origin;
	}

	/**
	 * @return direccion del rayo
	 */
	public Vector4 getDireccion() {
		return direccion;
	}

	/**
	 * Crea un rayo a partir de un origen y un final
	 * 
	 * @param origin origen del rayo
	 * @param fin final del rayo
	 * 
	 * @return rayo con origen en @param origin y 
	 * direccion @param fin - @param origin (normalizada) 
	 */
	public static Rayo RayoPcpioFin(Vector4 origin, Vector4 fin) {
		return new Rayo(origin, Vector4.sub(fin, origin).normalise());
	}

	/**
	 * Obtiene el punto 3D en coordenadas homogeneas resultado de
	 * sumar al punto de origen (lambda * direccion)
	 * 
	 * @param ray rayo sobre el que calcular el punto 3D
	 * @param lambda lambda unidades sobre las que se mueve el rayo
	 * 
	 * @return el punto 3D resultado de sumar al punto de 
	 * origen (lambda * direccion)
	 */
	public static Vector4 getInterseccion(Rayo ray, double lambda) {
		return Vector4.add(ray.origin, Vector4.mulEscalar(ray.direccion, lambda));
	}

	 /**
	  * Crea el rayo refractado a partir del rayo incidente, 
	  * el punto y objeto de interseccion y los indices de 
	  * refraccion del medio incidente y transmitido.
	  * 
	  * @param vista rayo incidente con direccion del 
	  * punto de vista al punto de interseccion
	  * @param o objeto con el que intersecta el rayo
	  * @param i punto 3D de interseccion en coordenadas homogeneas
	  * @param eps epsilon necesario para que el nuevo rayo no choque 
	  * con su punto de origen
	  * @param nI indice de refraccion del medio en el que el rayo incide
	  * @param nT indice de refraccion del medio en el que el rayo 
	  * transmitido se mueve
	  * 
	  * @return rayo refractado cuyo origen se encuentra en @param i y que 
	  * pasa del medio cuyo indice de refracion es @param nI al medio cuyo
	  * indice de refraccion es @param nT 
	  */
	 public static Rayo rayoRefractado(Rayo vista, Objeto o, Vector4 i, 
			 double eps, double nI, double nT) {
		Vector4 normal = o.normal(i, vista);
		
		double nR = nI / nT;
		if(o.estaDentro(vista, i)){
			normal = Vector4.negate(normal);
		}
		
		Vector4 vistaNegada = Vector4.negate(vista.getDireccion().normalise());
		
		double nR2 = Math.pow(nR, 2);	// nr^2
		double dotNI = Vector4.dot(normal, vistaNegada);	// N·I
		double dotNI2 = Math.pow(dotNI, 2);	// (N·I)^2
		
		// sqrt(1- nr^2 * (1 - (N·I)^2)
		double insideRoot = 1 - (nR2 * (1 - dotNI2));
		double root = Math.sqrt(insideRoot);
		
		double cosTitaT = root;
		double cosTitaI = dotNI;
		
		Vector4 primerTerm = Vector4.mulEscalar(normal, (nR * cosTitaI) - cosTitaT);
		Vector4 segundoTerm = Vector4.mulEscalar(vistaNegada, nR);
		
		Vector4 direccionT = Vector4.sub(primerTerm, segundoTerm).normalise();
		Vector4 origenT = Vector4.add(i, Vector4.mulEscalar(direccionT, eps));
		
		return new Rayo(origenT, direccionT);
	}

	 /**
	  * Crea el rayo reflejado a partir del rayo incidente, el punto
	  * de incidencia y el objeto sobre el que se ha incidido.
	  * 
	  * @param sombra rayo incidente
	  * @param o objeto sobre el que se incide
	  * @param i punto 3D incidente en coordenadas homogeneas
	  * @param eps epsilon necesario para que el nuevo rayo no choque 
	  * con su punto de origen
	  * @return rayo relejado cuyo origen se encuentra en @param i
	  */
	public static Rayo rayoReflejado(Rayo sombra, Objeto o, Vector4 i, double eps) {
		Vector4 luz = sombra.getDireccion();
		Vector4 normal = o.normal(i, sombra);

		double iln = Vector4.dot(luz, normal);
		Vector4 _2n = Vector4.mulEscalar(normal, 2);
		Vector4 _2ndot = Vector4.mulEscalar(_2n, iln);

		Vector4 reflejado = Vector4.sub(luz, _2ndot);
		reflejado = reflejado.normalise();
		reflejado = Vector4.negate(reflejado);

		/* Construccion del rayo devuelto */
		double epsilon = eps;
		Rayo returned = new Rayo(Vector4.add(i, Vector4.mulEscalar(reflejado, epsilon)), reflejado);
		return returned;
	}
}
