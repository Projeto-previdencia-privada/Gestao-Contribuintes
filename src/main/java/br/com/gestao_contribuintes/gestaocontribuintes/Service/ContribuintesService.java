package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Dependentes;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ContribuintesRepository;
import jakarta.transaction.Transactional;

@Service
public class ContribuintesService {
    private final ContribuintesRepository contribuintesRepository;

    public ContribuintesService(ContribuintesRepository contribuintesRepository) {
        this.contribuintesRepository = contribuintesRepository;
    }

    public Optional<Contribuintes> getContribuinteByCPF(String cpf) {
        return contribuintesRepository.findById(cpf);
    }

    public Contribuintes create(Contribuintes contribuintes) {
        if (contribuintes != null) {
            return contribuintesRepository.save(contribuintes);
        } else {
            throw new IllegalArgumentException("O objeto 'contribuintes' não pode ser nulo");
        }
    }

    public List<Contribuintes> getAllContribuintes() {
        List<Contribuintes> contribuintesList = contribuintesRepository.findAll();
        contribuintesList.forEach(contribuinte -> contribuinte.setDependentes(null)); // Define os dependentes como null
        return contribuintesList;
    }

    public Optional<Contribuintes> update(Contribuintes contribuintes) {
        if (contribuintes.getCPF() == null || !contribuintesRepository.existsByCPF(contribuintes.getCPF())) {
            return Optional.empty();
        }
        return Optional.of(contribuintesRepository.save(contribuintes));
    }

    public Optional<Contribuintes> updateCpfConjuge(String cpfContribuinte, String cpfConjuge) {
        Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpfContribuinte);
        if (contribuinteOptional.isPresent()) {
            Contribuintes contribuinte = contribuinteOptional.get();
            contribuinte.setCpfConjuge(cpfConjuge);
            return Optional.of(contribuintesRepository.save(contribuinte));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(String cpf) {
        if (cpf != null && contribuintesRepository.existsByCPF(cpf)) {
            contribuintesRepository.deleteByCPF(cpf);
            return true;
        }
        return false;
    }

    public Contribuintes addDependente(String cpfContribuinte, Dependentes dependente) {
        if (cpfContribuinte != null) {
            Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpfContribuinte);
            if (contribuinteOptional.isEmpty()) {
                throw new IllegalArgumentException(
                        "O contribuinte com o CPF " + cpfContribuinte + " não foi encontrado.");
            }

            Contribuintes contribuinte = contribuinteOptional.get();
            List<Dependentes> dependentes = contribuinte.getDependentes();

            dependente.setResponsavel(contribuinte);
            dependentes.add(dependente);
            contribuinte.setDependentes(dependentes);

            return contribuintesRepository.save(contribuinte);
        } else {
            throw new IllegalArgumentException("O CPF do contribuinte não pode ser nulo.");
        }
    }

    public boolean existsByCPF(String cpf) {
        return contribuintesRepository.existsByCPF(cpf);
    }

    public List<Contribuintes> getFamiliaByContribuinteCPF(String cpf) {
        // Busca o contribuinte pelo CPF fornecido
        Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpf);

        // Verifica se o contribuinte foi encontrado
        return contribuinteOptional.map(contribuinte -> {
            List<Contribuintes> familia = new ArrayList<>();
            familia.add(contribuinte);

            // Obtém o CPF do cônjuge do contribuinte
            String cpfConjuge = contribuinte.getCpfConjuge();

            // Se o contribuinte tiver um cônjuge, adiciona o cônjuge à lista da família
            if (cpfConjuge != null) {
                Optional<Contribuintes> conjugeOptional = contribuintesRepository.findById(cpfConjuge);
                conjugeOptional.ifPresent(familia::add);
            }

            // Obtém a lista de dependentes do contribuinte
            List<Dependentes> dependentes = contribuinte.getDependentes();

            // Se o contribuinte tiver dependentes, adiciona os dependentes à lista da
            // família
            if (dependentes != null && !dependentes.isEmpty()) {
                List<Contribuintes> dependentesComoContribuintes = dependentes.stream()
                        .map(dependente -> {
                            Contribuintes contribuinteDependente = new Contribuintes();
                            contribuinteDependente.setNomeCivil(dependente.getnomeCivil());
                            contribuinteDependente.setCPF(dependente.getCPF());
                            // Se houver necessidade de mais atributos, defina-os aqui
                            return contribuinteDependente;
                        })
                        .collect(Collectors.toList());
                familia.addAll(dependentesComoContribuintes);
            }

            return familia;
        }).orElseThrow(() -> new IllegalArgumentException("O contribuinte com o CPF " + cpf + " não foi encontrado."));
    }

    public List<Dependentes> getDependentesByContribuinteCPF(String cpf) {
        // Verifique se o CPF não é nulo
        if (cpf != null) {
            // Busque o contribuinte pelo CPF no repositório
            Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpf);

            // Verifique se o contribuinte foi encontrado
            if (contribuinteOptional.isPresent()) {
                // Obtém o contribuinte
                Contribuintes contribuinte = contribuinteOptional.get();

                // Obtém a lista de dependentes do contribuinte
                List<Dependentes> dependentes = contribuinte.getDependentes();

                // Retorna a lista de dependentes
                return dependentes;
            } else {
                // Se o contribuinte não foi encontrado, lance uma exceção
                throw new IllegalArgumentException("O contribuinte com o CPF " + cpf + " não foi encontrado.");
            }
        } else {
            // Se o CPF for nulo, lance uma exceção
            throw new IllegalArgumentException("O CPF do contribuinte não pode ser nulo.");
        }
    }

    public List<Contribuintes> getContribuintesComRelacionamento() {
        return contribuintesRepository.findAll().stream()
                .filter(contribuinte -> contribuinte.getDependentes() != null
                        && !contribuinte.getDependentes().isEmpty())
                .collect(Collectors.toList());
    }
}