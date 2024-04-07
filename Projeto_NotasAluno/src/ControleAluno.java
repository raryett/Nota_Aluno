
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ControleAluno {

    Aluno aluno = new Aluno();//declaraçao de objeto 

    java.sql.ResultSet rsRegistro; //objeto com caracteristica receber um conjunto de dados atraves da interface resultset.

    private Statement comando = null;
    private Connection conexao = null;

    public void Conexao() { //criando o metodo

        try {
            //definiçao de variaveis necessarias para conexao ao mysql
            //banco de dados
            String servername = "localhost:3306"; //conexao local com a porta 3306 depende da porta do xampp
            String namebd = "notasaluno";
            //usuario e senha do banco
            String username = "root";
            String password = "";
            //especifica o driver utilizado
            String drivername = "com.mysql.cj.jdbc.Driver"; //precisa ser especificado apenas uma vez, procurar o driver instalado
            Class.forName(drivername);

            //criando a conexao com o banco de dados, instruçao sql + variavel poderia ser a instruçao com todos os dados sem ter que declarar a variavel
            String url = "jdbc:mysql://" + servername + "/" + namebd + "?useTimezone=true&serverTimezone=UTC"; //timezone para nao ter erro com o fuso horario
            conexao = DriverManager.getConnection(url, username, password);
            comando = conexao.createStatement();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver nao encontrado: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro de conexao ao banco: " + e);
        }

    }

    public void Cadastrar(int nrgm, String nm, double n1, double n2) { //classe cadastrar
        aluno.setRgm(nrgm);
        aluno.setNome_Aluno(nm);
        aluno.setnotaA1(n1);
        aluno.setnotaA2(n2);

        PreparedStatement sql;//chamar interface

        try {
            sql = conexao.prepareStatement("Insert into aluno(rgm, nome, notaA1, notaA2) values(?, ?, ?, ?)");//instruçao em sql para inserir o registro
            sql.setInt(1, aluno.getRgm()); //ele vai ser o primeiro? 
            sql.setString(2, aluno.getNome_aluno());
            sql.setDouble(3, aluno.getnotaA1());
            sql.setDouble(4, aluno.getnotaA2());
            int reg = sql.executeUpdate();//resposta para saber se conseguiu inserir uma linha ao banco de dados ou nao
            if (reg != 0) {
                JOptionPane.showMessageDialog(null, "registro cadastrado com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "erro ao cadastrar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de conexao com o banco nome do erro: " + e);
        }

    }

    public String Consultar(int nrgm) {
        String dados = "";
        PreparedStatement sql;
        if (nrgm != 0) {

            try {//vai procurar a conexao com o banco de dados
                sql = conexao.prepareStatement("select * from aluno where rgm =? ");
                sql.setInt(1, nrgm);
                rsRegistro = sql.executeQuery();//lista interna que recebe um conjunto de dados
                while (rsRegistro.next()) { //verifica se ha um proximo elemento faça.
                    dados = rsRegistro.getString("nome") + ";"
                            + rsRegistro.getString("notaA1") + ";"
                            + rsRegistro.getString("notaA2");

                }

            } catch (Exception e) { //caso de algum erro de conexao : vem pra ca
                JOptionPane.showMessageDialog(null, "Erro de conexao ao banco, erro: " + e); //null desabilita uma parte do comando que nao quer utilizar
            }
        }
        return dados;
    }

    
    
    
    public void Alterar(int numrgm, String nm, double n1, double n2) {
        PreparedStatement sql;
        try {
                sql = conexao.prepareStatement("update aluno set nome=?, notaA1=?, notaA2=? where rgm=?");
                sql.setString(1, nm);
                sql.setDouble(2, n1);
                sql.setDouble(3, n2);
                sql.setInt(4, numrgm);

                int verifica = sql.executeUpdate();
                if (verifica != 0) {
                    JOptionPane.showMessageDialog(null, "Registro alterado com sucesso !!!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro !!!");
                }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão ao banco !!!");
        }

    }
    
    
    
    
    /*public void Alterar(int numrgm, String nm, double n1, double n2) {
        PreparedStatement sql;
        try {
            
                sql = conexao.prepareStatement("update aluno set nome='" + nm + "',"
                        + "notaA1='" + n1 + "'," + "notaA2='" + n2 + "'");
                int verifica = sql.executeUpdate();
                if (verifica != 0) {
                    JOptionPane.showMessageDialog(null, "Registro alterado com sucesso !!!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro !!!");
                }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão ao banco !!!");
        }

    }*/

    
    
    
    
    
    
    
    
    
    
    
    public void Excluir(int numrgm) {
        PreparedStatement sql;
        try {
            sql = conexao.prepareStatement("DELETE from aluno where rgm= ?");
            sql.setInt(1, numrgm);
            int verifica = sql.executeUpdate();
            String conf = JOptionPane.showInputDialog(null, "Excluir registro: S/N").toUpperCase();//transforma em maiusculo
            if (conf.equals("S")) {
                if (verifica != 0) {
                    JOptionPane.showMessageDialog(null, "Registro excluido com sucesso");
                }
            } else {
                JOptionPane.showMessageDialog(null, "erro ao excluir");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erro de acesso ao banco");

        }

    }
    
    
    public void Listar(){
        PreparedStatement sql;
        
        
        try{
             sql=conexao.prepareStatement("SELECT * from aluno");
             rsRegistro=sql.executeQuery();
             
             while(rsRegistro.next()){
                 System.out.print("RGM: "+rsRegistro.getString("rgm")+
                         " Nome: "+rsRegistro.getString("nome")+
                         " Nota A1: "+rsRegistro.getString("notaA1")+
                         " Nota A2: "+rsRegistro.getString("notaA2"));
                 
                 System.out.println();
             }
             
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "erro de acesso ao banco");
        }
       
        
    }
    
    
    
    public double CalculaMedia(double n1, double n2){
        double media=(n1+n2)/2;
        return media;
    }
    
    
    public String VerificaSituacao(double med){
        if(med>=7){
            return "Aprovado";
        }
        else{
            return "Reprovado";
        }
    
    }
    
    
    
    
    
    
    
    

}
