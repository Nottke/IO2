package algorithms;


public class SimulatedAnnealing {
	
  enum CoolingType
  /* Esquemas de enfriamiento disponibles
   *   GEOMETRIC: t = t * alpha
   *   LINEAR: t = t * (1 - (evaluation / max_evaluations))
   *   LOG: t = t * alpha * 1/ln(evaluation + 1)
   */
  {
    GEOMETRIC, LINEAR, LOG
  };
	
    /* Problema */
	static TSP problem;
	/* Esquema de enfriamiento */
	CoolingType cooling;
	/* Tipo de movimiento */
	TSPMove move_type;
	/* Parametro alfa para el esquema de enfriamiento geometrico */
	Double alpha;
	/* Mejor tour */
	Tour best_tour;
	
    public SimulatedAnnealing(TSP _problem, CoolingType _cooling, TSPMove _move_type, Double _alpha) 
    /* FUNCTION: constructor de la clase SimulatedAnnealing
     * INPUT: instancia de TSP: _problem, esquema de enfriamiento: _cooling,
     *        movimiento a utilizar: _move_type, valor de alfa para enfriamiento
     *        geometrico
     */
	{
		problem   = _problem;
		cooling   = _cooling;
		move_type = _move_type;
		alpha     = _alpha;
		best_tour = new Tour(Tour.InitialSolution.RANDOM, problem);
		System.out.println("\nInicializando Simulated Annealing ...");
	 };
	
    public void print_best_solution (boolean full)
    /* FUNCTION: print_best_solution
     * INPUT: booleano que indica si la solucion debe ser impresa completa
     * OUTPUT: ninguno
     */
    {
      System.out.println("\nMejor solucion Simulated Annealing: ");
      if (full) {
        best_tour.print();
      } else {
        best_tour.printCost();
      }
    };
    
    public void print_solution_tofile (String filename) 
    /* FUNCTION: print_solution_tofile
     * INPUT: string the indica la ruta al archivo para escribir la mejor solucion
     * OUTPUT: ninguno
     */
    {
      best_tour.printToFile(filename);
    };
	
	public void search (Tour initial_solution, Double t_initial, Double t_min, int max_evaluations) 
    /* 
     * FUNCTION: search
     * INPUT: solucion inicial: initial_solution, temperatura inicial: t_initial,  
     *        temperatura minima: t_min, numero maximo de evaluaciones: max_evaluations
     * OUTPUT: ninguno
     * COMMENT: esta funcion ejecuta la busqueda de simulated annealing
     *          desde la solucion inicial (initial_solution, el resultado
     *          final puede ser encontrado en best_tour)
     */
	{
		/* variable de temperatura */
		Double temperature = t_initial;
		/* variable para calculos de probabilidad */
		Double prob;
		/* numero maximo de evaluaciones */
		int evaluation = 0;
		
        /* variable del tour actual */
		Tour current_tour = new Tour(initial_solution);
        /* variable del tour vecino generado */
		Tour neighbor_tour = new Tour(initial_solution);
        /* solucion inicial se guarda como la mejor hasta el momento */
		best_tour.Copy(initial_solution);
		
		System.out.println("\nComenzando busqueda, solucion inicial: ");
        best_tour.print();
        
        /* bucle principal del algoritmo */
		while (terminationCondition(temperature, t_min, evaluation, max_evaluations)) {
			/* generar un vecino aleatoriamente */
			neighbor_tour.randomNeighbor(move_type);
			System.out.printf("  Evaluacion " + evaluation + "; t= %.2f", temperature);
			
			/* revisar funcion objetivo de la nueva solucion */
			if (neighbor_tour.getCost() < current_tour.getCost()) {
				/* mejor solucion encontrada */
				current_tour.Copy(neighbor_tour);
				System.out.print("; mejor costo=" + current_tour.getCost());
			} else {
				/* calcular criterio de aceptacion */
				prob = getAcceptanceProbability (neighbor_tour.getCost(),  current_tour.getCost(), temperature);
				
				if (Utilities.ran01() <= prob) {
					/* acepta la solucion peor */
					current_tour.Copy(neighbor_tour);
					System.out.print("; acepta peor costo=" + neighbor_tour.getCost());
				} else {
					/* no se acepta la solucion */
					System.out.print("; no acepta peor costo=" + neighbor_tour.getCost() + "; solucion actual costo=" + current_tour.getCost());
					neighbor_tour.Copy(current_tour);
				}
			}
			
			/* revisar si la nueva solucion es la mejor hasta el momento */
			if (current_tour.getCost() < best_tour.getCost()) {
				System.out.print("; mejor solucion actualizada!");
				best_tour.Copy(current_tour);
			} 
			System.out.print("\n");
			
			/* reducir la temperatura */
			temperature = reduceTemperature(temperature, t_initial, evaluation, max_evaluations);
			evaluation  = evaluation + 1;
		}
	};

	private boolean terminationCondition (Double t, Double t_min, int evaluations, int max_evaluations) 
    /* 
     * FUNCTION: terminationCondition
     * INPUT: temperatura actual: t, temperatura minima: t_min, numero actual
     *        de evaluacions: evaluations, numero maximo de evaluaciones:
     *         max_evaluations
     * OUTPUT: booleano que indica si se debe continuar la ejecucion o no. 
     *         True: si se debe continuar, False: si no se debe continuar
     */	
	{
		/* criterio de termino de la temperatura */
		if (t_min > 0) {
		  if (t <= t_min) return (false);
		}
		
		/* criterio de termino de las evaluaciones */
		if (max_evaluations > 0) {
			if (evaluations >= max_evaluations) return (false);
		}
		
		return (true);
	};
	
	public Double getAcceptanceProbability (int neighbor_cost, int current_cost, Double temperature) 
    /* A IMPLEMENTAR
     * FUNCTION: getAcceptanceProbability
     * INPUT: costo de la solucion a aceptar (neighbor_cost), 
     *        costo de la mejor solucion actual (current_cost),
     *        temperatura de la iteracion (temperature)
     * OUTPUT: Probabilidad de aceptacion (Double)
     * COMMENT: Utilice el Criterio Metropolis
     */
	{
		return(0.0);

	}
	
	public Double reduceTemperature (Double t, Double t0, int iteration, int max_iterations) 
    /* A IMPLEMENTAR: enfriamiento geometrico
     * FUNCTION: reduceTemperature
     * INPUT: temperatura de la iteracion actual (t), temperatura inicial (t0), iteracion actual (iteration), 
     *        numero maximo de iteraciones a ejecutar (max_iterations)
     * OUTPUT: Temperatura de la proxima iteracion (Double)
     * COMMENT: Calcule la nueva temperatura considando la variable cooling de la clase  
     *          la cual puede contener los siguientes valores:
     *           - CoolingType.GEOMETRIC: enfriamiento geometrico
     *           - CoolingType.LINEAR: enfriamiento lineal
     *           - CoolingType.LOG: enfriamiento logaritmico
     */
	{
			Double t_new = t;
			if (cooling == CoolingType.GEOMETRIC) {
				//
				//IMPLEMENTE AQUI EL ENFRIAMIENTO GEOMETRICO
				//
			}else if (cooling == CoolingType.LINEAR) {
			    t_new = (double)t0 * 
			    		(1 - ((double) iteration / (double) max_iterations));
			}else if (cooling == CoolingType.LOG) {
				t_new = (t0 * alpha) / Math.log(iteration-1);
			}
			return(t_new);
	};

}
