import java.util.ArrayList;

public class VectorSpaceTester
{
    public static void main(String[] args)
    {
        
        int noOfDocuments = 6;
        int noOfTerms = 3;
        double vectorSpace[][] = new double[noOfTerms][noOfDocuments];
        
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
        
        //Filling Vector Space Matrix
        for(int i=0;i<noOfTerms;i++)
        {
            PostingList currentTerm = bTree.get(i);
            double idf = currentTerm.getIDF();
            int documentFrequency = currentTerm.getDocumentFrequency();
            for(int j=0;j<documentFrequency;j++)
            {
                Posting currentDocument = currentTerm.getList().get(j);
                int docID = currentDocument.getDocID();
                int tf = currentDocument.getTermFrequency();
                //System.out.println("tf = " + tf + " idf = " + idf);
                double weight = (double)tf * (double)idf;
                vectorSpace[i][docID-1] = weight;
                //System.out.println(weight);
            }
        }
        
        //Print the matrix
        for(int i=0;i<noOfTerms;i++)
        {
            for(int j=0;j<noOfDocuments;j++)
            {
                System.out.print(vectorSpace[i][j] + " ");
            }
            System.out.println();
        }
        
    }
    
    public static int randomInteger(int min, int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}