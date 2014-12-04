/*
 * Copyright (c) 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bigdata.consumercomplaint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

// My system uses org.apache.hadoop.fs instead of *.*.*.filesystem
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;

/*
 http://github.com/avinsrid
 
 Minor Modifications made to the code. We were converting based on TSV by default. This will do the conversion for a CSV to Seq file.
 */

public class  ComplaintsCSVtoSeq {
	public static void main(String args[]) throws Exception {
		if (args.length != 2) {
			System.err.println("ComplaintsCSVtoSeq::main(): Arguments: [Input CSV File] [Output Sequence Directory]");
			return;
		}

		// We will read the respective input file and output directory given in above arguments by user
		String inputFile = args[0];
		String outputDir = args[1];
		Configuration configuration = new Configuration();
		FileSystem fileSystem = FileSystem.get(configuration);
		Writer writer = new SequenceFile.Writer(fileSystem, configuration, new Path(outputDir + "/chunk-0"),
				Text.class, Text.class);
		
		int count = 0;
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));

		// Text stores text using standard UTF8 encoding
		Text key = new Text();
		Text value = new Text();
		while(true) {
			String everyLine = reader.readLine();
			if (everyLine == null) {
				break;
			}

			// Here, I am splitting the data on a per line basis by commas
			String[] columns = everyLine.split(",", 3);
			if (columns.length != 3) {
				System.out.println("ComplaintsCSVtoSeq::main(): Invalid Line " + everyLine);
			}
			String productClassified = columns[0];
			String complaintId = columns[1];
			String customerIssue = columns[2];
			key.set("/" + productClassified + "/" + complaintId);
			value.set(customerIssue);
			writer.append(key, value);
			count++;
		}
		reader.close();
		writer.close();
		System.out.println("ComplaintsCSVtoSeq::main(): Wrote " + count + " entries to Seq File.");
	}
}
