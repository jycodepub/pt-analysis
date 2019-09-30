package com.jyeh.ptanalysis

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

class AnalysisLoader {

    fun load(filepath: String) : PropertyAnalysis {
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(File(filepath), PropertyAnalysis::class.java)
    }
}