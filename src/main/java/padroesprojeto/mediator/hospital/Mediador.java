package padroesprojeto.mediator.hospital;

public interface Mediador {

    void notificar(Componente remetente, Evento evento, String dado);
}
