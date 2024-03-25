package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ContribuintesRepository;
import jakarta.transaction.Transactional;

@Service
public class ContribuintesService {
    
    private final ContribuintesRepository contribuintesRepository;

    public ContribuintesService(ContribuintesRepository contribuintesRepository) {
        this.contribuintesRepository = contribuintesRepository;
    }

    @SuppressWarnings("null")
    public Contribuintes create(Contribuintes contribuintes) {
        return contribuintesRepository.save(contribuintes);
    }

    public List<Contribuintes> getAllContribuintes() {
        return contribuintesRepository.findAll();
    }

    public Optional<Contribuintes> update(Contribuintes contribuintes) {
        // Verifica se o contribuinte existe antes de atualizar
        if (contribuintes.getCPF() == null || !contribuintesRepository.existsByCPF(contribuintes.getCPF())) {
            return Optional.empty(); // Retorna um Optional vazio se o contribuinte não existir
        }
        return Optional.of(contribuintesRepository.save(contribuintes));
    }

    @Transactional
    public boolean delete(String cpf) {
        if (cpf != null) { // Verifica se o CPF não é nulo
            if (contribuintesRepository.existsByCPF(cpf)) {
                contribuintesRepository.deleteByCPF(cpf);
                return true; // Retorna true se o contribuinte for excluído com sucesso
            }
        }
        return false; // Retorna false se o CPF for nulo ou o contribuinte não existir
    }
}
