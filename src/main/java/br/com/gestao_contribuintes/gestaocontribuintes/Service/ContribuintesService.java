package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
//import jakarta.transaction.Transactional;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Dependentes;
//import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Filiacao;
//import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Responsavel;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ContribuintesRepository;
//import br.com.gestao_contribuintes.gestaocontribuintes.Repository.FiliacaoRepository;
//import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ResponsavelRepository;

@Service
public class ContribuintesService {

    private final ContribuintesRepository contribuintesRepository;

    public ContribuintesService(ContribuintesRepository contribuintesRepository) {
        this.contribuintesRepository = contribuintesRepository;
    }

    public Contribuintes create(Contribuintes contribuintes) {
        return contribuintesRepository.save(contribuintes);
    }

    public List<Contribuintes> getAllContribuintes() {
        return contribuintesRepository.findAll();
    }

    public Optional<Contribuintes> update(Contribuintes contribuintes) {
        if (contribuintes.getCPF() == null || !contribuintesRepository.existsByCPF(contribuintes.getCPF())) {
            return Optional.empty();
        }
        return Optional.of(contribuintesRepository.save(contribuintes));
    }

    public boolean delete(String cpf) {
        if (cpf != null && contribuintesRepository.existsByCPF(cpf)) {
            contribuintesRepository.deleteByCPF(cpf);
            return true;
        }
        return false;
    }

    public Contribuintes addDependente(String cpfContribuinte, Dependentes dependente) {
        Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpfContribuinte);
        if (contribuinteOptional.isEmpty()) {
            throw new IllegalArgumentException("O contribuinte com o CPF " + cpfContribuinte + " não foi encontrado.");
        }

        Contribuintes contribuinte = contribuinteOptional.get();
        List<Dependentes> dependentes = contribuinte.getDependentes();
        dependentes.add(dependente);
        contribuinte.setDependentes(dependentes);

        return contribuintesRepository.save(contribuinte);
    }

    public boolean existsByCPF(String cpf) {
        return contribuintesRepository.existsByCPF(cpf);
    }

    public List<Dependentes> getDependentesByContribuinteCPF(String cpf) {
        Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpf);
        if (contribuinteOptional.isEmpty()) {
            throw new IllegalArgumentException("O contribuinte com o CPF " + cpf + " não foi encontrado.");
        }
        return contribuinteOptional.get().getDependentes();
    }
}