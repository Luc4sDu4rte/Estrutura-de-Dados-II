public class Main {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        System.out.println("Inserindo 50:");
        tree.inserir(50);
        tree.printTree();

        System.out.println("Inserindo 1:");
        tree.inserir(1);
        tree.printTree();

        System.out.println("Inserindo 10:");
        tree.inserir(10);
        tree.printTree();

        System.out.println("Inserindo 55:");
        tree.inserir(55);
        tree.printTree();

        System.out.println("Removendo 55:");
        tree.apagar(55);
        tree.printTree();
    }
}
