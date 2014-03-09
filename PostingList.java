import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class PostingList implements Serializable{
 private ArrayList<Posting> list;
 private int documentFrequency;
 private int totalNoOfDocuments;
 //private double inverseDocumentFrequency;
 private static final long serialVersionUID = 42L;
 /* 2: < 1, 2: <1, 5>; 2, 1: <1> > */
 public PostingList(String input, int n)
 {
  totalNoOfDocuments = n;
  list = new ArrayList<Posting>();
  documentFrequency = charToInt(input.charAt(0));
  input = input.substring(3); //trim the docFreq off
  for(int j=0; j<documentFrequency; j++)
  {
   int docID = charToInt(input.charAt(2));
   int termFrequency = charToInt(input.charAt(5));
   ArrayList<Integer> positionList = new ArrayList<Integer>();
   int index = 9; //first index of position list
   for(int i=0; i<termFrequency; i++)
   {
    char position = input.charAt(index);
    positionList.add(charToInt(position));
    index = index + 3; //move to the next index
   }
   Posting newPosting = new Posting(docID, positionList);
   list.add(newPosting);
   int lastIndexOfPosting = input.indexOf('>');
   input = input.substring(lastIndexOfPosting+1); //trim the processed posting off
  }
 }
 
 //test constructor for IDFComparator
 public PostingList(int df, int N)
 {
     documentFrequency = df;
     totalNoOfDocuments = N;
 }
 
 public PostingList(){
  list = new ArrayList<Posting>();
  documentFrequency = list.size();
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
     return Math.log10(totalNoOfDocuments/documentFrequency);
 }
 
}
