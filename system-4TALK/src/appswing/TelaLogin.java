/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelo.Individual;
import regras_negocio.Fachada;

public class TelaLogin {
	private JDialog frame;			//permite controle modal
	
	private JLabel label;
	private JLabel lblEmail;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblSenha;
	private JButton btnEntrar;

	/**
	 * Launch the application.
	 */
//		public static void main(String[] args) {
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						TelaLogin window = new TelaLogin();
//						window.frame.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setResizable(false);
		frame.setTitle("Login");
		frame.setBounds(100, 100, 346, 145);
		frame.getContentPane().setLayout(null);
		
		// janela modal
		//---------------------------------------------
		frame.setModal(true);
		//---------------------------------------------
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		
		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(10, 80, 310, 14);
		frame.getContentPane().add(label);

		lblEmail = new JLabel("Nome");
		lblEmail.setBounds(25, 24, 46, 14);
		frame.getContentPane().add(lblEmail);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(87, 21, 86, 20);
		frame.getContentPane().add(textField);

		passwordField = new JPasswordField();
		passwordField.setBounds(87, 49, 86, 20);
		frame.getContentPane().add(passwordField);

		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(25, 52, 46, 14);
		frame.getContentPane().add(lblSenha);

		btnEntrar = new JButton("logar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String nome = textField.getText();
					String senha = new String(passwordField.getPassword());
					Individual individuo = Fachada.validarIndividuo(nome, senha);
					if(individuo != null) {
						TelaPrincipal.logado = individuo;
						frame.dispose();
					}
					else
						label.setText("individuo nao encontrado: ");

				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}

			}
		});
		btnEntrar.setBounds(206, 35, 72, 23);
		frame.getContentPane().add(btnEntrar);

		
		frame.setVisible(true);
	}

}
