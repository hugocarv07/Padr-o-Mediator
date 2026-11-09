package padroesprojeto.mediator.hospital;

public class Laboratorio extends Componente {

    public Laboratorio(Mediador mediador, String nome) {
        super(mediador, nome);
    }

    public void liberarResultado(String paciente, String exame, String resultado) {
        mediador.notificar(this, Evento.RESULTADO_LIBERADO, paciente + " - " + exame + ": " + resultado);
    }
}
