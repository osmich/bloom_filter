package bloomfilter

/**
 * Custom hash function that implements scala HashMap interface.
 * It contains only functionality necessary for Bloom filter and allows
 * to define the size of the hash table
 * @param m - size of the hash table
 * @tparam K - type class
 */
class HashFunction[K](val m: Integer) extends scala.collection.mutable.HashMap[K, Int] {

  private val hashTable = new Array[Int](m)
  private val hashValuesExistence = new Array[Boolean](m)

  private val randomGenerator = new scala.util.Random
  private val random = randomGenerator.nextInt(m)


  private def getHashIndex(key: K): Integer = {
    (key.hashCode + random) % m
  }

  override def get(key: K): Option[Int] = {
    val index = getHashIndex(key)
    Option(hashTable(index))
  }

  override def getOrElse[Int](key: K, default: => Int): Int = {
    val index = getHashIndex(key.asInstanceOf[K])
    hashTable(index).asInstanceOf[Int]
  }

  override def contains(key: K): Boolean = {
    hashValuesExistence(getHashIndex(key))
  }

  override def put(key: K, value: Int): Option[Int] = {
    val index = getHashIndex(key)
    hashValuesExistence(index) = true
    hashTable(index) = value

    None
  }

  override def remove(key: K): Option[Int] = {
    val index = getHashIndex(key)
    hashValuesExistence(index) = false
    None
  }

  def getBitMap(): Array[Boolean] = {
    hashValuesExistence
  }

  override def iterator: Iterator[(K, Int)] = ???

  override def subtractOne(elem: K): HashFunction.this.type = ???

  override def addOne(elem: (K, Int)): HashFunction.this.type = ???
}
