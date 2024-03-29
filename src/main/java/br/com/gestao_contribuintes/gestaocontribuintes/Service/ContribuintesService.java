package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.ArrayList;
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
        if (contribuintes != null) {
            return contribuintesRepository.save(contribuintes);
        } else {
            // exceção indicando que o argumento é inválido (nulo)
            throw new IllegalArgumentException("O objeto 'contribuintes' não pode ser nulo");
        }
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
        // Verifica se o CPF do contribuinte não é nulo
        if (cpfContribuinte != null) {
            Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpfContribuinte);
            if (contribuinteOptional.isEmpty()) {
                throw new IllegalArgumentException(
                        "O contribuinte com o CPF " + cpfContribuinte + " não foi encontrado.");
            }

            Contribuintes contribuinte = contribuinteOptional.get();
            List<Dependentes> dependentes = contribuinte.getDependentes();

            // Definindo o relacionamento bidirecional
            dependente.setResponsavel(contribuinte);
            dependentes.add(dependente);
            contribuinte.setDependentes(dependentes);

            return contribuintesRepository.save(contribuinte);
        } else {
            // exceção indicando que o CPF do contribuinte é inválido (nulo)
            throw new IllegalArgumentException("O CPF do contribuinte não pode ser nulo.");
        }
    }

    public boolean existsByCPF(String cpf) {
        return contribuintesRepository.existsByCPF(cpf);
    }

    public List<Dependentes> getDependentesByContribuinteCPF(String cpf) {
        // Verifica se o CPF do contribuinte não é nulo
        if (cpf != null) {
            Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpf);
            if (contribuinteOptional.isEmpty()) {
                throw new IllegalArgumentException("O contribuinte com o CPF " + cpf + " não foi encontrado.");
            }
            return contribuinteOptional.get().getDependentes();
        } else {
            // exceção indicando que o CPF do contribuinte é inválido (nulo)
            throw new IllegalArgumentException("O CPF do contribuinte não pode ser nulo.");
        }
    }

    public List<Contribuintes> getArvoreGenealogica(String cpfContribuinte, int profundidade) {
        // Verifica se o CPF do contribuinte não é nulo
        if (cpfContribuinte != null) {
            List<Contribuintes> arvoreGenealogica = new ArrayList<>();
            // Adiciona o contribuinte raiz à árvore genealógica
            Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpfContribuinte);
            if (contribuinteOptional.isPresent()) {
                arvoreGenealogica.add(contribuinteOptional.get());
                // Chama o método recursivo para obter os pais e filhos
                obterPaisEFilhos(contribuinteOptional.get(), arvoreGenealogica, profundidade, 1);
            }
            return arvoreGenealogica;
        } else {
            // exceção indicando que o CPF do contribuinte é inválido (nulo)
            throw new IllegalArgumentException("O CPF do contribuinte não pode ser nulo.");
        }
    }

    private void obterPaisEFilhos(Contribuintes contribuinte, List<Contribuintes> arvoreGenealogica,
            int profundidadeDesejada, int profundidadeAtual) {
        if (profundidadeAtual >= profundidadeDesejada) {
            return;
        }
        // Obtém os pais do contribuinte atual
        List<Contribuintes> pais = obterPais(contribuinte);
        // Adiciona os pais à árvore genealógica
        arvoreGenealogica.addAll(pais);
        // Recursivamente obtém os filhos dos pais
        for (Contribuintes pai : pais) {
            obterPaisEFilhos(pai, arvoreGenealogica, profundidadeDesejada, profundidadeAtual + 1);
        }
    }

    private List<Contribuintes> obterPais(Contribuintes contribuinte) {
        List<Contribuintes> pais = new ArrayList<>();
        // Lógica para obter os pais do contribuinte a partir da sua entidade ou do
        // banco de dados
        // Verifica se o contribuinte tem dependente
        if (contribuinte.getDependentes() != null && !contribuinte.getDependentes().isEmpty()) {
            // Itera sobre os dependentes do contribuinte
            for (Dependentes dependente : contribuinte.getDependentes()) {
                // Verifica se o dependente tem responsável
                if (dependente.getResponsavel() != null) {
                    // Adiciona o responsável à lista de pais, se ainda não estiver presente
                    if (!pais.contains(dependente.getResponsavel())) {
                        pais.add(dependente.getResponsavel());
                    }
                }
            }
        }
        return pais;
    }

}