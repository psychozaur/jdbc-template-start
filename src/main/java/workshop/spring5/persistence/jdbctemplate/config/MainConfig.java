package workshop.spring5.persistence.jdbctemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/*
    TODO 3 zmień poniższą klasę na klasę konfiguracyjną Spring'a
    skanowanie pakietów - workshop.spring5.persistence.jdbctemplate
 */
@Configuration
@ComponentScan("workshop.spring5.persistence.jdbctemplate")
@PropertySource("classpath:application.properties")
public class MainConfig {

    /* TODO 6 dataSource  - będzie wykorzystany dla wielu metod
            Utwórz medotę definiującą ziarno Spring'a  (@Bean)

            Zwracany typ: javax.sql.DataSource;
            W metodzie utwórz org.springframework.jdbc.datasource.DriverManagerDataSource
            Ustaw w utworzonym obiekcie pola:
                                            driverClassName (dla postgresa)
                                            url             (wskazuje na bazę bookee)
                                            username        (dane jak przy tworzeniu
                                            password         użytkownika w postgres )

            (można użyć innej implementacji np. dbcp dla optymalizacji puli połączeń)
        */

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/h2/my-dbs/persons.db");
        dataSource.setUsername("sa");
        dataSource.setPassword("qwerty");

        return dataSource;
    }

    /*
        TODO 7 definicja jdbcTemplate
            Utwórz medotę definiującą ziarno Spring'a  (@Bean)

            Zwracany typ: org.springframework.jdbc.core.JdbcTemplate
            W metodzie utwórz obiekt jdbcTemplate i ustaw w nim dataSource
            zwracany przez dataSource()
        */

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    /*

        TODO 18 definicja namedParameterJdbcTemplate
            Utwórz medotę definiującą ziarno Spring'a  (@Bean)

            Zwracany typ : org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
            W metodzie przekaż do konstruktora NamedParameterJdbcTemplate dataSource()
        */

}
