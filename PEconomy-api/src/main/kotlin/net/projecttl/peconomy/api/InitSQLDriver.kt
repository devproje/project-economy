package net.projecttl.peconomy.api

import org.bukkit.plugin.Plugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class InitSQLDriver(private val plugin: Plugin) {
    companion object {
        lateinit var sqlConnection: Connection
    }

    fun loadSQLModule() {
        val logger = plugin.logger

        val url = plugin.config.getString("SQL_IP")
        val username = plugin.config.getString("SQL_USERNAME")
        val password = plugin.config.getString("SQL_PASSWORD")
        val port = plugin.config.getInt("SQL_PORT")

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
        statement.executeUpdate("create database if not exists PEconomy default character set utf8;")
        statement.executeUpdate("use PEconomy;")
        statement.executeUpdate("create table if not exists account(" +
                "id int not null AUTO_INCREMENT," +
                "username varchar(25)  not null," +
                "`uuid` varchar(36) not null," +
                "`amount` int not null," +
                "primary key (id)," +
                "unique index (username)" +
                ");")
    }

    fun closeConnection() {
        try {
            if (sqlConnection != null || !sqlConnection.isClosed) {
                sqlConnection.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}