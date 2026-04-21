package entidades;

public class calculadora {

	// === CONSTANTES ===
	//	FÍSICA
	private static final double GRAVIDADE = 9.80665; // m/s² 
	// LIMITES DE RECORDES REAIS
	private static final double VELOCIDADE_MAX = 6583.33; // m/s (velocidade máxima de um canhão)
	private static final double ALTURA_MAX = 100000.0;     // 50 km
	private static final double DISTANCIA_MAX = 180000.0; // 100 km
	// LIMITES TECNICOS (para evitar erros de cálculo)
	private static final double ANGULO_MAX = 89.9; // em graus
	private static final double ANGULO_MIN = 0.1; // em graus

	// INPUTS
	double posicaoInicialX;  // x0
	double posicaoInicialY;  // y0
	double posicaoFinalX;    // X
	double posicaoFinalY;    // Y
	double deltaX;			  // Δx
	double deltaY;			  // Δy
	double anguloLancamento; // θ

	// OUTPUTS
	double velocidadeInicial;  // Σv0
	double velocidadeInicialX; // v0x
	double velocidadeInicialY; // v0y
	double alturaMaxima;       // Hmax
	double tempoTotal;		   // Σt

    // CONSTRUTOR DA CLASSE
	public calculadora(double posicaoInicialX, double posicaoInicialY, double posicaoFinalX, double posicaoFinalY, double anguloLancamento) {
		setPosicaoInicialX(posicaoInicialX);
		setPosicaoInicialY(posicaoInicialY);
		setPosicaoFinalX(posicaoFinalX);
		setPosicaoFinalY(posicaoFinalY);
		setDetalX();
		setDetalY();
		setAnguloLancamento(anguloLancamento);
	}
	public void calcularTudo() {	
		calcularVelocidadeInicial();  // v0
		calcularVelocidadeInicialX(); // v0x
		calcularVelocidadeInicialY(); // v0y
		calcularAlturaMaxima();       // Hmax
		calcularTempoTotal();         // Σt

	}
	// === GETTERS E SETTERS ===
	// v0X
	public void setPosicaoInicialX(double posicaoInicial) {
		if(posicaoInicial < 0.0)
			throw new IllegalArgumentException("Posição inicial não pode ser negativa.");

		this.posicaoInicialX = posicaoInicial;
	}
	public double getPosicaoInicialX() {
		return posicaoInicialX;
	}
	// v0Y
	public void setPosicaoInicialY(double posicaoInicial) {
		if(posicaoInicial < 0.0)
			throw new IllegalArgumentException("Posição inicial não pode ser negativa.");

		this.posicaoInicialY = posicaoInicial;
	}
	public double getPosicaoInicialY() {
		return posicaoInicialY;
	}
	// X
	public void setPosicaoFinalX(double posicaoFinal) {
		if(posicaoFinal < 0.0)
			throw new IllegalArgumentException("Posição final não pode ser negativa.");

		this.posicaoFinalX = posicaoFinal;
	}
	public double getPosicaoFinalX() {
		return posicaoFinalX;
	}
	// Y
	public void setPosicaoFinalY(double posicaoFinal) {
		this.posicaoFinalY = posicaoFinal;
	}
	public double getPosicaoFinalY() {
		return posicaoFinalY;
	}
	// Δx
	public void setDetalX() {
		double Δx = this.posicaoFinalX - this.posicaoInicialX;
		if(Δx <= 0.0)
			throw new IllegalArgumentException("A posição final deve ser maior que a posição inicial para um lançamento válido.");
		if(Δx > DISTANCIA_MAX)
			throw new IllegalArgumentException("A distância percorrida supera a Termosfera.");

		this.deltaX = Δx;
	}
	public double getDetalX() {
		return deltaX;
	}
	// Δy
	public void setDetalY() {
		double Δy = this.posicaoFinalY - this.posicaoInicialY;
		if(Δy > ALTURA_MAX)
			throw new IllegalArgumentException("A altura alcançada supera o limite máximo permitido.");

		this.deltaY = Δy;
	}
	public double getDetalY() {
		return deltaY;
	}
	// θ
	public void setAnguloLancamento(double angulo) {
		if(angulo < ANGULO_MIN || angulo > ANGULO_MAX) 
			throw new IllegalArgumentException("Ângulo de lançamento deve estar entre " + ANGULO_MIN + " e " + ANGULO_MAX + " graus.");

		this.anguloLancamento = Math.toRadians(angulo);
	}
	public double getAnguloLancamento() {
		return Math.toDegrees(anguloLancamento);
	}
	
	// OUTPUTS
	public double getVelocidadeInicial() {
		return velocidadeInicial;
	}
	public double getVelocidadeInicialX() {
		return velocidadeInicialX;
	}
	public double getVelocidadeInicialY() {
		return velocidadeInicialY;
	}
	public double getAlturaMaxima() {
		return alturaMaxima;
	}
	public double getTempoTotal() {
		return tempoTotal;
	}

	// === CÁLCULOS ===
	// v0
	public void calcularVelocidadeInicial() {
		double denominador = 2 * Math.pow(Math.cos(this.anguloLancamento), 2) * (this.deltaX * Math.tan(this.anguloLancamento) - this.deltaY);
		// Denominador não pode ser zero ou negativo
		if(denominador <= 0) 
			throw new IllegalArgumentException("Cálculo impossível com os valores fornecidos. Verifique as posições e o ângulo.");
		
		double v0 = Math.sqrt((GRAVIDADE * Math.pow(this.deltaX, 2)) / denominador);
		// Velocidade inicial deve ser inferior a 
		if(v0 > VELOCIDADE_MAX) 
			throw new IllegalArgumentException("Velocidade inicial supera o limite máximo permitido.");
	
		this.velocidadeInicial = v0;
	}
	// v0X
	public void calcularVelocidadeInicialX() {
		double v0X = this.velocidadeInicial * Math.cos(this.anguloLancamento);
		// Validações
		if(v0X <= 0) 
			throw new IllegalArgumentException("Velocidade inicial em X deve ser positiva para calcular o tempo total.");
		if(v0X > VELOCIDADE_MAX) 
			throw new IllegalArgumentException("Velocidade inicial em X supera o limite máximo permitido.");
		
		this.velocidadeInicialX = v0X;
	}
	// v0Y
	public void calcularVelocidadeInicialY() {
		this.velocidadeInicialY = this.velocidadeInicial * Math.sin(this.anguloLancamento);
	}
	// hmax
	public void calcularAlturaMaxima() {
		double hMax = posicaoInicialY +(Math.pow(this.velocidadeInicialY, 2)) / (2 * GRAVIDADE);
		if(hMax > ALTURA_MAX) 
			throw new IllegalArgumentException("A altura máxima calculada supera o limite permitido.");

		this.alturaMaxima = hMax;
	}
	// t
	public void calcularTempoTotal() {
		if(velocidadeInicialX <= 0) 
			throw new IllegalArgumentException("Velocidade inicial em X deve ser positiva para calcular o tempo total.");

		double tempoVoo = deltaX / velocidadeInicialX;
		if(tempoVoo <= 0) 
			throw new IllegalArgumentException("Cálculo do tempo total resultou em valor não positivo. Verifique os valores de entrada.");

		this.tempoTotal = tempoVoo;
	}
}