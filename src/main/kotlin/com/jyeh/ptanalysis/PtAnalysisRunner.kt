package com.jyeh.ptanalysis

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class PtAnalysisRunner : CommandLineRunner {

    override fun run(vararg args: String?) {
        val loader = AnalysisLoader()
        val analysis = loader.load(args[0]!!)
        analysis.analyze()
        println(analysis)
    }
}