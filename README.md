# TransactionAnalyserCsv
### Analyzes the information from the template csv file for your query
The system reports the total number of transactions and the average transaction value for a specific merchant in a specific date range from a properly formatted .csv file.

The program takes 4 arguments:
filename fromDate toDate merchant
1) <b>filename</b> is name of .csv file
2) <b>fromDate</b> indicates from what date the report should be generated
3) <b>toDate</b> indicates to what date the report should be generated
4) <b>merchant</b> is name of specific merchant

Date format pattern = <b>"DD/MM/YYYY hh:mm:ss"</b>

Arguments example: <i>example.csv "20/08/2018 12:00:00" "20/08/2018 13:00:00" Kwik-E-Mart</i>

Example of starting a program: <i>java -jar TransactionAnalyser-1.0.jar ../example.csv "20/08/2018 12:00:00" "20/08/2018 13:00:00" Kwik-E-Mart</i>

The jar file is located in /target folder.
