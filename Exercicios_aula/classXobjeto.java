public class classXobjeto {
    public void main(String[] args) {

        //Criando um objeto a partir da classe
        Professor p1 = new Professor();
        System.out.println(p1.nome);
        System.out.println(p1.drt);
        System.out.println(p1.contratacao);
        System.out.println(p1.status);
        System.out.println(p1.disciplina);

        /* Envio de parrametros:
         * Professor p1 = new Professor("Claudio", "123456", "17/08/1990", "Titular", "Matem´atica", 16);
            System.out.println(p1.nome);
            System.out.println(p1.drt);
            System.out.println(p1.contratacao);
            System.out.println(p1.status);
            System.out.println(p1.disciplina);
        */
        
    }
    
    //Definindo classe e criando atributos fixos
    class Professor {
        String nome = "Claudio";
        String drt = "123456";
        String contratacao = "17/08/1990";
        String status = "Titular";
        String disciplina = "Matem´atica";
        Integer cargaMaxima = 16;


        // Get = método para consultar o valor de um atributo
        // Set = método para alterar o valor de um atributo
    }

    //Classe com envio de parametros
    /*
     * class Professor {
            String nome;
            String drt;
            String contratacao;
            String status;
            String disciplina;
            Integer cargaMaxima;
            public Professor( String nome,
                String drt,
                String contratacao,
                String status,
                String disciplina,
                Integer cargaMaxima ) {
            this.nome = nome;
            this.drt = drt;
            this.contratacao = contratacao;
            this.status = status;
            this.disciplina = disciplina;
            this.cargaMaxima = cargaMaxima;
            }
        }
    */
}