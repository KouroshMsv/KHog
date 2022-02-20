package dev.kourosh.objectgenerator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import java.io.File

object KHog {
    private fun TypeSpec.Builder.generateValueConfig(latestKey: String, allItems: Map<String, List<MutableList<String>>>, rootConfig: ApplicationConfig): TypeSpec.Builder {
        val currentClass = this
        allItems.forEach { (key, values) ->
            if (values.size > 1) {
                val newKeys = values.map {
                    it.removeAt(0)
                    it
                }.toMutableList().filter { it.isNotEmpty() }.groupBy { it.firstOrNull() ?: "undefined" }
                val newObject = TypeSpec.objectBuilder(key).generateValueConfig(buildString {
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

    private fun TypeSpec.Builder.generateConfigCode(latestKey: String, allItems: Map<String, List<MutableList<String>>>, rootConfig: ApplicationConfig): TypeSpec.Builder {
        val currentClass = this
        allItems.forEach { (key, values) ->
            if (values.size > 1) {
                val newKeys = values.map {
                    it.removeAt(0)
                    it
                }.toMutableList().filter { it.isNotEmpty() }.groupBy { it.firstOrNull() ?: "undefined" }
                val newObject = TypeSpec.objectBuilder(key).generateConfigCode(buildString {
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
                                propertyValue.toBooleanStrictOrNull() != null -> Boolean::class to ".toBooleanStrict()"
                                propertyValue.toIntOrNull() != null -> Int::class to ".toInt()"
                                propertyValue.toLongOrNull() != null -> Long::class to ".toLong()"
                                propertyValue.toDoubleOrNull() != null -> Double::class to ".toDouble()"
                                else -> String::class to ""
                            }
                            currentClass.addProperty(

                                PropertySpec.builder(key, type)
                                    .getter(FunSpec.getterBuilder()
                                        .addStatement("return rootConfig.property(%S).getString()$value",propertyKey)
                                        .build())
                                    .build()
                            )
                        } catch (e: Exception) {
                            val propertyValue = propertyValue.getList()
                            val firstProperty = propertyValue.firstOrNull()
                            val (type, value) = when {
                                firstProperty == null -> String::class to ""
                                firstProperty.toBooleanStrictOrNull() != null -> Boolean::class to ".toBooleanStrict()"
                                firstProperty.toIntOrNull() != null -> Int::class to ".toInt()"
                                firstProperty.toLongOrNull() != null -> Long::class to ".toLong()"
                                firstProperty.toDoubleOrNull() != null -> Double::class to ".toDouble()"
                                else -> String::class to ""
                            }

                            val variable="rootConfig.property(\"$propertyKey\").getList()${if(value.isBlank()) "" else ".map { it$value }"}"
                            currentClass.addProperty(PropertySpec.builder(key, getListType(type.asClassName())).initializer("%L", variable).build())
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
     * @param configPath root of config in application.conf
     *
     *
     * rootPath="src.main.kotlin", className="Config", applicationConfig= HoconApplicationConfig(ConfigFactory.load()).config("config")
     * */
    fun generateValue(
        packageName: String,
        rootPath: String = "src.main.kotlin",
        className: String = "Config",
        configPath: String = "config"
    ) {
        val applicationConfig: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load()).config(configPath)

        val config = TypeSpec.objectBuilder(className).apply {

            val allItems = applicationConfig.keys().map { it.split(".").toMutableList() }.groupBy { it.firstOrNull() ?: "undefined" }
            generateValueConfig("", allItems, applicationConfig)
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

    /**
     * @param packageName project package name
     * @param className The name of the class to be generated
     * @param rootPath root path to create object
     * @param configPath root of config in application.conf
     *
     *
     * rootPath="src.main.kotlin", className="Config", applicationConfig= HoconApplicationConfig(ConfigFactory.load()).config("config")
     * */
    fun generateGetProperty(
        packageName: String,
        rootPath: String = "src.main.kotlin",
        className: String = "Config",
        configPath: String = "config"
    ) {
        val applicationConfig: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load()).config(configPath)

        val config = TypeSpec.objectBuilder(className).apply {
            addFunction(FunSpec.builder("register").apply {
                addParameter("config",ApplicationConfig::class)
                addStatement("rootConfig= config")
            }.build())
            addProperty(
                PropertySpec.builder("rootConfig", ApplicationConfig::class, KModifier.PRIVATE)
                    .mutable()
                    .initializer("%L", "io.ktor.server.config.HoconApplicationConfig(com.typesafe.config.ConfigFactory.load()).config(\"$configPath\")")
                    .build()
            )
            val allItems = applicationConfig.keys().map { it.split(".").toMutableList() }.groupBy { it.firstOrNull() ?: "undefined" }
            generateConfigCode("", allItems, applicationConfig)
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
