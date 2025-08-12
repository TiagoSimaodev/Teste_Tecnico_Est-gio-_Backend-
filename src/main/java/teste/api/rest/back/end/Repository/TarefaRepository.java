package teste.api.rest.back.end.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teste.api.rest.back.end.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>  {

}
