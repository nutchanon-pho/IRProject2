import java.io.Serializable;
import java.util.ArrayList;
public class Posting implements Serializable, Comparable<Posting>{
 private int docID;
 private ArrayList<Integer> positionList;
 private int termFrequency;
 private static final long serialVersionUID = 42L;
 //Constructor
 public Posting(int docIDInput , ArrayList<Integer> positionInput){
  docID = docIDInput;
  positionList = positionInput;
  termFrequency = positionList.size(); 
 }
 
 public int getDocID(){
  return docID;
 }
 public ArrayList<Integer> getPositionList(){
  return positionList;
 }
 public int getTermFrequency(){
  return termFrequency;
 }
 public boolean equals(Object obj){
  Posting other = (Posting) obj;
  if(docID == other.getDocID()){
   if(positionList.size()!=other.getPositionList().size())
    return false;
   for(int i =0;i<=positionList.size();i++)
    if(positionList.get(i)!=other.getPositionList().get(i)){
     return false;
    }
   return true;
  }
  else 
   return false;
  
 }
 public String toString(){
  String s = docID +"," + termFrequency + "<";
  for(int i = 0;i<termFrequency;i++)
  {
   s+=positionList.get(i);
   if(i != termFrequency -1) 
    s+=",";
  }
  s+= ">";
  return s;
 }
 
 public void add(int input)
 {
  positionList.add(input);
  termFrequency++;
 }

 @Override
 public int compareTo(Posting arg) {
  Posting castedArg = (Posting)arg;
  return this.docID - castedArg.getDocID();
 }
 
}
