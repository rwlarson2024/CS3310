import java.util.*;
public class pj2 
{
    public static void main (String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a value for n");
        int n = keyboard.nextInt();
        System.out.println("Enter a value for the kth smallest value");
        int k = keyboard.nextInt();
        int[] array = RandomNumberGenerator(n);
        int low = 0;
        int high = array.length;
        System.out.println("K'th smallest element is: " + kthSmalllestfifty(array, low, high, k));

        
    }
    public static int [] RandomNumberGenerator (int n)
    {
        int[] array = new int[n];
        Random random = new Random();
        for(int i = 0; i < array.length; i++)
        {
            array[i] = random.nextInt(100);
        }
        return array;
    }
    public static int partition(int[] S, int low, int high)
    {
        int a = S[high];
        int i = low;
        for(int j = low; j <= high -1; j++)
        {
            if(S[j] <= a)
            {
                int temp = S[i];
                S[i] = S[j];
                S[j] = temp;
                i++;
            }
        }
        int temp = S[i];
        S[i] = S[high];
        S[high] = temp;
        return i; 
    }
    //searches array for value x and patitions array around the value x
    public static int partition2(int S[], int low, int high, int x)
    {
        int i;
        for(i = low; i < high; i++)
        {
             if(S[i] == x)
             break; 
        }
        swap(S, i, high);
        i = low;
        for(int j = low; j<= high-1;j++)
        {
            if(S[j]<=x)
            {
                swap(S,i,j);
                i++;
            }
        }
        swap(S,i,high);
        return i;
    
    }
    public static int kthSmalllest(int S[], int low, int high, int k)
    {
        if(k > 0 && k <= high - low + 1)
        {
            int pos = partition(S,low,high);
            if(pos - low == k-1)
                return S[pos];
            if(pos-low > k -1 )
                return kthSmalllest(S, pos+1, high, k - pos + low -1);
        }
        return Integer.MAX_VALUE;

    }
    static void merge (int[] S,int low, int mid, int high)
    {
        int n1 = mid - low + 1;
        int n2 = high - mid; 
        int L[] = new int[n1];
        int R[] = new int[n2];

        for(int i = 0 ; i < n1 ; i++)
            L[i] = S[mid + i];

        for(int j = 0 ; j < n2 ; j++)
            R[j] = S[mid + 1 +j];
        int i = 0;
        int j = 0;
        int k = 1;

        while(i < n1 && j < n2)
        {
            if(L[i] <= R[j])
            {
                S[k] = L[i];
                i++;
            }
            else
            {
                S[k] = L[i];
                j++;
            }
            k++;
        } 
        //copy remaining elements into S[] from L[]
        while (i < n1)
        {
            S[k] = L[i];
            i++;
            k++;
        }
        //copy remaining elements into S[] from R[]
        while(j < n2)
        {
            S[k] = R[j];
            j++;
            k++;
        } 
    }
    static void sort(int[] S, int low, int high)
    {
        if (low < high)
        {
            int m  = low + (high - 1)/2;
            sort(S,low,m);
            sort(S,m+1,high);
            merge(S, low, m, high);
        }
    }
    static int[] swap(int[] S, int i, int j)
    {
        int temp = S[i];
        S[i] = S[j];
        S[j] = temp;
        return S;
    }
    static int findMedian(int S[], int i, int j)
    {
        Arrays.sort(S, i , j);
        return S[i+(j-1)/2];
    }
    public static int kthSmalllestfifty(int S[], int low, int high, int k)
    {
        int n = S.length;
        if(n < 50)
        {
            sort(S, low, high);
            return S[0];

        }
        if(k > 0 && k <= high - low + 1)
        {
            n = high - low +1;
            int i; 
            int median[] = new int[(n+4)/5];
            for(i = 0; i < n/5; i++)
            {
                median[i] = findMedian(S, low + i*5, low + i *5+5);
            }
            if(i*5 < n)
            {
                median[i] = findMedian(S, low+i*5 , low+i*5+n%5);
                i++;
            }
            //find median of all the medians recursively 
            //if there is only one value return the value.

            int medianofMedian = (i==1)? median[i-1]:
                                    kthSmalllestfifty(median, 0,i-1,i/2);
            int pos = partition2(S, low, high, medianofMedian);

            if(pos -low == k-1)
                return S[pos];
            //recur for left array
            if(pos - low > k -1 )
                return kthSmalllestfifty(S, low, pos-1, k);
            return kthSmalllestfifty(S, pos +1, high, k - pos +low -1);
        }
        return Integer.MAX_VALUE;
    }
}