import org.apache.spark.{SparkConf, SparkContext}

object SparkTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("appName").setMaster("local")
    val sc = new SparkContext(conf)

    sc.parallelize(Seq(1, 2, 3)).foreach(println)
  }

}
