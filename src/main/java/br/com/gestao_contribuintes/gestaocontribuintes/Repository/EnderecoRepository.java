package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Endereco;


public interface EnderecoRepository extends JpaRepository<Endereco, String> {
    List<Endereco> findByCPF(String cpf);
}