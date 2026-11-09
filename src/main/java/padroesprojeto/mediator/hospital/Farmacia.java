package padroesprojeto.mediator.hospital;

public class Farmacia extends Componente {

    public Farmacia(Mediador mediador, String nome) {
        super(mediador, nome);
    }

    public void dispensar(String paciente, String medicamento) {
        mediador.notificar(this, Evento.MEDICAMENTO_DISPENSADO, paciente + " - " + medicamento);
    }
}
