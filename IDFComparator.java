import java.util.Comparator;
import jdbm.helper.Tuple;

public class IDFComparator implements Comparator<Tuple> {
    private int N;
    
    public IDFComparator(int N)
    {
        this.N = N;
    }
    
    @Override
    public int compare(Tuple t1, Tuple t2) {
        PostingList p1 = (PostingList)t1.getValue();
        PostingList p2 = (PostingList)t2.getValue();
        return (int)((p2.getIDF(N) - p1.getIDF(N))*10000);
    }
}