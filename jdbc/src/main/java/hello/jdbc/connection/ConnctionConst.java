package hello.jdbc.connection;

// 데이터베이스에 접속하는데 필요한 기본 정보를 편리하게 사용하도록 상수로 만들었음
// 상수로 만들었기에 인스턴스 만들 수 없도록 abstract class로 만듦
public abstract class ConnctionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
