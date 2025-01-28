public class NoRubroNegro {
    int valor;
    NoRubroNegro esq, dir, pai;
    boolean cor; // true para vermelho, false para preto

    NoRubroNegro(int valor) {
        this.valor = valor;
        this.esq = null;
        this.dir = null;
        this.pai = null;
        this.cor = true; // novo nó é vermelho por padrão
    }
}
