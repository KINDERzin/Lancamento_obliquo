package entidades;

public class calculadora {

	private float ACELERACAO_MAXIMA = 10.0f;  // m/s²
	private float VELOCIDADE_MAXIMA = 30.56f; // m/s (110 km/h)
	private float GRAVIDADE = 9.81f;          // m/s²  

	// INPUTS
	float posicaoInicial;    // x0 ou y0
	float tempo;             // t
	float aceleracao;        // a
	float velocidadeInicial; // v0

	// OUTPUTS
	float velocidadeFinal; // v
	float deltaX;          // Δx = xFinal - xInicial
	float deltaY;          // Δy = yFinal - yInicial

    // CONSTRUTOR DA CLASSE
	public calculadora(float posicaoInicial, float tempo, float aceleracao, float velocidadeInicial) {
		this.posicaoInicial = posicaoInicial;
		this.tempo = tempo;
		this.aceleracao = aceleracao;
		this.velocidadeInicial = velocidadeInicial;

		// FORMULAS
		// v = v0 + a * t
		// v² = v0² + 2 * a * (x - x0) * t
		// x = x0 + v0 * t + a * t² / 2
	}

    // POSIÇÃO INICIAL (x0 ou y0) EM METROS
	public void setPosicaoInicial(float posicaoInicial) {
		this.posicaoInicial = posicaoInicial;
	}
	public float getPosicaoInicial() {
		return posicaoInicial;
	}
    
	// TEMPO (t) EM SEGUNDOS
	public void setTempo(float tempo) {
		if (tempo < 0) {
			System.out.println("Tempo não pode ser negativo.");
			throw new IllegalArgumentException("Tempo não pode ser negativo. (setTempo)");
		}
		this.tempo = tempo;
	}
	public float getTempo() {
		return tempo;
	}

	// ACELERAÇÃO (a) EM m/s²
	public void setAceleracao(float aceleracao) {
		if (aceleracao < 0) {
			System.out.println("Aceleração não pode ser negativa.");
			throw new IllegalArgumentException("Aceleração não pode ser negativa. (setAceleracao)");
		}
		if (aceleracao > ACELERACAO_MAXIMA) {
			System.out.println("Aceleração não pode ser maior que " + ACELERACAO_MAXIMA + " m/s².");
			throw new IllegalArgumentException("Aceleração não pode ser maior que " + ACELERACAO_MAXIMA + " m/s². (setAceleracao)");
		}
		this.aceleracao = aceleracao;
	}
	public float getAceleracao() {
		return aceleracao;
	}
    
	// VELOCIDADE INICIAL (v0) EM m/s
	public void setVelocidadeInicial(float v0) {
		if (v0 < 0) {
			System.out.println("Velocidade inicial não pode ser negativa.");
			throw new IllegalArgumentException("Velocidade inicial não pode ser negativa.");
		}
		if (v0 > VELOCIDADE_MAXIMA) {
			System.out.println("Velocidade inicial não pode ser maior que " + VELOCIDADE_MAXIMA + " m/s (110 km/h).");
			throw new IllegalArgumentException("Velocidade inicial não pode ser maior que " + VELOCIDADE_MAXIMA + " m/s (110 km/h).");
		}

		this.velocidadeInicial = v0;
	}
	public float getVelocidadeInicial() {
		return velocidadeInicial;
	}

	public float calcularVelocidadeFinal() {
		// Vfinal = Vinicial + (aceleração * tempo)
		if (velocidadeInicial < 0 || velocidadeInicial > VELOCIDADE_MAXIMA) {
			System.out.println("Velocidade inicial inválida. Deve ser entre 0 e " + VELOCIDADE_MAXIMA + " m/s.");
			throw new IllegalArgumentException("Velocidade inicial inválida. Deve ser entre 0 e " + VELOCIDADE_MAXIMA + " m/s.");
		} 
		if (aceleracao < 0 || aceleracao > ACELERACAO_MAXIMA) {
			System.out.println("Aceleração inválida. Deve ser entre 0 e " + ACELERACAO_MAXIMA + " m/s².");
			throw new IllegalArgumentException("Aceleração inválida. Deve ser entre 0 e " + ACELERACAO_MAXIMA + " m/s².");
		} 
		if (tempo < 0) {
			System.out.println("Tempo inválido. Deve ser maior ou igual a 0 segundos.");
			throw new IllegalArgumentException("Tempo inválido. Deve ser maior ou igual a 0 segundos.");
		} 
			this.velocidadeFinal = velocidadeInicial + (aceleracao * tempo);
			return velocidadeFinal;
	}

	public float calcularAceleracao() {
		// a = (Vfinal - Vinicial) / tempo
		if (velocidadeFinal < 0 || velocidadeFinal > VELOCIDADE_MAXIMA) {
			System.out.println("Velocidade final inválida. Deve ser entre 0 e " + VELOCIDADE_MAXIMA + " m/s.");
			throw new IllegalArgumentException("Velocidade final inválida. Deve ser entre 0 e " + VELOCIDADE_MAXIMA + " m/s.");
		} 
		if (velocidadeInicial < 0 || velocidadeInicial > VELOCIDADE_MAXIMA) {
			System.out.println("Velocidade inicial inválida. Deve ser entre 0 e " + VELOCIDADE_MAXIMA + " m/s.");
			throw new IllegalArgumentException("Velocidade inicial inválida. Deve ser entre 0 e " + VELOCIDADE_MAXIMA + " m/s.");
		} 
		if (tempo <= 0) {
			System.out.println("Tempo inválido. Deve ser maior que 0 segundos.");
			throw new IllegalArgumentException("Tempo inválido. Deve ser maior que 0 segundos.");
		} 
			this.aceleracao = (velocidadeFinal - velocidadeInicial) / tempo;
			return aceleracao;
	}
	
	public float calcularDeslocamentoX(float x, float x0) {
		if(x < 0.0 || x0 < 0.0) {
			System.out.println("Posição não pode ser negativa.");
			throw new IllegalArgumentException("Posição não pode ser negativa. (calcularDeslocamentoX)");
		}

		this.deltaX = x - x0;
		return deltaX;
	}

	public float calcularDeslocamentoY(float y, float y0) {
		if(y < 0.0 || y0 < 0.0) {
			System.out.println("Posição não pode ser negativa.");
			throw new IllegalArgumentException("Posição não pode ser negativa. (calcularDeslocamentoY)");
		}

		this.deltaY = y - y0;
		return deltaY;
	} 
}