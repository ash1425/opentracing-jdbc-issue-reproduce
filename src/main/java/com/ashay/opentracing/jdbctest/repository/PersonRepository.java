package com.ashay.opentracing.jdbctest.repository;

import com.ashay.opentracing.jdbctest.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Person person) {
        String insertStatement = "insert into person (\n" +
                "    age,\n" +
                "    city,\n" +
                "    name)\n" +
                "values (\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?)\n" +
                ";";
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(insertStatement, new String[]{"id", "age", "city", "name"});
            statement.setInt(1, person.getAge());
            statement.setString(2, person.getCity());
            statement.setString(3, person.getName());
            return statement;
        }, new GeneratedKeyHolder());
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("select\n" +
                        "    p.age,\n" +
                        "    p.city,\n" +
                        "    p.id,\n" +
                        "    p.name\n" +
                        "from\n" +
                        "    person p;",
                new BeanPropertyRowMapper<>(Person.class)
        );
    }
}
