package entidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class painelDesenho extends JPanel{
   // 1 pé = 20 pixels
   private static final double TAMANHO_BOLA = 15.0;
   private static final int MARGEM_CHAO = 50; 
   // Classes
   private projetil bola;
   private calculadora dadosCalculadora;
   // Atributos
   private double escala = 20.0; 
   private List<Point2D.Double> rastro;

   public painelDesenho() {
      setBackground(new Color(230, 245, 255));
      setBorder(BorderFactory.createLineBorder(Color.gray));
      rastro = new ArrayList<>();
   }

   // Sistema de zoom
   public double getEscala() {
      return escala;
   }
   public void setEscala(double novaEscala) {
    
      if (novaEscala >= 5.0  && novaEscala <= 150.0) {
         this.escala = novaEscala;
         repaint();
      }      
   }

   // Insere os dados calculados para configurar o desenho do projétil
   public void configurarDesenho(projetil p, calculadora c) {
      this.bola = p;
      this.dadosCalculadora = c;
      
      if( p == null)
         // Tira o rastro do projétil quando ele é removido 
         rastro.clear();
      else
         // Adiciona a posição atual do projétil ao rastro para ser desenhado
         rastro.add(new Point2D.Double(p.getPosicaoX(), p.getPosicaoY()));

      repaint();
   }

   // Função que desenha o chão, o alvo e o projétil
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

      desenharReguas(g2, larguraTotal, yChao);

      if(dadosCalculadora != null) {
         int alvoX = pesEmPixelsX(dadosCalculadora.getPosicaoFinalX());
         int alvoY = pesEmPixelsY(dadosCalculadora.getPosicaoFinalY(), yChao);

         // Desenha o alvo
         g2.setColor(Color.red);
         g2.drawOval(alvoX - 10, alvoY - 10, 20, 20);
         g2.fillOval(alvoX - 2, alvoY - 2, 4, 4);
         g2.drawString("Alvo", alvoX - 15, alvoY - 15);

         // Desenha o rastro do projétil
         g2.setColor(new Color(255, 140, 0, 150)); // Laranja semi-transparente
         for (int i = 1; i < rastro.size(); i++) {
            Point2D.Double anterior = rastro.get(i - 1);
            Point2D.Double atual = rastro.get(i);
            g2.drawLine(
               pesEmPixelsX(anterior.x), pesEmPixelsY(anterior.y, yChao),
               pesEmPixelsX(atual.x),    pesEmPixelsY(atual.y,    yChao)
            );
         }
         // Desenha o projétil
         if(bola != null) {
            int bolaX = pesEmPixelsX(bola.getPosicaoX());
            int bolaY = pesEmPixelsY(bola.getPosicaoY(), yChao);

            g2.setColor(Color.ORANGE);
            g2.fillOval(bolaX - (int)(TAMANHO_BOLA/2), bolaY - (int)(TAMANHO_BOLA/2), (int)TAMANHO_BOLA, (int)TAMANHO_BOLA);
            g2.setColor(Color.BLACK);
            g2.drawOval(bolaX - (int)(TAMANHO_BOLA/2), bolaY - (int)(TAMANHO_BOLA/2), (int)TAMANHO_BOLA, (int)TAMANHO_BOLA);
         }
      }
   }

   // Desenha as réguas de marcação em pés para o chão e a parede lateral
   private void desenharReguas(Graphics2D g2, int larguraTotal, int yChao) {
      g2.setColor(new Color(50, 50, 50, 150)); 
      g2.setFont(new Font("Monospaced", Font.BOLD, 10));

      // Ajusta o intervalo numérico para não poluir a tela se o zoom for muito pequeno
      int intervalo = (escala < 15) ? 10 : 5;

      // Eixo X (Marcações no chão horizontal)
      for (int ft = 0; (ft * escala) < larguraTotal; ft += intervalo) {
          int xPix = (int) (ft * escala);
          g2.drawLine(xPix, yChao, xPix, yChao + 5); // Tracinho
          g2.drawString(ft + " ft", xPix + 2, yChao + 15); // Texto
      }

      // Eixo Y (Marcações na parede vertical esquerda)
      for (int ft = 0; yChao - (ft * escala) > 0; ft += intervalo) {
          int yPix = yChao - (int) (ft * escala);
          g2.drawLine(0, yPix, 5, yPix); // Tracinho
          if (ft > 0) g2.drawString(ft + " ft", 8, yPix + 5); // Texto
      }
   }

   // Converte as coordenadas em pés para pixels, considerando a escala definida 
   private int pesEmPixelsX(double pesX) {
      return (int)(pesX * escala);
   }
   private int pesEmPixelsY(double pesY, int yChao) {
      return yChao - (int)(pesY * escala);
   }
}
