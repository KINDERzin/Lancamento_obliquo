import entidades.calculadora;
import entidades.painelDesenho;
import entidades.projetil;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;

public class telaPrincipal extends JFrame {

   private JTextField txtX0, txtY0, txtXFinal, txtYFinal, txtAngulo;
   private JTextField txtV0, txtV0x, txtV0y, txtHMax, txtTempo;
   private JPanel painelSuperior;
   private JButton btnResetar, btnMostrar, btnZoomIn, btnZoomOut;
   
   private painelDesenho painelDesenho;
   private projetil bola;
   private Timer timer;
   private double tempoPassado;

   private ImageIcon icon;

   public telaPrincipal() {
      setResizable(false);
      setTitle("Simulador de Lançamento Oblíquo com Zoom Inteligente");
      setSize(1000, 700);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setLayout(new BorderLayout(10, 10));
      icon = new ImageIcon("src/images/app_icon.jpg");
      setIconImage(icon.getImage());
      JPanel painelNorte = new JPanel(new BorderLayout());

      // Painel Superior (Inputs e Outputs)
      painelSuperior = new JPanel(new GridLayout(1, 2, 10, 10));
      painelSuperior.add(criarPainelInputs());
      painelSuperior.add(criarPainelOutputs());      
      
      btnMostrar = new JButton("Mostrar Configurações");
      btnMostrar.setVisible(false);
      btnMostrar.addActionListener(e -> {
         painelSuperior.setVisible(true);
         btnMostrar.setVisible(false);
         revalidate();
      });

      painelNorte.add(painelSuperior, BorderLayout.CENTER);
      painelNorte.add(btnMostrar, BorderLayout.SOUTH);
      add(painelNorte, BorderLayout.NORTH);

      // Painel Central (Desenho)
      painelDesenho = new painelDesenho();
      // Zoom via Scroll do Mouse
      painelDesenho.addMouseWheelListener(e -> {
         if(e.getWheelRotation() < 0)
            painelDesenho.setEscala(painelDesenho.getEscala() + 2.0);
         else
            painelDesenho.setEscala(painelDesenho.getEscala() - 2.0);
      });
      add(painelDesenho, BorderLayout.CENTER);

      // Painel Sul (Controles de Lançamento e Zoom)
      JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
      
      // Sub-painel para botões de Zoom
      JPanel painelZoom = new JPanel(new FlowLayout());
      painelZoom.setBorder(new TitledBorder("Zoom"));
      btnZoomIn = new JButton(" + ");
      btnZoomOut = new JButton(" - ");
      btnZoomIn.addActionListener(e -> painelDesenho.setEscala(painelDesenho.getEscala() + 5.0));
      btnZoomOut.addActionListener(e -> painelDesenho.setEscala(painelDesenho.getEscala() - 5.0));
      painelZoom.add(btnZoomOut);
      painelZoom.add(btnZoomIn);

      JButton btnLancar = new JButton("Calcular e Lançar");
      btnLancar.setFont(new Font("Arial", Font.BOLD, 14));
      btnLancar.setPreferredSize(new Dimension(200, 40));
      btnLancar.addActionListener(e -> iniciarAnimacao());

      painelSul.add(painelZoom);
      painelSul.add(btnLancar);
      add(painelSul, BorderLayout.SOUTH);
   }

   private JPanel criarPainelInputs() {
      JPanel painel = new JPanel(new GridBagLayout());
      painel.setBorder(new TitledBorder("Entradas (ft)"));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;
      
      // Adiciona os campos de input
      gbc.gridx = 0; gbc.gridy = 0; painel.add(new JLabel("x0:"), gbc);
      txtX0 = new JTextField("0", 10);
      gbc.gridx = 1; painel.add(txtX0, gbc);

      gbc.gridx = 2; gbc.gridy = 0; painel.add(new JLabel("y0:"), gbc);
      txtY0 = new JTextField("7", 10);
      gbc.gridx = 3; painel.add(txtY0, gbc);

      gbc.gridx = 0; gbc.gridy = 1; painel.add(new JLabel("X Alvo:"), gbc);
      txtXFinal = new JTextField("15", 10);
      gbc.gridx = 1; painel.add(txtXFinal, gbc);

      gbc.gridx = 2; gbc.gridy = 1; painel.add(new JLabel("Y Alvo:"), gbc);
      txtYFinal = new JTextField("10", 10);
      gbc.gridx = 3; painel.add(txtYFinal, gbc);

      gbc.gridx = 0; gbc.gridy = 2; painel.add(new JLabel("Ângulo (°):"), gbc);
      txtAngulo = new JTextField("55", 10);
      gbc.gridx = 1; painel.add(txtAngulo, gbc);

      return painel;
   }

