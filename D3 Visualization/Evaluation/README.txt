-Open all the D3 visualizations in Firefox browser
-The D3 visualiztion for parser calling chain (Bubble menu) uses GoJS library. It is available on GitHub
1) Request Response Classification Path-> tree.html
(Use with Mozilla Firefox at 80% zoom)
It gives the visualization of the various request-response types . Opening the nodes will give more information

2)Average MetaData Extraction Ratio by MIME type-> index.html
(Use with Mozilla Firefox at 100% zoom)
This visualization gives information about percent of meta data extracted per MIME type.

3)Parser calling chain-> index1.html
(Use with Mozilla Firefox at 150% zoom **Will take time to load)
This visualiztion gives information about how the parser calling is done, how the chain is invoked for a particular file. The various components of each parser are shown and a flowchart is produced when interacted with the components.

4)Metadata vs Original data ->index.html
(Use with Mozilla Firefox at 100% zoom **Will take time to load)
It shows the percentage of metadata extracted per parser and also how much percent of metadata is extracted from original data for each MIME type.

5)Language Diversity Analysis of the Polar Data->pie_chart.html
(Use with Mozilla Firefox at 80% zoom)
This pie chart represnts the % presence of each language in the dataset

6)WordCloud ->index.html
(Use with Mozilla Firefox at 90% zoom ** Will take time to load. Might have to reopen it)
This visualization represents the frequently occuring words in the dataset / meta data  and SWEET classes

7)NLTK-vs-CoreNLP-vs-OpenNLP-vs-GrobidQuantities-> index.html
(Use with Mozilla Firefox at 100% zoom)
This chart shows the maximal agreement between the 4 NER tools, giving the words that are maximally agreed upon

8)Hierachical display of measurements ->index.html
(Use with Mozilla Firefox at 100% zoom)
This chart shows the the minimum, maximum and average value of each measurement per domain, per MIME type, per type