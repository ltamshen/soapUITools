
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FindResources {

	public static void main(String[] args) throws IOException {
/*		String str,splitStr[];
		String outPut = "C:\\temp\\Resources.txt";
		File resultsFile = new File(outPut);
		resultsFile.createNewFile();
		FileWriter fileWriter = new FileWriter(resultsFile);*/
		
		
				
		String dirPath = "D:\\Data\\Project\\";
        File dir = new File(dirPath);
        String[] files = dir.list();
        listf(dirPath);
      
        /*if (files.length == 0) {
            System.out.println("The directory is empty");
        }
            else
            {
            	 for (String aFile : files)
            	 {
            		 
            		 try {
            			 Scanner scanner = new Scanner(new File(dirPath+aFile));
            			 System.out.println(aFile);
            			 fileWriter.write(aFile+System.getProperty("line.separator"));
            			 
            			 while(scanner.hasNext()){
            				 str = scanner.nextLine();
            				 if(str.indexOf("resourcePath") != -1){
            					 // found it
            					 splitStr = str.split("resourcePath");
            					 String after = splitStr[1];
            					 String resource[] = after.split(" ");	
            					 String method[] = str.split("methodName");
            					 String restName[] = method[1].split(" ");
            					 System.out.print(restName[0].replace("=", " ").replace("\"", " "));
            					 System.out.println(resource[0].replace("=", " ").replace("\"", " "));
            					 fileWriter.write(restName[0].replace("=", " ").replace("\"", " ")+resource[0].replace("=", " ").replace("\"", " "));
            					 fileWriter.write(System.getProperty("line.separator"));
            				 }
            			 }
            			 scanner.close();
            		 } 
            		 catch (FileNotFoundException e) {
            			 System.out.println("Could not find  file");
            			 e.printStackTrace();
            		 }
            	 }
            }*/
        //fileWriter.close();
	}

	
	public static List<File> listf(String directoryName) throws IOException {
		String str,splitStr[];
		String outPut = "C:\\temp\\Resources.txt";
		File resultsFile = new File(outPut);
		if(resultsFile.exists()){
			resultsFile.delete();
		}
		resultsFile.createNewFile();
		FileWriter fileWriter = new FileWriter(resultsFile,true);
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();
        
        // Try using a hashset for the resources so there are no duplicates printed
        
        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
       		 try {
    			 Scanner scanner = new Scanner(new File(file.getAbsolutePath())); 
    			 System.out.println(file.getAbsolutePath());
    			 fileWriter.write(file.getAbsolutePath()+System.getProperty("line.separator"));
    			 
    			 while(scanner.hasNext()){
    				 str = scanner.nextLine();
    				 if(str.indexOf("resourcePath") != -1){
    					 // found it
    					 splitStr = str.split("resourcePath");
    					 String after = splitStr[1];
    					 String resource[] = after.split(" ");	
    					 String method[] = str.split("methodName");
    					 String restName[] = method[1].split(" ");
    					 System.out.print(restName[0].replace("=", " ").replace("\"", " "));
    					 System.out.println(resource[0].replace("=", " ").replace("\"", " "));
    					 fileWriter.write(restName[0].replace("=", " ").replace("\"", " ")+resource[0].replace("=", " ").replace("\"", " "));
    					 fileWriter.write(System.getProperty("line.separator"));
    				 }
    			 }
    			 scanner.close();
    		 } 
    		 catch (FileNotFoundException e) {
    			 System.out.println("Could not find  file");
    			 e.printStackTrace();
    		 }
                //System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        //System.out.println(fList);
        fileWriter.close();
        return resultList;
    }
}




