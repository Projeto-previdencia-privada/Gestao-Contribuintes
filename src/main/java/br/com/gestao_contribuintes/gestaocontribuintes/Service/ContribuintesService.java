package br.com.gestao_contribuintes.gestaocontribuintes.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import br.com.gestao_contribuintes.gestaocontribuintes.DTO.AvoDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.DependentesDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.FamiliaDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.PaisDTO;
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

    // Método para atualizar dependente
    public Dependentes updateDependente(String cpfContribuinte, String cpfDependente, Dependentes dependente) {

        // Verifica se o contribuinte existe
        Contribuintes contribuinte = contribuintesRepository.findByCPF(cpfContribuinte);
        if (contribuinte == null) {
            throw new IllegalArgumentException("Contribuinte não encontrado");
        }

        // Verifica se o dependente existe
        Dependentes dependenteExistente = contribuinte.getDependentes().stream()
                .filter(dep -> dep.getCPF().equals(cpfDependente))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dependente não encontrado"));

        // Atualiza as informações do dependente
        dependenteExistente.setnomeCivil(dependente.getnomeCivil());

        // Salva as alterações
        contribuintesRepository.save(contribuinte);

        // Retorna apenas o dependente atualizado
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

        // Criar objeto FamiliaDTO e preencher dados do contribuinte principal
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

        // Criar e preencher a lista de PaisDTO
        List<PaisDTO> paisDTOs = new ArrayList<>();

        // Preencher dados do pai e da mãe do contribuinte principal, incluindo pais
        // adicionais
        adicionarPais(paisDTOs, contribuintePrincipal);

        familiaDTO.setPais(paisDTOs);

        // Criar e preencher a lista de AvosDTO
        List<AvoDTO> avosDTOs = new ArrayList<>();

        // Preencher avós a partir do pai
        adicionarAvos(avosDTOs, contribuintePrincipal.getCpfPai());

        // Preencher avós a partir da mãe
        adicionarAvos(avosDTOs, contribuintePrincipal.getCpfMae());

        familiaDTO.setAvos(avosDTOs);

        // Preencher dados dos dependentes
        List<DependentesDTO> dependentesDTOs = new ArrayList<>();
        List<Dependentes> dependentes = contribuintePrincipal.getDependentes();
        if (dependentes != null) {
            for (Dependentes dependente : dependentes) {
                DependentesDTO dependenteDTO = new DependentesDTO();
                dependenteDTO.setNomeCivil(dependente.getnomeCivil());
                dependenteDTO.setCpf(dependente.getCPF());
                dependentesDTOs.add(dependenteDTO);
            }
        }
        familiaDTO.setDependentes(dependentesDTOs);

        return familiaDTO;
    }

    // Função para adicionar pais ao PaisDTO, lidando com pais/mães adicionais
    private void adicionarPais(List<PaisDTO> paisDTOs, Contribuintes contribuintePrincipal) {
        if (contribuintePrincipal.getCpfPai() != null) {
            Contribuintes pai = contribuintesRepository.findByCPF(contribuintePrincipal.getCpfPai());
            if (pai != null) {
                PaisDTO paisDTO = new PaisDTO();
                paisDTO.setNomeCivil(pai.getNomeCivil());
                paisDTO.setCpf(pai.getCPF());
                paisDTOs.add(paisDTO);
            }
        }

        if (contribuintePrincipal.getCpfPai2() != null) {
            Contribuintes pai2 = contribuintesRepository.findByCPF(contribuintePrincipal.getCpfPai2());
            if (pai2 != null) {
                PaisDTO paisDTO = new PaisDTO();
                paisDTO.setNomeCivil(pai2.getNomeCivil());
                paisDTO.setCpf(pai2.getCPF());
                paisDTOs.add(paisDTO);
            }
        }

        if (contribuintePrincipal.getCpfPai3() != null) {
            Contribuintes pai3 = contribuintesRepository.findByCPF(contribuintePrincipal.getCpfPai3());
            if (pai3 != null) {
                PaisDTO paisDTO = new PaisDTO();
                paisDTO.setNomeCivil(pai3.getNomeCivil());
                paisDTO.setCpf(pai3.getCPF());
                paisDTOs.add(paisDTO);
            }
        }

        if (contribuintePrincipal.getCpfMae() != null) {
            Contribuintes mae = contribuintesRepository.findByCPF(contribuintePrincipal.getCpfMae());
            if (mae != null) {
                PaisDTO paisDTO = new PaisDTO();
                paisDTO.setNomeCivil(mae.getNomeCivil());
                paisDTO.setCpf(mae.getCPF());
                paisDTOs.add(paisDTO);
            }
        }

        if (contribuintePrincipal.getCpfMae2() != null) {
            Contribuintes mae2 = contribuintesRepository.findByCPF(contribuintePrincipal.getCpfMae2());
            if (mae2 != null) {
                PaisDTO paisDTO = new PaisDTO();
                paisDTO.setNomeCivil(mae2.getNomeCivil());
                paisDTO.setCpf(mae2.getCPF());
                paisDTOs.add(paisDTO);
            }
        }

        if (contribuintePrincipal.getCpfMae3() != null) {
            Contribuintes mae3 = contribuintesRepository.findByCPF(contribuintePrincipal.getCpfMae3());
            if (mae3 != null) {
                PaisDTO paisDTO = new PaisDTO();
                paisDTO.setNomeCivil(mae3.getNomeCivil());
                paisDTO.setCpf(mae3.getCPF());
                paisDTOs.add(paisDTO);
            }
        }
    }

    private void adicionarAvosPaternos(List<AvoDTO> avosDTOs, Contribuintes pai) {
        if (pai != null) {
            Set<String> cpfsAdicionados = new HashSet<>();

            // Primeiro avô paterno
            if (pai.getCpfPai() != null) {
                Contribuintes avoPaterno = contribuintesRepository.findByCPF(pai.getCpfPai());
                if (avoPaterno != null && !cpfsAdicionados.contains(avoPaterno.getCPF())) {
                    AvoDTO avoDTO = new AvoDTO();
                    avoDTO.setNomeCivil(avoPaterno.getNomeCivil());
                    avoDTO.setCpf(avoPaterno.getCPF());
                    avosDTOs.add(avoDTO);
                    cpfsAdicionados.add(avoPaterno.getCPF());
                }
            }

            // Segundo avô paterno, se existir
            if (pai.getCpfPai2() != null) {
                Contribuintes avoPaterno2 = contribuintesRepository.findByCPF(pai.getCpfPai2());
                if (avoPaterno2 != null && !cpfsAdicionados.contains(avoPaterno2.getCPF())) {
                    AvoDTO avoDTO = new AvoDTO();
                    avoDTO.setNomeCivil(avoPaterno2.getNomeCivil());
                    avoDTO.setCpf(avoPaterno2.getCPF());
                    avosDTOs.add(avoDTO);
                    cpfsAdicionados.add(avoPaterno2.getCPF());
                }
            }

            // Tecceiro avô paterno, se existir
            if (pai.getCpfPai3() != null) {
                Contribuintes avoPaterno3 = contribuintesRepository.findByCPF(pai.getCpfPai3());
                if (avoPaterno3 != null && !cpfsAdicionados.contains(avoPaterno3.getCPF())) {
                    AvoDTO avoDTO = new AvoDTO();
                    avoDTO.setNomeCivil(avoPaterno3.getNomeCivil());
                    avoDTO.setCpf(avoPaterno3.getCPF());
                    avosDTOs.add(avoDTO);
                    cpfsAdicionados.add(avoPaterno3.getCPF());
                }
            }
        }
    }

    private void adicionarAvosMaternos(List<AvoDTO> avosDTOs, Contribuintes mae) {
        if (mae != null) {
            Set<String> cpfsAdicionados = new HashSet<>();

            // Primeiro avô materno
            if (mae.getCpfMae() != null) {
                Contribuintes avoMaterno = contribuintesRepository.findByCPF(mae.getCpfMae());
                if (avoMaterno != null && !cpfsAdicionados.contains(avoMaterno.getCPF())) {
                    AvoDTO avoDTO = new AvoDTO();
                    avoDTO.setNomeCivil(avoMaterno.getNomeCivil());
                    avoDTO.setCpf(avoMaterno.getCPF());
                    avosDTOs.add(avoDTO);
                    cpfsAdicionados.add(avoMaterno.getCPF());
                }
            }

            // Segundo avô materno
            if (mae.getCpfMae2() != null) {
                Contribuintes avoMaterno2 = contribuintesRepository.findByCPF(mae.getCpfMae2());
                if (avoMaterno2 != null && !cpfsAdicionados.contains(avoMaterno2.getCPF())) {
                    AvoDTO avoDTO = new AvoDTO();
                    avoDTO.setNomeCivil(avoMaterno2.getNomeCivil());
                    avoDTO.setCpf(avoMaterno2.getCPF());
                    avosDTOs.add(avoDTO);
                    cpfsAdicionados.add(avoMaterno2.getCPF());
                }
            }

            // Terceiro avô materno
            if (mae.getCpfMae2() != null) {
                Contribuintes avoMaterno3 = contribuintesRepository.findByCPF(mae.getCpfMae3());
                if (avoMaterno3 != null && !cpfsAdicionados.contains(avoMaterno3.getCPF())) {
                    AvoDTO avoDTO = new AvoDTO();
                    avoDTO.setNomeCivil(avoMaterno3.getNomeCivil());
                    avoDTO.setCpf(avoMaterno3.getCPF());
                    avosDTOs.add(avoDTO);
                    cpfsAdicionados.add(avoMaterno3.getCPF());
                }
            }
        }
    }

    // Função para adicionar avós ao AvosDTO
    private void adicionarAvos(List<AvoDTO> avosDTOs, String cpfPaiOuMae) {
        if (cpfPaiOuMae != null) {
            Contribuintes paiOuMae = contribuintesRepository.findByCPF(cpfPaiOuMae);
            if (paiOuMae != null) {
                // Lista para armazenar os avôs e evitar duplicidade
                List<AvoDTO> novosAvos = new ArrayList<>();

                // Adiciona os avôs paternos e maternos
                adicionarAvosPaternos(novosAvos, paiOuMae);
                adicionarAvosMaternos(novosAvos, paiOuMae);

                // Adiciona os novos avôs à lista principal
                avosDTOs.addAll(novosAvos);
            }
        }
    }

    // Método para ativar um contribuinte
    public Contribuintes ativarContribuinte(String cpf) {
        Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpf);
        if (contribuinteOptional.isPresent()) {
            Contribuintes contribuinte = contribuinteOptional.get();
            contribuinte.setAtivo(true);
            return contribuintesRepository.save(contribuinte);
        } else {
            throw new IllegalArgumentException("Contribuinte com CPF " + cpf + " não encontrado.");
        }
    }

    // Método para inativar um contribuinte
    public Contribuintes inativarContribuinte(String cpf) {
        Optional<Contribuintes> contribuinteOptional = contribuintesRepository.findById(cpf);
        if (contribuinteOptional.isPresent()) {
            Contribuintes contribuinte = contribuinteOptional.get();
            contribuinte.setAtivo(false);
            return contribuintesRepository.save(contribuinte);
        } else {
            throw new IllegalArgumentException("Contribuinte com CPF " + cpf + " não encontrado.");
        }
    }

}