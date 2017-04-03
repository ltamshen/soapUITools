
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class FindResourcesRefactored {

    static HashSet<String> resourceName = new HashSet<String>(); // Use a hashset for the resources so there are no duplicates printed
	static String outPut = "C:\\temp\\Resources.txt";
	static String dirPath = "C:\\Data\\SoapUI\\Project\\";
	
	public static void main(String[] args) throws IOException {
	
		findAndWriteResources(dirPath);
      	}

	public static List<File> findAndWriteResources(String directoryName) throws IOException {
		String str;
		FileWriter fileWriter = createResultsFile(outPut);	
		File[] fList = getAllFilesFromDirectory(directoryName);
		List<File> resultList = putAllFilesInAList(fList);

        for (File file : fList) {
            if (isThisAnXmlFile(file)) {
       		 try {
       			 Scanner scanner = new Scanner(new File(file.getAbsolutePath())); 
       			 writeTheNameOfTheFile(file,fileWriter);

    			 while(scanner.hasNext()){
    				 str = scanner.nextLine();
    				 if(foundResourcePath(str)){
    					addToResourceName(str);				  					 
    				 }
    			 }
    			 writeResourceToFile(fileWriter);  			
    			 resourceName.clear();
    			 scanner.close();
    		 } 
    		 catch (FileNotFoundException e) {
    			 System.out.println("Could not find  file");
    			 e.printStackTrace();
    		 }     		 
            } else if (file.isDirectory()) {
                resultList.addAll(findAndWriteResources(file.getAbsolutePath()));
            }
        }
        fileWriter.close();
        return resultList;
    }
	
	
	public static FileWriter createResultsFile(String outPut) throws IOException{
		File resultsFile = new File(outPut);
		if(resultsFile.exists()){
			resultsFile.delete();
		}
		resultsFile.createNewFile();
		FileWriter fileWriter = new FileWriter(resultsFile,true);
		return fileWriter;
	}
	public static File[] getAllFilesFromDirectory(String directoryName){

        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        return fList;
	}
	public static List<File> putAllFilesInAList(File[] fList){
        List<File> resultList = new ArrayList<File>();
        resultList.addAll(Arrays.asList(fList));
        return resultList;
	}
	
	public static boolean isThisAnXmlFile(File file){
		return file.isFile() && file.getName().endsWith("xml") && (!file.getName().equals("settings.xml"));
	}
	
	public static void writeTheNameOfTheFile(File file, FileWriter fileWriter) throws IOException{

		 System.out.println(file.getAbsolutePath());
		 fileWriter.write(file.getAbsolutePath()+System.getProperty("line.separator")); // newline
	}
	
	public static void addToResourceName(String str){
		String splitStr [] = str.split("resourcePath");
		 String after = splitStr[1];
		 String resource[] = after.split(" ");	
		 String method[] = str.split("methodName");
		 String restName[] = method[1].split(" ");
		 resourceName.add(restName[0].replace("=", " ").replace("\"", " ")+resource[0].replace("=", " ").replace("\"", " "));
		 
	}
	public static void writeResourceToFile(FileWriter fileWriter) throws IOException{
		 System.out.println(resourceName);
		 for(String s:  resourceName){
		 fileWriter.write(s + System.getProperty("line.separator"));
		 }
	}
	public static boolean foundResourcePath(String str){
		 // parse the file for something like this:
		 // resourcePath="/edm/api/V4/matters/{matterId}/fields" methodName="POSTFields" 
		return str.indexOf("resourcePath") != -1;
	}
	
	
}




