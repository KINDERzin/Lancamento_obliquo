package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class painelDesenho extends JPanel{
   // 1 metro = 20 pixels
   private static final double ESCALA = 20.0; 
   private static final double TAMANHO_BOLA = 15.0;
   private static final int MARGEM_CHAO = 50; 

   private projetil bola;
   private calculadora dadosCalculadora;

   public painelDesenho() {
      setBackground(new Color(230, 245, 255));
      setBorder(BorderFactory.createLineBorder(Color.gray));
   }

   // Injeta os dados calculados para configurar o desenho do projétil
   public void configurarDesenho(projetil p, calculadora c) {
      this.bola = p;
      this.dadosCalculadora = c;
      repaint();
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;

      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      int larguraTotal = getWidth();
      int alturaTotal = getHeight();
      int yChao = alturaTotal - MARGEM_CHAO;

      // Desenha o chão
      g2.setColor(new Color(34, 139, 34));
      g2.fillRect(0, yChao, larguraTotal, MARGEM_CHAO);
      g2.setColor(Color.BLACK);
      g2.drawLine(0, yChao, larguraTotal, yChao);

      if(dadosCalculadora != null) {
         int alvoX = metroEmPixelsX(dadosCalculadora.getPosicaoFinalX());
         int alvoY = metroEmPixelsY(dadosCalculadora.getPosicaoFinalY(), yChao);

         g2.setColor(Color.red);
         g2.drawOval(alvoX - 10, alvoY - 10, 20, 20);
         g2.fillOval(alvoX - 2, alvoY - 2, 4, 4);
         g2.drawString("Alvo", alvoX - 15, alvoY - 15);

         if(bola != null) {
            int bolaX = metroEmPixelsX(bola.getPosicaoX());
            int bolaY = metroEmPixelsY(bola.getPosicaoY(), yChao);

            g2.setColor(Color.ORANGE);
            g2.fillOval(bolaX - (int)(TAMANHO_BOLA/2), bolaY - (int)(TAMANHO_BOLA/2), (int)TAMANHO_BOLA, (int)TAMANHO_BOLA);

            g2.setColor(Color.BLACK);
            g2.drawOval(bolaX - (int)(TAMANHO_BOLA/2), bolaY - (int)(TAMANHO_BOLA/2), (int)TAMANHO_BOLA, (int)TAMANHO_BOLA);
         }
      }
   }

   private int metroEmPixelsX(double metrosX) {
      return (int)(metrosX * ESCALA);
   }
   private int metroEmPixelsY(double metrosY, int yChao) {
      return yChao - (int)(metrosY * ESCALA);
   }
}
