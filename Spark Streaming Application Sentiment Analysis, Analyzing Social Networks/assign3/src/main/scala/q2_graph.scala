import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object q2_graph {
  def main(args: Array[String]): Unit = {

    if (args.length < 2) {
      println("Error: the class expects minimum 2 arguments.")
      println("Usage: input file and output file")
    }

    val input_file = args(0)
    val output_file = args(1)

    val sc = new SparkContext(new SparkConf().setAppName("Spark PageRank"))
    val parsedFile = sc.textFile(input_file)

    // create graph
    val edgesRDD: RDD[(VertexId, VertexId)] = parsedFile.map(line => line.split("\t")).map(line =>(line(0).toInt, line(1).toInt))
    val graph = Graph.fromEdgeTuples(edgesRDD, 1)


    // part a
    val outDegrees: VertexRDD[Int] = graph.outDegrees
    val a = outDegrees.sortBy(_._2, ascending=false).take(5)
    sc.parallelize(a).coalesce(1).saveAsTextFile(output_file+"part1")
    //part b
    val inDegrees: VertexRDD[Int] = graph.inDegrees
    val b = inDegrees.sortBy(_._2, ascending=false).take(5)
    sc.parallelize(b).coalesce(1).saveAsTextFile(output_file+"part2")

    // part c
    val ranks = graph.pageRank(0.01).vertices
    val c = ranks.sortBy(_._2, ascending = false).take(5)
    sc.parallelize(c).coalesce(1).saveAsTextFile(output_file+"part3")

    // part d
    val cc = graph.connectedComponents().vertices
    val d = cc.sortBy(_._2, ascending = false).take(5)
    sc.parallelize(d).coalesce(1).saveAsTextFile(output_file+"part4")

    // part e
    val triCounts = graph.triangleCount().vertices
    val e = triCounts.sortBy(_._2, ascending = false).take(5)
    sc.parallelize(e).coalesce(1).saveAsTextFile(output_file+"part5")
  }
}
