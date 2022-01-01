package dev.kourosh.objectgenerator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import java.io.File

object KHog {
    private fun TypeSpec.Builder.generateConfig(latestKey: String, allItems: Map<String, List<MutableList<String>>>, rootConfig: ApplicationConfig): TypeSpec.Builder {
        val currentClass = this
        allItems.forEach { (key, values) ->
            if (values.size > 1) {
                val newKeys = values.map {
                    it.removeAt(0)
                    it
                }.toMutableList().filter { it.isNotEmpty() }.groupBy { it.firstOrNull() ?: "undefined" }
                val newObject = TypeSpec.objectBuilder(key).generateConfig(buildString {
                    append(latestKey)
                    if (latestKey.isNotBlank())
                        append(".")
                    append(key)
                }, newKeys, rootConfig).build()
                currentClass.addType(newObject)
            } else if (values.size == 1) {
                try {
                    values.first().firstOrNull()?.let {
                        val propertyKey = buildString {
                            append(latestKey)
                            if (latestKey.isNotBlank())
                                append(".")
                            append(key)
                        }

                        val propertyValue = rootConfig.property(propertyKey)
                        try {
                            val propertyValue = propertyValue.getString()
                            val (type, value) = when {
                                propertyValue.toBooleanStrictOrNull() != null -> Boolean::class to propertyValue.toBooleanStrict()
                                propertyValue.toIntOrNull() != null -> Int::class to propertyValue.toInt()
                                propertyValue.toLongOrNull() != null -> Long::class to propertyValue.toLong()
                                propertyValue.toDoubleOrNull() != null -> Double::class to propertyValue.toDouble()
                                else -> String::class to propertyValue
                            }
                            currentClass.addProperty(
                                PropertySpec.builder(key, type)
                                    .initializer(if (type == String::class) "%S" else "%L", value)
                                    .build()
                            )
                        } catch (e: Exception) {
                            val propertyValue = propertyValue.getList()
                            val firstProperty = propertyValue.firstOrNull()
                            val (type, value) = when {
                                firstProperty == null -> Any::class to getListValue(listOf()) { it }
                                firstProperty.toBooleanStrictOrNull() != null -> Boolean::class to getListValue(propertyValue) { it.toBooleanStrict() }
                                firstProperty.toIntOrNull() != null -> Int::class to getListValue(propertyValue) { it.toInt() }
                                firstProperty.toLongOrNull() != null -> Long::class to getListValue(propertyValue) { it.toLong() }
                                firstProperty.toDoubleOrNull() != null -> Double::class to getListValue(propertyValue) { it.toDouble() }
                                else -> String::class to getListValue(propertyValue) { "\"$it\"" }
                            }


                            currentClass.addProperty(PropertySpec.builder(key, getListType(type.asClassName())).initializer("%L", value).build())
                        }

                    }
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
        return this
    }

    /**
     * @param packageName project package name
     * @param className The name of the class to be generated
     * @param rootPath root path to create object
     * @param applicationConfig load your application config
     *
     *
     * rootPath="src.main.kotlin", className="Config", applicationConfig= HoconApplicationConfig(ConfigFactory.load()).config("config")
     * */
    fun generate(
        packageName: String,
        rootPath: String = "src.main.kotlin",
        className: String = "Config",
        applicationConfig: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load()).config("config")
    ) {
        val config = TypeSpec.objectBuilder(className).apply {
            val allItems = applicationConfig.keys().map { it.split(".").toMutableList() }.groupBy { it.firstOrNull() ?: "undefined" }
            generateConfig("", allItems, applicationConfig)
        }.build()
        val kotlinFile = FileSpec.builder(packageName, className)
            .addType(config)
            .build()

        File(rootPath.replace(".", "\\")).apply {
            if (!exists())
                mkdir()
            kotlinFile.writeTo(this)
        }
    }

    private fun <T> getListValue(items: List<String>, map: (String) -> T) = buildCodeBlock {
        val lregex = items.map(map).toString().replace("[", " ").replace("]", " ")
        add("listOf($lregex)")
    }

    private fun getListType(className: ClassName) = List::class.asClassName().parameterizedBy(className.copy(nullable = false))
}
