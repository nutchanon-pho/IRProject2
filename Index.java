import java.util.ArrayList;
import java.util.Properties;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.Tuple;
import jdbm.helper.TupleBrowser;
import jdbm.helper.StringComparator;
import jdbm.btree.BTree;

import java.io.IOException;
public class Index {
	RecordManager recMan;
	long recID;
	TupleBrowser browser;
	Tuple tuple = new Tuple();
	BTree dictionary;
	Properties props;
	
	public Index(){
		props = new Properties();
		try{
			recMan = RecordManagerFactory.createRecordManager( "index", props );
			recID = recMan.getNamedObject("dictionary");
			if(recID != 0){
				dictionary = BTree.load(recMan, recID);
				System.out.println("Dictionary BTree loaded");
			}
			else{
				dictionary = BTree.createInstance(recMan,new StringComparator());
				recMan.setNamedObject("dictionary", dictionary.getRecid());
				System.out.println("Dictionary BTree created");
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
	public boolean reset() {
		try{
			dictionary = BTree.createInstance(recMan,new StringComparator());
			recMan.setNamedObject("dictionary", dictionary.getRecid());
			//System.out.println("Successfully reset");
			return true;
		}
		catch(IOException e){
			System.out.println("No such record");
			return false;
		}
	}
	public void addIndex(ArrayList<String> tokens,int docID){		
		@SuppressWarnings("unchecked")
		ArrayList<String> dumpTokens = (ArrayList<String>) tokens.clone();
		while(!tokens.isEmpty()){
			String key = tokens.remove(0);
			ArrayList<Integer> positionList = new ArrayList<Integer>();
			positionList.add(dumpTokens.indexOf(key)+1); //start at 1
			dumpTokens.set(dumpTokens.indexOf(key), "<RETRIVED>");
			while(tokens.indexOf(key)!= -1){
				int realPosition = tokens.indexOf(key);
				int position = dumpTokens.indexOf(key);
				positionList.add(position+1);
				tokens.remove(realPosition);
				dumpTokens.set(dumpTokens.indexOf(key), "<RETRIVED>");
			}
			if(!key.equalsIgnoreCase("-")){
				Posting post = new Posting(docID,positionList); 
				insert(key,post);
			}
		}

	}
	public void insert(String key,Posting value){
		try{
			PostingList existingPostingList = (PostingList)dictionary.find(key);
			if(existingPostingList == null){
				PostingList postingList = new PostingList();
				postingList.addPosting(value);
				dictionary.insert(key,postingList, false);
			}
			else{
				existingPostingList.addPosting(value);
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
	public String toString(){
		String s ="";
		try{
	        browser = dictionary.browse();
	        while ( browser.getNext(tuple) ) {
	            s+=printTuple( tuple );
	        }
		}catch(IOException e){
			e.printStackTrace();
		}
		return s;
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
