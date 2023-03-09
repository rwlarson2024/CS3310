
import java.io.*;
import java.util.*;
import java.util.function.DoubleFunction;
public class project3 
{
    public static void main(String[] args) throws Exception
    {
        boolean newt = true;
        boolean sec = false;
        boolean hybrid = false;
        boolean bisect = false;
        double iterationsStart = 10000;
        String fileName = "fun1.pol";
        double a = 0;
        double x = 0;
        double epsilon = 0.00000001;
        double delta   = 0.00000001;
        //reads user's input from commandline and checks for all the different flags that change what version of 
        //calculations that are conducted.
        /*for (int i = 0; i < args.length; i++)
        {
            String arg = args[i];
            switch (arg)
            {
                case "-newt":
                    newt = true;
                    break;
                case "-sec":
                    sec = true;
                    break;
                case "-hybrid":
                    hybrid = true;
                    break;
                case "-maxIt":
                    if(i + 1 <args.length && args[i+1].matches("\\d+") )
                    {
                        iterationsStart = Double.parseDouble(args[i+1]);
                        i++;
                    }
                    else
                    {
                        System.err.println("invalid argument for -maxIt ....");
                        System.exit(1);
                    }
                    break;
                    default:
                    // Check if the argument is a number
                        if (arg.matches("\\d+")) {
                            if (a == 0) {
                                a = Integer.parseInt(arg);
                            } else if (x == 0) {
                                x = Integer.parseInt(arg);
                            } else {
                                System.err.println("Too many integer arguments");
                                System.exit(1);
                            }
                        } else if (fileName == null) {
                            fileName = arg;
                        } else {
                            System.err.println("Invalid argument: " + arg);
                            System.exit(1);
                        }
            }           
        }*/
        File file = new File(fileName);
        if(!file.exists())
        {
            System.out.println("file does not exsist");
            System.exit(0);
        }
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            
            String line = br.readLine();
            double degree = Double.parseDouble(line);
            line = br.readLine();
            String[] doubles = line.split(" ");
            double[] coeffx = new double[doubles.length];
            for(int i = 0; i < doubles.length; i++)
            {
                coeffx[i] = Double.parseDouble(doubles[i]);
            }       
        
            //br.close();
            double[] solution= new double[3];
            if(newt = true)
            {
                x = 2;
                solution = newtons(coeffx,x,degree,iterationsStart,epsilon,delta);
                bisect = false;
            }
            else if(sec = true)
            {
                solution = secant(coeffx,a,x,degree, iterationsStart,epsilon);
                bisect = false;
            }
            else if(hybrid = true)
            {
                bisect = false;
            }
            else if(bisect = true)
            {
                solution = bisection(coeffx, degree, a, x, iterationsStart, epsilon);
            }
            String outputFile = "fun1.sol"; 
            write(outputFile, solution);
        }
    }
    public static void write(String outputFile, double[] solutions) throws IOException
    {
        BufferedWriter out = null;
        out = new BufferedWriter(new FileWriter(outputFile));
        for(int i =0; i < solutions.length; i++)
        {
            out.write(solutions[i]+" ");
            out.newLine();
        }
        out.flush();
        out.close();
    }
    //Computing the solution of a polynomial at a specific point x with the degree n
    static double f (double[] vectorCoeff, double degree, double x)
    {
        int j = 0;
        double sol=0; 
        for (double i = degree; i>=0.0 ; i-- )
        {
            sol = sol + (vectorCoeff[j]*(Math.pow(x,i)));
            j++;
        }
        return sol;
    }
    //Computing the derivative and solution of a polynomial at a specific point x with the degree n
    static double derivative(double[] vectorCoeff, double degree, double x)
    {
        int j =0; 
        double sol = 0; 
        for(double i = degree; i>=0; i--)
        {
            sol = sol + (vectorCoeff[j])*(i)*(Math.pow(x,i-1));
            j++;
        }
        return sol;
    }
    //bisection method of computation.
    public static double[] bisection(double[] f, double d, double a, double b, double maxint, double esp)
    {
        double[] solutions = new double[3];
        solutions[0] = 0;
        solutions[1] = 0;
        solutions[2] = 0;
        double fa = f(f,d,a);
        double fb = f(f,d,b);

        if (fa * fb >= 0)
        {
            System.out.println("inadepuate values for a and b.");
            return solutions;
        }
        double error = b - a;
        for(double i = 0; i<= maxint; i++)
        {
            error = error/2;
            double c = a + error;
            double fc = f(f,d,c);

            if(Math.abs(error) < esp || fc == 0  )
            {
                System.out.println("algorithm has converged after " + i + " iterations!");
                solutions[0] = c; 
                solutions[1] = i;
                solutions[2] = 1; 


                return solutions;
            }
            if(fa *fc < 0)
            {
                b = c;
                fb = fc;
            }
            else
            {
                a = c;
                fa = fc;
            }
        }
        System.out.print("max iterations reached without convergence ...");
        solutions[0] = 0; 
        solutions[1] = maxint;
        solutions[2] = -1;

        return solutions;
    }

    //Newton's method of computation
    public static double[] newtons(double[] f, double x, double degree, double maxInt, double eps, double delta)
    {
        double[] solutions = new double[3];
        solutions[0] = 0;
        solutions[1] = 0;
        solutions[2] = 0;
        double fx = f(f,degree,x);
        for(double i = 0; i<= maxInt; i++)
        {
            double fd = derivative(f,degree,x);
            if(Math.abs(fd) < delta)
            {
                System.out.println("small slope! ");
                return solutions;
            }
            double d = fx /fd;
            x = x - d;
            fx = f(f,degree,x);
            if(Math.abs(d) < eps)
            {
                System.out.println("algorithm has converged after "+ i +" iterations");
                solutions[0] = x;
                solutions[1] = i;
                solutions[2] = 1;
                return solutions;
            }
        }
        System.out.println("max itertations reached without convergence ...");
        return solutions;
    }
    //Secant method of computation
    public static double[] secant(double[] f, double a, double b,double degree, double maxInt, double eps )
    {
        double[] solutions = new double[3];
        solutions[0] = 0;
        solutions[1] = 0;
        solutions[2] = 0;
        double fa = f(f,degree,a);
        double fb = f(f,degree,b);
        for(double i = 0; i < maxInt; i++)
        {
            if(Math.abs(fa) > Math.abs(fb))
            {
                //swap
            }
            double d = (b-a)/ (fb - fa);
            b = a;
            fb = fa;
            d = d * fa;
            if(Math.abs(d) < eps)
            {
                System.err.println("algorith has converged after " + i + " iterations");
                solutions[0] = d;
                solutions[1] = i;
                solutions[2] = 1;
            }
            a = a -d;
            fa = f(f,degree,a);
        }
        System.out.println("Maximum number of iterations reached!");
        return solutions;
    }
}