import entidades.calculadora;
import entidades.painelDesenho;
import entidades.projetil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class telaPrincipal extends JFrame {

   // Componentes de Input
   private JTextField txtX0, txtY0, txtXFinal, txtYFinal, txtAngulo;
   // Componentes de Output
   private JTextField txtV0, txtV0x, txtV0y, txtHMax, txtTempo;
   // Painel de Animação (Customizado)
   private JPanel painelAnimacao;
   private JPanel painelSuperior;
   // Botão de Ação
   private JButton btnResetar; 
   private JButton btnMostrar;
   //Classes
   private painelDesenho painelDesenho;
   private projetil bola;
   // Timer para animação
   private Timer timer;
   private double tempoPassado;

   public telaPrincipal() throws NumberFormatException {
      setResizable(false);
      setTitle("Simulador de Lançamento Oblíquo");
      setSize(1000, 700);
      setMinimumSize(new Dimension(1000, 700));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setLayout(new BorderLayout(10, 10));
      
      JPanel painelNorte = new JPanel(new BorderLayout());

      // Painel Superior (Inputs e Outputs)
      painelSuperior = new JPanel(new GridLayout(1, 2, 10, 10));
      painelSuperior.add(criarPainelInputs());
      painelSuperior.add(criarPainelOutputs());      
      add(painelSuperior, BorderLayout.NORTH);
      
      btnMostrar = new JButton("Mostrar inputs");
      btnMostrar.setVisible(false);
      btnMostrar.setFocusPainted(false);
      btnMostrar.setBackground(new Color(240, 240, 240));
      btnMostrar.addActionListener(e -> {
         painelSuperior.setVisible(true);
         btnMostrar.setVisible(false);
         revalidate();
         repaint();
      });

      painelNorte.add(painelSuperior, BorderLayout.CENTER);
      painelNorte.add(btnMostrar, BorderLayout.SOUTH);
      add(painelNorte, BorderLayout.NORTH);

      // Painel Central (Animação)
      painelAnimacao = new JPanel();
      painelAnimacao.setBackground(new Color(135, 206, 250));
      painelAnimacao.setBorder(new TitledBorder("ANIMAÇÃO"));      
      add(painelAnimacao, BorderLayout.CENTER);
      
      painelDesenho = new painelDesenho();
      add(painelDesenho, BorderLayout.CENTER);
      
      // Botão de Ação (Sul)
      JButton btnLancar = new JButton("Calcular e Lançar");
      btnLancar.setFont(new Font("Arial", Font.BOLD, 14));
      btnLancar.addActionListener(e -> iniciarAnimacao());
      add(btnLancar, BorderLayout.SOUTH);
   }

   private JPanel criarPainelInputs() {
      JPanel painel = new JPanel(new GridBagLayout());
      painel.setBorder(new TitledBorder("Inputs"));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      // Linha 0: x0 e y0
      gbc.gridx = 0; gbc.gridy = 0; painel.add(new JLabel("x0:"), gbc);
      txtX0 = new JTextField(10);
      gbc.gridx = 1; painel.add(txtX0, gbc);

      gbc.gridx = 2; painel.add(new JLabel("y0:"), gbc);
      txtY0 = new JTextField(10);
      gbc.gridx = 3; painel.add(txtY0, gbc);

      // Linha 1: X e Y
      gbc.gridx = 0; gbc.gridy = 1; painel.add(new JLabel("X final:"), gbc);
      txtXFinal = new JTextField(10);
      gbc.gridx = 1; painel.add(txtXFinal, gbc);

      gbc.gridx = 2; painel.add(new JLabel("Y final:"), gbc);
      txtYFinal = new JTextField(10);
      gbc.gridx = 3; painel.add(txtYFinal, gbc);

      // Linha 2: Ângulo
      gbc.gridx = 0; gbc.gridy = 2; painel.add(new JLabel("Ângulo:"), gbc);
      txtAngulo = new JTextField(10);
      gbc.gridx = 1; painel.add(txtAngulo, gbc);

      return painel;
   }

   private JPanel criarPainelOutputs() {
      JPanel painel = new JPanel(new GridBagLayout());
      painel.setBorder(new TitledBorder("Outputs"));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      // Coluna 1
      gbc.gridx = 0; gbc.gridy = 0; painel.add(new JLabel("v0:"), gbc);
      txtV0 = criarCampoOutput(painel, gbc, 1, 0);

      gbc.gridx = 0; gbc.gridy = 1; painel.add(new JLabel("v0x:"), gbc);
      txtV0x = criarCampoOutput(painel, gbc, 1, 1);

      gbc.gridx = 0; gbc.gridy = 2; painel.add(new JLabel("v0y:"), gbc);
      txtV0y = criarCampoOutput(painel, gbc, 1, 2);

      // Coluna 2
      gbc.gridx = 2; gbc.gridy = 0; painel.add(new JLabel("Altura Máxima:"), gbc);
      txtHMax = criarCampoOutput(painel, gbc, 3, 0);

      gbc.gridx = 2; gbc.gridy = 1; painel.add(new JLabel("Tempo total:"), gbc);
      txtTempo = criarCampoOutput(painel, gbc, 3, 1);

      btnResetar = new JButton("Resetar");
      gbc.gridx = 2; 
      gbc.gridy = 2;
      gbc.gridwidth = 2;
      gbc.anchor = GridBagConstraints.EAST; 
      painel.add(btnResetar, gbc);

      btnResetar.addActionListener(e -> limparTudo());

      return painel;
   }

   // Limpa completamente todos os campos 
   private void limparTudo() {
      // Limpa os campos de input
      txtX0.setText("");
      txtY0.setText("");
      txtXFinal.setText("");
      txtYFinal.setText("");
      txtAngulo.setText("");

      // Limpa os campos de output
      txtV0.setText("");
      txtV0x.setText("");
      txtV0y.setText("");
      txtHMax.setText("");
      txtTempo.setText("");

      painelDesenho.configurarDesenho(null, null);
   }

   private JTextField criarCampoOutput(JPanel p, GridBagConstraints gbc, int x, int y) {
      JTextField tf = new JTextField(10);
      tf.setEditable(false);
      tf.setBackground(new Color(240, 240, 240));
      gbc.gridx = x; 
      gbc.gridy = y;
      p.add(tf, gbc);
      return tf;
   }

   private void iniciarAnimacao() {
      try {
         if (timer != null && timer.isRunning())
            timer.stop();

         painelSuperior.setVisible(false);
         btnMostrar.setVisible(true);

         revalidate();
         repaint();

         //Lê os inputs
         double x0 = Double.parseDouble(txtX0.getText().replace(',', '.'));
         double y0 = Double.parseDouble(txtY0.getText().replace(',', '.'));
         double xFinal = Double.parseDouble(txtXFinal.getText().replace(',', '.'));
         double yFinal = Double.parseDouble(txtYFinal.getText().replace(',', '.'));
         double angulo = Double.parseDouble(txtAngulo.getText().replace(',', '.'));
        
         double limiteX = painelDesenho.getWidth() / 20.0; // Limite para o projétil não sair da tela
         double limiteY = (painelDesenho.getHeight() - 50) / 20.0; // Limite para o projétil não sair da tela
         
         calculadora calc = new calculadora(x0, y0, xFinal, yFinal, angulo);
         
         if(xFinal > limiteX)
            throw new IllegalArgumentException("A posição final ultrapassa os limites da tela. Por favor, insira valores menores para X e Y finais.");
         calc.calcularTudo();

         if(calc.getAlturaMaxima() > limiteY)
            throw new IllegalArgumentException("A altura máxima ultrapassa os limites da tela. Por favor, insira valores menores para X e Y finais.");

         //Preenche os outputs
         txtV0.setText(String.format("%.2f", calc.getVelocidadeInicial()));
         txtV0x.setText(String.format("%.2f", calc.getVelocidadeInicialX()));
         txtV0y.setText(String.format("%.2f", calc.getVelocidadeInicialY()));
         txtHMax.setText(String.format("%.2f", calc.getAlturaMaxima()));
         txtTempo.setText(String.format("%.2f", calc.getTempoTotal()));

         bola = new projetil(x0, y0, calc.getVelocidadeInicialX(), calc.getVelocidadeInicialY());
         tempoPassado = 0.0;
         // Configura o timer para atualizar a posição do projétil a cada 20 ms
         timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               tempoPassado += 0.02; // Incrementa o tempo 
               
               if(tempoPassado > calc.getTempoTotal()) {
                  tempoPassado = calc.getTempoTotal(); // Garante que o tempo não ultrapasse o total
                  timer.stop();
               }

               // Atualiza a posição do projétil usando as fórmulas de movimento
               double novaPosicaoX = x0 + (calc.getVelocidadeInicialX() * tempoPassado);
               double novaPosicaoY = y0 + (calc.getVelocidadeInicialY() * tempoPassado) - (0.5 * 9.81 * Math.pow(tempoPassado, 2));

               if(novaPosicaoY < 0)
                  novaPosicaoY = 0; // Garante que o projétil não vá abaixo do chão
               
               // Atualiza a posição do projétil
               bola.setPosicaoX(novaPosicaoX);
               bola.setPosicaoY(novaPosicaoY);
               
               // Reconfigura o desenho para atualizar a posição do projétil
               painelDesenho.configurarDesenho(bola, calc);
            }
         });

         timer.start();
      } 
      catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, 
            "Por favor, insira valores numéricos válidos em todos os campos.",
            "Erro de Formato", 
            JOptionPane.ERROR_MESSAGE);
      }
      catch (IllegalArgumentException ex) {
         JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Lançamento", JOptionPane.ERROR_MESSAGE);
      }
   }
}
