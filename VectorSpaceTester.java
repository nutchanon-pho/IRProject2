import java.util.ArrayList;
import java.util.Vector;

public class VectorSpaceTester
{
    public static void main(String[] args)
    {
        
        int noOfDocuments = 6;
        int noOfTerms = 3;
        ArrayList<Double> scoreArray = new ArrayList<Double>();
        
        //Sample query
        Vector<Double> query = new Vector<Double>();
        query.add(0.0);
        query.add(0.707);
        query.add(0.707);
        
        ArrayList<PostingList> bTree = new ArrayList<PostingList>();//use ArrayList instead of B-tree for testing purpose
        
        //Initialization of B-tree
        for(int i = 0;i<noOfTerms;i++)
        {
            PostingList term = new PostingList(noOfDocuments);
            int randomDocumentFrequency = randomInteger(1,noOfDocuments); //random integer in [1,noOfDocuments]
            
            for(int j=0;j<randomDocumentFrequency;j++)
            {
                //create new Posting of a document with docID and random termFrequency
                Posting document = new Posting(j+1, randomInteger(1,100));
                term.add(document);
            }
            bTree.add(term);
            //System.out.println(term);
        }
        
        //Traversing B-Tree
        for(int i=0;i<noOfDocuments;i++) //traverse by documents
        {
            Vector<Double> documentVector = new Vector<Double>();
            for(int j=0;j<noOfTerms;j++) //traverse by terms
            {
                int currentDocID = i+1;
                PostingList currentTerm = bTree.get(j);
                Posting currentDocument = currentTerm.getPostingWithDocID(currentDocID);
                if(currentDocument != null)
                {
                    double idf = currentTerm.getIDF();
                    double tf = currentDocument.getTermFrequency();
                    double weight = tf * idf;
                    documentVector.add(weight);
                    //System.out.println("Weight of this doc = " + tf);
                }
                else
                {
                    documentVector.add(0.0);
                }
            }
            double score = computeScore(documentVector, query);
            System.out.println("Score of this doc = " + score);
            scoreArray.add(score);
        }
    }
    
    public static int randomInteger(int min, int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));
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