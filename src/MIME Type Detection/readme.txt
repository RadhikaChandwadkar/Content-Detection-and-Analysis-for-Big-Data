ANALYSIS
-----------------------------------------------------------------------------------------------------------------------------------------------------
Note: external libraries used are 
	1. json-simple-1.1.1.jar
	2. tika-1.11.jar
	

1. BFA.java

Input:
command line argument: 1. directory that directly contains files of a particular MIME type (training data for the MIME type)

Output:
Fingerprint represented as a JSON file.

Representation of JSON for D3 visualization:
[{"byte":0 , "frequency":0.67},...] x256 entries

2. BFC.java

Input:
command line argument: 1. directory that directly contains files of a particular MIME type (test data for the MIME type)
		       2. fingerprint of BFA output fingerprint represented as json file

Output:
Correlation strength represented as JSON file.

Representation of JSON:
[{"correlation":0.67 , "byte":0},...] x256 entries

3.  Byte Frequency Crosscorrelation

Input:
command line argument: 1. fingerprint represented as JSON file 
			2. directory that directly contains files of a particular MIME type (test data for the MIME type)

Output:
Cross correlation matrix represented as a CSV file.


4 FHT.java

input:
command line argument: 1. directory that directly contains files of a particular MIME type (training data for the MIME type)
		       2. header/trailer size for fingerprint generation.

output:
2 files 
1 for header of size H*256
2 for trailer of size T*256 represented as CSV files.




-------------------------------------------------------------------
Using d3 external js

<script src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.v0.6.3.js"></script>


---------------------------------------------------------------------------

Changes made in Tika Repository

1) tika-mimetypes.xml 
	- added match elements for the new MIME types discovered.
	- added new MIME type for zero length files in the Octet stream.

2)ZeroLengthFileDetector
	- new detector for detecting the zero length files as new MIME type application/zerosize.
	- 
3)org.apache.tika.detect.Detector
	-location tika-1.11-src\tika-1.11\tika-parsers\src\main\resources\META-INF\services.
	-The file contains all the detectors which are used by Tika. 
	-The new detector ZeroLengthFilDetector is added in the list of detectors for updation.

4)MediaTypeRegistry
	-