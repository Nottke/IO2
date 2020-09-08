package algorithms;
/* AlgorithmOptions class
 * Clase que lee los argumentos proporcionados para ejecutar el algoritmo
 *
 * @autor  Leslie Perez Caceres
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.Option;
import java.util.Comparator;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class AlgorithmOptions {
    /* Parametros por defecto del algoritmo */
    
    /* archivo para imprimir la solucion */
    public String output = "solucion.txt";
	
    /* archivo de la instancia */
    public String filename = "instances/burma14.tsp";
    //public String filename = "instances/eil51.tsp";
    
    /* semilla para el generador de numero aleatorios */
    public long seed;
    
    /* tipo de metaheuristica a ejecutar */
    public MHType metaheuristic = MHType.SA;
    
    /* tipo del movimiento para la metaheuristica */
    public TSPMove mh_move      = TSPMove.SWAP;
    
    /* parametro alfa para el enfriamiento geometrico*/
    public Double alpha        = 0.98;
    
    /* temperatura inicial */
    public Double t0           = 1000.0;
    
    /* temperatura minima */
    public Double t_min        = 900.0;
    
    /* evaluaciones maximas */
    public int max_evaluations = 1000;
    
    /* tipo de enfriamiento */
    public SimulatedAnnealing.CoolingType cooling = SimulatedAnnealing.CoolingType.GEOMETRIC;
    
    static class OptComparator implements Comparator<Option> {
        Map<String, Integer> opt = new HashMap<String, Integer>();
        
        public OptComparator() {
            int i = 0;
            opt.put("seed", i++);
            opt.put("i", i++);
            opt.put("h", i++);
        }
        
        @Override
        public int compare(Option o1, Option o2) {
            if (o1.getValue() == null || o2.getValue() == null)
                return 0;
            else
                return (opt.get(o1.getOpt()) - opt.get(o2.getOpt()));
        }
    };
    
    public AlgorithmOptions (String args[]) {
        /* Semilla para el generador de numeros aleatorios */
        seed = System.currentTimeMillis();
        /* Leer argumentos */
        readOptions(args);
        /* Setear semillas en el generador de numeros aleatorio */
        Utilities.seed = seed;
    };
    
    private void readOptions (String args[]) {
        
        if (args.length == 0) {
            System.out.println("# Warning: usando instancia por defecto: " + filename);
            System.out.println("           si desea utilizar otra instancia debe proporcionarla (use --instance <file_path>)");
            System.out.println("# Use el argumento `--help' para mayor informacion");

        }
        
        Options options = new Options();
        options.addOption("h", "help", false, "muestra esta ayuda");
        options.addOption("o", "output", true, "achivo para guardar solucion");
        options.addOption("i", "instance", true, "achivo de instancia (formato TSPLIB)");
        options.addOption("mh", "metaheuristic", true, "metaheuristica a aplicar [ sa ]");
        options.addOption("mhm", "move", true, "movimiento a utilizar en la heuristica [ 2opt | swap ]");
        options.addOption("tc", "cooling", true, "Esquema de enfriamiento de la temperatura [ geometric | log | linear ]");
        options.addOption("a", "alpha", true, "Parametro alfa para el esquema geometrico ]0,1]");
        options.addOption("t0", "t0", true, "Temperatura inicial ]0,DOUBLE_MAX]");
        options.addOption("tmin", "tmin", true, "Temperatura minima ]0,DOUBLE_MAX]");
        options.addOption("e", "evaluations", true, "maximo numero de soluciones a evaluar");
        options.addOption("seed", true, "numero para ser usado como semilla para el generador de numeros aleatorios");
        
        
        
        CommandLine cmd = null;
        CommandLineParser parser = new BasicParser();
        try {
            cmd = parser.parse(options, args);
            System.out.println("\n# Leyendo argumentos ...");
        } catch (ParseException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        
        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.setSyntaxPrefix("Usage: ");
            formatter.setOptionComparator(new OptComparator());
            formatter.printHelp("LSRunner [OPTION]... [ARGUMENT]...", "Options:", options, "");
            System.exit(0);
        }
        
        /* Semilla */
        if (cmd.hasOption("seed")) {
            seed = Integer.parseInt(cmd.getOptionValue("seed"));
            System.out.println("# -seed con valor " + seed);
        } else {
            System.out.println("# Note: semilla " + seed);
        }
                        
        /* Archivo de salida */
        if (cmd.hasOption("o")) {
            output = cmd.getOptionValue("o");
            System.out.println("# -o/output filename " + output);
        } else {
            System.err.println("# Note: output " + output);
        }
        
        /* Archivo de instancia */
        if (cmd.hasOption("i")) {
            filename = cmd.getOptionValue("i");
            System.out.println("# -i/instance filename " + filename);
        } else {
            System.err.println("# Note: instancia " + filename);
        }
        
        /* Seleccion de la metaheuristica */
        if (cmd.hasOption("mh")){
            String value;
            value = cmd.getOptionValue("mh");
            if (value.equals("sa"))
                metaheuristic = MHType.SA;
            else {
                System.err.println("Error: opcion no reconocida -mh "+ value);
                System.exit(1);
            }
            System.out.println("# -mh/metaheuristic " + metaheuristic);
        } else {
	    System.out.println("# Note: metaheuristic " + metaheuristic);
	}
        
        /* Seleccion del movimiento para la metaheuristica */
        if (cmd.hasOption("mhm")){
            String value;
            value = cmd.getOptionValue("mhm");
            if (value.equals("2opt")) {
                mh_move = TSPMove.TWO_OPT;
            } else if (value.equals("swap")) {
                mh_move = TSPMove.SWAP;
            } else {
                System.err.println("Error: opcion no reconocida -mhm "+ value);
                System.exit(1);
            }
            System.out.println("# -mhm/move " + mh_move);
        } else {
	    System.out.println("# Node: move " + mh_move);
	}
        
        /* Esquema de enfriamiento */
        if (cmd.hasOption("tc")){
            String value;
            value = cmd.getOptionValue("tc");
            if (value.equals("geometric")) {
                cooling = SimulatedAnnealing.CoolingType.GEOMETRIC;
            } else if (value.equals("log")) {
                cooling = SimulatedAnnealing.CoolingType.LOG;
            } else if (value.equals("linear")) {
            	cooling = SimulatedAnnealing.CoolingType.LINEAR;
            } else {
                System.err.println("Error: opcion no reconocida -tc "+ value);
                System.exit(1);
            }
            System.out.println("# -tc/cooling " + cooling);
        } else {
	    System.out.println("# Note: cooling " + cooling);
	}
        
        /* Parametro alfa */
        if (cmd.hasOption("a")) {
            alpha = Double.parseDouble(cmd.getOptionValue("alpha"));
            System.out.println("# -a/alpha " + alpha);
        } else {
            System.out.println("# Note: alpha " + alpha);
        }

        /* Temperatura inicial */
        if (cmd.hasOption("t0")) {
            t0 = Double.parseDouble(cmd.getOptionValue("t0"));
            System.out.println("# -t0 " + t0);
        } else {
            System.out.println("# Note: t0 " + t0);
        }
        
        /* Temperatura minima */
        if (cmd.hasOption("tmin")) {
            t_min = Double.parseDouble(cmd.getOptionValue("tmin"));
            System.out.println("# -tmin " + t_min);
        } else {
            System.out.println("# Note: tmin " + t_min);
        }

        /* Numero maximo de evaluaciones */
        if (cmd.hasOption("evaluations")) {
            max_evaluations = Integer.parseInt(cmd.getOptionValue("evaluations"));
            System.out.println("# -e/evaluations " + max_evaluations);
        } else {
            System.out.println("# Note: evaluations " + max_evaluations);
        }
        
        validateOptions();
    }
    
    private void validateOptions() {
    	
    	if (max_evaluations<=0 && t_min <=0) {
    	  System.err.println("Error: temperatura minima o evaluaciones maximas deben ser > 0, valor: " + max_evaluations);
    	  System.exit(1);
        }
        if (alpha <= 0 || alpha > 1) {
    	  System.err.println("Error: alfa debe ser > 0 y <= 1, valor: " + alpha);
    	  System.exit(1);
    	}
    	if (t0 <= 0) {
    	  System.err.println("Error: t0 debe ser > 0 , valor: " + t0);
          System.exit(1);
    	}
    };

}
