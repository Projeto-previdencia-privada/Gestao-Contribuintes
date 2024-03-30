package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Dependentes;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ContribuintesRepository;

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
        if (cpf != null) {
            Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpf);
            if (contribuinteOptional.isEmpty()) {
                throw new IllegalArgumentException("O contribuinte com o CPF " + cpf + " não foi encontrado.");
            }
            Contribuintes contribuinte = contribuinteOptional.get();

            List<Contribuintes> familia = new ArrayList<>();
            familia.add(contribuinte);

            List<Dependentes> dependentes = contribuinte.getDependentes();
            if (dependentes != null && !dependentes.isEmpty()) {
                for (Dependentes dependente : dependentes) {
                    Contribuintes responsavel = dependente.getResponsavel();
                    if (responsavel != null && !familia.contains(responsavel)) {
                        familia.add(responsavel);
                        // Verifica se o responsável tem filhos e os adiciona à família
                        List<Dependentes> filhosDoResponsavel = responsavel.getDependentes();
                        if (filhosDoResponsavel != null && !filhosDoResponsavel.isEmpty()) {
                            for (Dependentes filho : filhosDoResponsavel) {
                                if (!familia.contains(filho.getResponsavel())) {
                                    familia.add(filho.getResponsavel());
                                }
                            }
                        }
                    }
                }
            }

            return familia;
        } else {
            throw new IllegalArgumentException("O CPF do contribuinte não pode ser nulo.");
        }
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