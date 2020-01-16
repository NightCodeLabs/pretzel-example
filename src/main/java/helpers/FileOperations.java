package helpers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class FileOperations {

	
	
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
    
	
}
