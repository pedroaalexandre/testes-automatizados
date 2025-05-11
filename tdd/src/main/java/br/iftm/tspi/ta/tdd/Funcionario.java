package br.iftm.tspi.ta.tdd;

public class Funcionario {
    private String nome;
    private int horasTrabalhadas;
    private double valorHora;

    public Funcionario() {
    }

    public Funcionario(String nome, int horasTrabalhadas, double valorHora) {
        this.nome = nome;
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorHora = valorHora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    public double calcularPagamento() {
        double pagamento = (horasTrabalhadas * valorHora) * 4;
        if(pagamento >= 1518.0 && pagamento <= 15000.0) {
            return pagamento;
        }else if(pagamento > 15000){
            throw new IllegalArgumentException("O pagamento não pode ultrapassar R$ 15.000,00");
        }else{
            throw new IllegalArgumentException("O pagamento não pode ser menor que 1 salário mínimo");
        }
    }

    public int validaHorasTrabalhadas(int horasTrabalhadas) {
        if(horasTrabalhadas >= 20 && horasTrabalhadas <= 40) {
            return horasTrabalhadas;
        }else{
            throw new IllegalArgumentException("O funcionário deve trabalhar entre 20 e 40 horas.");
        }
    }

    public double validarValorHora (double valorHora) {
        if(valorHora >= 1518 * 0.01 && valorHora <= 1518*0.1) {
            return valorHora;
        }else{
            throw new IllegalArgumentException("O valor da hora trabalhada não pode ser inferior a 1% e nem superior a 10% do salário mínimo");
        }
    }
}
