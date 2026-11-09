package padroesprojeto.mediator.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CentralHospitalarTest {

    CentralHospitalar central;
    Medico medico;
    Laboratorio laboratorio;
    Farmacia farmacia;
    Enfermaria enfermaria;

    @BeforeEach
    void setUp() {
        central = new CentralHospitalar();
        medico = new Medico(central, "Dra. Ana");
        laboratorio = new Laboratorio(central, "Laboratório");
        farmacia = new Farmacia(central, "Farmácia");
        enfermaria = new Enfermaria(central, "Enfermaria");
        central.setMedico(medico);
        central.setLaboratorio(laboratorio);
        central.setFarmacia(farmacia);
        central.setEnfermaria(enfermaria);
    }

    @Test
    void deveEncaminharSolicitacaoDeExameAoLaboratorio() {
        medico.solicitarExame("João", "Hemograma");

        assertEquals(List.of("Exame solicitado: João - Hemograma"), laboratorio.getMensagens());
        assertTrue(farmacia.getMensagens().isEmpty());
        assertTrue(enfermaria.getMensagens().isEmpty());
    }

    @Test
    void deveInformarOMedicoQuandoOResultadoForLiberado() {
        laboratorio.liberarResultado("João", "Hemograma", "normal");

        assertEquals(List.of("Resultado liberado: João - Hemograma: normal"), medico.getMensagens());
    }

    @Test
    void devePrescreverParaAFarmaciaEParaAEnfermaria() {
        medico.prescrever("João", "Dipirona");

        assertEquals(List.of("Prescrição recebida: João - Dipirona"), farmacia.getMensagens());
        assertEquals(List.of("Prescrição a administrar: João - Dipirona"), enfermaria.getMensagens());
    }

    @Test
    void deveAvisarAEnfermariaQuandoOMedicamentoForDispensado() {
        farmacia.dispensar("João", "Dipirona");

        assertEquals(List.of("Medicamento disponível: João - Dipirona"), enfermaria.getMensagens());
    }

    @Test
    void deveAvisarOMedicoQuandoOMedicamentoForAdministrado() {
        enfermaria.registrarAdministracao("João", "Dipirona");

        assertEquals(List.of("Medicamento administrado: João - Dipirona"), medico.getMensagens());
    }

    @Test
    void remetenteNaoDeveReceberAPropriaMensagem() {
        medico.solicitarExame("João", "Hemograma");

        assertTrue(medico.getMensagens().isEmpty());
    }

    @Test
    void deveConduzirOFluxoCompletoSemQueOsColegasSeConhecam() {
        medico.solicitarExame("João", "Hemograma");
        laboratorio.liberarResultado("João", "Hemograma", "normal");
        medico.prescrever("João", "Dipirona");
        farmacia.dispensar("João", "Dipirona");
        enfermaria.registrarAdministracao("João", "Dipirona");

        assertEquals(1, laboratorio.getMensagens().size());
        assertEquals(2, medico.getMensagens().size());
        assertEquals(1, farmacia.getMensagens().size());
        assertEquals(2, enfermaria.getMensagens().size());
    }

    @Test
    void deveRegistrarNoHistoricoTodasAsInteracoes() {
        medico.solicitarExame("João", "Hemograma");
        laboratorio.liberarResultado("João", "Hemograma", "normal");

        assertEquals(List.of(
                "Dra. Ana -> EXAME_SOLICITADO: João - Hemograma",
                "Laboratório -> RESULTADO_LIBERADO: João - Hemograma: normal"), central.getHistorico());
    }

    @Test
    void deveFalharQuandoODestinatarioNaoEstaRegistrado() {
        CentralHospitalar centralIncompleta = new CentralHospitalar();
        Medico medicoSozinho = new Medico(centralIncompleta, "Dr. Paulo");
        centralIncompleta.setMedico(medicoSozinho);

        try {
            medicoSozinho.solicitarExame("João", "Hemograma");
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Laboratório não registrado", e.getMessage());
        }
    }
}
