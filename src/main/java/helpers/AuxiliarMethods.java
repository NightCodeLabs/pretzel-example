package helpers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cucumber.api.DataTable;

public class AuxiliarMethods {
	
	private static AuxiliarMethods auxiliarMethods = new AuxiliarMethods();
	
	private AuxiliarMethods() {}
	
	public static AuxiliarMethods getInstance() {
		return auxiliarMethods;
	}
	
    public String getDataTableValue(DataTable dataTable, String keyToSearch){   	
        String value = null;
        List<Map<String,String>> dataList = dataTable.asMaps(String.class, String.class);
        for (Map<String,String> row: dataList) {
        	for (Entry<String,String> entry : row.entrySet()) {
            if (entry.getKey().equals(keyToSearch)){
                value = entry.getValue();
            }
        	}
        }        
        if (value==null) {
            System.out.println("The key doesn't exists");
        }
        
        return value;
    }

}
