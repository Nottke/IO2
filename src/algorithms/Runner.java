package algorithms;

import algorithms.SimulatedAnnealing.CoolingType;
import algorithms.Tour.InitialSolution;

/* Runner class
 * Clase principal que implementa algoritmos metaheuristicos para
 * resolver instancias del TSP (Travelling Salesman Problem) 
 *
 * @autor  Leslie Perez Caceres
 * @version 1.0
 *
 *
 * ICI4144 Investigacion de Operaciones
 * 
 * REVISE LAS INTRUCCIONES DISPONIBLES EN PDF EN EL AULA VIRTUAL
 * 
 * Instrucciones generales:
 *  Implemente los metodos necesarios en este template 
 *  (clase SimulatedAnnealing.java) para poder ejecutar Simulated Annealing 
 *  para resolver el problema del vendedor viajero (TSP).
 *  
 *  En TSP.java implemente los metodos:
 *  - compute_tour_length 
 *  
 *  En SimulatedAnnealing.java implemente los metodos:
 *  - getAcceptanceProbability
 *  - reduceTemperature
 *
 * Informacion general: 
 *
 *  La clase TSPlibReader implementa metodos para leer instancias de TSP
 *  en los diferentes formatos de TSPLIB. 
 *  
 *  La clase TSP provee las interfaces para obtener distancias e implementa
 *  metodos heuristicos para construir soluciones del TSP.
 *  
 *  La clase Tour consiste en un tour de TSP junto con su costo. 
 *
 * Notas importantes:
 *  - En este codigo las soluciones del TSP (en la clase Tour) se 
 *    representan con un arreglo de enteros de tamano n+1, donde n 
 *    son los nodos (ciudades) del TSP y la ultima ciudad del tour 
 *    corresponde siempre a la primera ciudad.
 *    Ejemplo para tsp n = 5:
 *        2 4 3 1 0 2
 */

enum MHType
/* Metaheuristicas disponibles
 *   SA: simulated annealing.
 */
{
    SA
};

enum TSPMove
/* Movimientos de TSP disponibles
 *   TWO_OPT: operador 2-opt
 *   SWAP: operador swap
 */
{
    TWO_OPT, SWAP
};

public class Runner {
    /* active esta variable para probar la funcion objetivo*/
    static boolean test_flag = true;
  
    static void print_header () {
        System.out.println ("#*************************************************************");
        System.out.println ("#*************************************************************");
        System.out.println ("#**                                                         **");
        System.out.println ("#**          TEMPLATE SIMULATED ANNEALING FOR TSP           **");
        System.out.println ("#**                                                         **");
        System.out.println ("#**         ICI 4144: Investigacion de Operaciones          **");
        System.out.println ("#**             Profesora: Leslie Perez Caceres             **");
        System.out.println ("#**                                                         **");
        System.out.println ("#**      Pontificia Universidad Catolica de Valparaiso      **");
        System.out.println ("#**                                                         **");
        System.out.println ("#*************************************************************");
        System.out.println ("#*************************************************************");
    };

    public static void main(String[] args) {
        
      print_header ();
        
      /* Leer opciones desde la linea de comando */
      AlgorithmOptions opciones = new AlgorithmOptions(args);
        
      /* Inicializar objeto problema con la instancia entregada */
      TSP problem = new TSP(opciones.filename);
      
      /* Codigo utilizado solo para probar la funcion objetivo */
      if (test_flag) {
    	System.out.println("\n# Generando solucion de prueba ...");
        Tour test_solution = new Tour (Tour.InitialSolution.DETERMINISTIC, problem);
        test_solution.print();
        test_solution.printToFile(opciones.output);
        System.exit(0);
      }
        
      /* Crear solucion inicial */
      Tour solution = new Tour (Tour.InitialSolution.DETERMINISTIC, problem);
      //Tour solution = new Tour (Tour.InitialSolution.RANDOM, problem);
      //Tour solution = new Tour (Tour.InitialSolution.NEAREST_N, problem);
      
      /* Ejecutar metaheuristica */
      if (opciones.metaheuristic == MHType.SA) {
        /* Crear solver simulated annealing */
        SimulatedAnnealing solver = new SimulatedAnnealing (problem, opciones.cooling,
                                                      opciones.mh_move, opciones.alpha);
        /* Ejecutar la busqueda */
        solver.search(solution, opciones.t0, opciones.t_min, opciones.max_evaluations);
        		
        /* Mostrar la mejor solucion encontrada */
        System.out.println("# Terminando ejecucion ...");
        solver.print_best_solution (true);
        solver.print_solution_tofile(opciones.output);
      } else {
        /* Mostrar la mejor solucion encontrada */
        System.out.println("Error: Metaheuristica no disponible");
        System.exit(1);
      }
   };

}
