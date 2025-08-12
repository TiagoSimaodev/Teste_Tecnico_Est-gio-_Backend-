package teste.api.rest.back.end.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import teste.api.rest.back.end.Repository.TarefaRepository;
import teste.api.rest.back.end.model.Tarefa;
import teste.api.rest.back.end.model.Tarefa.PrioridadeTarefas;
import teste.api.rest.back.end.model.Tarefa.StatusTarefas;

@Service
public class TarefaService {

	
	private final TarefaRepository tarefaRepository;
	
	//recebendo o repository na minha service para utilizar. 
	public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }
	
	
	//criando a tarefa
	@Transactional
	public Tarefa criarTarefa(Tarefa tarefas) {
		return tarefaRepository.save(tarefas);
	}	
	
	
	// Atualizar uma tarefa existente
    @Transactional
    public Tarefa atualizarTarefa(Long id, Tarefa dadosAtualizados) {
        Optional<Tarefa> optTarefa = tarefaRepository.findById(id);
        if (optTarefa.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        Tarefa tarefa = optTarefa.get();

        // Atualiza os campos 
        tarefa.setTitulo(dadosAtualizados.getTitulo());
        tarefa.setDescricao(dadosAtualizados.getDescricao());
        tarefa.setStatus(dadosAtualizados.getStatus());
        tarefa.setPrioridade(dadosAtualizados.getPrioridade());

        // Se quiser atualizar subtarefas, faça aqui também (com cuidado)

        return tarefaRepository.save(tarefa);
    }  
    
    @Autowired
    private TarefaRepository tarRepository;

    
    // rotina para atualizar status. 
    public Tarefa atualizarStatus(Long id, String novoStatusStr) {
        // Busca a tarefa no banco
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));

        // Converte string para enum, cuidado com valores inválidos
        StatusTarefas novoStatus;
        try {
            novoStatus = StatusTarefas.valueOf(novoStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + novoStatusStr);
        }

        // Se for tentar concluir a tarefa, verifica subtarefas
        if (novoStatus == StatusTarefas.CONCLUIDO) {
            boolean temSubtarefasPendentes = tarefa.getSubtarefas().stream()
                    .anyMatch(sub -> sub.getStatus() == StatusTarefas.PENDENTE);
            if (temSubtarefasPendentes) {
                throw new RuntimeException("Não é possível concluir tarefa com subtarefas pendentes.");
            }
        }

        // Atualiza o status e salva
        tarefa.setStatus(novoStatus);
        return tarefaRepository.save(tarefa);
    }
    
    
    // deletando a tarefa por ID
    public void deletarTarefa(Long id) {
    		if(!tarefaRepository.existsById(id)) {
    			throw new RuntimeException("Tarefa não encontrada");
    		}
    		tarefaRepository.deleteById(id);
    	}
    
    //buscando por id
    
    public Optional<Tarefa> buscarPorId(Long id) {
    	return tarefaRepository.findById(id);
    }
    
    public List<Tarefa> listarTarefasComFiltro(StatusTarefas status, PrioridadeTarefas prioridade,
            LocalDateTime vencimentoAntes, LocalDateTime vencimentoDepois) {

// Aqui vamos criar uma consulta dinâmica usando o JPA Specification ou manualmente.

// Exemplo simples usando Spring Data JPA com query methods:
// (Mas para filtros dinâmicos, o ideal é Specification ou Criteria API)

// Supondo que seu repositório tenha métodos que combinam filtros,
// ou você pode criar uma consulta customizada.

// Exemplo simplificado: (você deve adaptar conforme seu repositório)
if (status != null && prioridade != null && vencimentoAntes != null && vencimentoDepois != null) {
return tarefaRepository.findByStatusAndPrioridadeAndDataVencimentoBetween(
status, prioridade, vencimentoDepois, vencimentoAntes);
}
// implementar demais combinações ou usar Specification para mais flexibilidade

return tarefaRepository.findAll(); // fallback: sem filtro
}

    
}
