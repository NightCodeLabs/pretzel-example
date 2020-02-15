package helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVReader;

public class FileOperations {

	private static FileOperations fileOperations = new FileOperations();
	
	private FileOperations() {}
	
	public static FileOperations getInstance() {
		return fileOperations;
	}
	
    public JSONArray CSVToJson(String csvPath) throws IOException, ParseException {

        File csvFile = new File(csvPath);

        JSONParser parser = new JSONParser();
        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        // Read data from CSV file
        List<? extends Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(csvFile).readAll();

        ObjectMapper mapper = new ObjectMapper();

        JSONArray jsonObject = (JSONArray) parser.parse(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));

        System.out.print(jsonObject.toString());

        return jsonObject;
    }
    
    
    public List<String[]> readCSV(String pathFile){
    	List<String[]> list = new ArrayList<>();
	    String[] fila = null;
    	CSVReader csvReader;
		try {
			csvReader = new CSVReader(new FileReader(pathFile));
			while((fila = csvReader.readNext()) != null) {    		
				   list.add(fila);
		}
		csvReader.close();
		}catch (FileNotFoundException e) {
			System.out.println("Something went wrong reading the CSV Report file");
		} catch (IOException e) {
			System.out.println("Something went wrong reading the CSV Report file");
		}
		
    	return list;
    }
    
    
   public String getAbsolutePath(String path) {
	   return Paths.get(path).toFile().getAbsolutePath();
   }
   
   public void initialiseChartsFolder(String path) {
	  try {
		  FileUtils.deleteDirectory(new File(path));
		  FileUtils.forceMkdir(new File(path));
	  } catch (IOException e) {
		  System.out.println("Something went wrong initialising the charts directory");
	  }			
   }
    
	
}
