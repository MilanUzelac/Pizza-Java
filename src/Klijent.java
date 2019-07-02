import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Klijent extends Frame {
    private Label kreirajPorudzbinu = new Label("Kreiraj porudzbinu");
    private Label velicinaPice = new Label("Izaberite velicinu pice");
    private CheckboxGroup cbg = new CheckboxGroup();
    private Checkbox pica25 = new Checkbox("25cm", cbg, false);
    private Checkbox pica32 = new Checkbox("32cm", cbg, false);
    private Checkbox pica50 = new Checkbox("50cm", cbg, false);
    private Label vrstaPice = new Label("Izaberite vrstu pice");
    private CheckboxGroup cbg2 = new CheckboxGroup();
    private Checkbox margarita = new Checkbox("Margarita", cbg2, false);
    private Checkbox funghi = new Checkbox("Funghi", cbg2, false);
    private Checkbox quatroStagione = new Checkbox("Quatro Stagione", cbg2, false);
    private Checkbox vegetariana = new Checkbox("Vegeteriana", cbg2, false);
    private Label izaberiDodatkeLabel = new Label("Izaberite dodatke");
    private Checkbox kecapCheckbox = new Checkbox("Kecap");
    private Checkbox majonezCheckbox = new Checkbox("Majonez");
    private Checkbox origanoCheckbox = new Checkbox("Origano");
    private Label nacinPlacanja = new Label("Nacin placanja");
    private CheckboxGroup cbg3 = new CheckboxGroup();
    private Checkbox karticaCheckbox = new Checkbox("Placanje karticom", cbg3, false);
    private Checkbox gotovinaCheckbox = new Checkbox("Placanje gotovinom", cbg3, false);
    private Label labelaAdresa = new Label("Unesite vasu adresu");
    private TextField adresaTextField = new TextField();
    private Label labelaBrojTelefona = new Label("Unesite vas broj telefona");
    private TextField brojTelefonaTextfield = new TextField();
    private Label labelaNapomena = new Label("Napomena");
    private TextField napomenaTextField = new TextField();
    private Button dugmePosalji = new Button("Poruci!");
    private Label odgovorLabela = new Label("Odgovor");
    private TextArea textAreaOdgovor = new TextArea();
    private Socket socket;

    private static boolean DaLiJeBroj(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public Klijent() {
        setLayout(new GridLayout(26, 2));
        setSize(600, 800);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(1);
            }
        });

        setTitle("Narucite picu!");
        InetAddress adresa = null;
        try {
            adresa = InetAddress.getByName("127.0.0.1");
            socket = new Socket(adresa, 7777);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(new Color(239, 200, 186));
        Font font = new Font("Arial", Font.BOLD, 22);
        Font font2 = new Font("Arial", Font.BOLD, 12);
        kreirajPorudzbinu.setFont(font);
        velicinaPice.setFont(font);
        vrstaPice.setFont(font);
        margarita.setFont(font2);
        funghi.setFont(font2);
        quatroStagione.setFont(font2);
        vegetariana.setFont(font2);
        izaberiDodatkeLabel.setFont(font);
        kecapCheckbox.setFont(font2);
        majonezCheckbox.setFont(font2);
        origanoCheckbox.setFont(font2);
        nacinPlacanja.setFont(font);
        karticaCheckbox.setFont(font2);
        gotovinaCheckbox.setFont(font2);
        adresaTextField.setBackground(Color.yellow);
        labelaAdresa.setFont(font);
        labelaBrojTelefona.setFont(font);
        labelaNapomena.setFont(font);
        brojTelefonaTextfield.setBackground(Color.yellow);
        napomenaTextField.setBackground(Color.yellow);
        dugmePosalji.setLabel("Poruci!");
        dugmePosalji.setBackground(Color.orange);
        odgovorLabela.setFont(font);

        add(kreirajPorudzbinu);
        add(velicinaPice);
        add(pica25);
        add(pica32);
        add(pica50);
        add(vrstaPice);
        add(margarita);
        add(funghi);
        add(quatroStagione);
        add(vegetariana);
        add(izaberiDodatkeLabel);
        add(kecapCheckbox);
        add(majonezCheckbox);
        add(origanoCheckbox);
        add(nacinPlacanja);
        add(gotovinaCheckbox);
        add(karticaCheckbox);
        add(labelaAdresa);
        add(adresaTextField);
        add(labelaBrojTelefona);
        add(brojTelefonaTextfield);
        add(labelaNapomena);
        add(napomenaTextField);
        add(dugmePosalji);
        add(odgovorLabela);
        add(textAreaOdgovor);

        setVisible(true);

        dugmePosalji.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                    boolean ucitajPodatke = true;
                    String dodaci = "";

                    if (adresaTextField.getText().isEmpty() || brojTelefonaTextfield.getText().isEmpty()) {
                        Dialog dialog = new Dialog(new Frame("Niste uneli!!"));
                        dialog.setTitle("Oppssss!");
                        dialog.add(new Label("Morate popuniti obavezna polja za adresu i broj telefona!", Label.CENTER));
                        dialog.setSize(400, 100);
                        dialog.setLocation(760, 490);
                        Button dugme = new Button("OK");
                        dialog.setLayout(new GridLayout(2, 1));
                        dialog.add(dugme);
                        dugme.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                dialog.setVisible(false);
                            }
                        });
                        dialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent windowEvent) {
                                dialog.setVisible(false);
                            }
                        });
                        ucitajPodatke = false;
                        dialog.setVisible(true);
                    }

                    boolean ucitajBrojeve = false;
                    if (ucitajPodatke) {

                        String unos = brojTelefonaTextfield.getText();
                        if (Klijent.DaLiJeBroj(unos) && (unos.length() <= 10 && unos.length() >= 8)) {
                            ucitajBrojeve = true;
                        } else {
                            Dialog dialog = new Dialog(new Frame("Nieste uneli!!"));
                            dialog.setTitle("Oppssss!");
                            dialog.add(new Label("Morate uneti brojeve telefona: tj broj od 8 do 10 cifara, a ne slova!!", Label.CENTER));
                            dialog.setSize(500, 200);
                            dialog.setLocation(710, 440);
                            Button dugme = new Button("OK");
                            dialog.setLayout(new GridLayout(2, 1));
                            dialog.add(dugme);
                            dugme.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    dialog.setVisible(false);
                                }
                            });
                            dialog.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent windowEvent) {
                                    dialog.setVisible(false);
                                }
                            });
                            dialog.setVisible(true);
                        }
                    }

                    if (ucitajPodatke && ucitajBrojeve) {
                        if (kecapCheckbox.getState() && majonezCheckbox.getState() && origanoCheckbox.getState()) {
                            dodaci = kecapCheckbox.getLabel() + "'" + majonezCheckbox.getLabel() + "'" + origanoCheckbox.getLabel();
                        } else if (majonezCheckbox.getState() && origanoCheckbox.getState()) {
                            dodaci = majonezCheckbox.getLabel() + "'" + origanoCheckbox.getLabel();
                        } else if (kecapCheckbox.getState() && origanoCheckbox.getState()) {
                            dodaci = kecapCheckbox.getLabel() + "'" + origanoCheckbox.getLabel();
                        } else if (kecapCheckbox.getState() && majonezCheckbox.getState()) {
                            dodaci = kecapCheckbox.getLabel() + "'" + majonezCheckbox.getLabel();
                        } else if (origanoCheckbox.getState()) {
                            dodaci = origanoCheckbox.getLabel();
                        } else if (majonezCheckbox.getState()) {
                            dodaci = majonezCheckbox.getLabel();
                        } else if (kecapCheckbox.getState()) {
                            dodaci = kecapCheckbox.getLabel();
                        }

                        Pica pica = new Pica(cbg.getSelectedCheckbox().getLabel(), cbg2.getSelectedCheckbox().getLabel(), dodaci, cbg3.getSelectedCheckbox().getLabel(), adresaTextField.getText(), brojTelefonaTextfield.getText(), napomenaTextField.getText());
                        System.out.println(pica.toString());

                        out.println(pica.toString());

                        String vreme = in.readLine();
                        textAreaOdgovor.append("Pica ce biti isporucena za: " + vreme + " minuta..." + "\n");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        Klijent klijent = new Klijent();
    }
}
