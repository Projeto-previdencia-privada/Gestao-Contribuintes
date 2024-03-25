package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Filiacao;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Responsavel;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ContribuintesRepository;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.FiliacaoRepository;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ResponsavelRepository;

@Service
public class ContribuintesService {

    private final ContribuintesRepository contribuintesRepository;
    private final FiliacaoRepository filiacaoRepository;
    private ResponsavelRepository responsavelRepository;

    public ContribuintesService(ContribuintesRepository contribuintesRepository, FiliacaoRepository filiacaoRepository,
            ResponsavelRepository responsavelRepository) {
        this.contribuintesRepository = contribuintesRepository;
        this.filiacaoRepository = filiacaoRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @SuppressWarnings("null")
    public Contribuintes create(Contribuintes contribuintes) {
        return contribuintesRepository.save(contribuintes);
    }

    @Transactional
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

    public Filiacao cadastrarDependente(String cpfResponsavel, Filiacao dependente) {
        Optional<Responsavel> responsavelOptional = responsavelRepository.findById(cpfResponsavel);
        if (responsavelOptional.isEmpty()) {
            throw new IllegalArgumentException("O responsável com o CPF " + cpfResponsavel + " não foi encontrado.");
        }

        Contribuintes responsavel = contribuintesRepository.findByCPF(cpfResponsavel);
        dependente.setContribuinte(responsavel);
        dependente.setCPF(cpfResponsavel);

        return filiacaoRepository.save(dependente);
    }

    public boolean existsByCPF(String cpf) {
        return contribuintesRepository.existsByCPF(cpf);
    }

    public List<Filiacao> getDependentesByContribuinteCPF(String cpf) {
        // Consulta o repositório para obter a lista de dependentes associados ao
        // contribuinte com o CPF fornecido
        return filiacaoRepository.findByContribuinte_CPF(cpf);
    }
}
