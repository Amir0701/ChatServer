package com.lambakean.data.repository;

import com.lambakean.data.model.RefreshTokenWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenWrapperRepository extends JpaRepository<RefreshTokenWrapper, Long> {

    RefreshTokenWrapper findByToken(String token);

}
