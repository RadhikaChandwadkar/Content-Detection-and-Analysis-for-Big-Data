# Content Detection and Analysis for File Type Detection

#####Project Description
JPL and USC under the direction of Dr. Mattmann have worked to collect a corpus of “deep web” polar datasets spanning many file types, scientific data, images, videos, and other information on the Web. These pieces of data were collected using Apache Nutch, Apache Tika, and Apache Solr.

Right now, though the data has been collected, it has not been synthesized into a core website allowing Deep Search - ultimately the goal of this project is to construct such a web site using modern information retrieval technologies including Nutch, Tika and Solr, along with Banana, Facetview, and D3.js. The goal is to create http://polar.usc.edu and to use this website to demonstrate value to the NSF, to USC, and to NASA.

Polar Deep Search Engine for CSCI401 Capstone

##### Related Repos
- [solr-enrich](https://github.com/lorsposto/solr-enrich) - python scripts for extracting metadata from text content of solr documents

####Set up

######Maven

You will need to install Maven if you do not have it already.

https://maven.apache.org/

For mac, if you have homebrew:

`brew install maven`

######Install static dependencies

`npm install`

`bower install` or `node_modules/.bin/bower install`

You need to place static dependencies in resources:

`cp bower_components/startbootstrap-scrolling-nav/css src/main/resources/edu/usc/polar/`

`cp bower_components/startbootstrap-scrolling-nav/js src/main/resources/edu/usc/polar/`

`cp bower_components/startbootstrap-scrolling-nav/fonts src/main/resources/edu/usc/polar/`

######Start command:

From the root directory, run:

`mvn jetty:run`

or

`mvn tomcat7:run`

Then navigate to <http://localhost:8080>



# Indexing to Solr from Team Project Submission

Downloading the directory of a team's submission from the Amazon S3 bucket, you likely will end up with something like this:

```
├── TeamX/
│   ├── cca
│   ├── raw
│   │   ├── crawldb
│   │   ├── linkdb
│   │   ├── segments
```

When one performs a Nutch crawl, the data is stored in `crawldb`, `linkdb`, and `segments`. This data can be indexed into Solr and viewed in a Banana dashboard. Wooh!

### Version Info
For reference, the versions I'm using are as follows:
- Solr 4.4.0
- Nutch 1.10 or 1.11 (don't remember, my downloads folder tells me 1.11 but my  notes say 1.10-trunk as per the CS572 Assigment)
- Banana 1.5

### Putting it all together
##### Solr
For me, Solr is at `~/solr-4.4.0/solr` (i.e. the internal solr directory); this is what I'll refer to as `$SOLR_HOME`. In `$SOLR_HOME` to build a directory of the example set-up at `$SOLR_HOME/example`:
```
ant example
```
Navigate to that `$SOLR_HOME/example`. To run the example:
```
java -jar start.jar
```
Then navigate to http://localhost:8983/solr to bring you to the admin page where you can manage collections. Cool!

