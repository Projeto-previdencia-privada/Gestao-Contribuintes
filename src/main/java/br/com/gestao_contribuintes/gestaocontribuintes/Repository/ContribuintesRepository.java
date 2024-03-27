package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Dependentes;

@Repository
public interface ContribuintesRepository extends JpaRepository<Contribuintes, String> {

    boolean existsByCPF(String cpf);

    Contribuintes findByCPF(String cpf);

    void deleteByCPF(String cpf);

    // Método para buscar dependentes de um contribuinte pelo CPF
    List<Dependentes> findDependentesByCPF(String cpf);

}
