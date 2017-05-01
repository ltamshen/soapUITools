
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FindCallingTests {

// Search the desired test case, find its guid and then search all tests for that guid

	public static String  testName = "POSTTest.xml";
	public static String guid;
	
	public static void main(String[] args) throws IOException {
		//testName = args[0];	
		String dirPath = "C:\\Data\\Project\\";
		
		if(!testName.contains(".xml")){
			testName = testName.concat(".xml");
		}
				
		findGuid(dirPath, testName);
		System.out.println(guid);

		// parse all tests for the guid
	    listf(dirPath,guid);
      	}

	public static void findGuid(String directoryName, String testName){
		String str, splitstr[];
		// if the input contains ".xml" in test case name, remove it
		String nameNoExtension = removeExtension(testName);
		
		 // get all the files from a directory
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        
        List<File> resultList = new ArrayList<File>();
        resultList.addAll(Arrays.asList(fList));

        // for each file in the directory, if it's a file, read/parse it for the guid
        for (File file : fList) {
        	
            if (file.isFile() && file.getName().equals(testName)) {
       		 try {
    			 Scanner scanner = new Scanner(new File(file.getAbsolutePath())); 
    			 System.out.println(file.getAbsolutePath());
    			 // parse the file for guid   			 
    			 while(scanner.hasNext()){
    				 str = scanner.nextLine();
    				 if(str.indexOf(nameNoExtension) != -1){
    					 // found it
    					 // this is where we look for the guid
    					 splitstr = str.split("id=");
    					 splitstr = splitstr[1].split(" ");
    					 guid = splitstr[0].replace("\"", "");
    					 break;

    					  }
    			 }
    			 scanner.close();
    		 } 
    		 catch (FileNotFoundException e) {
    			 System.out.println("Could not find  file");
    			 e.printStackTrace();
    		 }     		 
       		 // otherwise it's not a file, it's a directory, continue recursion
            } else if (file.isDirectory()) {
                findGuid(file.getAbsolutePath(),testName);
            }
        }     
    }

	
	public static List<File> listf(String directoryName,String guid) throws IOException {
		
		String str;
		String nameNoExtension = removeExtension(testName);
		String outPut = "C:\\temp\\CallingTests_" + nameNoExtension + ".txt";
		
		// if the resource.txt (output) file exists, delete it before creating a new one
		File resultsFile = new File(outPut);
		
		if(resultsFile.exists()){
			resultsFile.delete();
		}
		resultsFile.createNewFile();
		FileWriter fileWriter = new FileWriter(resultsFile,true);
		
        // get all the files from a directory
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        
        List<File> resultList = new ArrayList<File>();
        resultList.addAll(Arrays.asList(fList));

        // for each file in the directory, if it's a file, read/parse it for the guid
        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith("xml") && (!file.getName().equals("settings.xml"))) {
       		 try {
    			 Scanner scanner = new Scanner(new File(file.getAbsolutePath())); 
    			 
    			 // parse the file for guid   			 
    			 while(scanner.hasNext()){
    				 str = scanner.nextLine();
    				 if(str.indexOf(guid) != -1){
    					 // found it
    					 System.out.println(file.getAbsolutePath());
    	    			 fileWriter.write(file.getAbsolutePath()+System.getProperty("line.separator")); // newline
    					  break;  				 }
    			 }
    			 scanner.close();
    		 } 
    		 catch (FileNotFoundException e) {
    			 System.out.println("Could not find  file");
    			 e.printStackTrace();
    		 }     		 
       		 // otherwise it's not a file, it's a directory, continue recursion
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath(), guid));
            }
        }
        fileWriter.close();
        return resultList;
    }
	
	public static String removeExtension(String fileName){
		String nameNoExtension = fileName;
		
		if(fileName.indexOf(".") > 0){
				nameNoExtension = fileName.substring(0,fileName.lastIndexOf("."));
			}
		return nameNoExtension;
	}
}


