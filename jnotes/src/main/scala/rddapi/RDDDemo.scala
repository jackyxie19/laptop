package rddapi

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jacky on 2017/7/14.
  */
object RDDDemo {
  val conf = new SparkConf().setAppName("RDDDemo").setMaster("local[*]")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    //    aggregateDemo
    //    aggregateByKeyDemo
    //    cartesianDemo
    //    setCheckPointDemo
  }

  def aggregateDemo: Unit = {
    val z = sc.parallelize(List(1, 2, 3, 4, 5, 6), 2)

    // lets first print out the contents of the RDD with partition labels
    def myfunc(index: Int, iter: Iterator[(Int)]): Iterator[String] = {
      iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
    }

    //   collect() Return an array that contains all of the elements in this RDD
    z.mapPartitionsWithIndex(myfunc).collect()

    //    z.mapPartitionsWithIndex(myfunc).collect

    z.aggregate(0)(math.max(_, _), _ + _)

    // This example returns 16 since the initial value is 5
    // reduce of partition 0 will be max(5, 1, 2, 3) = 5
    // reduce of partition 1 will be max(5, 4, 5, 6) = 6
    // final reduce across partitions will be 5 + 5 + 6 = 16
    // note the final reduce include the initial value
    z.aggregate(5)(math.max(_, _), _ + _)


    val z1 = sc.parallelize(List("a", "b", "c", "d", "e", "f"), 2)

    //lets first print out the contents of the RDD with partition labels
    def myfunc1(index: Int, iter: Iterator[(String)]): Iterator[String] = {
      iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
    }

    z1.mapPartitionsWithIndex(myfunc1).collect

    z1.aggregate("")(_ + _, _ + _)


    // See here how the initial value "x" is applied three times.
    // - once for each partition
    // - once when combining all the partitions in the second reduce function.
    z1.aggregate("x")(_ + _, _ + _)

    // Below are some more advanced examples. Some are quite tricky to work out.

    val z2 = sc.parallelize(List("12", "23", "345", "4567"), 2)
    z2.aggregate("")((x, y) => math.max(x.length, y.length).toString, (x, y) => x + y)

    z2.aggregate("")((x, y) => math.min(x.length, y.length).toString, (x, y) => x + y)

    val z3 = sc.parallelize(List("12", "23", "345", ""), 2)
    z3.aggregate("")((x, y) => math.min(x.length, y.length).toString, (x, y) => x + y)

    val z4 = sc.parallelize(List("12", "23", "", "345"), 2)
    z4.aggregate("")((x, y) => math.min(x.length, y.length).toString, (x, y) => x + y)
    //    res144: String = 11
  }

  def aggregateByKeyDemo = {
    val pairRDD = sc.parallelize(List(("cat", 2), ("cat", 5), ("mouse", 4), ("cat", 12), ("dog", 12), ("mouse", 2)), 2)

    // lets have a look at what is in the partitions
    def myfunc(index: Int, iter: Iterator[(String, Int)]): Iterator[String] = {
      iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
    }

    pairRDD.mapPartitionsWithIndex(myfunc).collect
    //    res2: Array[String] = Array([partID:0, val: (cat,2)], [partID:0, val: (cat,5)], [partID:0, val: (mouse,4)], [partID:1, val: (cat,12)], [partID:1, val: (dog,12)], [partID:1, val: (mouse,2)])

    pairRDD.aggregateByKey(0)(math.max(_, _), _ + _).collect
    //    res3: Array[(String, Int)] = Array((dog,12), (cat,17), (mouse,6))

    pairRDD.aggregateByKey(100)(math.max(_, _), _ + _).collect
    //    res4: Array[(String, Int)] = Array((dog,100), (cat,200), (mouse,200))
  }

  def cartesianDemo = {
    val x = sc.parallelize(List(1, 2, 3, 4, 5))
    val y = sc.parallelize(List(6, 7, 8, 9, 10))
    x.cartesian(y).collect
    //    res0: Array[(Int, Int)] = Array((1,6), (1,7), (1,8), (1,9), (1,10), (2,6), (2,7), (2,8), (2,9), (2,10), (3,6), (3,7), (3,8), (3,9), (3,10), (4,6), (5,6), (4,7), (5,7), (4,8), (5,8), (4,9), (4,10), (5,9), (5,10))
  }

  def setCheckPointDemo = {
    sc.setCheckpointDir("my_directory_name")
    val a = sc.parallelize(1 to 4)
    a.checkpoint
    a.count
    //  14/02/25 18:13:53 INFO SparkContext: Starting job: count at <console>:15
    //    ...
    //    14/02/25 18:13:53 INFO MemoryStore: Block broadcast_5 stored as values to memory (estimated size 115.7 KB, free 296.3 MB)
    //    14/02/25 18:13:53 INFO RDDCheckpointData: Done checkpointing RDD 11 to file:/home/cloudera/Documents/spark-0.9.0-incubating-bin-cdh4/bin/my_directory_name/65407913-fdc6-4ec1-82c9-48a1656b95d6/rdd-11, new parent is RDD 12
    //    res23: Long = 4
  }

  def coalesceDemo = {
    val y = sc.parallelize(1 to 10, 10)
    val z = y.coalesce(2, false)
    z.partitions.length
    //下面两个方法相等
    z.coalesce(2, true);
    z.repartition(2)
    //    res9: Int = 2
  }

  def cogroupDemo = {
    val a = sc.parallelize(List(1, 2, 1, 3), 1)
    val b = a.map((_, "b"))
    val c = a.map((_, "c"))
    b.cogroup(c).collect
    //    res7: Array[(Int, (Iterable[String], Iterable[String]))] = Array(
    //      (2,(ArrayBuffer(b),ArrayBuffer(c))),
    //      (3,(ArrayBuffer(b),ArrayBuffer(c))),
    //      (1,(ArrayBuffer(b, b),ArrayBuffer(c, c)))
    //    )

    val d = a.map((_, "d"))
    b.cogroup(c, d).collect
    //    res9: Array[(Int, (Iterable[String], Iterable[String], Iterable[String]))] = Array(
    //      (2,(ArrayBuffer(b),ArrayBuffer(c),ArrayBuffer(d))),
    //      (3,(ArrayBuffer(b),ArrayBuffer(c),ArrayBuffer(d))),
    //      (1,(ArrayBuffer(b, b),ArrayBuffer(c, c),ArrayBuffer(d, d)))
    //    )

    val x = sc.parallelize(List((1, "apple"), (2, "banana"), (3, "orange"), (4, "kiwi")), 2)
    val y = sc.parallelize(List((5, "computer"), (1, "laptop"), (1, "desktop"), (4, "iPad")), 2)
    x.cogroup(y).collect
    //    res23: Array[(Int, (Iterable[String], Iterable[String]))] = Array(
    //      (4,(ArrayBuffer(kiwi),ArrayBuffer(iPad))),
    //      (2,(ArrayBuffer(banana),ArrayBuffer())),
    //      (3,(ArrayBuffer(orange),ArrayBuffer())),
    //      (1,(ArrayBuffer(apple),ArrayBuffer(laptop, desktop))),
    //      (5,(ArrayBuffer(),ArrayBuffer(computer))))
  }

  def collectAsMapDemo = {
    val a = sc.parallelize(List(1, 2, 1, 3), 1)
    val b = a.zip(a)
    b.collectAsMap
    //    res1: scala.collection.Map[Int,Int] = Map(2 -> 2, 1 -> 1, 3 -> 3)
  }

  def combineByKey = {
    val a = sc.parallelize(List("dog", "cat", "gnu", "salmon", "rabbit", "turkey", "wolf", "bear", "bee"), 3)
    val b = sc.parallelize(List(1, 1, 2, 2, 2, 1, 2, 2, 2), 3)
    val c = b.zip(a)
    val d = c.combineByKey(List(_), (x: List[String], y: String) => y :: x, (x: List[String], y: List[String]) => x ::: y)
    d.collect
    //    res16: Array[(Int, List[String])] = Array((1,List(cat, dog, turkey)), (2,List(gnu, rabbit, salmon, bee, bear, wolf)))
  }

  def countByApproxDistinctDemo: Unit = {
    val a = sc.parallelize(1 to 10000, 20)
    val b = a ++ a ++ a ++ a ++ a
    b.countApproxDistinct(0.1)
    //    res14: Long = 8224

    b.countApproxDistinct(0.05)
    //    res15: Long = 9750

    b.countApproxDistinct(0.01)
    //    res16: Long = 9947

    b.countApproxDistinct(0.001)
    //    res0: Long = 10000
  }

  def countApproxDistinctByKeyDemo: Unit = {
    val a = sc.parallelize(List("Gnu", "Cat", "Rat", "Dog"), 2)
    val b = sc.parallelize(a.takeSample(true, 10000, 0), 20)
    val c = sc.parallelize(1 to b.count().toInt, 20)
    val d = b.zip(c)
    d.countApproxDistinctByKey(0.1).collect
    //    res15: Array[(String, Long)] = Array((Rat,2567), (Cat,3357), (Dog,2414), (Gnu,2494))

    d.countApproxDistinctByKey(0.01).collect
    //    res16: Array[(String, Long)] = Array((Rat,2555), (Cat,2455), (Dog,2425), (Gnu,2513))

    d.countApproxDistinctByKey(0.001).collect
    //    res0: Array[(String, Long)] = Array((Rat,2562), (Cat,2464), (Dog,2451), (Gnu,2521))
  }

  def countByValueDemo: Unit = {
    val b = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 2, 4, 2, 1, 1, 1, 1, 1))
    b.countByValue
    //    res27: scala.collection.Map[Int,Long] = Map(5 -> 1, 8 -> 1, 3 -> 1, 6 -> 1, 1 -> 6, 2 -> 3, 4 -> 2, 7 -> 1)
  }

  def depandenciesDemo: Unit = {
    val b = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 2, 4, 2, 1, 1, 1, 1, 1))
    val a = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 2, 4, 2, 1, 1, 1, 1, 1))
    //    b: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[32] at parallelize at <console>:12
    b.dependencies.length
    //      Int = 0

    b.map(a => a).dependencies.length
    //      res40: Int = 1

    b.cartesian(a).dependencies.length
    //      res41: Int = 2

    b.cartesian(a).dependencies
    //      res42: Seq[org.apache.spark.Dependency[_]] = List(org.apache.spark.rdd.CartesianRDD$$anon$1@576ddaaa, org.apache.spark.rdd.CartesianRDD$$anon$2@6d2efbbd)
  }

  def mixDataPartialFuncDemo: Unit = {
    val a = sc.parallelize(List("cat", "horse", 4.0, 3.5, 2, "dog"))
    a.collect({
      case a: Int => "is integer"
      case b: String => "is string"
    }).collect
    //    res17: Array[String] = Array(is string, is string, is integer, is string)

    val myfunc: PartialFunction[Any, Any] = {
      case a: Int => "is integer"
      case b: String => "is string"
    }
    //    Checks if a value is contained in the function's domain.
    myfunc.isDefinedAt("")
    //    res21: Boolean = true

    myfunc.isDefinedAt(1)
    //    res22: Boolean = true

    myfunc.isDefinedAt(1.5)
    //    res23: Boolean = false
  }

  def filterByRangeDemo: Unit ={
    val randRDD = sc.parallelize(List( (2,"cat"), (6, "mouse"),(7, "cup"), (3, "book"), (4, "tv"), (1, "screen"), (5, "heater")), 3)
    val sortedRDD = randRDD.sortByKey()

    /*
    Returns an RDD containing only the elements in the inclusive range lower to upper.
    If the RDD has been partitioned using a RangePartitioner,
    then this operation can be performed efficiently by only scanning the partitions that might contain matching elements.
    Otherwise, a standard filter is applied to all partitions.
     */
    sortedRDD.filterByRange(1, 3).collect
//    res66: Array[(Int, String)] = Array((1,screen), (2,cat), (3,book))
  }

  def foldDemo: Unit ={
    val a = sc.parallelize(List(1,2,3), 3)
    a.fold(0)(_ + _)
//    res59: Int = 6
  }

  def fullOuterJoinDemo: Unit ={
    val pairRDD1 = sc.parallelize(List( ("cat",2), ("cat", 5), ("book", 4),("cat", 12)))
    val pairRDD2 = sc.parallelize(List( ("cat",2), ("cup", 5), ("mouse", 4),("cat", 12)))
    pairRDD1.fullOuterJoin(pairRDD2).collect

//    res5: Array[(String, (Option[Int], Option[Int]))] = Array((book,(Some(4),None)), (mouse,(None,Some(4))), (cup,(None,Some(5))), (cat,(Some(2),Some(2))), (cat,(Some(2),Some(12))), (cat,(Some(5),Some(2))), (cat,(Some(5),Some(12))), (cat,(Some(12),Some(2))), (cat,(Some(12),Some(12))))
  }

  def glomDemo: Unit ={
    val a = sc.parallelize(1 to 100, 3)
    a.glom.collect
//    res8: Array[Array[Int]] = Array(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33), Array(34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66), Array(67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100))
  }

  def groupByDemo: Unit ={
    val a = sc.parallelize(1 to 9, 3)
    a.groupBy(x => { if (x % 2 == 0) "even" else "odd" }).collect
//    res42: Array[(String, Seq[Int])] = Array((even,ArrayBuffer(2, 4, 6, 8)), (odd,ArrayBuffer(1, 3, 5, 7, 9)))

    val a2 = sc.parallelize(1 to 9, 3)
    def myfunc(a: Int) : Int =
    {
      a % 2
    }
    a2.groupBy(myfunc).collect
//    res3: Array[(Int, Seq[Int])] = Array((0,ArrayBuffer(2, 4, 6, 8)), (1,ArrayBuffer(1, 3, 5, 7, 9)))

    val a3 = sc.parallelize(1 to 9, 3)
    def myfunc3(a: Int) : Int =
    {
      a % 2
    }
    a3.groupBy(myfunc3(_), 3).collect
    a3.groupBy(myfunc3(_), 1).collect
//    res7: Array[(Int, Seq[Int])] = Array((0,ArrayBuffer(2, 4, 6, 8)), (1,ArrayBuffer(1, 3, 5, 7, 9)))

    import org.apache.spark.Partitioner
    class MyPartitioner extends Partitioner {
      def numPartitions: Int = 2
      def getPartition(key: Any): Int =
      {
        key match
        {
          case null => 0
          case key: Int => key % numPartitions
          case _ => key.hashCode % numPartitions
        }
      }
      override def equals(other: Any): Boolean =
      {
        other match
        {
          case h: MyPartitioner => true
          case _ => false
        }
      }
    }
    val a4 = sc.parallelize(1 to 9, 3)
    val p = new MyPartitioner()
    val b = a4.groupBy((x:Int) => { x }, p)
//    val c = b.mapWith(i => i)((a4, b) => (b, a4))
//    c.collect
//    res42: Array[(Int, (Int, Seq[Int]))] = Array((0,(4,ArrayBuffer(4))), (0,(2,ArrayBuffer(2))), (0,(6,ArrayBuffer(6))), (0,(8,ArrayBuffer(8))), (1,(9,ArrayBuffer(9))), (1,(3,ArrayBuffer(3))), (1,(1,ArrayBuffer(1))), (1,(7,ArrayBuffer(7))), (1,(5,ArrayBuffer(5))))
  }

  def groupByKeyDemo: Unit ={
    val a = sc.parallelize(List("dog", "tiger", "lion", "cat", "spider", "eagle"), 2)
    val b = a.keyBy(_.length)
    b.groupByKey.collect
//    res11: Array[(Int, Seq[String])] = Array((4,ArrayBuffer(lion)), (6,ArrayBuffer(spider)), (3,ArrayBuffer(dog, cat)), (5,ArrayBuffer(tiger, eagle)))
  }

  def histogramDemo: Unit ={
    //even spacing
    val a = sc.parallelize(List(1.1, 1.2, 1.3, 2.0, 2.1, 7.4, 7.5, 7.6, 8.8, 9.0), 3)
    a.histogram(5)
//    res11: (Array[Double], Array[Long]) = (Array(1.1, 2.68, 4.26, 5.84, 7.42, 9.0),Array(5, 0, 0, 1, 4))

    val a1 = sc.parallelize(List(9.1, 1.0, 1.2, 2.1, 1.3, 5.0, 2.0, 2.1, 7.4, 7.5, 7.6, 8.8, 10.0, 8.9, 5.5), 3)
    a1.histogram(6)
//    res18: (Array[Double], Array[Long]) = (Array(1.0, 2.5, 4.0, 5.5, 7.0, 8.5, 10.0),Array(6, 0, 1, 1, 3, 4))

    //custom spacing
    val a2 = sc.parallelize(List(1.1, 1.2, 1.3, 2.0, 2.1, 7.4, 7.5, 7.6, 8.8, 9.0), 3)
    a2.histogram(Array(0.0, 3.0, 8.0))
//    res14: Array[Long] = Array(5, 3)

    val a3 = sc.parallelize(List(9.1, 1.0, 1.2, 2.1, 1.3, 5.0, 2.0, 2.1, 7.4, 7.5, 7.6, 8.8, 10.0, 8.9, 5.5), 3)
    a3.histogram(Array(0.0, 5.0, 10.0))
//    res1: Array[Long] = Array(6, 9)

    a3.histogram(Array(0.0, 5.0, 10.0, 15.0))
//    res1: Array[Long] = Array(6, 8, 1)
  }

  def intersectionDemo: Unit ={
    val x = sc.parallelize(1 to 20)
    val y = sc.parallelize(10 to 30)
    val z = x.intersection(y)

    z.collect
//    res74: Array[Int] = Array(16, 12, 20, 13, 17, 14, 18, 10, 19, 15, 11)
  }

  def joinDemo: Unit ={
    val a = sc.parallelize(List("dog", "salmon", "salmon", "rat", "elephant"), 3)
    val b = a.keyBy(_.length)
    val c = sc.parallelize(List("dog","cat","gnu","salmon","rabbit","turkey","wolf","bear","bee"), 3)
    val d = c.keyBy(_.length)
    b.join(d).collect

//    res0: Array[(Int, (String, String))] = Array((6,(salmon,salmon)), (6,(salmon,rabbit)), (6,(salmon,turkey)), (6,(salmon,salmon)), (6,(salmon,rabbit)), (6,(salmon,turkey)), (3,(dog,dog)), (3,(dog,cat)), (3,(dog,gnu)), (3,(dog,bee)), (3,(rat,dog)), (3,(rat,cat)), (3,(rat,gnu)), (3,(rat,bee)))
  }

  def lookupDemo: Unit ={
    val a = sc.parallelize(List("dog", "tiger", "lion", "cat", "panther", "eagle"), 2)
    val b = a.map(x => (x.length, x))
    b.lookup(5)
//    res0: Seq[String] = WrappedArray(tiger, eagle)
  }

  def mapPartionDemo: Unit ={
    /**
      * example1
      */
    val a = sc.parallelize(1 to 9, 3)
    def myfunc[T](iter: Iterator[T]) : Iterator[(T, T)] = {
      var res = List[(T, T)]()
      var pre = iter.next
      while (iter.hasNext)
      {
        val cur = iter.next;
        //Adds an element at the beginning of this list.
        res ::= (pre, cur)
        pre = cur;
      }
      res.iterator
    }
    a.mapPartitions(myfunc).collect
//    res0: Array[(Int, Int)] = Array((2,3), (1,2), (5,6), (4,5), (8,9), (7,8))

    /**
      * example2
      */
    val x = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9,10), 3)
    def myfunc2(iter: Iterator[Int]) : Iterator[Int] = {
      var res = List[Int]()
      while (iter.hasNext) {
        val cur = iter.next;
        res = res ::: List.fill(scala.util.Random.nextInt(10))(cur)
      }
      res.iterator
    }
    x.mapPartitions(myfunc2).collect
    // some of the number are not outputted at all. This is because the random number generated for it is zero.
