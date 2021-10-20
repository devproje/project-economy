package net.projecttl.economy

import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class InitSQL(private val plugin: JavaPlugin) {

    private val logger       = plugin.logger
    private val url          = plugin.config.getString("SQL_URL")
    private val databaseName = plugin.config.getString("SQL_DATABASE")
    private val username     = plugin.config.getString("SQL_USERNAME")
    private val password     = plugin.config.getString("SQL_PASSWORD")
    private val port         = plugin.config.getInt("SQL_PORT")
    private val moneyUnit    = plugin.config.getString("MONEY_UNIT")

    fun connect() {
        if (sqlConnection.isClosed) {
            logger.info("Loading driver...")

            Class.forName("com.mysql.cj.jdbc.Driver")
            plugin.logger.info("Connecting to SQL...")

            try {
                sqlConnection =
                    DriverManager.getConnection("jdbc:mysql://${url}:${port}/$databaseName", username, password)
                logger.info("Connected to ${url}:${port}/$databaseName")
            } catch (exception: SQLException) {
                exception.printStackTrace()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun disconnect() {
        try {
            if (!sqlConnection.isClosed) {
                sqlConnection.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    init {
        dbName     = databaseName
        moneyUnits = moneyUnit
    }

    companion object {
        lateinit var sqlConnection: Connection
        var moneyUnits: String? = null
        var dbName: String?     = null
    }
}