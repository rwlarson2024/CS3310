public class pj1
{
    public static void main(String[] args)
    {
        int[] a = {1,-2,-8,11,14,-9,10,-11,5,6,-8,5,7,-9,10,2,3,-5,1,2,4,5,-6};
        int[] b = {-4,-5,-6,9,8,-3,10,5,4,-6,7,-2,4,6,20,13,-15,12,-20};
        int output;
        int n = a.length;
        
        System.out.print("The First algorithm has a CPU time of : ");
        output = maxSubSum1(a);
        System.out.print("The Subsequent Value is : ");
        System.out.println(output);
        System.out.println();

        System.out.print("The Second algorithm has a CPU time of : ");       
        output = maxSubSum2(a); 
        System.out.print("The Subsequent Value is : ");
        System.out.println(output);
        System.out.println();
       
        long Start = System.nanoTime();
        output = maxSumRec(a,0 , a.length -1);
        long End = System.nanoTime();
        long CPUTime = End - Start;
        System.out.print("The Third algorithm has a CPU time of : ");
        System.out.println(CPUTime);
        System.out.print("The Subsequent Value is : ");
        System.out.println(output);
        System.out.println();

        System.out.print("The Fourth algorithm has a CPU time of : ");
        output = maxSumDynamic(a, n);
        System.out.print("The Subsequent Value is : ");
        System.out.println(output);
        System.out.println();


    }
    public static int maxSubSum1 (int [] a)
    {
        int maxindex = 0;
        int minINdex = 0;
        long Start = System.nanoTime();
        int maxSum = Integer.MIN_VALUE;

        for(int i = 0; i < a.length; i++)
            for(int j = i; j < a.length; j++)
            {
                int thisSum = 0;
                for(int k = i; k <= j; ++k)
                    thisSum += a[k];
                if(thisSum > maxSum)
                {
                    maxSum = thisSum;
                    minINdex = i;
                    maxindex = j;
                }
            }

            long End = System.nanoTime();
            long CPUTime = End - Start;
            System.out.println(CPUTime);
            System.out.println("the Indicies are: " + minINdex + " and " + maxindex);
        return maxSum;

    }
    public static int maxSubSum2(int[] a)
    {
        int maxindex = 0;
        int minINdex = 0;
        long Start = System.nanoTime();
        int maxSum = Integer.MIN_VALUE;
        for(int i = 0; i<a.length; i++)
        {
            int thisSum = 0;
            for(int j = i; j < a.length; j++)
            {
                thisSum += a[j];
                if(thisSum > maxSum)
                {
                    maxSum = thisSum;
                    minINdex = i;
                    maxindex = j;
                }

            }
        }
        long End = System.nanoTime();
        long CPUTime = End - Start;
        System.out.println(CPUTime);
        System.out.println("the Indicies are: " + minINdex + " and " + maxindex);
        return maxSum;

    }
    public static int maxSumRec(int[] a, int left, int right)
    {
        int maxindex = 0;
        int minINdex = 0;        
        if(left == right)
            return a[left];
            
        int center = (left + right)/2;
        int maxLeftSum = maxSumRec(a, left, center);
        int maxRightSum = maxSumRec(a, center+1, right);

        int maxLeftBorderSum = Integer.MIN_VALUE;
        int leftBorderSum = 0;
        for(int i = center; i>=left; --i)
        {
            leftBorderSum += a[i];
            if(leftBorderSum > maxLeftBorderSum)
            {
                maxLeftBorderSum = leftBorderSum;
                minINdex = i;
            }
        }

        int maxRightBorderSum = Integer.MIN_VALUE;
        int rightBorderSum = 0;
        for(int j = center + 1; j<= right; ++j)
        {
            rightBorderSum += a[j];
            if(rightBorderSum > maxRightBorderSum)
            {
                maxRightBorderSum = rightBorderSum;
                maxindex =j;
            }    
        }
        
        if(maxLeftSum > maxRightSum)
        {
            if(maxLeftSum > (maxLeftBorderSum + maxRightBorderSum))
            return maxLeftSum;
        }
        else if(maxRightSum > maxLeftSum)
        {
            if(maxRightSum > (maxLeftBorderSum + maxRightBorderSum))
            return maxRightSum; 
        }
        System.out.println("the Indicies are: " + minINdex + " and " + maxindex);
        return (maxLeftBorderSum + maxRightBorderSum); 
    }
    

    public static int maxSumDynamic(int[] a, int n )
    {
        int maxindex = 0;
        int minINdex = 0;
        long Start = System.nanoTime();
        int sum = 0;
        int max = a[0];
        for(int i = 1; i< n; i++)
        {
            if(max < a[i])
            {
                max = a[i];
            }
        }
        if(max <= 0)
        {
            return max;
        }
        for(int i = 1; i < n-1; i++)
        {
            if(a[i] > 0)
            {
                sum += a[i];
            }
        }
        long End = System.nanoTime();
        long CPUTime = End - Start;
        System.out.println(CPUTime);
        System.out.print("the Indicies are: " + minINdex + " and " + maxindex);
        return sum - max;
    }

}