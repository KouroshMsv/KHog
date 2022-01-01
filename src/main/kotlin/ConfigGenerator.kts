import com.typesafe.config.ConfigFactory
import dev.kourosh.objectgenerator.KHog
import io.ktor.server.config.*

KHog.generateGetProperty(
    packageName = "dev.kourosh",
    rootPath = "src.main.kotlin",
    className = "Config",
    configPath ="config"
)
