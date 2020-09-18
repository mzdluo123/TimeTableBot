package io.github.mzdluo123.timetablebot.utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.mzdluo123.timetablebot.config.AppConfig
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DAOImpl
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import kotlin.reflect.KClass

val dataSource by lazy {
    val dbConfig = HikariConfig().apply {
        jdbcUrl = AppConfig.getInstance().dbUrl
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = AppConfig.getInstance().dbUser
        password = AppConfig.getInstance().dbPwd
        maximumPoolSize = 5
    }
    HikariDataSource(dbConfig)
}
val dbConfig: Configuration = DefaultConfiguration().set(dataSource).set(SQLDialect.MYSQL)

inline fun <reified  T >dbCtx(receiver: (DSLContext) -> T):T {
    DSL.using(dbConfig).use {
        return receiver(it)
    }
}

val DAOs = hashMapOf<String, DAOImpl<*, *, *>>()
fun <T : DAOImpl<*, *, *>> createDao(dao: KClass<T>): T {
    val name = dao.qualifiedName ?: throw IllegalStateException()
    if (DAOs.containsKey(name)) {
        return DAOs[name] as T
    }
    dao.constructors.forEach {
        if (it.parameters.isNotEmpty() && it.parameters[0].type.classifier == Configuration::class) {
            val instance = it.call(dbConfig)
            DAOs[name] = instance as DAOImpl<*, *, *>
            return instance
        }
    }
    throw IllegalAccessError("这不是一个dao")
}