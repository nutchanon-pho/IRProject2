import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.Tuple;
import jdbm.helper.TupleBrowser;
import jdbm.helper.StringComparator;
import jdbm.btree.BTree;

import java.io.IOException;
public class Index {
  Scanner in = new Scanner(System.in);  
  RecordManager recMan;
  long recID;
  TupleBrowser browser;
  Tuple tuple = new Tuple();
  BTree dictionary;
  Properties props;
  
  public Index(String indexName){
    props = new Properties();
    try{
      recMan = RecordManagerFactory.createRecordManager( "indexDatabase", props );
      recID = recMan.getNamedObject(indexName);
      if(recID != 0){
        dictionary = BTree.load(recMan, recID);
        System.out.println(indexName + " BTree loaded");
      }
      else{
        dictionary = BTree.createInstance(recMan,new StringComparator());
        recMan.setNamedObject(indexName, dictionary.getRecid());
        System.out.println(indexName + " BTree created");
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
  public void save(){
    try{
      recMan.commit();
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }
  public int getSize(){
      return dictionary.size();
      
  }
  public boolean reset(String indexName) {
    try{
      dictionary = BTree.createInstance(recMan,new StringComparator());
      recMan.setNamedObject(indexName, dictionary.getRecid());
      //System.out.println("Successfully reset");
      return true;
    }
    catch(IOException e){
      System.out.println("No such record");
      return false;
    }
  }
  public void addIndex(ArrayList<String> tokens,int docID,String docName,String type){  
    while(!tokens.isEmpty()){
      String key = tokens.get(0);
      if(!key.equalsIgnoreCase("-")){
        Posting post = new Posting(docName,docID);
   if(type.equalsIgnoreCase("sender")){
     post.setSenderTermFrequency(getTermFrequency(tokens,key));
  }
  if(type.equalsIgnoreCase("subject")){
   post.setSubjectTermFrequency(getTermFrequency(tokens,key));
  }
  if(type.equalsIgnoreCase("content")){
   post.setContentTermFrequency(getTermFrequency(tokens,key));
  }
        insert(key,post,type);
      }
    }
  }
  
  
  public static int getTermFrequency(ArrayList<String> tokens,String key){
    int tf = 0;
    int index=0;
    while(  (index=tokens.indexOf(key))!=-1){
      tf++;
      tokens.remove(index);
    }
    return tf;
  }
  
  public void remove(String key) throws IOException
  {
      PostingList existingPostingList = (PostingList)dictionary.find(key);
      if(existingPostingList == null){
          return;
      }
      else{
          dictionary.remove(key);
      }
  }
  
  public void insert(String key,Posting value,String type){
      try{
      
      PostingList existingPostingList = (PostingList)dictionary.find(key);
      if(existingPostingList == null){
        PostingList postingList = new PostingList();
        postingList.addPosting(value,type);
        dictionary.insert(key,postingList, false);
      }
      else{
        
        existingPostingList.addPosting(value,type);
      }
    }
    catch(IOException e){  
      e.printStackTrace();
    }
  }
  
  public PostingList getPostingList(String keyword){
    PostingList existingPostingList = new PostingList();
    try{
      existingPostingList = (PostingList)dictionary.find(keyword);
    }
    catch(IOException e){
      e.printStackTrace();
    }
    return existingPostingList;
  }
  //To String
  public String toString(){
    String s ="";
    try{
      browser = dictionary.browse();
      int i= 0;
      while ( browser.getNext(tuple) ) {
          s+=printTuple( tuple );
      }
    }catch(IOException e){
      e.printStackTrace();
    }
    return s;
  }
  
  public TupleBrowser getBrowser() throws IOException
  {
      return dictionary.browse();
  }
  
  public void close(){
    try{
      recMan.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }
  private String printTuple(Tuple tuple){
    String word = (String) tuple.getKey();
    PostingList postingList = (PostingList) tuple.getValue();
    return pad(word+","+postingList.getDocumentFrequency()+":",25) + postingList;
  }
  private static String pad( String str, int width ) {
    StringBuffer buf = new StringBuffer( str );
    int space = width-buf.length();
    while ( space-- > 0 ) {
      buf.append( ' ' );
    }
    return buf.toString();
  }
  
}
