Spark Streaming with Twitter and Kafka

In this part, you will create a Spark Streaming application that will continuously read data from
Twitter about a topic. These Twitter feeds will be analyzed for their sentiment, and then analyzed
using ElasticSearch. To exchange data between these two, you will use Kafka as a broker. Everything
will be done locally on your computer. Below are the steps to get started:

Setting up your development environment:

• You will need to create a Twitter app and get credentials. It can be done at
https://apps.twitter.com/

• Download Apache Kafka and go through the quickstart steps:
https://kafka.apache.org/quickstart

• Windows users might want to consider WSL, which gives a Unix-like environment on Windows:
https://docs.microsoft.com/en-us/windows/wsl/install-win10

• I have provided a Spark Scala project that gives an example of how to connect Spark Structured
Streaming to a Kafka broker. The project can be downloaded from
http://www.utdallas.edu/~axn112530/cs6350/kafka.zip.

The next section gives you the instructions on how to compile and run the project.

• Later, you will also need to set up Elasticsearch and Kibana environment to visualize data. You
will need to download Elasticsearch, Kibana, and Logstash from
https://www.elastic.co/downloads/

Running the Example

The example project can be downloaded from:
http://www.utdallas.edu/~axn112530/cs6350/kafka.zip

It illustrates a simple way to communicate between Structured Streaming and Kafka. Below are the
steps:

• Make sure that you first start Zookeeper and then Apache Kafka. After ensuring that these two
services are running, create two topics, which we will call topicA and topicB. After this, use the
producer utility to generate some messages on topicA, and start a consumer on topicB.

• For building this project, you need to have Scala Build Tool (SBT) installed. It can be downloaded from
https://www.scala-sbt.org

After downloading, you should be able to go to root of your project and build by the following
commands.

sbt
> assembly

This will create a fat jar (i.e. a jar containing all its dependencies) under target/scala-2.11
directory.

• Run the Spark project by using the following command:
spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0
--class ClassName PathToJarFile topicA topicB

The above will accept data from topicA, perform word count, and display the results on the
console as well as forward the message to topicB. This is accomplished by the Spark Structured Streaming application.

Starting the Project

For this project, you will need to perform the following steps:

• Create a Twitter application using Spark and Scala. An example of how to do this is available
here: http://bahir.apache.org/docs/spark/current/spark-streaming-twitter/

• The application should perform search about a topic and gather Tweets related to it. The next
step is sentiment evaluation of the Tweets. Various libraries are available for this task.
The sentiment evaluation should happen continuously using a stream approach. At the end of
every window, a message containing the sentiment should be sent to Kafka through a topic.

• In the next step, you will configure Logstash, Elasticsearch, and Kibana to read from the topic
and set up visualization of sentiment.


Visualizing the data using Elasticsearch and Kibana

You were able to read the data using a console consumer in a previous step. Let’s now move forward
and visualize the data using Elastcsearch. To send data from Kafka to Elasticsearch, you need a
processing pipeline that is provided by Logstash. Download Elasticsearch, Kibana, and Logstash
from https://www.elastic.co/downloads.

After downloading the data, you need to go to the appropriate directories, and start the services in
the following order:

1. Elasticsearch using the following command in the elasticsearch-5.6.8/bin directory:
./elasticsearch

2. Kibana using the following command in the kibana-5.6.8-darwin-x86_64/bin directory:
./kibana

3. Logstash: Go to the logstash-5.6.8 directory and create a file logstash-simple.conf with following
content:

input {
kafka {
bootstrap_servers => "localhost:9092"
topics => ["YourTopic"]
}
}
output {
elasticsearch {
hosts => ["localhost:9200"]
index => "YourTopic-index"
}
}

Then run the following command
bin/logstash -f logstash-simple.conf

This sets up the right pipeline between Kafka and Elasticsearch.

If everything is set up properly, you should be able to go to http://localhost:5601 and use Kibana
to visualize your data in real-time. You will have to search for the appropriate topic index, which is
YourTopic-index in the example shown above. You can read more about Kibana here.


Analyzing Social Networks using GraphX

In this part, you will use Spark GraphX to analyze social network data. You are free to choose any
one of the Social network datasets available from the SNAP repository.
You will use this dataset to construct a GraphX graph and run some queries and algorithms on the
graph. You will write a class in Scala that will read in the data using an argument, and perform
following steps:

2.1 Loading Data

Load the data into a dataframe or RDD using Spark. Define a parser so that you can identify
and extract relevant fields. Note that GraphX edges are directed, so if your dataset has undirected
relationships, you might need to convert those into 2 directed relationships. That is, if your dataset
contains an undirected friendship relationship between X and Y, then you might need to create 2
edges one from X to Y and the other from Y to X.

2.2 Create Graphs

Define edge and vertex structure and create property graphs.

2.3 Running Queries

Run the following queries using the GraphX API and write your output to a file specified by the
output parameter.
a. Find the top 5 nodes with the highest outdegree and find the count of the number of outgoing
edges in each
b. Find the top 5 nodes with the highest indegree and find the count of the number of incoming edges
in each
c. Calculate PageRank for each of the nodes and output the top 5 nodes with the highest PageRank
values. You are free to define the threshold parameter.
d. Run the connected components algorithm on it and find the top 5 components with the largest
number of nodes.
e. Run the triangle counts algorithm on each of the vertices and output the top 5 vertices with the
largest triangle count. In case of ties, you can randomly select the top 5 vertices.

QUESTION 1

The scala classes within the assign3 folder for Question 1 are: 
1. q1_twitter.scala
2. Sentiment.scala
3. GetSentiment.scala

Instructions to run
1) start zookeeper using the following command
bin/zookeeper-server-start.sh config/zookeeper.properties

2) Start Kafka server
bin/kafka-server-start.sh config/server.properties

3) Run .jar file 
spark-submit --class q1_twitter TwitterSentimentAnalysis-assembly-0.1.jar streamingTopic hollywood

Here Kafka consumer topic   =>  streamingTopic
And Tweets HashTag   =>   hollywood

4) Start consumer topic
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092  --topic streamingTopic --from-beginning

→ Here we’ll see the sentiments coming up on the console

5) Starting ElasticSearch service
sudo -i service elasticsearch start

6) Start Kibana service
sudo -i service kibana start

7) Start logstash
Go to the bin folder and type the following command
./logstash -f logstash-simple.conf

→ Here simple.conf contains the text given by the professor in the assignment. For topic input to elastic output. 

→ After running the above commands, we can go to kibana by typing the following
http://localhost:5601/app/kibana#/visualize in Browser

→ We used the visualize option there to get the various graphs for the sentiments coming in real time. It takes the data from the consumer console, which are the sentiments and show them in various visual forms in kibana. 

The result graphs are in the folder as "Q1_results" which consists of the graph plots as well as tweets and the sentiment analysis performed on it.



QUESTION 2

The scala class within the assign3 folder for Question 2 are: 
1. q2_graph.scala

To run this on the AWS cluster, the combined jar file (Q1,Q2) is uploaded to S3. It is executed as a Spark application specifying the path of the jar file on S3 and two arguments - input data set (SOC Epinions textfile) and the Output folder path.
Both input data and output folder will be on S3
Eg. 
Input file:
s3://6350assign3/Q2_soc_Epinions.txt
Output file:
s3://6350assign3/Q2_Output/

The output folder path is specified. Since there are 5 queries, each saving into a text file, all the query results get stored within the Q2_Output folder.

The result files are included in the folder as "Q2_results"

