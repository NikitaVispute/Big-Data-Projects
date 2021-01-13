# Word Count for Named Entities and Search Engine for Movies Data

**Technology: Spark in Scala.**<br>

**•	Pre-processed a large text file from gutenberg.org website and then implemented a mapreduce function to the data to count the most frequent named entities in the text.** <br>

**•	Pre-processed the movie summary dataset from Carnegie Movie Summary Corpus and built a search engine for the data using the tf-idf technique by MapReduce for each document pair. The query is of two types: single term or multiple terms and this would display the appropriate movies when entered in the search engine.**<br>

**1 WordCount for Named Entities**<br>

Compute the word frequency for named entities in a large file. <br>
Steps <br>
1. Find a large text file from the Gutenberg project: https://www.gutenberg.org and upload it to your Databricks cluster.<br>
2. Write code for a mapreduce program in Scala/PySpark which reads in the file, and then extracts only the named entities. <br>
3. The output from the map task should be in the form of (key, Value) where key is the named entity, and value is its count (i.e. once every time it occurs)<br>
4. The output from the reducer should be sorted in descending order of count. That is, the named entity that is most frequent should appear at the top.<br>

**2 Search Engine for Movie Plot Summaries**<br>

Work with a dataset of movie plot summaries that is available from the Carnegie Movie Summary Corpus site. Building a search engine for the plot summaries that are available in the file “plot summaries.txt” using the tf-idf technique.<br>
Implemented using Scala/PySpark code that can run on a Databricks cluster.<br>

Steps<br>
1. Extract and upload the file plot summaries.txt from http://www.cs.cmu.edu/~ark/personas/data/MovieSummaries.tar.gz to Databricks. Upload a file containing user’s search terms one per line.<br>
2. Remove stopwords by a method of your choice.<br>
3. Create a tf-idf for every term and every document (represented by Wikipedia movie ID) using the MapReduce method.<br>
4. Read the search terms from the search file and output following:<br>
(a) User enters a single term: Output the top 10 documents with the highest tf-idf values for that term.<br>
(b) User enters a query consisting of multiple terms: An example could be “Funny movie with action scenes”. Evaluate cosine similarity between the query and all the documents and return top 10 documents having the highest cosine similarity values.<br>
For the search terms entered by the user, you will return the list of movie names sorted by their relevance values in descending order. <br>
5. You can display output of your program on the screen<br>
