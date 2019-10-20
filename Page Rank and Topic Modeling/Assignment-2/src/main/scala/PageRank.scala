import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions._

object PageRank {
  def main(args: Array[String]): Unit = {

    if (args.length != 3) {
      println(" PageRank ")
    }
    // class parameters: location of csv, max no. of iterations and output file location
    val sc = new SparkContext(new SparkConf().setAppName("Spark PageRank"))
    val sqlContext= new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._

    val input_source_file = sc.textFile(args(0))
    val max_iterations = args(1)
    val output_dest = args(2)
    // Initial PageRank value
    val initial_val = 10
    val alpha = 0.15
    var airline =input_source_file.map(x=>(x.split(",")(0),x.split(",")(1)))
    // excluding the header
    val header = airline.first
    airline = airline.filter(x => x != header)

    var airport_code = input_source_file.map(x=>(x.split(",")(0),x.split(",")(3)))

    val origin_airport_code = airline.map{case (x,y) => x}.distinct().collect().toList
    val dest = airline.map{case (x,y) => y}.distinct().collect().toList

    val airports = origin_airport_code.union(dest.filter(x => !origin_airport_code.contains(x)))
    val out_links_Map = scala.collection.mutable.Map[String,Double]()
    val outgoing=airline.groupByKey()
    val nes=outgoing.map(x=>(x._1,x._2.size)).collect()

    for ((k,v)<- nes) out_links_Map.put(k,v)

    var resultMap = scala.collection.mutable.Map[String,Double]()

    var PageRank_Map = scala.collection.mutable.Map[String,Double]()
    for (key<- airports) PageRank_Map.put(key,initial_val)

    for(iteration <- 1 to max_iterations.toInt){

      for (key <- airports){
        resultMap.put(key, 0)
      }

      for (key <- airports){
        PageRank_Map.put(key, PageRank_Map.get(key).getOrElse(Double).asInstanceOf[Double] / out_links_Map.get(key).getOrElse(Double).asInstanceOf[Double])
      }

      for ((k, v) <- airline.collect().toList) {
        resultMap.put(v, resultMap.get(v).getOrElse(Double).asInstanceOf[Double] + PageRank_Map.get(k).getOrElse(Double).asInstanceOf[Double])
      }

      for((key,value) <- resultMap) {
        resultMap.put(key, ((alpha/airports.size) + (1 - alpha) * value))
      }

      PageRank_Map = resultMap.clone()
    }

    val result = resultMap.toSeq.sortBy(-_._2)

    // getting the airport codes

    val result_for_code = result.toDF()

    val headerNames = Seq("ORIGIN_AIRPORT_ID","PAGERANK")
    val newDF = result_for_code.toDF(headerNames: _*)


    val AC = airport_code.distinct()
    val AirportCode = AC.toDF()
    val headerNamesA = Seq("ORIGIN_AIRPORT_IDA","ORIGIN")
    val newDFA = AirportCode.toDF(headerNamesA: _*)

    val joined = newDF.join(newDFA, newDF("ORIGIN_AIRPORT_ID") === newDFA("ORIGIN_AIRPORT_IDA")).select("ORIGIN_AIRPORT_ID","PAGERANK","ORIGIN")


    val joined1 = joined.orderBy(desc("PAGERANK"))

    joined1.rdd.coalesce(1).saveAsTextFile(output_dest)

  }
}