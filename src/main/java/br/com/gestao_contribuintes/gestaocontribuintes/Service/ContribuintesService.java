package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.gestao_contribuintes.gestaocontribuintes.DTO.DependentesDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.FamiliaDTO;
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
        if (contribuintes == null) {
            throw new IllegalArgumentException("O objeto 'contribuintes' não pode ser nulo");
        }

        String cpf = contribuintes.getCPF();
        if (contribuintesRepository.existsByCPF(cpf)) {
            throw new IllegalArgumentException("O CPF " + cpf + " já está cadastrado.");
        }

        // Se o CPF não existe, salva o contribuinte no banco de dados
        return contribuintesRepository.save(contribuintes);
    }

    public List<Contribuintes> getAllContribuintes() {
        List<Contribuintes> contribuintesList = contribuintesRepository.findAll();
        return contribuintesList;
    }

    public Optional<Contribuintes> update(Contribuintes contribuintes) {
        if (contribuintes.getCPF() != null && !contribuintesRepository.existsByCPF(contribuintes.getCPF())) {
            return Optional.empty();
        }

        Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(contribuintes.getCPF());
        if (contribuinteOptional.isPresent()) {
            Contribuintes contribuinteExistente = contribuinteOptional.get();

            // Atualiza apenas campos não nulos do contribuinte recebido
            if (contribuintes.getNomeCivil() != null) {
                contribuinteExistente.setNomeCivil(contribuintes.getNomeCivil());
            }
            if (contribuintes.getNomeSocial() != null) {
                contribuinteExistente.setNomeSocial(contribuintes.getNomeSocial());
            }
            if (contribuintes.getEndereco() != null) {
                contribuinteExistente.setEndereco(contribuintes.getEndereco());
            }
            if (contribuintes.getEmail() != null) {
                contribuinteExistente.setEmail(contribuintes.getEmail());
            }
            if (contribuintes.getSalario() != null) {
                contribuinteExistente.setSalario(contribuintes.getSalario());
            }
            if (contribuintes.getCategoria() != null) {
                contribuinteExistente.setCategoria(contribuintes.getCategoria());
            }
            if (contribuintes.getTelefone() != null) {
                contribuinteExistente.setTelefone(contribuintes.getTelefone());
            }
            if (contribuintes.getInicioContribuicao() != null) {
                contribuinteExistente.setInicioContribuicao(contribuintes.getInicioContribuicao());
            }
            if (contribuintes.getCpfPai() != null) {
                contribuinteExistente.setCpfPai(contribuintes.getCpfPai());
            }
            if (contribuintes.getCpfMae() != null) {
                contribuinteExistente.setCpfMae(contribuintes.getCpfMae());
            }

            // Atualiza CPF do cônjuge
            String cpfConjugeAntigo = contribuinteExistente.getCpfConjuge();
            String novoCpfConjuge = contribuintes.getCpfConjuge();
            if (novoCpfConjuge != null && !novoCpfConjuge.equals(cpfConjugeAntigo)) {
                contribuinteExistente.setCpfConjuge(novoCpfConjuge);

                if (cpfConjugeAntigo != null && !cpfConjugeAntigo.equals(novoCpfConjuge)) {
                    Optional<Contribuintes> conjugeOptional = contribuintesRepository.findById(cpfConjugeAntigo);
                    conjugeOptional.ifPresent(conjuge -> {
                        conjuge.setCpfConjuge(null);
                        contribuintesRepository.save(conjuge);
                    });
                }
            }

            // Salva as alterações no banco de dados
            return Optional.of(contribuintesRepository.save(contribuinteExistente));
        }

        return Optional.empty();
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

    // Método para atualizar um dependente
    public Dependentes updateDependente(String cpfContribuinte, String cpfDependente, Dependentes dependente) {
        // Busca o contribuinte pelo CPF
        Contribuintes contribuinte = contribuintesRepository.findByCPF(cpfContribuinte);
        if (contribuinte == null) {
            throw new IllegalArgumentException("Contribuinte não encontrado");
        }

        // Procura o dependente pelo CPF
        Dependentes dependenteExistente = null;
        for (Dependentes dep : contribuinte.getDependentes()) {
            if (dep.getCPF().equals(cpfDependente)) {
                dependenteExistente = dep;
                break;
            }
        }
        if (dependenteExistente == null) {
            throw new IllegalArgumentException("Dependente não encontrado");
        }

        // Atualiza as informações do dependente
        dependenteExistente.setnomeCivil(dependente.getnomeCivil());

        // Salva as alterações
        contribuintesRepository.save(contribuinte);

        return dependenteExistente;
    }

    // Método para excluir um dependente
    public void deleteDependente(String cpfContribuinte, String cpfDependente) {
        // Busca o contribuinte pelo CPF
        Contribuintes contribuinte = contribuintesRepository.findByCPF(cpfContribuinte);
        if (contribuinte == null) {
            throw new IllegalArgumentException("Contribuinte não encontrado");
        }

        // Remove o dependente pelo CPF
        Dependentes dependenteParaRemover = null;
        for (Dependentes dep : contribuinte.getDependentes()) {
            if (dep.getCPF().equals(cpfDependente)) {
                dependenteParaRemover = dep;
                break;
            }
        }
        if (dependenteParaRemover == null) {
            throw new IllegalArgumentException("Dependente não encontrado");
        }
        contribuinte.getDependentes().remove(dependenteParaRemover);

        // Salva as alterações
        contribuintesRepository.save(contribuinte);
    }

    // Método para verificar se um dependente existe
    public boolean dependenteExists(String cpfContribuinte, String cpfDependente) {
        // Busca o contribuinte pelo CPF
        Contribuintes contribuinte = contribuintesRepository.findByCPF(cpfContribuinte);
        if (contribuinte == null) {
            throw new IllegalArgumentException("Contribuinte não encontrado");
        }

        // Verifica se o dependente existe
        for (Dependentes dependente : contribuinte.getDependentes()) {
            if (dependente.getCPF().equals(cpfDependente)) {
                return true; // Dependente encontrado
            }
        }

        return false; // Dependente não encontrado
    }

    // FAMÍLIA
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

    public FamiliaDTO getFamiliaDTOByPrincipalCPF(String cpfPrincipal) {
        // Buscar contribuinte principal pelo CPF
        Contribuintes contribuintePrincipal = contribuintesRepository.findByCPF(cpfPrincipal);
        if (contribuintePrincipal == null) {
            throw new IllegalArgumentException(
                    "O contribuinte principal com CPF " + cpfPrincipal + " não foi encontrado.");
        }

        // Criar objeto FamiliaDTO e preencher os dados do contribuinte principal
        FamiliaDTO familiaDTO = new FamiliaDTO();
        familiaDTO.setNomeCivilPrincipal(contribuintePrincipal.getNomeCivil());
        familiaDTO.setCpfPrincipal(contribuintePrincipal.getCPF());

        // Preencher dados do cônjuge, se existir
        String cpfConjuge = contribuintePrincipal.getCpfConjuge();
        if (cpfConjuge != null) {
            Contribuintes conjuge = contribuintesRepository.findByCPF(cpfConjuge);
            if (conjuge != null) {
                familiaDTO.setConjugeNomeCivil(conjuge.getNomeCivil());
                familiaDTO.setConjugeCPF(conjuge.getCPF());
            }
        }

        // Buscar e preencher os dados do pai e da mãe do contribuinte principal
        String cpfPaiPrincipal = contribuintePrincipal.getCpfPai();
        if (cpfPaiPrincipal != null) {
            Contribuintes paiPrincipal = contribuintesRepository.findByCPF(cpfPaiPrincipal);
            if (paiPrincipal != null) {
                familiaDTO.setNomeCivilPai(paiPrincipal.getNomeCivil());
                familiaDTO.setCpfPai(paiPrincipal.getCPF());

                // Buscar e preencher os dados do avô paterno
                String cpfAvoPaterno = paiPrincipal.getCpfPai();
                if (cpfAvoPaterno != null) {
                    Contribuintes avoPaterno = contribuintesRepository.findByCPF(cpfAvoPaterno);
                    if (avoPaterno != null) {
                        familiaDTO.setNomeCivilAvoPaterno(avoPaterno.getNomeCivil());
                        familiaDTO.setCpfAvoPaterno(avoPaterno.getCPF());
                    }
                }

                // Buscar e preencher os dados da avó paterna
                String cpfAvoMaterno = paiPrincipal.getCpfMae();
                if (cpfAvoMaterno != null) {
                    Contribuintes avoMaterno = contribuintesRepository.findByCPF(cpfAvoMaterno);
                    if (avoMaterno != null) {
                        familiaDTO.setNomeCivilAvóPaterno(avoMaterno.getNomeCivil());
                        familiaDTO.setCpfAvóPaterno(avoMaterno.getCPF());
                    }
                }
            }
        }

        // Buscar e preencher os dados da mãe do contribuinte principal
        String cpfMaePrincipal = contribuintePrincipal.getCpfMae();
        if (cpfMaePrincipal != null) {
            Contribuintes maePrincipal = contribuintesRepository.findByCPF(cpfMaePrincipal);
            if (maePrincipal != null) {
                familiaDTO.setNomeCivilMae(maePrincipal.getNomeCivil());
                familiaDTO.setCpfMae(maePrincipal.getCPF());

                // Buscar e preencher os dados do avô materno
                String cpfAvoPaterno = maePrincipal.getCpfPai();
                if (cpfAvoPaterno != null) {
                    Contribuintes avoPaterno = contribuintesRepository.findByCPF(cpfAvoPaterno);
                    if (avoPaterno != null) {
                        familiaDTO.setNomeCivilAvoMaterno(avoPaterno.getNomeCivil());
                        familiaDTO.setCpfAvoMaterno(avoPaterno.getCPF());
                    }
                }

                // Buscar e preencher os dados da avó materna
                String cpfAvoMaterno = maePrincipal.getCpfMae();
                if (cpfAvoMaterno != null) {
                    Contribuintes avoMaterno = contribuintesRepository.findByCPF(cpfAvoMaterno);
                    if (avoMaterno != null) {
                        familiaDTO.setNomeCivilAvóMaterno(avoMaterno.getNomeCivil());
                        familiaDTO.setCpfAvóMaterno(avoMaterno.getCPF());
                    }
                }
            }
        }

        // Preencher dados dos dependentes
        List<DependentesDTO> dependentesDTO = new ArrayList<>();
        List<Dependentes> dependentes = contribuintePrincipal.getDependentes();
        if (dependentes != null) {
            for (Dependentes dependente : dependentes) {
                DependentesDTO dependenteDTO = new DependentesDTO();
                dependenteDTO.setNomeCivil(dependente.getnomeCivil());
                dependenteDTO.setCpf(dependente.getCPF());
                dependentesDTO.add(dependenteDTO);
            }
        }
        familiaDTO.setDependentes(dependentesDTO);

        return familiaDTO;
    }

}