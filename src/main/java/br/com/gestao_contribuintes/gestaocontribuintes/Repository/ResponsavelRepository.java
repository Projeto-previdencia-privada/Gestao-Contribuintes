package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Responsavel;

public interface ResponsavelRepository extends JpaRepository<Responsavel, String> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário
}

