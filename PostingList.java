import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class PostingList implements Serializable{
 private ArrayList<Posting> list;
 private int documentFrequency;
 private int totalNoOfDocuments;
 private static final long serialVersionUID = 42L;
 /* 2: < 1, 2: <1, 5>; 2, 1: <1> > */
 
 //test constructor for IDFComparator
 public PostingList(int df, int N)
 {
     documentFrequency = df;
     totalNoOfDocuments = N;
 }
 
 public PostingList(int N){
  list = new ArrayList<Posting>();
  documentFrequency = list.size();
  totalNoOfDocuments = N;
 }
 public void addPosting(Posting posting){
  if(!list.contains(posting)){
   list.add(posting);
   Collections.sort(list);
   documentFrequency = list.size();
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
 
 public double getIDF()
 {
     double df = (double)totalNoOfDocuments/(double)documentFrequency;
     return Math.log10(df);
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
 
}
