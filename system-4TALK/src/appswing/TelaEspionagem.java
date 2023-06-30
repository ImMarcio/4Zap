/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Pesist~encia de Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
import regras_negocio.Fachada;

public class TelaEspionagem {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JLabel label;
	private ButtonGroup grupobotoes;

	private JTextField textField_2;
	private JLabel label_1;
	private JButton button_1;

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
	public TelaEspionagem() {
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
				listagem();
			}
		});
		frame.setTitle("Espionagem");
		frame.setBounds(100, 100, 819, 411);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 53, 749, 283);
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

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 347, 718, 14);
		frame.getContentPane().add(label);

		button = new JButton("listar ausentes");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<String> lista = Fachada.ausentes(TelaPrincipal.logado.getNome());
					String texto = "";
					for (String s : lista)
						texto += s + "\n";
					JOptionPane.showMessageDialog(null, texto, "Ausentes", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(518, 19, 161, 23);
		frame.getContentPane().add(button);

		grupobotoes = new ButtonGroup();

		textField_2 = new JTextField();
		textField_2.setBounds(96, 22, 161, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		label_1 = new JLabel("pesquisa:");
		label_1.setBounds(24, 25, 70, 14);
		frame.getContentPane().add(label_1);

		button_1 = new JButton("Listar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button_1.setBounds(278, 20, 89, 23);
		frame.getContentPane().add(button_1);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			List<Mensagem> lista = Fachada.espionarMensagens(TelaPrincipal.logado.getNome(), textField_2.getText());

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

	public void fechar() {
		frame.dispose();
	}
}
