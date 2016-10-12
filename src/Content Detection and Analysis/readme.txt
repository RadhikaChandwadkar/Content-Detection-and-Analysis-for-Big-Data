External Libraries used:
json-simple-1.1.1.jar
opennlp-tools-1.5.0.jar
tika-app-1.13.jar
commons-csv-1.2.jar 
org.apache.commons.io.jar 
htmlparser-1.6p.jar 


GrobidParser.java
Description: This code contains the firing of TEI generation command for each of the input files. Another command is fired to the Google Scholar API. All the related data is extracted.

input: Directory name that contains the files to run.

output: 1 json file per input file with the publication related data. 


SAXHandler.java
Description: Supporting file for GrobidParser.java. It performs the SAX parsing for the TEI XML files.


GeoTopic.java
Description: This code contains the firing of command for running the geotopic parser. The geotopic information is extracted.

input: Directory name that contains the files to run.

output: 1 json file per input file with the geographical  data. 


TagRatio.java
Description: This file has a command for generation of xhtml files. The tags are removed and tag ratio algorithm is implemented. The extracted text is written in a file named 'output.txt'.The measurements are extracted from the files.

input: Directory name that contains the files to run.

output: JSON file containing the measurement data with units. 1 JSON file per input file.

SearchInPos.java
Description: This code scans the POS Dictionary for getting the units for measurements. It is a supporting file for TagRatio.java


CreatePOSDictionary.java
Description: This code generates POS dictionary for the measurement extraction.

input: Input text file named 'f.txt' containing measurement units and the tag for them.

output: POS Dictionary in xml format


Publication.java
Description: This a normal java class which represents a Publication which is stored as a json in the metadata file.

GetMetaScore.java
Description: This code generates a meta score over all the metadata files generated over the parsers.

input: Directory name that contains the json files to run.

output: the meta score for the files and extracted data.

OntologyExtractor.java
Description: This code generates json files containing the SWEET concepts found in the files.

input: Directory name that contains the files to run.

output: The json files containing SWEET concepts.


JsonCombining.java
Description: It combines the output files of Grobid, Tag ratio, Geotopic parser and Sweet Ontology Parser.

input: 4 command line arguments which are the directories containing the outputs of all the parsers.

output: The combined json files of the all the parsers.

YearExtraction.java
Description: This is used to extract the years from the Grobid journal parser's output files for D3 Visualization.

input: 1 argument which is the directory containing the output of the grobid parsers.

output: prints the number of occurances of each year in a csv output files.

TryCall.java (LSTU)
Description: This code generates DOI for files using LSTU.

input: 1 argument which is the directory containing files for which the DOI is to be generated.

output: DOI for all the given files.

OntCount.java
Description: This is used to extract the occurances of each of the SWEET concept from the Ontology parser's output files for D3 Visualization.

input:  1 argument which is the directory containing the output of the Ontology extractor parsers.

output: prints the number of occurances of each SWEET concept in a csv output file.

Countries.java
Description: This is used to extract the occurances of each of Location from the Geotopic parser's output files for D3 Visualization.

input:  1 argument which is the directory containing the output of the Geotopic extractor parsers.

output: prints the number of occurances of each Location in a csv output file.

ConvertCSV.java
Description: This is used to get a csv file from the Geotopic parser's output (json) files for D3 Visualization.

input:  1 argument which is the directory containing the output of the Geotopic extractor parsers.

output: prints the Longititude, Latitude, Location from all files in a csv output file.


GetMetaScore.java
Description: This is used to get a score for each json file containing metadata from the all the parsers' as well as an overall average score for the files.

input:  1 argument which is the directory containing the output of all the parsers.

output: Prints the overall Metascore on the console. It also appends the DOI of the file and the individual score to each file at the END of the File.

ParserScorer.java
Description: This is used to get a score for each Parser.

input:  1 argument which is the directory containing the output of all the parsers.

output: Prints the overall Metascore for each parser on the console.


Solr.java
Description: This file is used for firing the query for Solr injestion

input: 1 argument that specifies the input directory 
Ouput: Solr injestion for the inputed files









