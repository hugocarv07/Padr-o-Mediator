package padroesprojeto.mediator.hospital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Componente {

    protected final Mediador mediador;
    private final String nome;
    private final List<String> mensagens = new ArrayList<>();

    public Componente(Mediador mediador, String nome) {
        this.mediador = mediador;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void receber(String mensagem) {
        mensagens.add(mensagem);
    }

    public List<String> getMensagens() {
        return Collections.unmodifiableList(mensagens);
    }
}
