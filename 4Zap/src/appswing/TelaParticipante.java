/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Grupo;
import modelo.Individual;
import regras_negocio.Fachada;
import javax.swing.SwingConstants;

public class TelaParticipante {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel lblEmail;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblSenha;
	private JButton button;
	private JPanel panel;

	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private ButtonGroup grupobotoes;
	private Timer timer;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Create the application.
	 */
	public TelaParticipante() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
			}
		});
		frame.setTitle("Participantes");
		frame.setBounds(100, 100, 538, 323);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 15, 478, 179);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setDefaultEditor(Object.class, null); // desabilita edicao de celulas
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.YELLOW);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(44, 259, 373, 14);
		frame.getContentPane().add(label);

		lblEmail = new JLabel("Nome");
		lblEmail.setBounds(54, 205, 46, 14);
		frame.getContentPane().add(lblEmail);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(94, 205, 86, 20);
		frame.getContentPane().add(textField);

		passwordField = new JPasswordField();
		passwordField.setBounds(94, 236, 86, 20);
		frame.getContentPane().add(passwordField);

		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(54, 239, 46, 14);
		frame.getContentPane().add(lblSenha);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Tipo",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(188, 201, 107, 61);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(2, 0));

		radioButton = new JRadioButton("grupo");
		panel.add(radioButton);
		radioButton_1 = new JRadioButton("individual");
		panel.add(radioButton_1);
		radioButton_1.setSelected(true);
		grupobotoes = new ButtonGroup(); // permite selecao unica dos botoes
		grupobotoes.add(radioButton);
		grupobotoes.add(radioButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Participante");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(313, 205, 83, 13);
		frame.getContentPane().add(lblNewLabel_1);

		button = new JButton("Criar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = textField.getText();
					String senha = new String(passwordField.getPassword());
					if (radioButton.isSelected())
						Fachada.criarGrupo(nome);
					if (radioButton_1.isSelected())
						Fachada.criarIndividuo(nome, senha);

					label.setText("participante criado");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button.setBounds(300, 228, 101, 21);
		frame.getContentPane().add(button);
		
		JButton btnNewButton_2 = new JButton("Remover");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() < 0)
						JOptionPane.showMessageDialog(null, "selecione uma linha");
					String nome = (String) table.getValueAt(table.getSelectedRow(), 0); // 0=nome
					Object[] options = { "Confirmar", "Cancelar" };
					int escolha = JOptionPane.showOptionDialog(null, "Esta operacao removera o participante do sistema", "Alerta",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (escolha == 0) {
						Fachada.removerParticipante(nome);
						listagem();
						label.setText("exclusao realizada");
					} else
						label.setText("exclusao cancelada");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnNewButton_2.setBounds(300, 252, 101, 21);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Grupos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(434, 205, 54, 13);
		frame.getContentPane().add(lblNewLabel);
		
		btnNewButton = new JButton("Inserir");
		btnNewButton.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        String nomeIndividuo = JOptionPane.showInputDialog(frame, "Digite o nome da pessoa:", "Inserir em grupo", JOptionPane.PLAIN_MESSAGE);
		        if (nomeIndividuo.equals(null) || nomeIndividuo.isEmpty()) { return; }
		        
		        String nomeGrupo = JOptionPane.showInputDialog(frame, "Digite o nome do grupo:", "Inserir em grupo", JOptionPane.PLAIN_MESSAGE);
		        if (nomeGrupo.equals(null) || nomeGrupo.isEmpty()) { return; }
		        
		        try {
		            Fachada.inserirGrupo(nomeIndividuo, nomeGrupo);
		            label.setText("Pessoa inserida com sucesso");
		        } catch (Exception ex) {
		            label.setText(ex.getMessage());
		        }
		    }
		});
		btnNewButton.setBounds(413, 228, 101, 21);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Remover");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        String nomePessoa = JOptionPane.showInputDialog(frame, "Digite o nome da pessoa:", "Remover de Grupo", JOptionPane.PLAIN_MESSAGE);
		        if (nomePessoa == null || nomePessoa.isEmpty()) { return; }
		        
		        String nomeGrupo = JOptionPane.showInputDialog(frame, "Digite o nome do grupo:", "Remover de Grupo", JOptionPane.PLAIN_MESSAGE);
		        if (nomeGrupo == null || nomeGrupo.isEmpty()) { return; }
		        
		        try {
		            Fachada.removerGrupo(nomePessoa, nomeGrupo);
		            label.setText("Pessoa removida do grupo com sucesso");
		        } catch (Exception ex) {
		            label.setText(ex.getMessage());
		        }
		    }
		});

		btnNewButton_1.setBounds(413, 252, 101, 21);
		frame.getContentPane().add(btnNewButton_1);

		// temporizador
		timer = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
				String s = formatador.format(LocalDateTime.now());
				frame.setTitle("Participante: " + s);
				frame.repaint();
				listagem();
			}
		});
		timer.setRepeats(true);
		timer.setDelay(3000); // 3 segundos
		timer.start();

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			
			List<Individual> lista1 = Fachada.listarIndividuos();
			List<Grupo> lista2 = Fachada.listarGrupos();

			DefaultTableModel model = new DefaultTableModel();
			// criar as colunas do table
			model.addColumn("Nome");
			model.addColumn("Tipo");
			model.addColumn("Grupos/Membros");

			// criar as linhas do table
			for (Individual individuo : lista1) {
				String texto = "";
				for (Grupo g : individuo.getGrupos())
					texto += g.getNome() + " ";
				model.addRow(new String[] { individuo.getNome(), "Individual", texto });
			}

			for (Grupo grupo : lista2) {
				String texto = "";
				for (Individual i : grupo.getIndividuos())
					texto += i.getNome() + " ";
				model.addRow(new String[] { grupo.getNome(), "Grupo", texto });
			}
			table.setModel(model);
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(frame, erro.getMessage());
		}
	}
}
