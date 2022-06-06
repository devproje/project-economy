package net.projecttl.economy.core

import net.projecttl.economy.core.model.DatabaseCredential
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

lateinit var conn: Connection

class Database(val model: DatabaseCredential) {

    fun connect() {
        instance.logger.info("Loading driver...")

        Class.forName("com.mysql.cj.jdbc.Driver")
        instance.logger.info("Connecting to SQL...")

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://${model.url}:${model.port}/${model.db_name}",
                    model.username, model.password
            )
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        init()
    }

    fun disconnect() {
        try {
            if (!conn.isClosed) conn.close()
        } catch (exception: SQLException) {
            exception.printStackTrace()
        }
    }

    private fun init() {
        val stmt = conn.prepareStatement(
                """
                    create table if not exists account(
                        `uuid` varchar(36) not null,
                        `amount` int not null,
                        primary key (`uuid`)
                    );
                """.trimIndent()
        )
        stmt.executeUpdate()
    }
}