package teste.api.rest.back.end.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import teste.api.rest.back.end.Repository.TarefaRepository;
import teste.api.rest.back.end.model.Tarefa;

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
    
    
    // deletando a tarefa por ID
    public void deletarTarefa(Long id) {
    		if(!tarefaRepository.existsById(id)) {
    			throw new RuntimeException("Tarefa não encontrada");
    		}
    	}
    
    //buscando por id
    
    public Optional<Tarefa> buscarPorId(Long id) {
    	return tarefaRepository.findById(id);
    }
    
}
