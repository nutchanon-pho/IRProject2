import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

public class FTSP{
    public static void main(String[] args)throws IOException
    {
        System.out.print(args[0]);
        if(args.length >= 3){
            if(args[0].equalsIgnoreCase("create")){
                String indexName = args[1];
                String folderName = args[2];
                Index index = new Index(indexName);
                File rootDir;
                File[] accounts;
                //Get folder from sibbling node
                rootDir = new File("../"+folderName);
                System.out.println(rootDir.getAbsolutePath());
                accounts = rootDir.listFiles(); // get user accounts
                int docID = 1;
                for(File f : accounts)
                {
                    File sendFolder = new File(f.getAbsolutePath()+"/"+"sent");
                    System.out.println(f.getName());
                    ArrayList<File> emails = getEmails(sendFolder);//account's sentItem
                    if(emails!=null){
                        for(int i = 0;i<emails.size();i++){
                            addIndexFromFile(index, emails.get(i), docID, f.getName() + "" + emails.get(i).getName());
                            //System.out.println(emails.get(i).getName());
                            docID++;
                        }
                    }
                }
                System.out.println(index);
                //index.save();
                return;
            }
            if(args[0].equalsIgnoreCase("search")){
                // Earth's Part
                System.out.println("Search Index");
                return;
            }
        }
        System.out.println("invalid argument");
    }
    
 /////////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<File> filterEmail(ArrayList<File> emails){
        ArrayList<File> newlist = new ArrayList<File>();
        for(int i =0;i<emails.size();i++){
            String fileName = emails.get(i).getName();
            if(fileName.indexOf(".") != -1)
                fileName = fileName.substring(0,fileName.length()-1);
            if(Integer.parseInt(fileName) <=50){
                newlist.add(emails.get(i));
            }
        }
        return newlist;
    }
    public static ArrayList<File> getEmails(File folder){
        
        File[] emails = folder.listFiles();// Get email
        if(emails == null)return null;
        //Put every email in arrayList to be filtered out later
        ArrayList<File> emailFiles = new ArrayList<File>();
        for(File email : emails){
            emailFiles.add(email);
        }
        emailFiles = filterEmail(emailFiles);
        return emailFiles;
    }
    
    public static ArrayList<String> getTokens(String line){
        //System.out.println(line);
        ArrayList<String> tokens = new ArrayList<String>();
        String words[] = line.split("\\s+");
        for(int i = 0;i<words.length;i++){
            words[i] = words[i].replaceAll("[^a-zA-Z]","");
            words[i] = words[i].toLowerCase();
            if(words[i].length()!=0)
                tokens.add(words[i]);          
        }
        
        return tokens;
    }
    
    public static void addIndexFromFile(Index index, File email,int docID, String docName) throws FileNotFoundException, IOException
    {
        Pattern senderZone = Pattern.compile("^From:\\s+");
        Pattern subjectZone = Pattern.compile("^Subject:\\s+");
        BufferedReader bf = new BufferedReader(new FileReader(email));
        String s="start";
        int i =1;
        String senderToken ="";
        String subjectToken = "";
        while(s.length()!=0)
        {
            s= bf.readLine();
            Matcher senderZoneMatcher = senderZone.matcher(s);
            Matcher subjectZoneMatcher = subjectZone.matcher(s);
            if(senderZoneMatcher.find()){
                senderToken = s.substring(senderZoneMatcher.end());
            }
            if(subjectZoneMatcher.find()){
                subjectToken = s.substring(subjectZoneMatcher.end());
            }
            i++;
        }
        ArrayList<String> senderTokenList = getTokens(senderToken);
        //System.out.println(senderTokenList);
        ArrayList<String> subjectTokenList = getTokens(subjectToken);
        //System.out.println("Sender :"+senderToken);
        //System.out.println("Subject:"+subjectToken);
        index.addIndex(senderTokenList, docID, docName,"sender");
        index.addIndex(subjectTokenList, docID, docName,"subject");
        String contentLine = "";
        while( (contentLine = bf.readLine()) != null)
        {
            ArrayList<String> contentTokenList = getTokens(contentLine);
            index.addIndex(contentTokenList, docID, docName,"content");
        }
        //System.out.print(index.toString());
    }
}