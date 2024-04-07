
public class Aluno {

    private int rgm;//só a mesca classe ve
    private String nome_aluno;
    private double notaA1;
    private double notaA2;

    public int getRgm() {  //metodo que retorna dados
        return this.rgm;
    }

    public void setRgm(int numrgm) {
        this.rgm = numrgm;
    }

    public String getNome_aluno() {  //metodo que retorna dados
        return this.nome_aluno;
    }

    public void setNome_Aluno(String nomeAluno) {
        this.nome_aluno = nomeAluno;

    }

    public double getnotaA1() {
        return this.notaA1;
    }

    public void setnotaA1(double nA1) {

        this.notaA1 = nA1;
    }

    public double getnotaA2() {
        return this.notaA2;
    }

    public void setnotaA2(double nA2) {

        this.notaA2 = nA2;
    }
}
