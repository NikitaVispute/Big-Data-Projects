import org.apache.spark.ml.feature.StopWordsRemover

import scala.collection.mutable
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.clustering.{DistributedLDAModel, LDA}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object TopicModeling {
  def main(args: Array[String]): Unit = {
    // create Spark context with Spark configuration
    val sc = new SparkContext(new SparkConf().setAppName("Spark Model"))

    val corpus = sc.textFile(args(0))

    val stopWordSet = StopWordsRemover.loadDefaultStopWords("english").toSet

    val tokenized: RDD[Seq[String]] = corpus.map(_.toLowerCase.split("\\s")).map(_.filter(_.length > 3).filter(token => !stopWordSet.contains(token)).filter(_.forall(java.lang.Character.isLetter)))

    val termCounts: Array[(String, Long)] = tokenized.flatMap(_.map(_ -> 1L)).reduceByKey(_ + _).collect().sortBy(-_._2)

    val numStopwords = 20

    val vocabArray: Array[String] = termCounts.takeRight(termCounts.size - numStopwords).map(_._1)

    val vocab: Map[String, Int] = vocabArray.zipWithIndex.toMap

    val documents: RDD[(Long, Vector)] =
      tokenized.zipWithIndex.map { case (tokens, id) =>
        val counts = new mutable.HashMap[Int, Double]()
        tokens.foreach { term =>
          if (vocab.contains(term)) {
            val idx = vocab(term)
            counts(idx) = counts.getOrElse(idx, 0.0) + 1.0
          }
        }
        (id, Vectors.sparse(vocab.size, counts.toSeq))
      }

    val numTopics = 10
    val lda = new LDA().setK(5).setMaxIterations(10)

    val ldaModel = lda.run(documents)

    var res = "Topic Modelling" + "\n"
    val topicIndices = ldaModel.describeTopics(maxTermsPerTopic = 1)

    var topicsTermsTop=Seq[String]()

    topicIndices.foreach{ case (terms, termWeights) =>
      terms.zip(termWeights).foreach { case (term, weight) =>
        topicsTermsTop = topicsTermsTop :+ vocabArray(term)
      }
    }


    res = res + "\n"+"================== Top Topic ================== \n"
    topicsTermsTop.foreach(s => res = res+s+"\n")

    //topicIndices.foreach { case (terms, termWeights) =>
    //terms.zip(termWeights).foreach { case (term, weight) =>
    //res = res + vocabArray(term.toInt) + "\t" + weight
    //}
    //}

    sc.parallelize(List(res)).saveAsTextFile(args(1))
  }
}
