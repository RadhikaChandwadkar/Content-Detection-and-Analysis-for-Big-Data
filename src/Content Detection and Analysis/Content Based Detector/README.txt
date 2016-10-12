1.Reference:
https://github.com/rrgirish/filetypeDetection

*********Assuming R is installed on the system**********
2.Content_Detection.java 
(found in src folder)
-This file is used to create the CSV files as needed by the R program .
-These CSV contain byte histogram with label stating if positive or negative. 
-Created for the Mime type application/xhtml+xml

3.main.R 
(from respository referenced in[1])
-Modified this file to take the three CSV files generated above and create a trained model for application/xhtml+xml
-The R file generates a model that has been included in tika.

4.Integrating model with Tika
-copy the model file to \tika-core\test \resources\org\apache\tika\detect\
-Created NNExampleModelDetector.java file that loads the trained model. This file can be found in \tika-core\src\main\java\org\apache\tika\detect


5. Optimx library was installed for R as the program in R that created the trained model was referring it.
-The installation files can be found at https://cran.r-project.org/web/packages/optimx/index.html


