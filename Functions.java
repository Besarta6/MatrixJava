 /*And let's say outside where you call this method your returned object is obj. 
    Then get access to array1 as obj[0] and access array2 as obj[1] 
    (proper casting will be needed).*/   
   
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Functions {
    
    Parameters parameters = new Parameters();
    Random rand = new Random();

    public int [][] mutation(int[][]v, int r, double probMutation)
    {
        double p = 1.0/(v.length);
        //random.nextInt(max - min + 1) + min
        //double n = rand.nextInt(9)/100.0;
        
        double n = probMutation;
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                           
            if (p>=n) {
                if (v[i][j]==0) {
                    v[i][j]=1;
                }
               else {
                    v[i][j]=0;
                }
            
            }
            }
            
             int[] coeficientV = new int[v.length];
            
            for (int k = 0; k < v.length; k++) {
                for (int j = 0; j < v[0].length; j++) {
                    if(v[k][j]==1)
                    coeficientV[k] ++;
                }
           
        }
           
           remove(v, coeficientV, r);
            add(v, coeficientV, r);
        }
        return v;
    }
    public int read() throws FileNotFoundException, IOException
    {
        List<Integer> numbers = new ArrayList<>();
    for (String line : Files.readAllLines(Paths.get("D:\\Diploma\\MatApp\\src\\file.txt"))) {
    for (String part : line.split("\\s+")) {
        Integer i = Integer.valueOf(part);
        numbers.add(i);
    }
}

return numbers.get(0);
}
    public Object[][] crossover(int[][] v, int[][] w, int r, double probCrossover)
    {   int a = v.length;
        int b = w.length;
        int l = 0;
        int m = 0;
        if(a>b)
        {l=a;
         m = b;
        }
        else
        {   m =a;
            l=b;
        }

        int c;
        int f;
        c = rand.ints(1, l).findFirst().getAsInt();
        f = rand.ints(1, m).findFirst().getAsInt();
            if (probCrossover!=1) {
            // SWAP
            for (int i = 1 ; i < probCrossover-1; i++) {
                for (int j = 0; j < probCrossover-1; j++) {
                                 
                int temp;
                temp = v[i][j];
                v[i][j] = w[i][j];
                w[i][j] = temp;
                }
            }
        }
            int[] coeficientV = new int[v.length];
            int[] coeficientW = new int[v.length];
            
            for (int i = 0; i < v.length; i++) {
                for (int j = 0; j < v[0].length; j++) {
                    if(v[i][j]==1)
                         coeficientV[i] ++;
                }
           
        }
            for (int i = 0; i < w.length; i++) {
                for (int j = 0; j < w[0].length; j++) {
                    if(w[i][j]==1)
                         coeficientW[i] ++;
                }
           
        }
           remove(w, coeficientW, r);
           remove(v, coeficientV, r);
            add(v, coeficientV, r);
           add(w, coeficientW, r);


        return new Object[][]{v, w};
    }
    public int[][] SelectWithReplacement(int[][][] p)
    {   Random random = new Random();
        int sum = 0;
        int [] f = new int [parameters.popsize];
        for (int i = 0; i < p.length; i++) {
            f[i] = assessFitness(p[i]);
            sum+=f[i];
        }
        if (sum==0) {
           for (int i = 0; i < p.length; i++) {
            f[i] = 1;
           
        }  
        }
        for (int i = 2; i < p.length; i++) {
            f[i]=f[i]+f[i-1];
        }
        int n = f[random.nextInt(f.length)];
        for (int i = 2; i < p.length; i++) {
            
            if (f[i-1]<n && n<=f[i]) {
                return p[i];
            }
        }
      
        return p[1];
    }  
    public int[][] tournamentSelection(int[][][] P, int tournamentSize)
    { 
        //int t = rand.nextInt(P.length-1) + 1;
        int index = rand.nextInt(P.length-1) + 1;
        int[][] Best = P[index];
        for (int i = 2; i < tournamentSize; i++) {
            int[][] Next = P[rand.nextInt(P.length-1) + 1];
            if(assessFitness(Best)<assessFitness(Next))
            {
                Best = Next;
            }
        }
        return Best;
    }
    public int llambda(int[][] m)
    {   int[] v = new int[m.length-1];
        int l = v[0];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if(i<m.length-1){
                if(m[i][j]==m[i+1][j]&&m[i][j]==1)
                {
                    v[i]++;
                }
                }
            }}
            for (int i = 1; i < v.length; i++) {
               
                     if(v[i]>l)
                {   l=v[i];
                }
                
               
            }
        
        //System.out.println(l);
        return l;
    }
    public static int getRandomInt(int max) 
    {
       
        Random rand2 = new Random();        
        rand2.nextInt();  
        return rand2.nextInt(max); 
}
    public int[][] selectParents(int i_tournamentSize)
    { 
        int startIndex = getRandomInt(i_tournamentSize);
        int [][] best = parameters.answer[startIndex];
        // get as many chromosomes as specified by tournament size
        for (int i = 2;i<i_tournamentSize; i++)
        {
           int[][] next = parameters.answer[startIndex];
            if (assessFitness(next)<assessFitness(best)) {
                best = next;
            }
        }

        return best;
 }
    public static void swap(int[][] matrix, int columns, int i, int j) 
    {
    double tmp = matrix[i / columns][i % columns];
    matrix[i / columns][i % columns] = matrix[j / columns][j % columns];
    matrix[j / columns][j % columns] = (int) tmp;
}
    public  void copyMember(int[][] matricaA, int[][] matricaB) 
    {
        for (int i = 0; i < matricaA.length; i++) {
            System.arraycopy(matricaA[i], 0, matricaB[i], 0, matricaA[0].length);            
        }
    }    
    public void copy(int [][] a, int [][][] P, int i, int m, int n)
    {
       
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    P[i][j][k] = a[j][k];
                }
           
        }
    }
    public void shuffle(int[][] a) 
    {  
   
    for (int i = 0; i <a.length; i++) {
        for (int j = 0; j <a[0].length - 1; j++) {
           
            int temp = a[i][j];
            a[i][j] = a[i][j+1];
            a[i][j+1] = temp;
        }
    }
           
}
    public  int getRandomNumber()
    {
        Random r = new Random();
        int Low = 0;
        int High = 4;
        int Result = r.nextInt(High-Low) + Low;
        return (int)Result/2;
   }
    public void remove(int [][] generatedMember, int[] coefficientCounter, int r)
    {
        for (int i = 0; i <  generatedMember.length; i++) {
                    for (int j = 0; j <  generatedMember[0].length; j++) {
                        if (coefficientCounter[i]>r &&generatedMember[i][j]==1) {
                             generatedMember[i][j] = 0;
                             coefficientCounter[i]--;
                        } 
                        else{

                        }
                    }
              }
    }
    public void add(int [][] generatedMember, int[] coefficientCounter, int r)
    { for (int i = 0; i < generatedMember.length; i++) {
                for (int j = 0; j < generatedMember[0].length; j++) {
                    if (coefficientCounter[i]>=r) {
                    } 
                    else{
                    if (generatedMember[i][j]==0) {
                         generatedMember[i][j] = 1;
                         coefficientCounter[i]++;
                    }
                    }
                }
        }
}
    public  int[][] generateAMember(int r, int m, int n) throws IOException
    {  
       int[][] generatedMember = new int[m][n];
        int[] coefficientCounter = new int[m];
         
            for (int i = 0; i < m; i++) {                 
               for (int j = 0; j < n; j++) {
                    if (coefficientCounter[i]<r) {
                          generatedMember[i][j] = getRandomNumber();                  
                          if (generatedMember[i][j]==1) {
                              coefficientCounter[i]++;
                          }     
                        }                   
                     }
            }
           remove(generatedMember,coefficientCounter,r);     
           add(generatedMember,coefficientCounter,r);

        
        
            return generatedMember;

    }     
    public int assessFitness(int[][] multi)
    {
        int s[] = new int[multi[0].length];        
        int s1[] = new int[multi[0].length];
        double rule = 0.0;
        for (int i = 0; i < multi[0].length; i++) {
            for (int j = 0; j < multi.length; j++) {
               s[i] += multi[j][i];
               s1[i] += Math.pow(multi[j][i],2);
            }
        }
        for (int i = 0; i < multi[0].length; i++) {
            s[i]= (int) Math.pow(s[i], 2);
        }
        for (int i = 0; i < multi[0].length; i++) {
               rule += s[i]-s1[i];
            
        }
        rule = rule/2;
            
         return (int)rule;
    }
}
