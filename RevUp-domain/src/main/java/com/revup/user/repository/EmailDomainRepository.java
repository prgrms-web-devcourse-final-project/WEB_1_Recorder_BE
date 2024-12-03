package com.revup.user.repository;

import com.revup.user.entity.EmailDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailDomainRepository extends CrudRepository<EmailDomain , Long> {

    Optional<EmailDomain> findByDomain(String domain);
}
