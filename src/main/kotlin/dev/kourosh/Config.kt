package dev.kourosh

import kotlin.Any
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.collections.List

public object Config {
  public val listi4s: List<Boolean> = listOf( true, true, false, false )

  public val listis2312: List<Any> = listOf(  )

  public val listis: List<Int> = listOf( 1, 2, 3, 4 )

  public val listi2s: List<Double> = listOf( 1.2, 2.0, 3.0, 4.2 )

  public val listi3s: List<String> = listOf( "w.2", "2", "3", "4.2" )

  public object externalRestApis {
    public object optime {
      public val baseUrl: String = "https://se.optime-ai.com/"

      public val readTimeout: Int = 2000

      public val debuggable: Boolean = true

      public val isHttps: Boolean = true

      public val connectTimeout: Int = 60

      public val token: String =
          "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZDNhOThhNjEtNjZiZi00NDQ3LTkzODktMTc1MWQzMDJjY2FiIiwiYXVkIjoiZmFzdGFwaS11c2VyczphdXRoIiwiZXhwIjoxNjUzNDE4ODE0fQ.OAaMw9DzQchxfidF7nT810-0TTMgPEZD2QDOoG5RwDM"
    }

    public object parsiMap {
      public val baseUrl: String = "https://api.parsimap.ir/"

      public val connectTimeout: Int = 60

      public val token: String = "p153ff52b9c4e34ea2b9f7103bad4e0efdd98397bc"

      public val debuggable: Boolean = true

      public val readTimeout: Int = 60

      public val isHttps: Boolean = true
    }

    public object cedarmap {
      public val isHttps: Boolean = true

      public val readTimeout: Int = 60

      public val connectTimeout: Int = 60

      public val debuggable: Boolean = true

      public val baseUrl: String = "https://api.cedarmaps.com/"
    }

    public object mapIr {
      public val baseUrl: String = "https://map.ir/"

      public val isHttps: Boolean = true

      public val token: String =
          "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjUxMzFkOWRiNGU0ZDAxODgyNjNhNWFlZjcyOGRhOTdkYjYzMDRkNDhmNGIxZDYyZDdhYWE1NTE3Njg2ZDgwZWVmNmU2NmZkMDIyNDBhNjQxIn0.eyJhdWQiOiIxNTYxNCIsImp0aSI6IjUxMzFkOWRiNGU0ZDAxODgyNjNhNWFlZjcyOGRhOTdkYjYzMDRkNDhmNGIxZDYyZDdhYWE1NTE3Njg2ZDgwZWVmNmU2NmZkMDIyNDBhNjQxIiwiaWF0IjoxNjMyNjgwMTMxLCJuYmYiOjE2MzI2ODAxMzEsImV4cCI6MTYzNTE4NTczMSwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.PrpI_12FNklN6zkPO7cxzUuzftwlK9PkN6Uu6EOhY0Kq_1W7NSCRTclViA-fPwhLbc0s1vX5Q4YyPjREzNQxD7nz_Tsl4iX6Gk2EivYCWqY5HiVGESvWiL1Su-vaH2XoyBVbkZuR0U-Lwq6Ug3yoekJT5Hni1xr_1Bh8KJCHV4LvoIcBSH0m3r9dzKupc8lJwqDSiBCTEb-dRRZ3Lm53CLAV3Wm07JPIX1ikIlCONPAHHq5AmQkF6mRaOXZAVZ_ICwSQvv4IA9Z2fRBQo_G51bu7xI1Cc_HKj0bqc3Fbzvy4vLEHVlwsQw65rJl5c3lHX_pJdn9bqBshmB5sAZ0GpQ"

      public val readTimeout: Int = 60

      public val debuggable: Boolean = true

      public val connectTimeout: Int = 60
    }

    public object google {
      public val isHttps: Boolean = true

      public val token: String = "AIzaSyCHwwChWdOoLxF80z4cFmZrAv-p-l3qX4w"

      public val debuggable: Boolean = true

      public val readTimeout: Int = 60

      public val connectTimeout: Int = 60

      public val baseUrl: String = "https://maps.googleapis.com/"
    }

    public object neshan {
      public val readTimeout: Int = 60

      public val baseUrl: String = "https://api.neshan.org/"

      public val isHttps: Boolean = true

      public val token: String = "service.8V1MuNJ35ZeoDz6lN5YYHY6QSQcBJbaoU0lQV82x"

      public val connectTimeout: Int = 60

      public val debuggable: Boolean = true
    }

    public object addressParser {
      public val debuggable: Boolean = true

      public val baseUrl: String = "http://localhost:5000"

      public val isHttps: Boolean = false

      public val connectTimeout: Int = 60

      public val readTimeout: Int = 60
    }
  }

  public object jwt {
    public val issuer: String = "https://ecourier.mahex.com/uaa/oauth/token"

    public val domain: String = "https://ecourier.mahex.com/uaa/token_keys"

    public val realm: String = "ktor"

    public val audience: String = "openid"
  }

  public object config {
    public val geoLocationRatio: Double = 0.1

    public object geolocatingConfidence {
      public val badTo: Int = 69

      public val bestFrom: Int = 90

      public val worstTo: Int = 59

      public val worstFrom: Int = 0

      public val goodFrom: Int = 70

      public val badFrom: Int = 60

      public val goodTo: Int = 89

      public val bestTo: Int = 100
    }

    public object distance {
      public val near: Int = 250

      public val mid: Int = 450
    }
  }

  public object neo4j {
    public val password: Int = 123456

    public val username: String = "neo4j"

    public val boltUri: String = "bolt://localhost:11862"
  }

  public object postgres {
    public val password: String = "root"

    public val jdbcUri: String =
        "jdbc:postgresql://192.168.19.23:5432/jaakoocore_db?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"

    public val poolSize: Int = 5

    public val username: String = "postgres"
  }
}
