import java.util.Vector;
import java.util.ArrayList;

public class ScoreTester
{
    public static void main(String[] args)
    {
        int N = 3;
        ArrayList<Vector<Double>> vectorSpace = new ArrayList<Vector<Double>>();
        for(int i=0; i<N; i++)
        {
            Vector<Double> v = new Vector<Double>();
            v.add(Math.random());
            v.add(Math.random());
            v.add(Math.random());
            vectorSpace.add(v);
        }
        System.out.println(vectorSpace);
        
        Vector<Double> query = new Vector<Double>();
        query.add(0.0);
        query.add(0.707);
        query.add(0.707);
        
        for(int i=0; i<N; i++)
        {
            System.out.println(computeScore(vectorSpace.get(i), query));
        }
        
    }
    
    public static double computeScore(Vector<Double> v1, Vector<Double> v2)
    {
        int noOfTerms = v1.size(); //Loop checking term by term
        double score = 0;
        double normV1 = 0;
        double normV2 = 0;
        for(int i=0 ;i<noOfTerms; i++)
        {
            score += v1.get(i)*v2.get(i);
            
            normV1 += Math.pow(v1.get(i),2);
            normV2 += Math.pow(v2.get(i),2);
        }
        normV1 = Math.sqrt(normV1);
        normV2 = Math.sqrt(normV2);
        double normalizedScore = score / (normV1 * normV2);
        return normalizedScore;
    }
}

