import java.util.ArrayList;
public class IndexTester{
  public static void main(String[] args){
    Index index = new Index("ohm");
    ArrayList<String> tokens = new ArrayList<String>();
    tokens.add("i");
    tokens.add("Just");
    tokens.add("love");
    tokens.add("you");
    tokens.add("i");
    tokens.add("never");
    tokens.add("die");
    index.addIndex(tokens,1,"Pana-1","content");
    tokens = new ArrayList<String>();
    tokens.add("Fuck");
    tokens.add("you");
    tokens.add("i");
    index.addIndex(tokens,1,"Pana-1","subject");
    System.out.println(index.toString());
    PostingList p = index.getPostingList("i");
    ArrayList<Posting> pt = p.getList();
    for(int i = 0;i<pt.size();i++){
      System.out.println(pt.get(i).getTotalTermFrequency());
    }
    index.save();
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
}