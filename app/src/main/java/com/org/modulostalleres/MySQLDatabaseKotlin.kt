package com.org.modulostalleres

import java.sql.*
import java.util.Properties

object MySQLDatabaseKotlin {
    private var conn: Connection? = null
    private var username = "titec1" // provide the username
    private var password = "titec1-LECOFQ" // provide the corresponding password
    private var database = "titec1bd"

    /*
    @JvmStatic fun main(args: Array<String>) {
        // make a connection to MySQL Server
        //getConnection()
        // execute the query via connection object
        //executeMySQLQuery()
    }*/

    public fun main(){
        //println("-----------CONNECTION SUCCESSFUL----------------")
        getConnection()
        executeMySQLQuery()
    }

    private fun executeMySQLQuery() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SHOW DATABASES;")

            if (stmt.execute("select * from persona;")) {
                resultset = stmt.resultSet
                println("...............................AQUI ESTOY CTM.............")
                println(resultset)
            }

            while (resultset!!.next()) {
                println(resultset.getString("Database"))
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                conn = null
            }
        }
    }

    /**
     * This method makes a connection to MySQL Server
     * In this example, MySQL Server is running in the local host (so 127.0.0.1)
     * at the standard port 3306
     */
    private fun getConnection() {
        val connectionProps = Properties()
        connectionProps["user"] = username
        connectionProps["password"] = password
        connectionProps["database"] = database
        println("-------------------------------------------")
        println(connectionProps)
        println("-----------------------AQUI ESTA EL INTENTO")
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection("jdbc:mysql://10.150.45.137:3306/", connectionProps)
            println(conn)
            println("PASEEEEEEEEEEEEEEEEEEEEE")
        } catch (ex: SQLException) {
            // handle any errors
                println(ex)
            //ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            println(ex)
            //ex.printStackTrace()
        }
    }
}