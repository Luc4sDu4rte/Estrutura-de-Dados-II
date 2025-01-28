import java.util.Stack;

public class RedBlackTree {
    private NoRubroNegro raiz;

    public RedBlackTree() {
        this.raiz = null;
    }

    public NoRubroNegro getRaiz() {
        return this.raiz;
    }

    public void inserir(int valor) {
        NoRubroNegro novoNo = new NoRubroNegro(valor);
        raiz = inserirRec(raiz, novoNo);
        ajustarInsercao(novoNo);
    }

    private NoRubroNegro inserirRec(NoRubroNegro atual, NoRubroNegro novo) {
        if (atual == null) {
            return novo;
        }
        if (novo.valor < atual.valor) {
            atual.esq = inserirRec(atual.esq, novo);
            atual.esq.pai = atual;
        } else if (novo.valor > atual.valor) {
            atual.dir = inserirRec(atual.dir, novo);
            atual.dir.pai = atual;
        }
        return atual;
    }

    private void ajustarInsercao(NoRubroNegro no) {
        while (no != null && no != raiz && no.pai.cor == true) {
            NoRubroNegro avo = no.pai.pai;
            if (no.pai == avo.esq) {
                NoRubroNegro tio = avo.dir;
                if (tio != null && tio.cor == true) {
                    no.pai.cor = false;
                    tio.cor = false;
                    avo.cor = true;
                    no = avo;
                } else {
                    if (no == no.pai.dir) {
                        no = no.pai;
                        rotacaoEsquerda(no);
                    }
                    no.pai.cor = false;
                    avo.cor = true;
                    rotacaoDireita(avo);
                }
            } else {
                NoRubroNegro tio = avo.esq;
                if (tio != null && tio.cor == true) {
                    no.pai.cor = false;
                    tio.cor = false;
                    avo.cor = true;
                    no = avo;
                } else {
                    if (no == no.pai.esq) {
                        no = no.pai;
                        rotacaoDireita(no);
                    }
                    no.pai.cor = false;
                    avo.cor = true;
                    rotacaoEsquerda(avo);
                }
            }
        }
        raiz.cor = false; // A raiz sempre deve ser preta
    }

    public void apagar(int valor) {
        NoRubroNegro no = buscarNo(raiz, valor);
        if (no == null) return;
        apagarNo(no);
    }

    private NoRubroNegro buscarNo(NoRubroNegro no, int valor) {
        if (no == null || no.valor == valor) {
            return no;
        }
        if (valor < no.valor) {
            return buscarNo(no.esq, valor);
        }
        return buscarNo(no.dir, valor);
    }

    private void apagarNo(NoRubroNegro no) {
        NoRubroNegro substituto = no;
        NoRubroNegro filho = null;
        boolean corOriginal = substituto.cor;

        if (no.esq == null) {
            filho = no.dir;
            substituirNo(no, no.dir);
        } else if (no.dir == null) {
            filho = no.esq;
            substituirNo(no, no.esq);
        } else {
            substituto = min(no.dir);
            corOriginal = substituto.cor;
            filho = substituto.dir;

            if (substituto.pai == no) {
                if (filho != null) {
                    filho.pai = substituto;
                }
            } else {
                substituirNo(substituto, substituto.dir);
                substituto.dir = no.dir;
                if (substituto.dir != null) {
                    substituto.dir.pai = substituto;
                }
            }

            substituirNo(no, substituto);
            substituto.esq = no.esq;
            if (substituto.esq != null) {
                substituto.esq.pai = substituto;
            }
            substituto.cor = no.cor;
        }

        if (corOriginal == false) {
            ajustarRemocao(filho);
        }
    }

