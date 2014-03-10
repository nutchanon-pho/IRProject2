import java.io.Serializable;
import java.util.ArrayList;
public class Posting implements Serializable, Comparable<Posting>{
 private int docID;
<<<<<<< HEAD
<<<<<<< HEAD
 private int termFrequency;
 private static final long serialVersionUID = 42L;
 //Constructor
 public Posting(int docIDInput, int tf){
  docID = docIDInput;
  termFrequency = tf; 
=======
=======
>>>>>>> parent of 4b90b73... version 0.1
 private ArrayList<Integer> positionList;
 private int termFrequency;
 private static final long serialVersionUID = 42L;
 public Posting(int docIDInput , ArrayList<Integer> positionInput){
  docID = docIDInput;
  positionList = positionInput;
  termFrequency = positionList.size(); 
<<<<<<< HEAD
>>>>>>> parent of 4b90b73... version 0.1
=======
>>>>>>> parent of 4b90b73... version 0.1
 }
 
 public int getDocID(){
  return docID;
 }
<<<<<<< HEAD
<<<<<<< HEAD
 
 public int getTermFrequency(){
  return termFrequency;
 }
 /*
=======
=======
>>>>>>> parent of 4b90b73... version 0.1
 public ArrayList<Integer> getPositionList(){
  return positionList;
 }
 public int getTermFrequency(){
  return termFrequency;
 }
<<<<<<< HEAD
>>>>>>> parent of 4b90b73... version 0.1
=======
>>>>>>> parent of 4b90b73... version 0.1
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
  
<<<<<<< HEAD
<<<<<<< HEAD
 }*/
 
 public String toString(){
  String s = docID +"," + termFrequency;
  return s;
 }
 
 /*public void add(int input)
 {
  positionList.add(input);
  termFrequency++;
 }*/
=======
=======
>>>>>>> parent of 4b90b73... version 0.1
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
 public ArrayList<Integer> intersect(ArrayList<Integer> p2)
 {
  ArrayList<Integer> p1 = positionList;
  ArrayList<Integer> answer = new ArrayList<Integer>();
  int p1Index = 0;
  int p2Index = 0;
  while(p1Index < p1.size() && p2Index < p2.size())
  {
   if(p1.get(p1Index) == p2.get(p2Index))
   {
    answer.add(p1.get(p1Index));
   }
   else
   {
    if(p1.get(p1Index) < p2.get(p2Index))
    {
     answer.add(p1.get(p1Index));
     answer.add(p2.get(p2Index));
    }
    else
    {
     answer.add(p2.get(p2Index));
     answer.add(p1.get(p1Index));
    }
   }
   p1Index++;
   p2Index++;
  }
  return answer;
 }
 
 public void add(int input)
 {
  positionList.add(input);
  termFrequency++;
 }
<<<<<<< HEAD
>>>>>>> parent of 4b90b73... version 0.1
=======
>>>>>>> parent of 4b90b73... version 0.1

 @Override
 public int compareTo(Posting arg) {
  Posting castedArg = (Posting)arg;
  return this.docID - castedArg.getDocID();
 }
 
}
