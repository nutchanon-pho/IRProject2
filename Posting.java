import java.io.Serializable;
import java.util.ArrayList;
public class Posting implements Serializable, Comparable<Posting>{
 private String docName;
 private int docID;
 private int senderTermFrequency;
 private int subjectTermFrequency;
 private int contentTermFrequency;
 private static final long serialVersionUID = 42L;
 //Constructor
 public Posting(String docName, int docID){
  this.docName = docName;
  this.docID = docID;
  senderTermFrequency = 0;
  subjectTermFrequency = 0;
  contentTermFrequency = 0;
 }
 
 public int getDocID(){
  return docID;
 }
 @Override
 public String toString(){
  String s = docID +",S:" + getSenderTermFrequency()+",SU:"+getSubjectTermFrequency()+",C:"+getContentTermFrequency();
  return s;
 }
 
 @Override
 public int compareTo(Posting arg) {
  Posting castedArg = (Posting)arg;
  return this.docID - castedArg.getDocID();
 }
 @Override
 public boolean equals(Object other){
 Posting o = (Posting) other;
 if(docID == o.getDocID())return true;
 else return false;
 }
 public int getTotalTermFrequency()
 {return senderTermFrequency + subjectTermFrequency + contentTermFrequency;}
 
 public int getSenderTermFrequency()
 {return senderTermFrequency;}
 
 public int getSubjectTermFrequency()
 {return subjectTermFrequency;}
 
 public int getContentTermFrequency()
 {return contentTermFrequency;}
 
 public void setSenderTermFrequency(int tf)
 {senderTermFrequency = tf;}
 
 public void setSubjectTermFrequency(int tf)
 {subjectTermFrequency = tf;}
 
 public void setContentTermFrequency(int tf)
 {contentTermFrequency = tf;}
 
}
