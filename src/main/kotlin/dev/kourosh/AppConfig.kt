package dev.kourosh

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

object AppConfig {

    private val rootConfig: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load())

    object postgres {
        private val postgres = rootConfig.config("postgres")
        val jdbcUrl = postgres.property("jdbcUri").getString()
        val username = postgres.property("username").getString()
        val password = postgres.property("password").getString()
        val poolSize = postgres.property("poolSize").getString().toInt()

    }

    object neo4j {
        private val neo4j = rootConfig.config("neo4j")
        val boltUri = neo4j.property("boltUri").getString()
        val username = neo4j.property("username").getString()
        val password = neo4j.property("password").getString()
    }

    object externalRestApis {
        private val basePath = "externalRestApis"

        object addressParser {
            private val addressParser = rootConfig.config("$basePath.addressParser")
            val baseUrl = addressParser.property("baseUrl").getString()
            val isHttps = addressParser.property("isHttps").getString().toBoolean()
            val debuggable = addressParser.property("debuggable").getString().toBoolean()
            val connectTimeout = addressParser.property("connectTimeout").getString().toLong()
            val readTimeout = addressParser.property("readTimeout").getString().toLong()
        }

        object cedarmap {
            private val cedarmap = rootConfig.config("$basePath.cedarmap")
            val baseUrl = cedarmap.property("baseUrl").getString()
            val isHttps = cedarmap.property("isHttps").getString().toBoolean()
            val debuggable = cedarmap.property("debuggable").getString().toBoolean()
            val connectTimeout = cedarmap.property("connectTimeout").getString().toLong()
            val readTimeout = cedarmap.property("readTimeout").getString().toLong()
        }

        object optime {
            private val optime = rootConfig.config("$basePath.optime")
            val token = optime.property("token").getString()
//            val token = optime.property("token").getString()
            val baseUrl = optime.property("baseUrl").getString()
            val isHttps = optime.property("isHttps").getString().toBoolean()
            val debuggable = optime.property("debuggable").getString().toBoolean()
            val connectTimeout = optime.property("connectTimeout").getString().toLong()
            val readTimeout = optime.property("readTimeout").getString().toLong()
        }
        object neshan {
            private val neshan = rootConfig.config("$basePath.neshan")
            val token = neshan.property("token").getString()
            val baseUrl = neshan.property("baseUrl").getString()
            val isHttps = neshan.property("isHttps").getString().toBoolean()
            val debuggable = neshan.property("debuggable").getString().toBoolean()
            val connectTimeout = neshan.property("connectTimeout").getString().toLong()
            val readTimeout = neshan.property("readTimeout").getString().toLong()
        }
        object mapIr {
            private val mapIr = rootConfig.config("$basePath.mapIr")
            val token = mapIr.property("token").getString()
            val baseUrl = mapIr.property("baseUrl").getString()
            val isHttps = mapIr.property("isHttps").getString().toBoolean()
            val debuggable = mapIr.property("debuggable").getString().toBoolean()
            val connectTimeout = mapIr.property("connectTimeout").getString().toLong()
            val readTimeout = mapIr.property("readTimeout").getString().toLong()
        }
        object google {
            private val google = rootConfig.config("$basePath.google")
            val token = google.property("token").getString()
            val baseUrl = google.property("baseUrl").getString()
            val isHttps = google.property("isHttps").getString().toBoolean()
            val debuggable = google.property("debuggable").getString().toBoolean()
            val connectTimeout = google.property("connectTimeout").getString().toLong()
            val readTimeout = google.property("readTimeout").getString().toLong()
        }
        object parsiMap {
            private val parsimap = rootConfig.config("$basePath.parsiMap")
            val token = parsimap.property("token").getString()
            val baseUrl = parsimap.property("baseUrl").getString()
            val isHttps = parsimap.property("isHttps").getString().toBoolean()
            val debuggable = parsimap.property("debuggable").getString().toBoolean()
            val connectTimeout = parsimap.property("connectTimeout").getString().toLong()
            val readTimeout = parsimap.property("readTimeout").getString().toLong()
        }
    }

    object jwt {
        private val cedarmap = rootConfig.config("jwt")
        val issuer = cedarmap.property("issuer").getString()
        val domain = cedarmap.property("domain").getString()
        val audience = cedarmap.property("audience").getString()
        val realm = cedarmap.property("realm").getString()
    }

    object config {
        private val basePath = "config"

        object distance {
            private val distance = rootConfig.config("$basePath.distance")
            val near = distance.property("near").getString().toInt()
            val mid = distance.property("mid").getString().toInt()
//            val far = distance.property("far").getString().toInt()
        }

        object geolocatingConfidence {
            private val geolocatingConfidence = rootConfig.config("$basePath.geolocatingConfidence")
            val bestRange = geolocatingConfidence.property("bestFrom").getString().toInt()..geolocatingConfidence.property("bestTo").getString().toInt()
            val goodRange = geolocatingConfidence.property("goodFrom").getString().toInt()..geolocatingConfidence.property("goodTo").getString().toInt()
            val badRange = geolocatingConfidence.property("badFrom").getString().toInt()..geolocatingConfidence.property("badTo").getString().toInt()
            val worstRange = geolocatingConfidence.property("worstFrom").getString().toInt()..geolocatingConfidence.property("worstTo").getString().toInt()
        }

        val geoLocationRatio = rootConfig.property("$basePath.geoLocationRatio").getString().toDouble()
    }

    fun get(path: String) = rootConfig.property(path).getString()


}