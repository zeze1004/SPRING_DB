package hello.jdbc.repository;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crue() throws SQLException {
        // save
        Member memberB = new Member("memberB", 10000);
        repository.save(memberB);

        // findById
        Member findMember = repository.findById("memberB");
        log.info("findMember={}", findMember);

        // findMembers는 findById 함수에서 새로 만들어진 member인스턴스로 memberb와 다른 인스턴스인데 테스트는 통과함 왜일까?
        // 롬복 @Data 어노테이션을 사용하면 equals()를 오버라이딩 해줌
        assertThat(findMember).isEqualTo(memberB);
    }
}
