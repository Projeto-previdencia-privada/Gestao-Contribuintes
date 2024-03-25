package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Filiacao;

public interface FiliacaoRepository extends JpaRepository<Filiacao, String> {
    List<Filiacao> findByContribuinte_CPF(String cpf);
}
