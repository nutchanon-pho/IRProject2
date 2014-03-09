import java.util.Comparator;

public class IDFComparator implements Comparator<PostingList> {
    @Override
    public int compare(PostingList p1, PostingList p2) {
       // return (int)(p1.getIDF() - p2.getIDF());
        return (int)((p2.getIDF() - p1.getIDF())*10000);
    }
}