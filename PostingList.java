import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class PostingList implements Serializable{
    private ArrayList<Posting> list;
    private int documentFrequency;
    private static final long serialVersionUID = 42L;
    /* 2: < 1, 2: <1, 5>; 2, 1: <1> > */
    
    
    
    public PostingList(){
        list = new ArrayList<Posting>();
        documentFrequency = list.size();
    }
    
    public void addPosting(Posting posting,String type){
        if(!list.contains(posting)){
            list.add(posting);
            Collections.sort(list);
            documentFrequency = list.size();
        }
        else
        {
            if(type.equalsIgnoreCase("sender")){
                Posting exsistingPosting = getPostingWithDocID(posting.getDocID());
                exsistingPosting.setSenderTermFrequency(posting.getSenderTermFrequency());
            }
            if(type.equalsIgnoreCase("subject")){
                Posting exsistingPosting = getPostingWithDocID(posting.getDocID());
                exsistingPosting.setSubjectTermFrequency(posting.getSubjectTermFrequency());
            }
            if(type.equalsIgnoreCase("content")){
                Posting exsistingPosting = getPostingWithDocID(posting.getDocID());
                exsistingPosting.setContentTermFrequency(posting.getContentTermFrequency());
            }
        }
    }
    
    
    public String toString()
    {
        String resultString = "<";
        for(int i =0;i<list.size();i++)
        {
            resultString += list.get(i).toString() ;
            if(i!= list.size()-1){
                resultString += ";";
            }
            
        }
        resultString += ">\n";
        return resultString;
    }
    
    public int getDocumentFrequency()
    {
        return documentFrequency;
    }
    
    private int charToInt(char input)
    {
        int result = ((int)input) - 48;
        return result;
    }
    
    public ArrayList<Posting> getList()
    {
        return list;
    }
    public void add(Posting input)
    {
        list.add(input);
        documentFrequency++;
    }
    
    private double roundToFourDecimalPoints(double d)
    {
        return Math.floor(d * 10000) / 10000.0;
    }
    
    public double getIDF(int totalNoOfDocuments)
    {
        double df = (double)totalNoOfDocuments/(double)documentFrequency;
        double idf = Math.log10(df);
        return roundToFourDecimalPoints(idf);
    }
    
    public double getSenderIDF(int totalNoOfDocuments)
    {
        double df = (double)getSenderDocumentFrequency()/(double)documentFrequency;
        double idf = Math.log10(df);
        return roundToFourDecimalPoints(idf);
    }
    
    public double getSubjectIDF(int totalNoOfDocuments)
    {
        double df = (double)getSubjectDocumentFrequency()/(double)documentFrequency;
        double idf = Math.log10(df);
        return roundToFourDecimalPoints(idf);
    }
    
    public double getContentIDF(int totalNoOfDocuments)
    {
        double df = (double)getContentDocumentFrequency()/(double)documentFrequency;
        double idf = Math.log10(df);
        return roundToFourDecimalPoints(idf);
    }
    
    public Posting getPostingWithDocID(int docID)
    {
        for(Posting p : list)
        {
            if(docID == p.getDocID())
                return p;
        }
        return null;
    }
    
    public int getSenderDocumentFrequency()
    {
        int df = 0;
        for(Posting p : list)
        {
            if(p.getSenderTermFrequency()>0)
                df++;
        }
        return df;
    }
    
    public int getSubjectDocumentFrequency()
    {
        int df = 0;
        for(Posting p : list)
        {
            if(p.getSubjectTermFrequency()>0)
                df++;
        }
        return df;
    }
    
    public int getContentDocumentFrequency()
    {
        int df = 0;
        for(Posting p : list)
        {
            if(p.getContentTermFrequency()>0)
                df++;
        }
        return df;
    }
    
}
