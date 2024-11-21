package com.revup.user.repository;

import com.revup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // TODO: 피드백 코드 작성 중 테스트 용도로 만들어두었습니다. 병합 시 삭제 or 변경 필요 - 김민우
}
