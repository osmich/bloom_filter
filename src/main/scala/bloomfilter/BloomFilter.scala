package bloomfilter

/**
 * Implementation of Bloom filter. By default it uses custom HashMap function.
 * The HashFunction used here may be replaced by scala HashMap[A, Int].
 *
 * @param k number of hash functions
 * @param m - size of array in hash functions
 * @tparam A - type class
 */
class BloomFilter[A](k: Int, m: Int) {

  if(k < 1 || m < 1) {
    throw new IllegalArgumentException("Arguments cannot equal 0 nor be negative")
  }

  val hashmaps = new Array[HashFunction[A]](k)

  for(i <- 0 until k) {
    hashmaps(i) = new HashFunction[A](m)
  }

  def add(element: A): Unit = {
    for(i <- hashmaps.indices) {
      hashmaps(i).put(element, hashmaps(i).getOrElse(element, 0) + 1)
    }
  }

  // attention: using this function may cause false-negative responses
  // (i.e bloom filter may return false for elements that were added to it)
  def remove(element: A): Unit = {
    for(i <- hashmaps.indices) {
      if(hashmaps(i).contains(element)) {
        val currentValue = hashmaps(i).getOrElse(element, 0)
        if(currentValue > 1) {
          hashmaps(i).put(element, currentValue - 1)
        }
        else {
          hashmaps(i).put(element, 0)
        }

      }
    }
  }


  def contains(element: A): Boolean = {
    for(i <- hashmaps.indices) {
      if(!hashmaps(i).contains(element)) {
        return false
      }
      else if(hashmaps(i).getOrElse(element, 0) == 0) {
        return false
      }
    }
    true
  }

  def getBitMapFilter(): Array[Array[Boolean]] = {
    val bitmaps = Array.ofDim[Boolean](k, m)
    for(index <- hashmaps.indices) {
      bitmaps(index) = hashmaps(index).getBitMap()
    }

    bitmaps
  }
}
