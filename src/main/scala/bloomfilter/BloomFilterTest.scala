package bloomfilter

object BloomFilterTest {

    def main(args: Array[String]): Unit = {

        val bloomFilter = new BloomFilter[String](5, 20)

        println("Adding element `aa`")
        bloomFilter.add("aa")
        println("contains `aa`: " + bloomFilter.contains("aa"))
        println("contains `bb`: " + bloomFilter.contains("bb"))

        println("Removing `aa`")
        bloomFilter.remove("aa")
        println("contains `aa`: " + bloomFilter.contains("aa"))

        println()

        bloomFilter.add("7")
        bloomFilter.add("3")
        bloomFilter.add("3")
        bloomFilter.add("4")
        bloomFilter.add("7")
        bloomFilter.add("1")

        bloomFilter.remove("7")

        println(bloomFilter.contains("7"))
        println(bloomFilter.contains("3"))
        println(bloomFilter.contains("4"))
        println(bloomFilter.contains("1"))

        println()

        bloomFilter.remove("1")
        println(bloomFilter.contains("1"))

        println()
        println()


    }


}
