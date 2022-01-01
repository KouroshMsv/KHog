import com.typesafe.config.ConfigFactory
import dev.kourosh.objectgenerator.KHog
import io.ktor.server.config.*

KHog.generate(
    packageName = "dev.kourosh",
    rootPath = "src.main.kotlin",
    className = "Config",
    applicationConfig = HoconApplicationConfig(ConfigFactory.load()).config("config")
)
