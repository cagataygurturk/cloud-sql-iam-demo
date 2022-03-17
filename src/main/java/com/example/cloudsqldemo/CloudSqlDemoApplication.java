package com.example.cloudsqldemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class CloudSqlDemoApplication implements CommandLineRunner {

    final JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(CloudSqlDemoApplication.class);

    public CloudSqlDemoApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudSqlDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        dropTable();
        createTable();
        insertRecords();
        var test = printRecords();
        LOG.info("Database works. Record count is " + test.size());
    }


    public void dropTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS `USERS`");
    }

    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `USERS` (" +
                "    `ID` int(11) unsigned NOT NULL auto_increment," +
                "    `EMAIL` varchar(255) NOT NULL default ''," +
                "    PRIMARY KEY  (`ID`)" +
                ") ");
    }

    public void insertRecords() {
        for (int i = 0; i < 10; i++) {
            jdbcTemplate.execute("INSERT INTO `USERS` (`EMAIL`) VALUES (" + i + ")");
        }
    }

    private List<Map<String, Object>> printRecords() {
        return jdbcTemplate.queryForList("SELECT * FROM `USERS`");
    }
}