   private JPanel criarPainelOutputs() {
      JPanel painel = new JPanel(new GridBagLayout());
      painel.setBorder(new TitledBorder("Resultados"));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(2, 5, 2, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;
      // Adiciona os campos de output
      txtV0 = criarCampoOutput(painel, gbc, "v0 (ft/s):", 0);
      txtV0x = criarCampoOutput(painel, gbc, "v0x:", 1);
      txtV0y = criarCampoOutput(painel, gbc, "v0y:", 2);
      txtHMax = criarCampoOutput(painel, gbc, "H máx:", 3);
      txtTempo = criarCampoOutput(painel, gbc, "Tempo (s):", 4);

      btnResetar = new JButton("Limpar");
      gbc.gridx = 1; gbc.gridy = 5;
      painel.add(btnResetar, gbc);
      btnResetar.addActionListener(e -> limparTudo());

      return painel;
   }

   private JTextField criarCampoOutput(JPanel p, GridBagConstraints gbc, String label, int y) {
      gbc.gridx = 0; gbc.gridy = y;
      p.add(new JLabel(label), gbc);
      JTextField tf = new JTextField(8);
      tf.setEditable(false);
      tf.setBackground(new Color(230, 230, 230));
      gbc.gridx = 1;
      p.add(tf, gbc);
      return tf;
   }

   private void iniciarAnimacao() {
      try {
         if (timer != null && timer.isRunning()) timer.stop();

         double x0 = Double.parseDouble(txtX0.getText().replace(',', '.'));
         double y0 = Double.parseDouble(txtY0.getText().replace(',', '.'));
         double xFinal = Double.parseDouble(txtXFinal.getText().replace(',', '.'));
         double yFinal = Double.parseDouble(txtYFinal.getText().replace(',', '.'));
         double angulo = Double.parseDouble(txtAngulo.getText().replace(',', '.'));
        
         calculadora calc = new calculadora(x0, y0, xFinal, yFinal, angulo);
         calc.calcularTudo();

         // --- LÓGICA DE ZOOM AUTOMÁTICO ---
         // Enquadra o lançamento para ocupar 70% da tela disponível
         double margem = 0.7;
         double escalaX = (painelDesenho.getWidth() * margem) / xFinal;
         // Altura útil considerando que o topo tem painéis e o fundo tem o chão
         double escalaY = (painelDesenho.getHeight() - 100) * margem / calc.getAlturaMaxima();
         
         double escalaIdeal = Math.min(escalaX, escalaY);
         painelDesenho.setEscala(escalaIdeal);
         // ---------------------------------

         txtV0.setText(String.format("%.2f", calc.getVelocidadeInicial()));
         txtV0x.setText(String.format("%.2f", calc.getVelocidadeInicialX()));
         txtV0y.setText(String.format("%.2f", calc.getVelocidadeInicialY()));
         txtHMax.setText(String.format("%.2f", calc.getAlturaMaxima()));
         txtTempo.setText(String.format("%.2f", calc.getTempoTotal()));

         painelSuperior.setVisible(false);
         btnMostrar.setVisible(true);

         bola = new projetil(x0, y0, calc.getVelocidadeInicialX(), calc.getVelocidadeInicialY());
         tempoPassado = 0.0;
         
         painelDesenho.configurarDesenho(null, null);
         painelDesenho.adicionarPontoRastro(x0, y0);

         timer = new Timer(20, e -> {
            tempoPassado += 0.02;

            boolean terminou = tempoPassado > calc.getTempoTotal();
            if(terminou) {
               tempoPassado = calc.getTempoTotal();
               timer.stop();
            }

            double novaX = x0 + (calc.getVelocidadeInicialX() * tempoPassado);
            double novaY = y0 + (calc.getVelocidadeInicialY() * tempoPassado) - (0.5 * 32.2 * Math.pow(tempoPassado, 2));

            bola.setPosicaoX(novaX);
            bola.setPosicaoY(Math.max(0, novaY));

            if(novaY >= 0)
               painelDesenho.adicionarPontoRastro(novaX, novaY);

            painelDesenho.configurarDesenho(bola, calc);
         });

         timer.start();
      } catch (Exception ex) {
         JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
      }
   }

   private void limparTudo() {
      txtX0.setText("");
      txtY0.setText("");
      txtXFinal.setText(""); 
      txtYFinal.setText("");
      txtAngulo.setText("");
      txtV0.setText(""); 
      txtV0x.setText(""); 
      txtV0y.setText("");
      txtHMax.setText(""); 
      txtTempo.setText("");
      painelDesenho.configurarDesenho(null, null);
      painelDesenho.setEscala(20.0);
   }
}