//    res8: Array[Int] = Array(1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 5, 7, 7, 7, 9, 9, 10)

    /**
      * flatmap
      */
    val x2 = sc.parallelize(1 to 10, 3)
    x2.flatMap(List.fill(scala.util.Random.nextInt(10))(_)).collect

//    res1: Array[Int] = Array(1, 2, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10)
  }

  def mapValuesDemo: Unit ={
    val a = sc.parallelize(List("dog", "tiger", "lion", "cat", "panther", "eagle"), 2)
//    val array = a.randomSplit(Array(1.0,2.3),11L)
    val b = a.map(x => (x.length, x))
    b.mapValues("x" + _ + "x").collect
//    res5: Array[(Int, String)] = Array((3,xdogx), (5,xtigerx), (4,xlionx), (3,xcatx), (7,xpantherx), (5,xeaglex))
  }

  /**
    * 用于机器学习取训练集与测试集
    */
  def randomSplitDemo: Unit ={
    val y = sc.parallelize(1 to 10)

    val splits = y.randomSplit(Array(0.6, 0.4), 11L)
    val training = splits(0)
    val test = splits(1)
    training.collect
//    res:85 Array[Int] = Array(1, 4, 5, 6, 8, 10)
    test.collect
//    res86: Array[Int] = Array(2, 3, 7, 9)

    val y2 = sc.parallelize(1 to 10)
    val splits2 = y2.randomSplit(Array(0.1, 0.3, 0.6))

    val rdd1 = splits2(0)
    val rdd2 = splits2(1)
    val rdd3 = splits2(2)

    rdd1.collect
//    res87: Array[Int] = Array(4, 10)
    rdd2.collect
//    res88: Array[Int] = Array(1, 3, 5, 8)
    rdd3.collect
//    res91: Array[Int] = Array(2, 6, 7, 9)
  }

  /**
    * 从新分区,并分区内排序
    */
  def repartitonAndSortWithinPartions: Unit ={
    // first we will do range partitioning which is not sorted
    val randRDD = sc.parallelize(List( (2,"cat"), (6, "mouse"),(7, "cup"), (3, "book"), (4, "tv"), (1, "screen"), (5, "heater")), 3)
    val rPartitioner = new org.apache.spark.RangePartitioner(3, randRDD)
    val partitioned = randRDD.partitionBy(rPartitioner)
    def myfunc(index: Int, iter: Iterator[(Int, String)]) : Iterator[String] = {
      iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
    }
    partitioned.mapPartitionsWithIndex(myfunc).collect

//    res0: Array[String] = Array([partID:0, val: (2,cat)], [partID:0, val: (3,book)], [partID:0, val: (1,screen)], [partID:1, val: (4,tv)], [partID:1, val: (5,heater)], [partID:2, val: (6,mouse)], [partID:2, val: (7,cup)])


    // now lets repartition but this time have it sorted
    val partitioned2 = randRDD.repartitionAndSortWithinPartitions(rPartitioner)
    def myfunc2(index: Int, iter: Iterator[(Int, String)]) : Iterator[String] = {
      iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
    }
    partitioned2.mapPartitionsWithIndex(myfunc2).collect

//    res1: Array[String] = Array([partID:0, val: (1,screen)], [partID:0, val: (2,cat)], [partID:0, val: (3,book)], [partID:1, val: (4,tv)], [partID:1, val: (5,heater)], [partID:2, val: (6,mouse)], [partID:2, val: (7,cup)])
  }

  def sortByDemo: Unit ={
    val y = sc.parallelize(Array(5, 7, 1, 3, 2, 1))
    y.sortBy(c => c, true).collect
//    res101: Array[Int] = Array(1, 1, 2, 3, 5, 7)

    y.sortBy(c => c, false).collect
//    res102: Array[Int] = Array(7, 5, 3, 2, 1, 1)

    val z = sc.parallelize(Array(("H", 10), ("A", 26), ("Z", 1), ("L", 5)))
    z.sortBy(c => c._1, true).collect
//    res109: Array[(String, Int)] = Array((A,26), (H,10), (L,5), (Z,1))

    z.sortBy(c => c._2, true).collect
//    res108: Array[(String, Int)] = Array((Z,1), (L,5), (H,10), (A,26))
  }

  def sortByKeyDemo: Unit ={
    val a = sc.parallelize(List("dog", "cat", "owl", "gnu", "ant"), 2)
    val b = sc.parallelize(1 to a.count.toInt, 2)
    val c = a.zip(b)
    c.sortByKey(true).collect
//    res74: Array[(String, Int)] = Array((ant,5), (cat,2), (dog,1), (gnu,4), (owl,3))
    c.sortByKey(false).collect
//    res75: Array[(String, Int)] = Array((owl,3), (gnu,4), (dog,1), (cat,2), (ant,5))

    val a1 = sc.parallelize(1 to 100, 5)
    val b1 = a1.cartesian(a1)
    val c1 = sc.parallelize(b1.takeSample(true, 5, 13), 2)
    val d1 = c1.sortByKey(false)
//    res56: Array[(Int, Int)] = Array((96,9), (84,76), (59,59), (53,65), (52,4))
  }

  def substractDemo: Unit ={
    val a = sc.parallelize(1 to 9, 3)
    val b = sc.parallelize(1 to 3, 3)
    val c = a.subtract(b)
    c.collect
//    res3: Array[Int] = Array(6, 9, 4, 7, 5, 8)
  }

  def toDebugStringDemo: Unit ={
    val a = sc.parallelize(1 to 9, 3)
    val b = sc.parallelize(1 to 3, 3)
    val c = a.subtract(b)
    c.toDebugString
//    res6: String =
//    MappedRDD[15] at subtract at <console>:16 (3 partitions)
//      SubtractedRDD[14] at subtract at <console>:16 (3 partitions)
//        MappedRDD[12] at subtract at <console>:16 (3 partitions)
//          ParallelCollectionRDD[10] at parallelize at <console>:12 (3 partitions)
//            MappedRDD[13] at subtract at <console>:16 (3 partitions)
//              ParallelCollectionRDD[11] at parallelize at <console>:12 (3 partitions)
  }

  def treeAggregateDemo: Unit ={
    val z = sc.parallelize(List(1,2,3,4,5,6), 2)

    // lets first print out the contents of the RDD with partition labels
    def myfunc(index: Int, iter: Iterator[(Int)]) : Iterator[String] = {
      iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
    }

    z.mapPartitionsWithIndex(myfunc).collect
//    res28: Array[String] = Array([partID:0, val: 1], [partID:0, val: 2], [partID:0, val: 3], [partID:1, val: 4], [partID:1, val: 5], [partID:1, val: 6])

    z.treeAggregate(0)(math.max(_, _), _ + _)
//    res40: Int = 9

    // Note unlike normal aggregrate. Tree aggregate does not apply the initial value for the second reduce
    // This example returns 11 since the initial value is 5
    // reduce of partition 0 will be max(5, 1, 2, 3) = 5
    // reduce of partition 1 will be max(4, 5, 6) = 6
    // final reduce across partitions will be 5 + 6 = 11
    // note the final reduce does not include the initial value
    z.treeAggregate(5)(math.max(_, _), _ + _)
//    res42: Int = 11
  }

  def zipPartitions: Unit ={
    val a = sc.parallelize(0 to 9, 3)
    val b = sc.parallelize(10 to 19, 3)
    val c = sc.parallelize(100 to 109, 3)
    def myfunc(aiter: Iterator[Int], biter: Iterator[Int], citer: Iterator[Int]): Iterator[String] =
    {
      var res = List[String]()
      while (aiter.hasNext && biter.hasNext && citer.hasNext)
      {
        val x = aiter.next + " " + biter.next + " " + citer.next
        res ::= x
      }
      res.iterator
    }
    a.zipPartitions(b, c)(myfunc).collect
//    res50: Array[String] = Array(2 12 102, 1 11 101, 0 10 100, 5 15 105, 4 14 104, 3 13 103, 9 19 109, 8 18 108, 7 17 107, 6 16 106)
  }
}
