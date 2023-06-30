/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Mensagem;
import modelo.Participante;
import regras_negocio.Fachada;

public class TelaConversa {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_4;
	private JLabel label;
	private JTextField textField;

	private JComboBox<String> comboBox;
	private JLabel label_1;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// TelaMensagem window = new TelaMensagem();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public TelaConversa() {
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
				frame.setTitle(frame.getTitle() + " -- logado:" + TelaPrincipal.logado.getNome());
				carregarComboBox();
			}
		});
		frame.setTitle("Conversa");
		frame.setBounds(100, 100, 851, 411);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 53, 785, 179);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setDefaultEditor(Object.class, null);  //desabilita edicao de celulas
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		button_4 = new JButton("Apagar mensagem");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() < 0)
						JOptionPane.showMessageDialog(null, "selecione uma linha");
					else {
						// pegar o id na linha selecionada
						int id = (int) table.getValueAt(table.getSelectedRow(), 0); // 0=nome

						Object[] options = { "Confirmar", "Cancelar" };
						int escolha = JOptionPane.showOptionDialog(null, "Esta opera��o apagar� a mensagem", "Alerta",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if (escolha == 0) {
							Fachada.apagarMensagem(TelaPrincipal.logado.getNome(), id);
							listagem();
							label.setText("exclus�o realizada");
						} else
							label.setText("exclus�o cancelada");
					}
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.setBounds(24, 277, 176, 23);
		frame.getContentPane().add(button_4);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 347, 718, 14);
		frame.getContentPane().add(label);

		button = new JButton("Criar mensagem");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int index = comboBox.getSelectedIndex();
					if (index < 0)
						label.setText("selecione um destinatario");
					else {
						String nome = (String) comboBox.getSelectedItem();
						Fachada.criarMensagem(TelaPrincipal.logado.getNome(), nome, textField.getText());
						listagem();
					}
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(21, 243, 176, 23);
		frame.getContentPane().add(button);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(207, 244, 602, 20);
		frame.getContentPane().add(textField);

		comboBox = new JComboBox<>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				listagem();
			}
		});
		comboBox.setBounds(184, 20, 301, 22);
		frame.getContentPane().add(comboBox);

		label_1 = new JLabel("Selecione o destinatario:");
		label_1.setBounds(29, 24, 145, 14);
		frame.getContentPane().add(label_1);

		frame.setVisible(true);
	}

	public void carregarComboBox() {
		List<Participante> todos = new ArrayList<>();
		todos.addAll(Fachada.listarIndividuos());
		todos.addAll(Fachada.listarGrupos());

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (Participante p : todos) {
			model.addElement(p.getNome());
		}
		comboBox.setModel(model);
		comboBox.setSelectedIndex(-1);
	}

	public void fechar() {
		frame.dispose();
	}

	public void listagem() {
		try {
			if (comboBox.getSelectedIndex() < 0)
				return;
			String destinatario = (String) comboBox.getSelectedItem();
			List<Mensagem> lista = Fachada.obterConversa(TelaPrincipal.logado.getNome(), destinatario);

			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			// criar as colunas (0,1,2,3,4) da tabela
			model.addColumn("Id");
			model.addColumn("Datahora");
			model.addColumn("Emitente");
			model.addColumn("Destinatario");
			model.addColumn("Texto");
			// criar as linhas da tabela
			label.setText("");
			for (Mensagem m : lista) {
				model.addRow(new Object[]{
						m.getId(), 
						m.getDataHora(),
						m.getEmitente().getNome(), 
						m.getDestinatario().getNome(),
						m.getTexto() 
						});
				}
			table.setModel(model);

			// redimensionar a coluna 3
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setWidth(50); // coluna do id
			table.getColumnModel().getColumn(1).setWidth(100); // coluna do data
			table.getColumnModel().getColumn(4).setMinWidth(300); // coluna do texto
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // habilita
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, ex.getMessage());
		}
	}
}