    private void ajustarRemocao(NoRubroNegro no) {
        while (no != raiz && (no == null || no.cor == false)) {
            if (no == no.pai.esq) {
                NoRubroNegro irmao = no.pai.dir;

                if (irmao != null && irmao.cor == true) {
                    irmao.cor = false;
                    no.pai.cor = true;
                    rotacaoEsquerda(no.pai);
                    irmao = no.pai.dir;
                }

                if ((irmao.esq == null || irmao.esq.cor == false) &&
                    (irmao.dir == null || irmao.dir.cor == false)) {
                    if (irmao != null) irmao.cor = true;
                    no = no.pai;
                } else {
                    if (irmao.dir == null || irmao.dir.cor == false) {
                        if (irmao.esq != null) irmao.esq.cor = false;
                        if (irmao != null) irmao.cor = true;
                        rotacaoDireita(irmao);
                        irmao = no.pai.dir;
                    }

                    if (irmao != null) irmao.cor = no.pai.cor;
                    no.pai.cor = false;
                    if (irmao.dir != null) irmao.dir.cor = false;
                    rotacaoEsquerda(no.pai);
                    no = raiz;
                }
            } else {
                NoRubroNegro irmao = no.pai.esq;

                if (irmao != null && irmao.cor == true) {
                    irmao.cor = false;
                    no.pai.cor = true;
                    rotacaoDireita(no.pai);
                    irmao = no.pai.esq;
                }

                if ((irmao.esq == null || irmao.esq.cor == false) &&
                    (irmao.dir == null || irmao.dir.cor == false)) {
                    if (irmao != null) irmao.cor = true;
                    no = no.pai;
                } else {
                    if (irmao.esq == null || irmao.esq.cor == false) {
                        if (irmao.dir != null) irmao.dir.cor = false;
                        if (irmao != null) irmao.cor = true;
                        rotacaoEsquerda(irmao);
                        irmao = no.pai.esq;
                    }

                    if (irmao != null) irmao.cor = no.pai.cor;
                    no.pai.cor = false;
                    if (irmao.esq != null) irmao.esq.cor = false;
                    rotacaoDireita(no.pai);
                    no = raiz;
                }
            }
        }
        if (no != null) no.cor = false;
    }

    private void substituirNo(NoRubroNegro antigo, NoRubroNegro novo) {
        if (antigo.pai == null) {
            raiz = novo;
        } else if (antigo == antigo.pai.esq) {
            antigo.pai.esq = novo;
        } else {
            antigo.pai.dir = novo;
        }
        if (novo != null) {
            novo.pai = antigo.pai;
        }
    }

    private NoRubroNegro min(NoRubroNegro no) {
        while (no.esq != null) {
            no = no.esq;
        }
        return no;
    }

    private void rotacaoEsquerda(NoRubroNegro no) {
        NoRubroNegro dir = no.dir;
        no.dir = dir.esq;
        if (dir.esq != null) {
            dir.esq.pai = no;
        }
        dir.pai = no.pai;
        if (no.pai == null) {
            raiz = dir;
        } else if (no == no.pai.esq) {
            no.pai.esq = dir;
        } else {
            no.pai.dir = dir;
        }
        dir.esq = no;
        no.pai = dir;
    }

    private void rotacaoDireita(NoRubroNegro no) {
        NoRubroNegro esq = no.esq;
        no.esq = esq.dir;
        if (esq.dir != null) {
            esq.dir.pai = no;
        }
        esq.pai = no.pai;
        if (no.pai == null) {
            raiz = esq;
        } else if (no == no.pai.dir) {
            no.pai.dir = esq;
        } else {
            no.pai.esq = esq;
        }
        esq.dir = no;
        no.pai = esq;
    }

    public void printTree() {
    if (raiz == null) {
        System.out.println("Árvore vazia");
        return;
    }

    Stack<NoRubroNegro> globalStack = new Stack<>();
    globalStack.push(raiz);
    int gaps = 32; // Espaçamento inicial entre os nós
    boolean isRowEmpty = false;
    String separator = "-------------------------------------------------";
    System.out.println(separator);

    while (!isRowEmpty) {
        Stack<NoRubroNegro> localStack = new Stack<>();
        isRowEmpty = true;

        for (int j = 0; j < gaps; j++)
            System.out.print(' ');

        while (!globalStack.isEmpty()) {
            NoRubroNegro temp = globalStack.pop();
            if (temp != null) {
                System.out.print(temp.valor + (temp.cor ? "V" : "P")); // Adiciona "V" ou "P" indicando a cor
                localStack.push(temp.esq);
                localStack.push(temp.dir);

                if (temp.esq != null || temp.dir != null)
                    isRowEmpty = false;
            } else {
                System.out.print("__");
                localStack.push(null);
                localStack.push(null);
            }

            for (int j = 0; j < gaps * 2 - 2; j++)
                System.out.print(' ');
        }

        System.out.println();
        gaps /= 2;

        while (!localStack.isEmpty())
            globalStack.push(localStack.pop());
    }

    System.out.println(separator);
}
    
}
