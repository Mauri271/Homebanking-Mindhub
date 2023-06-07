package com.mindhub.homebanking.repositories;

import java.util.List;
import com.mindhub.homebanking.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountsRepository extends JpaRepository <Accounts, Long>{
    List <Accounts> findById(long Id);
}


