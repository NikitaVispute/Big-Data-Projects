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

