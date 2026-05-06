package entidades;

import javax.swing.Timer;

public class animacaoArremesso {
   Timer timer;
   projetil proj;
   double tempoAtual;
   double tempoTotal;
   double xOrigem;
   double yOrigem;
   double velocidadeInicialX;
   double velocidadeInicialY;
   double angulo; // Ângulo de lançamento

   public animacaoArremesso(projetil proj) {
      setProj(proj);
      configurarTimer();
   }
   
   // === GETTERS E SETTERS ===
   // PROJÉTIL
   public void setProj(projetil itemArremesso) {
      if(itemArremesso == null) 
         throw new IllegalArgumentException("O objeto projetil não pode ser nulo.");
      
      this.proj = itemArremesso;
   }
   public projetil getProj() {
      return proj;
   }
   // TEMPO TOTAL (t) EM SEGUNDOS
   public void setTempoTotal(double tempoTotal) {
      if (tempoTotal < 0) 
         throw new IllegalArgumentException("Tempo total não pode ser negativo.");
      
      this.tempoTotal = tempoTotal;
   }
   public double getTempoTotal() {
      return tempoTotal;
   }
   // ANGULO DE LANÇAMENTO (θ) EM GRAUS
   public void setAngulo(double angulo) {
      if (angulo < 0.0 || angulo > 90.0) 
         throw new IllegalArgumentException("Ângulo de lançamento deve estar entre 0.1 e 89.9 graus.");
      
      this.angulo = angulo;
   }
   public double getAngulo() {
      return angulo;
   }

   // === CALCULOS ===
   private void configurarTimer() {
      //Configura 60 FPS
      timer = new Timer(16, e -> atualizarPosicao());
   }
   // Atualiza a posição do projétil
   private void atualizarPosicao() {
      tempoAtual += 0.016; // Incrementa o tempo a cada atualização (16 ms)

      if(tempoAtual <= tempoTotal) {
         double novoX = xOrigem + velocidadeInicialX * tempoAtual;
         // Y = y0 + v0y * t - (g * t²) / 2
         double novoY = yOrigem + velocidadeInicialY * tempoAtual - 0.5 * 32.2 * tempoAtual * tempoAtual;
         
         proj.setPosicaoX(novoX);
         proj.setPosicaoY(novoY);

      }
      else
         timer.stop(); // Para a animação quando o tempo total for atingido
   }
   // Será chamar ao iniciar a animação pelo botao na interface
   public void iniciarAnimacao(double v0, double angulo) {
      if(v0 < 0.0)
         throw new IllegalArgumentException("Velocidade inicial não pode ser negativa.");
      if(angulo < 0.0 || angulo > 90.0)
         throw new IllegalArgumentException("Ângulo de lançamento deve estar entre 0.1 e 89.9 graus.");

      setAngulo(angulo);

      // Define a posição inicial do projétil
      this.xOrigem = proj.getPosicaoX();
      this.yOrigem = proj.getPosicaoY();
      // Calcula a velocidade inicial em X e Y
      this.velocidadeInicialX = v0 * Math.cos(Math.toRadians(angulo)); 
      this.velocidadeInicialY = v0 * Math.sin(Math.toRadians(angulo)); 
      // Garante que o tempo começa do zero
      this.tempoAtual = 0.0;

      timer.start();
   }
}