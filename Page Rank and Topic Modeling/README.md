# Page Rank for Airports data and Topic Modeling for Classics Book 

**Technology: Spark in Scala, PySpark, AWS, Databricks.**

**Implemented a Scala class to compute the page rank of each node (using airport data: origin and destination) based on number of inlinks and outlinks using MapReduce and executed it using AWS S3 & AWS EMR cluster.**

**Implemented a Scala class to compute the top 5 topics in a classic book from www.gutenberg.org using the LDA model for topic modeling and executed it using AWS S3 & AWS EMR cluster.**

**1 PageRank for Airports**<br>
Dataset:<br>
1. The dataset for this project will be a graph that shows connections between various airports. This
data is available at:Bureau of Transportation website at https://www.transtats.bts.gov/DL_SelectFields.asp?Table_ID=236 and
get the data for the latest month available.<br>
2. We are only interested in the following fields to create the graph<br>
• Origin (Origin Airport Code)<br>
• Dest (Destination Airport Code)<br>
3. Download and unzip to get a csv file.<br>

Steps:<br>
1. Program Arguments: Your class should have three parameters:<br>
      1. Location of the csv file containing the fields mentioned above<br>
      2. Maximum number of iterations to run<br>
      3. Location of output file<br>
2. You will compute the page rank of each node (airport) based on number of inlinks and outlinks.<br>
There may be multiple connections between two airports, and you should consider them independent of each other to compute the number of inlinks and outlinks. <br>
3. You have to limit yourself to maximum number of iterations specified by the input parameter number 2. You can initialize all the PageRank values to be 10.0.<br>
4. Name of class as PageRank in Scala Project.<br>
5. Your output should be stored in the location specified by the third parameter. The output should contain the airport code and its PageRank, and data should be sorted by the PageRank in a descending order. All files should be loaded on AWS S3.<br>


**2 Topic Modeling from Classics Books**<br>
Choose one of the classic books from the Gutenberg project http://www.gutenberg.org and perform topic analysis on it. You will then output the 5 most important topics from the book and list them.<br>
The program should take the following arguments:<br>
    1. Path of the input file<br>
    2. Path of the output file<br>
Create another class in the same project as part 1, and name of class be TopicModeling.<br>
Your output should be stored in the location specified by the second parameter. The output should contain the the listing of top 5 topics. Any files that you use should be hosted on AWS S3.<br>


Folder contains<br>
1. The built jar file for Q1 and Q2.<br>
2. The project files for Q1, Q2 written in Scala using IntelliJ IDE) in "Assignment-2"<br>
3. Output file for Q1 in folder OutputPageRank<br>
4. Output file for Q2 in folder OutputTopicModeling<br>

(jar file for Q1, Q2- "s3://6350assign2/assignment-2_2.11-0.1.jar")<br>
Question 1: Page Rank<br>

1. As given above, the Scala code for Page Rank is in the "Assignment-2" folder within the src -> main -> scala folder as "PageRank.scala"<br>
2. The data file used for this Question, "Airport_data.csv" file is loaded into AWS S3 in the bucket "6350assign2" as "s3://6350assign2/Airport_data_Aug.csv".<br>
3. To run the code and view the output, a step was added with the parameters:<br>
	1. location of the data file ("s3://6350assign2/Airport_data_Aug.csv")<br>
	2. No of iterations (we specified 5)<br>
	3. location of output file (we named it as "outputPageRank" to be stored on S3 as "s3://6350assign2/outputPageRank/")<br>
4. We also added the jar file named "assignment-2_2.11-0.1" and gave the spark details option as <--class "PageRank">.<br>
5. The output files were downloaded from the S3 output folder as text files in the folder OutputPageRank as part-00000.txt. It contains the airport ID (first cloumn), the page rank for each in descending order (second column), the airport code (3letters) according the the airport ID (third column). The first record has the highest page rank.<br>


Question 2: Topic Modeling<br>

1. As given above, the Scala code for Topic Modeling is in the "Assignment-2" folder within the src -> main -> scala folder as "TopicModeling.scala"<br>
2. The data file used for this Question, "Pride_and_Prejudice.txt" file is loaded into AWS S3 in the bucket "6350assign2" as "s3://6350assign2/Pride_and_Prejudice.txt".<br>
3. To run the code and view the output, a step was added with the parameters:<br>
	1. location of the data file ("s3://6350assign2/Pride_and_Prejudice.txt")<br>
	2. location of output file (we named it as "outputTopic Modeling" to be stored on S3 as "s3://6350assign2/outputTopicModeling/")<br>
4. We also added the jar file named "assignment-2_2.11-0.1" and gave the spark details option as <--class "Topic Modeling">.<br>
5. The output file was downloaded from the S3 output folder as a text file in the folder OutputTopicModeling as part-00001.txt.It contains the top 5 topics from the text dataset.<br>
