import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ExportDescription {
  
	static String outPut = "C:\\temp\\Descriptions.html";
	static String dirPath = "C:\\Data\\Project\\";
	
	public static void main(String[] args) throws IOException {
		findAndWriteDescription(dirPath);
      	}

	public static List<File> findAndWriteDescription(String directoryName) throws IOException {

		FileWriter fileWriter = createResultsFile(outPut);	
		File[] fList = getAllFilesFromDirectory(directoryName);
		List<File> resultList = putAllFilesInAList(fList);

        for (File file : fList) {
            if (isThisAnXmlFile(file)) {
       		 try {
       			 Scanner scanner = new Scanner(new File(file.getAbsolutePath())); 
       			 writeTheNameOfTheFile(file,fileWriter);
       			 exportDescription(file,fileWriter);
    			 scanner.close();
    		 } 
    		 catch (FileNotFoundException e) {
    			 System.out.println("Could not find file");
    			 e.printStackTrace();
    		 }     		 
            } else if (file.isDirectory()) {
                resultList.addAll(findAndWriteDescription(file.getAbsolutePath()));
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

		 //System.out.println(file.getAbsolutePath());
		 fileWriter.write("<font color=\"blue\"><p><b>" + file.getAbsolutePath()+ ": </font></b></p>"); // newline
	}

/*	public static void writeTheHeader(FileWriter fileWriter) throws IOException{
		fileWriter.write("<h1 style=\"color:blue;margin-left:30px;\">SoapUI Testcase Descriptions</h1>");
	}
*/
	public static void exportDescription(File file, FileWriter fileWriter){
		  try {

			    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
			    FileInputStream fileInputStream = new FileInputStream(file);
			    InputSource is = new InputSource(fileInputStream);
			    Document doc = documentBuilder.parse(is);
			    Element element = doc.getDocumentElement();

			 // get all child nodes
			    NodeList nodes = element.getChildNodes();
			    
			    boolean foundDescription = false;
			    int i =  0;
			    // find the description node and get it's contents
			    while(i < nodes.getLength() && !foundDescription){
			    	if(nodes.item(i).getNodeName().equals("con:description")){
			    		//System.out.println(nodes.item(i).getTextContent());
			    		String text = nodes.item(i).getTextContent();
			    		fileWriter.write(text.replace("\n", "<br>"));
			    		foundDescription = true;
			    	}
			    	i++;
			    }
			    } catch (Exception e) {
			    e.printStackTrace();
			    }
			  }
	}






