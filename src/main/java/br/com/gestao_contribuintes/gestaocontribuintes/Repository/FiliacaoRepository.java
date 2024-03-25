package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Filiacao;

public interface FiliacaoRepository extends JpaRepository<Filiacao, String> {

    // Método para encontrar dependente pelo CPF do contribuinte
    List<Filiacao> findByContribuinte_CPF(String cpf);

}
