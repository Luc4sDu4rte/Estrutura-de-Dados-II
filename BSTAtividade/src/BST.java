import java.util.Stack;

public class BST {
    private No raiz;

    public BST() {
        this.raiz = null;
    }

    public No getRaiz() {
        return this.raiz;
    }

    private void setRaiz(No raiz){
        this.raiz = raiz;
    }

    public void inserir(int valor) {
        if (this.raiz == null) {
            this.raiz = new No(valor);
        } else {
            inserir(this.raiz, valor);
        }
    }

    private No inserir(No raiz, int valor) {
        if (raiz == null) {
            return new No(valor);
        }
        if (valor < raiz.valor){
            raiz.esq = inserir(raiz.esq, valor);

        } else if (valor > raiz.valor){
            raiz.dir = inserir(raiz.dir, valor);

        } else{
            return raiz;
        }
        atualizarAltura(raiz);
        return rebalancear(raiz);
    }

    public boolean isFolha(No no) {
        if (no.esq == null && no.dir == null) {
            return true;
        }
        return false;
    }

    public boolean buscar(int valor) {
        return buscar(this.raiz, valor);
    }

    // Recebe como parâmetro o raiz e o valor a ser procurado
    private boolean buscar(No raiz, int valor) {
        // Condição de parada: se a raiz for null, retorna false
        if (raiz == null)
            return false;
        // Se o valor da raiz for o valor procurado, retorna true
        if (raiz.valor == valor)
            return true;
        // Se o valor for menor que a raiz, busca na sub-arvore da esquerda
        if (valor < raiz.valor)
            return buscar(raiz.esq, valor);
        // Se o valor for maior que a raiz, busca na sub-arvore da direta
        else
            return buscar(raiz.dir, valor);
        }
 
    public void apagar(int valor) {
        raiz = apagar(this.raiz, valor);
    }

    // O método apagar procura um determinado elemento na árvore e o apaga da árvore
    private No apagar(No raiz, int valor) {
        if (raiz == null) {
            return null;
        }
        if (valor < raiz.valor)
            raiz.esq = apagar(raiz.esq, valor);
        else if (valor > raiz.valor)
            raiz.dir = apagar(raiz.dir, valor);
        else {
            if (raiz.esq == null && raiz.dir == null) {
                return null;
            } else if (raiz.esq == null || raiz.dir == null) {
                if (raiz.esq != null) {
                    raiz = raiz.esq;
                } else {
                    raiz = raiz.dir;
                }
            } else {
                No tempNo = min(raiz.dir);
                raiz.valor = tempNo.valor;
                raiz.dir = apagar(raiz.dir, tempNo.valor);
            }
        }
        atualizarAltura(raiz);
        return rebalancear(raiz);
    }

    // Encontrar o menor valor da árvore
    private No min(No raiz) {
        // Se a raiz for nula, retorna a raiz (condição parada)
        if (raiz == null)
            return raiz;
        // Enquanto a sub-arvore da esquerda não for nula
        // Busca o maior elemento na sub-arvore da esquerda
        if (raiz.esq != null) {
            return min(raiz.esq);
        }
        return raiz;
        }
    
    private No max(No raiz) {
        if (raiz == null)
            return raiz;

        if (raiz.dir != null) {
            return max(raiz.dir);
        }

        return raiz;
        }        
    
    public int getFatorBalanceamento(No no) {
        if (no == null)
            return 0;
        return altura(no.esq) - altura(no.dir);
    }

    public int altura(No no) {
        if (no == null)
            return 0;
        return no.alt;
    }

    public void atualizarAltura(No no) {
        no.alt = 1 + Math.max(altura(no.esq), altura(no.dir));
    }

    public int nivelElemento(int valor) {
        return nivelElemento(this.raiz, valor, 0);
    }

    private int nivelElemento(No raiz, int valor, int nivel) {
        if (raiz == null)
            return -1;

        if (raiz.valor == valor)
            return nivel;

        if (valor < raiz.valor) {
            return nivelElemento(raiz.esq, valor, nivel + 1);
        } else {
            return nivelElemento(raiz.dir, valor, nivel + 1);
        }

    }

    public int getQtdNos() {
        return this.getQtdNos(this.raiz);
    }

    private int getQtdNos(No raiz) {
        if (raiz == null) {
            return 0;
        }

        return 1 + getQtdNos(raiz.esq) + getQtdNos(raiz.dir);
    }

    public int getQtdFolhas() {
        return this.getQtdFolhas(this.raiz);
    }

    private int getQtdFolhas(No raiz) {
        if (raiz == null) {
            return 0;
        }

        if (isFolha(raiz))
            return 1;
        else
            return getQtdFolhas(raiz.esq) + getQtdFolhas(raiz.dir);
    }

    //Rotações
    public No rotacaoDireita(No A) {
        No B = A.esq;
        No Bdir = B.dir;

        B.dir = A;
        A.esq = Bdir;

        atualizarAltura(A);
        atualizarAltura(B);

        return B;
    }

    public No rotacaoEsquerda(No A) {
        No B = A.dir;
        No Besq = B.esq;

        B.esq = A;
        A.dir = Besq;

        atualizarAltura(A);
        atualizarAltura(B);

        return B;
    }

    private No rebalancear(No no) {
        int fatorBalanceamento = getFatorBalanceamento(no);

        if (fatorBalanceamento > 1 && getFatorBalanceamento(no.esq) >= 0) {
            return rotacaoDireita(no);
        }

        if (fatorBalanceamento > 1 && getFatorBalanceamento(no.esq) < 0) {
            no.esq = rotacaoEsquerda(no.esq);
            return rotacaoDireita(no);
        }

        if (fatorBalanceamento < -1 && getFatorBalanceamento(no.dir) <= 0) {
            return rotacaoEsquerda(no);
        }

        if (fatorBalanceamento < -1 && getFatorBalanceamento(no.dir) > 0) {
            no.dir = rotacaoDireita(no.dir);
            return rotacaoEsquerda(no);
        }

        return no;
    }


    public void printTree() {
        Stack globalStack = new Stack();
        globalStack.push(raiz);
        int gaps = 32;
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) {
                No temp = (No) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.valor);
                    localStack.push(temp.esq);
                    localStack.push(temp.dir);
                    if (temp.esq != null ||
                            temp.dir != null)
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
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        }
        System.out.println(separator);
    }
}
