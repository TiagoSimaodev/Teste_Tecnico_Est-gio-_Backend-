package teste.api.rest.back.end.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import teste.api.rest.back.end.Repository.TarefaRepository;
import teste.api.rest.back.end.Specification.TarefaSpecification;
import teste.api.rest.back.end.model.Tarefa;
import teste.api.rest.back.end.model.Tarefa.PrioridadeTarefas;
import teste.api.rest.back.end.model.Tarefa.StatusTarefas;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

 // metodos do crud

    @Transactional
    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Optional<Tarefa> atualizarTarefa(Long id, Tarefa dadosAtualizados) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefa.setTitulo(dadosAtualizados.getTitulo());
                    tarefa.setDescricao(dadosAtualizados.getDescricao());
                    tarefa.setStatus(dadosAtualizados.getStatus());
                    tarefa.setPrioridade(dadosAtualizados.getPrioridade());
                    return tarefaRepository.save(tarefa);
                });
    }

    public void deletarTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        tarefaRepository.deleteById(id);
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    //  metodo de atualização de status

    public Tarefa atualizarStatus(Long id, String novoStatusStr) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));

        StatusTarefas novoStatus;
        try {
            novoStatus = StatusTarefas.valueOf(novoStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + novoStatusStr);
        }

        if (novoStatus == StatusTarefas.CONCLUIDO) {
            boolean temSubtarefasPendentes = tarefa.getSubtarefas().stream()
                    .anyMatch(sub -> sub.getStatus() == StatusTarefas.PENDENTE);
            if (temSubtarefasPendentes) {
                throw new RuntimeException("Não é possível concluir tarefa com subtarefas pendentes.");
            }
        }

        tarefa.setStatus(novoStatus);
        return tarefaRepository.save(tarefa);
    }

    //  Listagem com filtros flexíveis 

    public List<Tarefa> listarTarefasComFiltro(StatusTarefas status, PrioridadeTarefas prioridade,
                                               LocalDateTime vencimentoInicio, LocalDateTime vencimentoFim) {

        Specification<Tarefa> specs = Specification
                .where(TarefaSpecification.hasStatus(status))
                .and(TarefaSpecification.hasPrioridade(prioridade))
                .and(TarefaSpecification.vencimentoBetween(vencimentoInicio, vencimentoFim));

        return tarefaRepository.findAll(specs);
    }
}