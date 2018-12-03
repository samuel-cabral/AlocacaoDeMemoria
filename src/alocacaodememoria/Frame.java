package alocacaodememoria;

import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class Frame {
    int posicaoInicio, posicaoFim, tamanhoFisico, tamanho;
    boolean frameOcupado;

    public Frame(int posicaoInicio, int posicaoFim, int tamanho, boolean frameOcupado) {
        this.posicaoInicio = posicaoInicio;
        this.posicaoFim = posicaoFim;
        this.tamanhoFisico = posicaoFim - posicaoInicio;
        this.tamanho = tamanho;
        this.frameOcupado = frameOcupado;
    }
    private static final Logger LOG = Logger.getLogger(Frame.class.getName());
    
    
    
    
}
