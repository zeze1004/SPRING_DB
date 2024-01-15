package hello.jdbc.connection;

import static hello.jdbc.connection.ConnctionConst.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionTest {

    // 커넥션을 획득하는 종류
    // 1. DriverManager: 커넥션을 획득할 때마다 파라미터(URL, USERNAME, PASSWORD)를 계속 전달해야함
    // 2. DataSource: 처음 객체를 생성할 때만 피라미터 넘겨주지만, 이후에는 getConnection()만 호출하면 커넥션 획득 가능
    //      -> 설정과 사용의 분리, 사용하는 곳은 파라미터를 안 넘겨줘도 돼서 속성을 몰라도 됨
    @Test
    void driverManager() throws SQLException {
        Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection connection2 = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 커넥션을 2개 획득

        log.info("connection1={}, class={}", connection1, connection1.getClass());
        log.info("connection2={}, class={}", connection2, connection2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        // DriverManagerDataSource: 항상 새로운 커넥션을 획득
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws InterruptedException, SQLException {
        // 커넥션 풀을 사용하는 DataSource
        // 커넥션풀링을 사용할 것
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10); // 기본 풀사이즈는 100
        dataSource.setPoolName("HikariCP pool");
        // 커넥션 풀에서 커넥션을 생성하는 작업은 앱 실행 속도에 영향을 주지 않기 위해 별도의 쓰레드에서작동
        // 별도의 쓰레드에서 동작하기 때문에 테스트가 먼저 종료됨
        // 그래서 1초 대기해서 커넥션 풀 생성 과정 확인할 수 있음
        useDataSource(dataSource);
        Thread.sleep(1000); // 커넥션 풀에서 커넥션 생성 시간 대기

    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();

        log.info("connection1={}, class={}", connection1, connection1.getClass());
        log.info("connection2={}, class={}", connection2, connection2.getClass());
    }
}
