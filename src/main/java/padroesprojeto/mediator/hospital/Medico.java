package padroesprojeto.mediator.hospital;

public class Medico extends Componente {

    public Medico(Mediador mediador, String nome) {
        super(mediador, nome);
    }

    public void solicitarExame(String paciente, String exame) {
        mediador.notificar(this, Evento.EXAME_SOLICITADO, paciente + " - " + exame);
    }

    public void prescrever(String paciente, String medicamento) {
        mediador.notificar(this, Evento.PRESCRICAO, paciente + " - " + medicamento);
    }
}
