For our project Text Summarization Using TextRank algorithm we have used the dataset: BBC-News Summary from the Kaggle website: https://www.kaggle.com/pariza/bbc-news-summary

The input dataset has been loaded on AWS S3 at 

Articles 	-> s3://6350project/BBC News Summary/Articles/

Summaries 	-> s3://6350project/BBC News Summary/Summaries/

Please save it on your own bucket in S3 if you need to execute the code and copy the S3 key credentials in the appropriate code section.  


The output dataset directly writes the files to AWS S3 at:

Hypothesis (Our generated summaries) 	-> s3://6350project/BBC News Summary/Hypothesis/

Results				 	-> s3://6350project/BBC News Summary/Results/

Please save it on your own bucket in S3 and modify the path accordingly if you need to execute the code.

Steps to execute code:
1. Execute the code in a databricks notebook.

2. Install the libaries from PyPi onto the cluster.
	1. py-rouge===1.1
	2. nltk===3.4.5
	3. networkx===2.4
	4. s3fs

3. Run each cell code. Results (Evaluation metrics) will be printed on the screen as well as saved into the Results folder in AWS S3. Graphs will be printed on the screen. The generated summaries will also be saved into the Hypothesis folder on AWS S3.



