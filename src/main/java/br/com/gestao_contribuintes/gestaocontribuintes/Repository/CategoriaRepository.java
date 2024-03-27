package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, String> {
    List<Categoria> findByContribuinte_CPF(String cpf);
}