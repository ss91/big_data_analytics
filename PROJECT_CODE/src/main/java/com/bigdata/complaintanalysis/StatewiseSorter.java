/* This code takes the trimmed dataset with 4 columns and makes new csv files matching the first column - this is like code uploaded
by avinash except files made dynamically iterating through the entire dataset and adding. Runtime not too bad ~15/20 secs*/

package StatewiseSorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StatewiseSorter {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/nachirau/Desktop/bigdata proj/Consumer_Complaints.csv");
		FileReader fileReader = new FileReader(file);
		BufferedReader Readline = new BufferedReader(fileReader);
		String Line, parameter;
		FileWriter writeFile = null;
		ArrayList<String> states = new ArrayList<String>();
		Map<String,String> statefiles = new HashMap<String,String>();
		try {
			while((Line = Readline.readLine()) !=null) {
				int count=0;
				boolean Flag = false;
				Scanner scanLine = new Scanner(Line).useDelimiter(",");
				while(scanLine.hasNext()) {
					count++;
					parameter = scanLine.next();
					if(count==1 && parameter.length()==2) {
						if(writeFile!=null) writeFile.close();
						if(!statefiles.containsKey(parameter)) {
							
							System.out.println("No match exists for state :" + parameter + "\nMake new file");
							states.add(parameter);
							String path = "/Users/nachirau/Desktop/bigdata proj/sorted/" + parameter+".csv";
							statefiles.put(parameter, path);
							writeFile = new FileWriter(path);
							System.out.println(parameter);
						}
						else {
							
							writeFile = new FileWriter(statefiles.get(parameter),true);
						}
					}
					else if(count==1 && parameter.length()!=2) { 
						Flag = true;
						break;
					}
					if(parameter !=null) writeFile.append(parameter);
					if(parameter !=null) writeFile.append(", ");
				}
				if(!Flag) writeFile.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
