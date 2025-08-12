package teste.api.rest.back.end.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teste.api.rest.back.end.model.Tarefa;
import teste.api.rest.back.end.model.Tarefa.PrioridadeTarefas;
import teste.api.rest.back.end.model.Tarefa.StatusTarefas;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>  {
	 List<Tarefa> findByStatusAndPrioridadeAndDataVencimentoBetween(
			 	StatusTarefas status,
			 	PrioridadeTarefas prioridade,
		        LocalDateTime dataInicio,
		        LocalDateTime dataFim
		    );
}
