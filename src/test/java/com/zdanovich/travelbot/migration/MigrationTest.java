package com.zdanovich.travelbot.migration;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJdbcTest
public class MigrationTest {

    private static final Logger logger = LoggerFactory.getLogger(MigrationTest.class);

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringLiquibase liquibase;

    @Test
    public void check_meta_data() throws Exception {
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData().getClientInfoProperties()) {
            logger.info("Catalog: " + connection.getCatalog());
            logger.info("Schema: " + connection.getSchema());
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            logger.info("Driver name: " + databaseMetaData.getDriverName());
            logger.info("URL: " + databaseMetaData.getURL());
            logger.info("Username: " + databaseMetaData.getUserName());
            while (resultSet.next()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    logger.info(String.format("%s (%s) %s", resultSetMetaData.getColumnTypeName(i),
                            resultSetMetaData.getColumnClassName(i), resultSetMetaData.getColumnName(i)));
                }
            }
        }
    }

    @Test
    public void check_table_creation() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.getMetaData()
                     .getTables(null, null, "%", new String[]{"TABLE"})) {
            List<String> tables = new ArrayList<>(10);
            while (resultSet.next()) {
                tables.add(resultSet.getString(3));
                logger.info("Table name : " + resultSet.getString(3));
            }
            Assert.assertFalse(tables.isEmpty());
        }
    }
}
