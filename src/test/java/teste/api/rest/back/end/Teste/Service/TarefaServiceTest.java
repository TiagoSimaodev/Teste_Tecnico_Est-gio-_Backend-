package teste.api.rest.back.end.Teste.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import teste.api.rest.back.end.Repository.TarefaRepository;
import teste.api.rest.back.end.Service.TarefaService;
import teste.api.rest.back.end.model.Tarefa;
import teste.api.rest.back.end.model.Tarefa.StatusTarefas;

public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarTarefa_deveSalvarTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Teste");
        tarefa.setDescricao("Descrição");
        tarefa.setDataVencimento(LocalDateTime.now());
        tarefa.setStatus(StatusTarefas.PENDENTE);

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa resultado = tarefaService.criarTarefa(tarefa);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.getTitulo());
        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void atualizarStatus_deveLancarErroSeSubtarefasPendentes() {
        Tarefa tarefaPai = new Tarefa();
        tarefaPai.setId(1L);
        tarefaPai.setStatus(StatusTarefas.PENDENTE);

        Tarefa subtarefaPendente = new Tarefa();
        subtarefaPendente.setStatus(StatusTarefas.PENDENTE);

        ArrayList<Tarefa> subtarefas = new ArrayList<>();
        subtarefas.add(subtarefaPendente);
        tarefaPai.setSubtarefas(subtarefas);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaPai));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            tarefaService.atualizarStatus(1L, "CONCLUIDO");
        });

        assertEquals("Não é possível concluir tarefa com subtarefas pendentes.", exception.getMessage());
        verify(tarefaRepository, never()).save(any());
    }

    @Test
    void atualizarStatus_deveAtualizarStatusSeValido() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setStatus(StatusTarefas.PENDENTE);
        tarefa.setSubtarefas(new ArrayList<>()); // sem subtarefas pendentes

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa atualizada = tarefaService.atualizarStatus(1L, "CONCLUIDO");

        assertEquals(StatusTarefas.CONCLUIDO, atualizada.getStatus());
        verify(tarefaRepository, times(1)).save(tarefa);
    }
}

