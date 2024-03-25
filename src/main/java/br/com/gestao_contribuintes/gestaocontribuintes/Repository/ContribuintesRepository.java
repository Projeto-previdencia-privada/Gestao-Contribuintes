package br.com.gestao_contribuintes.gestaocontribuintes.Repository;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
//import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Filiacao;

@Repository
public interface ContribuintesRepository extends JpaRepository<Contribuintes, String> {

    boolean existsByCPF(String cpf);

    Contribuintes findByCPF(String cpf);

    void deleteByCPF(String cpf);

    /*@Query("SELECT f FROM Filiacao f WHERE f.contribuinte.CPF = :cpf")
    static
    List<Filiacao> findDependentesByCPF(String cpf) {

        throw new UnsupportedOperationException("Unimplemented method 'findDependentesByCPF'");
    }*/
}
