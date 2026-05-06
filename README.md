# 🏀 Simulador de Lançamento Oblíquo
### Baseado em *Fundamentos de Física* — Halliday, Resnick & Walker

> Simulador interativo de física desenvolvido em Java com interface gráfica Swing, focado na resolução reversa de problemas de lançamento oblíquo com motor físico no **Sistema Imperial (ft)**.

---

## 🖼️ Preview
 
![Simulador de Lançamento Oblíquo](https://github.com/user-attachments/assets/21279eb8-05fb-4577-90c4-ec2f86d7b3b7)
 
*Exemplo de lançamento com x₀=0, y₀=7, X=15, Y=10, θ=55° — v₀ calculada em 24,45 ft/s.*

---

## 📌 Contexto do Projeto

Este projeto foi desenvolvido como ferramenta de validação para problemas clássicos de cinemática, com foco no **lançamento oblíquo** conforme formulado por Halliday. O caso de uso principal é o **arremesso de lance livre no basquete**: dado que a bola parte de uma posição inicial conhecida e deve acertar a cesta em uma posição final precisa, qual deve ser a velocidade de lançamento?

O diferencial deste simulador está na **abordagem de cálculo reverso** — ao contrário de simulações convencionais que partem da velocidade para encontrar onde o projétil cai, aqui o usuário define **onde o projétil deve chegar** e o sistema calcula a velocidade necessária.

---

## ⚙️ Configurações do Motor Físico

| Parâmetro | Valor | Unidade |
|---|---|---|
| Sistema de medidas | Imperial | — |
| Gravidade (g) | **32.2** | ft/s² |
| Distância máxima (X) | 190.0 | ft |
| Altura máxima (Y) | 90.0 | ft |
| Ângulo de lançamento | 0.1° – 89.9° | graus |

> ⚠️ **Importante:** a gravidade adotada é **32.2 ft/s²** (padrão imperial), conforme o sistema de unidades do projeto. Não confundir com 9.81 m/s² (padrão SI).

### Fórmulas Utilizadas

O sistema resolve a equação da trajetória para $v_0$ a partir das coordenadas do alvo:

$$v_0 = \sqrt{\frac{g \cdot \Delta x^2}{2\cos^2\theta \cdot (\Delta x \cdot \tan\theta - \Delta y)}}$$

A partir de $v_0$, são derivados:

$$v_{0x} = v_0 \cdot \cos\theta \qquad v_{0y} = v_0 \cdot \sin\theta$$

$$H_{max} = y_0 + \frac{v_{0y}^2}{2g} \qquad t_{total} = \frac{\Delta x}{v_{0x}}$$

A posição do projétil em cada instante $t$ é calculada por:

$$x(t) = x_0 + v_{0x} \cdot t \qquad y(t) = y_0 + v_{0y} \cdot t - \frac{1}{2} g t^2$$

---

## 🖥️ Interface Gráfica

A interface foi construída com **Java Swing** e conta com recursos visuais pensados para análise física precisa.

### Zoom Dinâmico e Automático
Ao calcular um lançamento, o sistema determina automaticamente a escala ideal para que a parábola completa seja enquadrada dentro da área de visualização de **1000 × 700 px**, ocupando aproximadamente 70% da tela disponível. A escala é calculada individualmente para os eixos X e Y, e o menor valor é adotado para garantir que nenhuma parte da trajetória fique fora do campo de visão.

O zoom também pode ser ajustado manualmente pelo usuário a qualquer momento.

### Réguas Dinâmicas
Os eixos X (horizontal) e Y (vertical) exibem marcações em **pés (ft)** que se adaptam automaticamente ao nível de zoom ativo. Em escalas menores, o intervalo entre marcações aumenta para evitar poluição visual.

### Rastro de Trajetória
Durante a animação, o caminho percorrido pelo projétil é registrado e desenhado de forma persistente na tela, formando a curva parabólica completa. Isso permite a análise visual da trajetória mesmo após o projétil ter chegado ao destino.

---

## ✅ Exemplo de Validação — Lance Livre de Basquete

Os valores abaixo reproduzem um cenário realista de arremesso de lance livre e podem ser usados para validar o funcionamento do simulador:

| Campo | Valor |
|---|---|
| Posição inicial X ($x_0$) | `0` ft |
| Altura inicial Y ($y_0$) | `7` ft |
| Posição do alvo X | `15` ft |
| Altura do alvo Y | `10` ft |
| Ângulo de lançamento ($\theta$) | `55°` |

**Resultado esperado:** a bola deve seguir uma trajetória parabólica e acertar o alvo com precisão nas coordenadas (15, 10). Os valores de $v_0$, $v_{0x}$, $v_{0y}$, $H_{max}$ e $t_{total}$ serão exibidos no painel de resultados.

| Saída | Valor |
|---|---|
| $v_0$ | 24,45 ft/s |
| $v_{0x}$ | 14,02 ft/s |
| $v_{0y}$ | 20,03 ft/s |
| $H_{max}$ | 13,23 ft |
| $t_{total}$ | 1,07 s |

> Resultado validado na simulação — veja o [Preview](#️-preview) no topo do documento.

---

## 🚀 Como Usar

**1. Preencha as entradas** no painel superior esquerdo:
- `x0` e `y0` — posição inicial do projétil (em pés)
- `X Alvo` e `Y Alvo` — posição do alvo (em pés)
- `Ângulo (°)` — ângulo de lançamento

**2. Clique em `Calcular e Lançar`** para iniciar a simulação. O painel de inputs será minimizado automaticamente para maximizar a área de visualização.

**3. Acompanhe os resultados** no painel superior direito:
- Velocidade inicial total ($v_0$), componentes ($v_{0x}$, $v_{0y}$)
- Altura máxima ($H_{max}$) e tempo de voo ($t_{total}$)

**4. Ajuste o zoom** conforme necessário:
- **Scroll do mouse** sobre o painel de animação para zoom suave
- Botões **`+`** e **`-`** no painel inferior para ajuste em incrementos fixos

**5. Clique em `Mostrar Configurações`** para retornar ao painel de inputs e realizar um novo cálculo.

**6. Clique em `Limpar`** para resetar todos os campos e limpar a tela.

---

## 🗂️ Estrutura do Projeto

```
├── main.java                        # Ponto de entrada — inicializa a interface via SwingUtilities
│
├── telaPrincipal.java               # JFrame principal — layout, controles e loop de animação
│
└── entidades/
    ├── calculadora.java             # Motor físico — cálculo reverso de v0 e demais outputs
    ├── projetil.java                # Entidade do projétil — posição e velocidade em X e Y
    ├── painelDesenho.java           # JPanel customizado — renderização, rastro e réguas
    └── animacaoArremesso.java       # Controlador de animação via javax.swing.Timer
```

### Responsabilidades por Classe

**`calculadora`** — núcleo físico do sistema. Recebe as coordenadas do alvo e o ângulo, e resolve as equações para retornar $v_0$, $v_{0x}$, $v_{0y}$, $H_{max}$ e $t_{total}$. Todas as validações físicas (limites de distância, altura e ângulo) estão centralizadas aqui.

**`projetil`** — modelo de dados do projétil em voo, armazenando posição $(x, y)$ e velocidade $(v_x, v_y)$ atualizadas a cada tick da animação.

**`painelDesenho`** — responsável por toda a renderização visual: chão, alvo, bola, rastro e réguas. Converte coordenadas físicas em pés para pixels usando a escala atual.

**`telaPrincipal`** — orquestra a interface: lê os inputs, instancia a `calculadora`, configura o zoom automático, atualiza os outputs e controla o `Timer` da animação.

---

## 🔧 Requisitos

- **Java** 8 ou superior
- Nenhuma dependência externa — apenas a biblioteca padrão Java SE (`javax.swing`, `java.awt`)

---

## 📚 Referência Bibliográfica

> HALLIDAY, David; RESNICK, Robert; WALKER, Jearl. **Fundamentos de Física — Volume 1: Mecânica**. 10. ed. Rio de Janeiro: LTC, 2016.

---

*Projeto acadêmico — Simulação de Física Computacional com Java Swing.*
