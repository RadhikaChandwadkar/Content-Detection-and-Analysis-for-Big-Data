External Jars :
json-simple-1.1.1.jar
opennlp-tools-1.5.0.jar
tika-app-1.13.jar
commons-csv-1.2.jar 
org.apache.commons.io.jar 
htmlparser-1.6p.jar 


Java codes

DocumentLanguageDetection.java
Description:
Identifies language of the file in the dataset
Input: directory that contains all the files
Output:csv file that contains count of each language

ExtraMinMax.java
Description:
Finds out the maximum and minimum values of measuremenst in a dataset
Input:The Json files that contain all the meta data
Output:csv file that contains the min, max and average

MeasurementDetails.java
Description:
This file calculates the total count for each measurement occuring in the data
Input:Input Json metadata files
Output:CSV file having the count for each measurement

MinMax.java
Description:This is a file that defines the class . Basically a java BEAN 

SizeDiversity.java
Description:
This code calculates the ratio of meta data to the main file for evry file in the dataset.
Input: The directory that contains the files
Output:the final ratio for each file along with the average ratio


MaximalAgreement.java
Description: This file check for agreement between the 4 NER tools 
Input: The JSON files created by each of the NER toolkit for the whole dataset
Output: The maximally agreed words with their respective counts

ParserScore.java
Description: Calcualtes the number of entities by each parser and score upon the MIME type
Input:File directory for MIME type
Ouput:Average score over the MIME type

MetadataSize.java
Description:Calculates the average metadata file size per MIME type
Input: File directory for MIME type
Output:Average file size over the MIME type

NLTKRunner.java / GrobidQuantities/ Standford.java
Description: used for running the commands for each of the NER toolkit
Input:the folder containing the dataset
Output: The tagged values