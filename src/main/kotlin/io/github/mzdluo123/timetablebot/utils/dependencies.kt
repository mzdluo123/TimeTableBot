package io.github.mzdluo123.timetablebot.utils

import io.github.mzdluo123.timetablebot.config.AppConfig
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

val yaml by lazy {
    Yaml(Constructor(AppConfig::class.java))
}