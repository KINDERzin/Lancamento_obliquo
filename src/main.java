
import javax.swing.JFrame;
import javax.swing.JLabel;


public class main {

	public static void main(String[] args) {
		JFrame janela = new JFrame("Graficos e representacao de dados");
		// Desativa o layout padrão da janela
		janela.setLayout(null);

		// Fecha a janela quando o usuário clicar no "X"
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Seta a visibilidade da janela para true
		janela.setVisible(true);
		// Define o tamanho da janela
		janela.setSize(400, 400);
		// Centraliza a janela na tela
		janela.setLocationRelativeTo(null);
		
		JLabel labelInicio = new JLabel("Olá usuário!");
		janela.add(labelInicio);

		labelInicio.setVisible(true);
		labelInicio.setBounds(150, 0, 100, 30);
	}
}
