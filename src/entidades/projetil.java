package entidades;

public class projetil {
   double posicaoX; // x
   double posicaoY; // y
   double velocidadeX; // vx
   double velocidadeY; // vy

   public projetil(double posicaoX, double posicaoY, double velocidadeX, double velocidadeY) {
      setPosicaoX(posicaoX);
      setPosicaoY(posicaoY);
      setVelocidadeX(velocidadeX);
      setVelocidadeY(velocidadeY);
   }

   // POSIÇÃO X (x) EM PÉS
   public double getPosicaoX() {
      return posicaoX;
   }
   public void setPosicaoX(double posicaoX) {
      if (posicaoX < 0.0) 
         throw new IllegalArgumentException("Posição X não pode ser negativa.");
      
      this.posicaoX = posicaoX;
   }
   // POSIÇÃO Y (y) EM PÉS
   public double getPosicaoY() {
      return posicaoY;
   }
   public void setPosicaoY(double posicaoY) {
      if (posicaoY < 0.0) 
         throw new IllegalArgumentException("Posição Y não pode ser negativa.");
      
      this.posicaoY = posicaoY;
   }
   // VELOCIDADE X (vx) EM ft/s
   public double getVelocidadeX() {
      return velocidadeX;
   }
   public void setVelocidadeX(double velocidadeX) {
      if (velocidadeX < 0.0)
         throw new IllegalArgumentException("Velocidade X não pode ser negativa.");
      
      this.velocidadeX = velocidadeX;
   }
   // VELOCIDADE Y (vy) EM ft/s
   public double getVelocidadeY() {
      return velocidadeY;
   }
   public void setVelocidadeY(double velocidadeY) {      
      this.velocidadeY = velocidadeY;
   }
}
