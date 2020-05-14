import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Satcliqueproblem {

	/*
	 * 4 clausulas con 3 literales cada uno, son en total 12 vertices. Cada vertice
	 * almacena el literal true o false y la clausula a la que pertenece.HashMap
	 * <Key, Value> donde key es el id y value es la clausula y el literal true o
	 * false .
	 */
	private HashMap<Integer, String> vertices = new HashMap<Integer, String>();

	private ArrayList<String> conexiones = new ArrayList<String>();
	/* En este arraylist se almacenan todos los posibles 4cliques. */
	private ArrayList<String> cuatroCliques = new ArrayList<String>();

	/*
	 * Este metodo imprime el mapa con sus claves (key) y valores.
	 */
	public void printVerticesMap(HashMap<Integer, String> hmap) {
		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			System.out.print("Key: " + mentry.getKey() + " -> Value: ");
			System.out.println(mentry.getValue());
		}
	}
	//o(n)= n t(n) = 2+ 2+ n(2 + 1 + 2 + 1 + 1 + 2) , t(n) = 9n + 4
	/* Metodo que va solicitando conexiones entre todos los vertices. */
	public void setConexiones() {
		int i = 1;
		int j = 2;
		while (j <= 12 && i <= 12) {
			if (i != j) {
				conecta(vertices.get(i), vertices.get(j));
				// System.out.println("Conexiones entre " + i + " y " + j);
			}
			j++;
			if (j == 13) {
				i++;
				j = 1;
			}

		}

	}

	/* Metodo que crea conexiones entre vertices. */
	public void conecta(String vertice1, String vertice2) {
		/*
		 * Cada valor contiene clausula separado por "-" y luego el literal. Esta
		 * dispuesto de esta manera, ya que, esta prohibido unir dos vertices de la
		 * misma clausula y que sean los literales opuestos.
		 */
		int posSeparadorV1 = vertice1.indexOf("-");
		String clausulaV1 = vertice1.substring(0, posSeparadorV1);
		String literalV1 = vertice1.substring(posSeparadorV1 + 1, vertice1.length());
		String primeraLetraV1 = vertice1.substring(posSeparadorV1 + 1, posSeparadorV1 + 2);
		boolean marcaV1 = true;
		/* Si es del tipo negado pongo una marca y me quedo con el literal en true. */
		if (!primeraLetraV1.equals("x")) {
			marcaV1 = false;
			/* Nos saltamos el no. */
			literalV1 = vertice1.substring(posSeparadorV1 + 3, vertice1.length());
		}

		int posSeparadorV2 = vertice2.indexOf("-");
		String clausulaV2 = vertice2.substring(0, posSeparadorV2);
		String literalV2 = vertice2.substring(posSeparadorV2 + 1, vertice2.length());
		String primeraLetraV2 = vertice2.substring(posSeparadorV2 + 1, posSeparadorV2 + 2);
		boolean marcaV2 = true;
		/* Si es del tipo negado pongo una marca y me quedo con el literal en true. */
		if (!primeraLetraV2.equals("x")) {
			marcaV2 = false;
			/* Nos saltamos el no. */
			literalV2 = vertice2.substring(posSeparadorV2 + 3, vertice2.length());
		}
		if (!clausulaV1.equals(clausulaV2)) {
			/* Caso literales opuestos. */
			if (literalV1.equals(literalV2) && marcaV1 == marcaV2) {
				conexiones.add(vertice1 + "<---->" + vertice2);
				/* Caso de literales diferentes. */
			} else if (!literalV1.equals(literalV2)) {
				conexiones.add(vertice1 + "<---->" + vertice2);
			}
		}

	}

	/*
	 * Este metodo elimina las direcciones dobles, es decir, C1-x1<---->C2-x1 y
	 * C2-x1<---->C1-x1
	 */
	public void quitarConexionesDirigidas() {

		//1 + 1 + n(1+10) = 11n + 2
		for (int i = 0; i < conexiones.size(); i++) {
			int posV1 = conexiones.get(i).indexOf("<");
			int posV2 = conexiones.get(i).indexOf(">");
			String V1 = conexiones.get(i).substring(0, posV1);
			String V2 = conexiones.get(i).substring(posV2 + 1, conexiones.get(i).length());
			conexiones.remove(V2 + "<---->" + V1);
		}

	}

	/*
	 * Como esto es un ejemplo de que es capaz de encontrar un subgrafo cualquiera,
	 * este metodo te encuentra el primer subgrafo posible desde el vertice
	 * introducido por parametro.
	 */
	//26n + 15
	public void calculate4clique(int posComienzo) {
		/*
		 * Tenemos que recorrer el arraylist. Y escoger 4 vertices siguiendo el
		 * recorrido.
		 */
		//1
		String cliquevertices[] = new String[4];

		//8
		/* La primera conexion tiene el vertice 1 y 2 */
		int posV1 = conexiones.get(posComienzo).indexOf("<");
		int posV2 = conexiones.get(posComienzo).indexOf(">");
		String V1 = conexiones.get(posComienzo).substring(0, posV1);
		String V2 = conexiones.get(posComienzo).substring(posV2 + 1, conexiones.get(posComienzo).length());
		//2
		cliquevertices[0] = V1;
		cliquevertices[1] = V2;

		//3
		int posAvanza = 1;
		boolean foundV3 = false;
		boolean foundV4 = false;
		// 1+ n( 1 + 25 ) = 26n + 1
		while (foundV4 == false) {
			//4
			int posV2Buscar = conexiones.get(posAvanza).indexOf("<");
			String V2Buscar = conexiones.get(posAvanza).substring(0, posV2Buscar);

			//2
			/* Si encontramos el vertice 2 hay que buscar en sus conexiones */
			if (V2.equals(V2Buscar) && foundV3 == false) {
				//6
				posAvanza++;
				int posV3 = conexiones.get(posAvanza).indexOf(">");
				String V3 = conexiones.get(posAvanza).substring(posV3 + 1, conexiones.get(posAvanza).length());
				/* Este vertice tercero nos vale. */
				cliquevertices[2] = V3;
				/* Con este marcador, nos quedamos con el primero que encontremos. */
				foundV3 = true;
			}
			//1
			posAvanza++;
			/* Caso V3 hallado, buscamos V4 */
			//1
			if (foundV3 == true) {
				//4
				int posV3Buscar = conexiones.get(posAvanza).indexOf("<");
				String V3Buscar = conexiones.get(posAvanza).substring(0, posV3Buscar);
				//2
				if (cliquevertices[2].equals(V3Buscar) && foundV4 == false) {
					//1
					posAvanza++;
					//4
					int posV4 = conexiones.get(posAvanza).indexOf(">");
					String V4 = conexiones.get(posAvanza).substring(posV4 + 1, conexiones.get(posAvanza).length());
					/* Ultimo vertice hallado. */
					//2
					cliquevertices[3] = V4;

					foundV4 = true;
				}
			}
		}
		cuatroCliques.add("Clique : " + cliquevertices[0] + ", " + cliquevertices[1] + ", " + cliquevertices[2] + " y "
				+ cliquevertices[3]);
	}
    //T(n) =  + 102
	public static void main(String args[]) {
		/* Primero, se transforma el 3SAT a un grafo. Despues, se halla el 4clique. */
		/* Hay 3 literales en cada clausula pero pueden existir varios. */

		//complejidad 3
		Satcliqueproblem objeto = new Satcliqueproblem();
		//1
		String literalestrue[] = { "x1", "x2", "x3", "x4" };
		//1
		String literalesfalse[] = { "nox1", "nox2", "nox3", "nox4" };
		/* Como maximo hay 4 clausulas. */
		//1
		String clausulas[] = { "C1", "C2", "C3", "C4" };
		System.out.println("Reducci�n 3SAT a 4Clique");
		System.out.println("Partimos del siguiente 3SAT:");

		/*
		 * Creamos los vertices manualmente, formado por clausula y literal para
		 * identificarlos.
		 */
		//1
		ArrayList<String> clausulaLiteral = new ArrayList<String>();
		/* Clausula 1 */
		// 2 accesos y una asignacion = 3 * 3 = 9
		clausulaLiteral.add(clausulas[0] + "-" + literalestrue[0]);
		clausulaLiteral.add(clausulas[0] + "-" + literalestrue[1]);
		clausulaLiteral.add(clausulas[0] + "-" + literalestrue[2]);
		// 4 accesos
		System.out
				.println(clausulas[0] + " : " + literalestrue[0] + ", " + literalestrue[1] + " y " + literalestrue[2]);
		/* Clausula 2 */
		// 9
		clausulaLiteral.add(clausulas[1] + "-" + literalestrue[0]);
		clausulaLiteral.add(clausulas[1] + "-" + literalesfalse[1]);
		clausulaLiteral.add(clausulas[1] + "-" + literalestrue[3]);
		// 4
		System.out
				.println(clausulas[1] + " : " + literalestrue[0] + ", " + literalesfalse[1] + " y " + literalestrue[3]);
		/* Clausula 3 */
		// 9
		clausulaLiteral.add(clausulas[2] + "-" + literalesfalse[0]);
		clausulaLiteral.add(clausulas[2] + "-" + literalesfalse[1]);
		clausulaLiteral.add(clausulas[2] + "-" + literalesfalse[2]);
		// 4
		System.out.println(
				clausulas[2] + " : " + literalesfalse[0] + ", " + literalesfalse[1] + " y " + literalesfalse[2]);
		/* Clausula 4 */
		// 9
		clausulaLiteral.add(clausulas[3] + "-" + literalesfalse[0]);
		clausulaLiteral.add(clausulas[3] + "-" + literalestrue[1]);
		clausulaLiteral.add(clausulas[3] + "-" + literalesfalse[2]);
		// 4
		System.out.println(
				clausulas[3] + " : " + literalesfalse[0] + ", " + literalestrue[1] + " y " + literalesfalse[2]);
		System.out.println("Almacenamos los vertices:");
		// 1
		int idVertice = 1;
		// 1 + n (2+1) = 3n+1
		while (idVertice <= 12) {
			/* Se almacenan los vertices */
			objeto.vertices.put(idVertice, clausulaLiteral.get(idVertice - 1));
			idVertice++;
		}
		/* Se imprime mapa de vertices. */

		// n lineal
		objeto.printVerticesMap(objeto.vertices);
		/* Creamos las conexiones */
		// 1
		System.out.println("Ahora se crean las conexiones, form�ndose un grafo:");
		//t(n) = 9n + 4
		objeto.setConexiones();
		// 11n + 2
		objeto.quitarConexionesDirigidas();
		//1
		int numConexiones = 0;
		/* Se imprime el arraylist de conexiones */
		// 1+ 1+ n(1 + 1) = 2n + 2
		for (int i = 0; i < objeto.conexiones.size(); i++) {
			System.out.println(objeto.conexiones.get(i));
			numConexiones++;
		}
		//2
		System.out.println("Grafo con " + numConexiones + " conexiones.");
		System.out.println("Ahora se calcula el subgrafo 4 clique.");
		
		/* Para formarse un 4-clique hay que escoger 4 vertices */
		/*Empieza en la conexion 2 para que salga nuestro ejemplo documentado.*/

		// 26n + 15
		objeto.calculate4clique(2);
		/* Se imprime el arraylist del 4clique */
		// 1 + 1 + n(1 + 1) = 2n + 2
		for (int i = 0; i < objeto.cuatroCliques.size(); i++) {
			System.out.println(objeto.cuatroCliques.get(i));
		}
	}

}
