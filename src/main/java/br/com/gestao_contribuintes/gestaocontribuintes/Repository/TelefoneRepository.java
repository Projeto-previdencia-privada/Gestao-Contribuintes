package br.com.gestao_contribuintes.gestaocontribuintes.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, String> {
    // List<Telefone> findByContribuinte_CPF(String cpf);
}
