package appswing;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import modelo.Individual;

public class TelaPrincipal {
	private JFrame frame;
	private JMenu mnParticipante;
	private JMenu mnLogar;
	private JMenu mnConversa;
	private JMenu mnMensagem;
	private JMenu mnLogoff;
	private JMenu mnEspionagem;

	public static Individual logado;

	private TelaConversa telaconversa;
	private TelaMensagem telamensagem;
	private TelaEspionagem telaespionagem;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("4TALK - Sem Participante logado");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		label = new JLabel("New label");
		label.setBounds(0, 0, 471, 251);
		frame.getContentPane().add(label);
		ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/imagem.png"));
		imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(imagem);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		mnParticipante = new JMenu("Participante");
		mnParticipante.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				TelaParticipante tela = new TelaParticipante();
			}
		});
		menuBar.add(mnParticipante);

		// -----------------------------------------------------------------

		mnLogar = new JMenu("Login");
		mnLogar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (TelaPrincipal.logado != null) {
					JOptionPane.showMessageDialog(null, "Fa�a logoff antes de logar");
					;
					return;
				}
				TelaLogin telalogin = new TelaLogin();
				if (TelaPrincipal.logado != null) {
					frame.setTitle("4TALK - logado=" + TelaPrincipal.logado.getNome());

					if (TelaPrincipal.logado.getAdministrador())
						mnEspionagem.setVisible(true);
				}
			}
		});
		menuBar.add(mnLogar);

		mnLogoff = new JMenu("Logoff");
		mnLogoff.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				TelaPrincipal.logado = null;
				frame.setTitle("4TALK - sem individuo logado");
				mnEspionagem.setVisible(false);
				if (telaconversa != null)
					telaconversa.fechar();
				if (telamensagem != null)
					telamensagem.fechar();
				if (telaespionagem != null)
					telaespionagem.fechar();
			}
		});
		menuBar.add(mnLogoff);

		// -----------------------------------------------------------------
		mnConversa = new JMenu("Conversa");
		mnConversa.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (TelaPrincipal.logado == null) {
					JOptionPane.showMessageDialog(null, "Vc precisa se logar");
					return;
				}
				telaconversa = new TelaConversa();
			}
		});

		menuBar.add(mnConversa);

		mnMensagem = new JMenu("Mensagem");
		mnMensagem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (TelaPrincipal.logado == null) {
					JOptionPane.showMessageDialog(null, "Vc precisa estar logado");
					return;
				}
				telamensagem = new TelaMensagem();
			}
		});
		menuBar.add(mnMensagem);

		mnEspionagem = new JMenu("Espionagem");
		mnEspionagem.setVisible(false);
		mnEspionagem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (TelaPrincipal.logado == null) {
					JOptionPane.showMessageDialog(null, "Vc precisa estar logado");
					return;
				}
				if (!TelaPrincipal.logado.getAdministrador()) {
					JOptionPane.showMessageDialog(null, "Vc precisa ser administrador");
					return;
				}

				telaespionagem = new TelaEspionagem();
			}
		});
		menuBar.add(mnEspionagem);

	}
}
