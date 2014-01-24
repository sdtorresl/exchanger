import java.io.BufferedWriter;
import java.math.BigInteger;
import java.io.*;

class SequencesExchanger {
    private int[] a;
    private BigInteger numLeft;
    private BigInteger total;

    public SequencesExchanger(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Min 1");
        }
        a = new int[n];

        total = getFactorial(n);
        
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numLeft = new BigInteger(total.toString());
    }

    public BigInteger getNumLeft() {
        return numLeft;
    }

    public BigInteger getTotal() {
        return total;
    }

    public boolean hasMore() {
        return numLeft.compareTo(BigInteger.ZERO) == 1;
    }

    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(new BigInteger(Integer.toString(i)));
        }
        return fact;
    }

    public int[] getNext() {

        if (numLeft.equals(total)) {
            numLeft = numLeft.subtract(BigInteger.ONE);
            return a;
        }

        int temp;

        // Find largest index j with a[j] < a[j+1]
        int j = a.length - 2;
        while (a[j] > a[j + 1]) {
            j--;
        }

        // Find index k such that a[k] is smallest integer
        // greater than a[j] to the right of a[j]
        int k = a.length - 1;
        while (a[j] > a[k]) {
            k--;
        }

        // Interchange a[j] and a[k]
        temp = a[k];
        a[k] = a[j];
        a[j] = temp;

        // Put tail end of permutation after jth position in increasing order
        int r = a.length - 1;
        int s = j + 1;

        while (r > s) {
            temp = a[s];
            a[s] = a[r];
            a[r] = temp;
            r--;
            s++;
        }

        numLeft = numLeft.subtract(BigInteger.ONE);
        return a;
    }

    public static boolean saveFile(String FilePath, String FileContent, boolean CleanFileContent) {
     
        FileWriter file;
        BufferedWriter writer;
         
        try {
            file = new FileWriter(FilePath, !CleanFileContent);
            writer = new BufferedWriter(file);
            writer.write(FileContent, 0, FileContent.length());
             
            writer.close();
            file.close();
     
            return true;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        int[] indexes;

        String[][] elements;
        String[][] names;

        //Get the number of secuences
        boolean valid = true;
        int data = 0;
        int species = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // Get the number of species
        do {
            System.out.print("Ingrese el número de especies que desea permutar: ");
            try {
                species = Integer.parseInt(in.readLine());
                if (species <=0) {
                    System.out.println("El número debe ser mayor que 0");
                    valid = false;
                }
                else {
                    valid = true;
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Recuerde ingresar solo números");
                valid = false;
            }

        } while(!valid);

        // Get the number of sequences
        do {
            System.out.print("Ingrese el número de secuencias de las especies: ");
            try {
                data = Integer.parseInt(in.readLine());
                if (data <=0) {
                    System.out.println("El número debe ser mayor que 0");
                    valid = false;
                }
                else {
                    valid = true;
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Recuerde ingresar solo números");
                valid = false;
            }

        } while(!valid);

        elements = new String[species][data];
        names = new String[species][data];

        for (int i=0; i<data; i++) {
            do {
                int j = i+1;
                System.out.print("Ingrese el nombre de la secuencia número " + j + ": ");
                String name = in.readLine();

                for (int e=0; e<species; e++) {
                	names[e][i] = name;
                }
                

                System.out.println("La secuencia " + j + " se llama " + names[0][i]);
                System.out.print("¿Es correcto? (S/N): ");
                valid = in.readLine().toLowerCase().equals("s") || in.readLine().toLowerCase().equals("y");
                System.out.println();
            } while (!valid);
        }

        // For each specie
        for (int e=0; e<species; e++) {
        	System.out.println("\n*************\n* Especie "+(e+1)+"*\n*************\n");

	        for (int i=0; i<data; i++) {
	            do {
	                int j = i+1;
                
	                System.out.print("Ingrese la secuencia " + names[e][i] + ": ");
	                elements[e][i] = in.readLine();

	                System.out.println("La secuencia " + names[e][i] + " es " + elements[e][i]);
	                System.out.print("¿Es correcto? (S/N): ");
	                valid = in.readLine().toLowerCase().equals("s") || in.readLine().toLowerCase().equals("y");
	                System.out.println();
	            } while (!valid);
	        }
        }
        
        //Get the base name of the files
        String filePath = "";
        String filePathBase;
        do {
            System.out.print("\nGuardar archivo como: ");
            filePath = in.readLine();
            System.out.print("Desea guardar en: " + filePath + "?" + "(S/N)");
            valid = in.readLine().toLowerCase().equals("s") || in.readLine().toLowerCase().equals("y");
                System.out.println();
        } while (!valid);

        // Print output for each sequence
        filePathBase = filePath;
        for (int j=0; j<data; j++) {
        	filePath = filePathBase + j;

	        StringBuffer fileContent = new StringBuffer("**************\n* Permutador *\n**************\n\n");
	        saveFile(filePath, fileContent.toString(), true);

	        for (int e=0; e<species; e++) {
	        	
		        SequencesExchanger x = new SequencesExchanger(elements[e].length);
		        StringBuffer permutation, concatenate;
		        String name;
		        int p = 1;
		        while (x.hasMore()) {
		            fileContent = new StringBuffer();

		            String header = "\nEspecie " + e + ": ";
		            System.out.println(header);
		        
		            permutation = new StringBuffer();
		            concatenate = new StringBuffer();
		            name ="";

		            indexes = x.getNext();
		            for (int i = 0; i < indexes.length; i++) {
		                permutation.append(elements[e][indexes[i]]);

		                if (i != indexes.length-1 && indexes.length != 0) 
		                    name = names[e][indexes[i]]+"-";
		                else
		                    name = names[e][indexes[i]];
		            
		                concatenate.append(name);
		            }
		            
		            System.out.println(concatenate.toString());
		            System.out.println(permutation.toString());

		            p++;

		            //Save data into a file
		            fileContent.append(header);
		            fileContent.append("\n");
		            fileContent.append(concatenate.toString());
		            fileContent.append("\n");
		            fileContent.append(permutation.toString());
		            fileContent.append("\n\n");
		            saveFile(filePath, fileContent.toString(), false);
		        }
	        }
        }
    }
}