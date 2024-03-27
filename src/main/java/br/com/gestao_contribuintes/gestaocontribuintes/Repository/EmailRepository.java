package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Email;

public interface EmailRepository extends JpaRepository<Email, String> {
    
    List<Email> findByContribuinte_CPF(String cpf);
}