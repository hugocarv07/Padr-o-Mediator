package padroesprojeto.mediator.hospital;

public class Enfermaria extends Componente {

    public Enfermaria(Mediador mediador, String nome) {
        super(mediador, nome);
    }

    public void registrarAdministracao(String paciente, String medicamento) {
        mediador.notificar(this, Evento.MEDICAMENTO_ADMINISTRADO, paciente + " - " + medicamento);
    }
}
