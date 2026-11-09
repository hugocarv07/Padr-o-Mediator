package padroesprojeto.mediator.hospital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ConcreteMediator: é o único que conhece todos os colegas e decide quem
 * recebe cada evento. Os colegas só conhecem o Mediador.
 */
public class CentralHospitalar implements Mediador {

    private Medico medico;
    private Laboratorio laboratorio;
    private Farmacia farmacia;
    private Enfermaria enfermaria;
    private final List<String> historico = new ArrayList<>();

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public void setEnfermaria(Enfermaria enfermaria) {
        this.enfermaria = enfermaria;
    }

    public List<String> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    @Override
    public void notificar(Componente remetente, Evento evento, String dado) {
        historico.add(remetente.getNome() + " -> " + evento + ": " + dado);

        switch (evento) {
            case EXAME_SOLICITADO ->
                    destinatario(laboratorio, "Laboratório").receber("Exame solicitado: " + dado);
            case RESULTADO_LIBERADO ->
                    destinatario(medico, "Médico").receber("Resultado liberado: " + dado);
            case PRESCRICAO -> {
                destinatario(farmacia, "Farmácia").receber("Prescrição recebida: " + dado);
                destinatario(enfermaria, "Enfermaria").receber("Prescrição a administrar: " + dado);
            }
            case MEDICAMENTO_DISPENSADO ->
                    destinatario(enfermaria, "Enfermaria").receber("Medicamento disponível: " + dado);
            case MEDICAMENTO_ADMINISTRADO ->
                    destinatario(medico, "Médico").receber("Medicamento administrado: " + dado);
        }
    }

    private Componente destinatario(Componente componente, String descricao) {
        if (componente == null) {
            throw new IllegalStateException(descricao + " não registrado");
        }
        return componente;
    }
}
