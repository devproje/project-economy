package net.projecttl.pbalance.api

import org.bukkit.plugin.Plugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import kotlin.system.exitProcess

class InitSQLDriver(private val plugin: Plugin) {
    companion object {
        lateinit var sqlConnection: Connection

        var database: String?        = null
        var table: String?           = null
        var username: String?        = null
        var uuid: String?            = null
        var balance: String?         = null
        var moneyUnit: String?       = null
        var commandEnabled: Boolean? = null
        var exchangeGUI: Boolean?    = null
    }

    private val inlineDatabase       = plugin.config.getString("DATABASE")
    private val inlineTable          = plugin.config.getString("TABLE")
    private val inlineUsername       = plugin.config.getString("USERNAME")
    private val inlineUuid           = plugin.config.getString("UUID")
    private val inlineBalance        = plugin.config.getString("BALANCE")
    private val inlineMoneyUnit      = plugin.config.getString("MONEY_UNIT")
    private val inlineCommandEnabled = plugin.config.getBoolean("COMMAND_ENABLED")
    private val inlineExchangeGUI    = plugin.config.getBoolean("EXCHANGE_GUI")

    init {
        database       = inlineDatabase
        table          = inlineTable
        username       = inlineUsername
        uuid           = inlineUuid
        balance        = inlineBalance
        moneyUnit      = inlineMoneyUnit
        commandEnabled = inlineCommandEnabled
        exchangeGUI    = inlineExchangeGUI
    }

    fun loadSQLModule() {
        val logger   = plugin.logger
        val url      = plugin.config.getString("SQL_URL")
        val username = plugin.config.getString("SQL_USERNAME")
        val password = plugin.config.getString("SQL_PASSWORD")
        val port     = plugin.config.getInt("SQL_PORT")

        logger.info("Loading driver...")

        Class.forName("com.mysql.cj.jdbc.Driver")
        plugin.logger.info("Connecting to SQL...")

        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://${url}:${port}/", username, password)
            logger.info("Connected to ${url}:${port}")
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        val statement: Statement = sqlConnection.createStatement()
        statement.executeUpdate("create database if not exists $database default character set utf8;")
        statement.executeUpdate("use ${database};")
        statement.executeUpdate("create table if not exists ${table}(" +
                "`uuid` varchar(36) not null," +
                "`username` varchar(25)  not null," +
                "`amount` int not null," +
                "primary key (uuid)," +
                "unique index (username)" +
                ");")
    }

    fun openConnection() {
        val logger   = plugin.logger
        val url      = plugin.config.getString("SQL_URL")
        val username = plugin.config.getString("SQL_USERNAME")
        val password = plugin.config.getString("SQL_PASSWORD")
        val port     = plugin.config.getInt("SQL_PORT")

        logger.info("Loading driver...")

        Class.forName("com.mysql.cj.jdbc.Driver")
        plugin.logger.info("Connecting to SQL...")

        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://${url}:${port}/", username, password)
            logger.info("Connected to ${url}:${port}")
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun closeConnection() {
        try {
            if (!sqlConnection.isClosed) {
                sqlConnection.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}