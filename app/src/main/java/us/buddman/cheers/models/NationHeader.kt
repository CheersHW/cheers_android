package us.buddman.cheers.models

/**
 * Created by Chad Park on 2018-02-11.
 */
data class NationHeader(
        val country: String, val gold: Int, val silver: Int, val bronze: Int, val rank: Int
) {
    val rankingText: String
        get() = rank.toString() + "위"
    val goldText: String
        get() = gold.toString()
    val silverText: String
        get() = silver.toString()
    val bronzeText: String
        get() = bronze.toString()

}