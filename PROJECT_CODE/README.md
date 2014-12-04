####################################################################
COMPILATION INSTRUCTIONS
####################################################################

Navigate to PROJECT-CODE

mvn clean install

1) To execute CSV to Seq converter:

java -cp target/Classification-Files-Big-Data-Project-1.0.jar com.bigdata.consumercomplaint.ComplaintsCSVtoSeq $path_to_input_dir $path_to_output_dir

2) To execute column reducer file:

java -cp target/Classification-Files-Big-Data-Project-1.0.jar com.bigdata.consumercomplaint.ColumnReducerSorter data/Consumer_Complaints.csv data/

Output will be stored in data/SortedData.csv

3) To convert SortedData.csv into a csv file that contains columns in format of [Product, Complaint ID, Issue] for NY state alone

~/BigData_Project/PROJECT-FILES$ java -cp target/Classification-Files-Big-Data-Project-1.0.jar com.bigdata.consumercomplaint.NY
NY::main(): Successfully written 12238 rows with NY state

####################################################################

