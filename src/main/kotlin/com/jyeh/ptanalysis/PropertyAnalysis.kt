package com.jyeh.ptanalysis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.io.StringWriter

class PropertyAnalysis {
    lateinit var name: String
    var priceMean: Double = 0.0
    var priceStd: Double = 0.0
    var sizeMean: Double = 0.0
    var sizeStd: Double = 0.0
    var yearMean: Double = 0.0
    var yearStd: Double = 0.0
    lateinit var myProperty: Property
    lateinit var sampleProperties: List<Property>

    override fun toString(): String {
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
        val writer = StringWriter()
        mapper.writeValue(writer, this)
        return writer.toString()
    }

    fun analyze() {
        calcMeanAndDiff()
        calcStd()
        sortProperties()
    }

    private fun calcMeanAndDiff() {
        var sumPrice = 0.0
        var sumSize = 0.0
        var sumYear = 0.0
        for (p in sampleProperties) {
            sumPrice += p.price
            sumSize += p.size
            sumYear += p.year
            p.priceDiff = p.price.minus(myProperty.price)
            p.sizeDiff = p.size.minus(myProperty.size)
            p.yearDiff = p.year.minus(myProperty.year)
        }
        priceMean = sumPrice / sampleProperties.size
        sizeMean = sumSize / sampleProperties.size
        yearMean = sumYear / sampleProperties.size
    }

    private fun calcStd() {
        var sumPrice = 0.0
        var sumSize = 0.0
        var sumYear = 0.0
        for (p in sampleProperties) {
            sumPrice += (p.price.minus(priceMean)) * (p.price.minus(priceMean))
            sumSize += (p.size.minus(sizeMean)) * (p.size.minus(sizeMean))
            sumYear += (p.year.minus(yearMean)) * (p.year.minus(yearMean))
        }
        priceStd = Math.sqrt(sumPrice /(sampleProperties.size -1))
        sizeStd = Math.sqrt(sumSize /(sampleProperties.size -1))
        yearStd = Math.sqrt(sumYear /(sampleProperties.size -1))
    }

    private fun sortProperties() {
        sampleProperties = sampleProperties.sortedWith(Comparator<Property> { p1, p2 ->
            val p1absDiff = Math.abs(p1.priceDiff) + Math.abs(p1.sizeDiff) + Math.abs(p1.yearDiff)
            val p2absDiff = Math.abs(p2.priceDiff) + Math.abs(p2.sizeDiff) + Math.abs(p2.yearDiff)
            when {
                p1absDiff > p2absDiff -> -1 // sort in desc order
                p1absDiff < p2absDiff -> 1
                else -> 0
            }
        })
    }
}