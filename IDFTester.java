import java.util.ArrayList;
import java.util.Collections;

public class IDFTester
{
    public static void main(String[] args)
    {
        ArrayList<PostingList> list = new ArrayList<PostingList>();
        int N = 100;
        for(int i=0;i<N;i++)
        {
            list.add(new PostingList((int)(1+Math.random()*10),N));
        }
        
        Collections.sort(list, new IDFComparator());
        
        /*for(PostingList p : list)
        {
            System.out.println(p.getIDF());
        }*/
        
        int percentile91st = find91stPercentilePosition(list);
        System.out.println("\nThe 91st Percentile is at: " + percentile91st);
        System.out.println("The value at the 91st Percentile is: " + list.get(percentile91st).getIDF());
        list = eliminateStopWords(list);
        
        for(PostingList p : list)
        {
            System.out.println(p.getIDF());
        }
    }
    
    public static int find91stPercentilePosition(ArrayList<PostingList> list)
    {
        int percentile91st = (int)((91.0/100.0)*list.size());
        double targetValue = list.get(percentile91st).getIDF();
        for(int i = 0; i<list.size(); i++)
        {
            if(list.get(i).getIDF() == targetValue)
            {
                return i;
            }
        }
        return -1;
    }
    
    public static ArrayList<PostingList> eliminateStopWords(ArrayList<PostingList> list)
    {
        int percentile91st = find91stPercentilePosition(list);
        int noOfStopWords = list.size() - percentile91st;
        for(int i=0; i<noOfStopWords; i++)
        {
            int lastIndex = list.size()-1;
            list.remove(lastIndex);
        }
        return list;
    }
}

