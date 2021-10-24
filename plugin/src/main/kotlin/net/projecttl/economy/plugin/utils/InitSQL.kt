package net.projecttl.economy.plugin.utils

import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class InitSQL(private val plugin: JavaPlugin) {

    private val logger       = plugin.logger
    private val url          = plugin.config.getString("SQL_URL")
    private val databaseName = plugin.config.getString("SQL_DATABASE")
    private val username     = plugin.config.getString("SQL_USERNAME")
    private val password     = plugin.config.getString("SQL_PASSWORD")
    private val port         = plugin.config.getInt("SQL_PORT")
    private val moneyUnit    = plugin.config.getString("MONEY_UNIT")

    init {
        dbName     = databaseName
        moneyUnits = moneyUnit
    }

    companion object {
        lateinit var connection: Connection
        var moneyUnits: String? = null
        var dbName: String?     = null
    }

    fun connect() {
        logger.info("Loading driver...")

        Class.forName("com.mysql.cj.jdbc.Driver")
        plugin.logger.info("Connecting to SQL...")

        try {
            connection = DriverManager.getConnection("jdbc:mysql://${url}:${port}", username, password)
            logger.info("Connected to ${url}:${port}")
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        createDatabase()
    }

    fun disconnect() {
        try {
            if (!connection.isClosed) {
                connection.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun createDatabase() {
        val statement: Statement = connection.createStatement()
        statement.executeUpdate("create database if not exists $databaseName default character set utf8;")
        statement.executeUpdate("use ${databaseName};")
        statement.executeUpdate(
            "create table if not exists account(" +
                    "`uuid` varchar(36) not null," +
                    "`username` varchar(25) not null," +
                    "`amount` int not null," +
                    "`discord_id` varchar(18) null," +
                    "primary key (uuid)," +
                    "unique index (username)" +
                    ");"
        )
    }
}