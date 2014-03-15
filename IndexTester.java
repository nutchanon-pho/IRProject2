import java.util.ArrayList;
import jdbm.helper.Tuple;
import jdbm.helper.TupleBrowser;
import java.io.*;
import java.util.Collections;

public class IndexTester{
    public static void main(String[] args) throws IOException
    {
        Index index = new Index("ohm");
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("i");
        tokens.add("Just");
        tokens.add("love");
        tokens.add("you");
        tokens.add("i");
        tokens.add("never");
        tokens.add("die");
        index.addIndex(tokens,1,"Pana-1","content");
        tokens = new ArrayList<String>();
        tokens.add("Fuck");
        tokens.add("you");
        tokens.add("i");
        index.addIndex(tokens,1,"Pana-1","subject");
        tokens = new ArrayList<String>();
        tokens.add("kuy");
        tokens.add("u");
        tokens.add("i");
        index.addIndex(tokens,2,"Pana-1","subject");
        index.save();
        System.out.println(index);
        
        int totalNoOfDocuments = 2;
        ArrayList<Tuple> list = new ArrayList<Tuple>();
        TupleBrowser browser = index.getBrowser();
        Tuple tuple = new Tuple();
        while ( browser.getNext(tuple) ) {
            Tuple currentTuple = new Tuple(tuple.getKey(), tuple.getValue());
            list.add(currentTuple);
            System.out.println(((PostingList)(tuple.getValue())).getIDF(totalNoOfDocuments));
        }
        Collections.sort(list, new IDFComparator(totalNoOfDocuments));
        eliminateStopWords(index, list, totalNoOfDocuments);
        System.out.println(index);
    }
    
    
    public static int find91stPercentilePosition(ArrayList<Tuple> list, int N)
    {
        int percentile91st = (int)((91.0/100.0)*list.size());
        PostingList p = (PostingList)list.get(percentile91st).getValue();
        double targetValue = p.getIDF(N);
        for(int i = 0; i<list.size(); i++)
        {
            PostingList currentPostingList = (PostingList)list.get(i).getValue();
            if(currentPostingList.getIDF(N) == targetValue)
            {
                return i;
            }
        }
        return -1;
    }
    
    public static void eliminateStopWords(Index index,ArrayList<Tuple> list, int N) throws IOException
    {
        int percentile91st = find91stPercentilePosition(list, N);
        System.out.println("Position kuy rai : " + percentile91st + " " + list.size());
        for(int i=percentile91st;i<list.size();i++)
        {
            String key = (String)list.get(i).getKey();
            index.remove(key);
        }
    }
}