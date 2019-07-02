import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server extends Frame {
    private Label labelaNeisporucene = new Label("Neisporucene porudzbine");
    private Label labelaIsporucene = new Label("Isporucene porudzbine");
    private Panel glavniPanel = new Panel();
    private Panel neisporucenePanel = new Panel();
    private Panel isporucenePanel = new Panel();
    private Panel neisporuceneTimerPanel = new Panel();

    public Server() {
        setSize(1200, 700);
        setBackground(new Color(239, 200, 186));
        setLayout(new GridLayout(4, 1));
        setTitle("Server");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(1);
            }
        });

        Font font = new Font("Arial", Font.BOLD, 22);
        labelaNeisporucene.setFont(font);
        labelaNeisporucene.setAlignment(Label.CENTER);
        labelaIsporucene.setAlignment(Label.CENTER);
        labelaNeisporucene.setSize(700, 40);
        labelaIsporucene.setFont(font);
        labelaIsporucene.setBackground(Color.green);
        labelaNeisporucene.setBackground(Color.orange);
        neisporucenePanel.setBackground(Color.white);
        glavniPanel.setLayout(new GridLayout(1, 2));
        neisporuceneTimerPanel.setBackground(Color.yellow);
        neisporucenePanel.setLayout(new GridLayout(11, 1));
        neisporuceneTimerPanel.setLayout(new GridLayout(11, 1));
        isporucenePanel.setLayout(new GridLayout(11, 1));

        add(labelaNeisporucene);
        glavniPanel.add(neisporucenePanel, "West");
        glavniPanel.add(neisporuceneTimerPanel, "East");
        add(glavniPanel);
        add(labelaIsporucene);
        add(isporucenePanel);

        setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            System.out.println("Server pokrenut...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Uspostavljena veza izmedju klijenta i servera...");

                new Thread() {
                    @Override
                    public void run() {
                        BufferedReader in = null;
                        try {
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                            String picapica = in.readLine();

                            Random random = new Random();
                            int vreme = random.nextInt(40) + 10;
                            out.println(vreme);

                            System.out.println("Vreme do isporuke: " + vreme);
                            Label picaLabela = new Label(picapica);
                            neisporucenePanel.add(picaLabela);
                            revalidate();

                            ArrayList<Label> labele = new ArrayList<Label>();
                            labele.add(new Label(""));
                            for (Label labela : labele) {
                                new Thread() {
                                    boolean isporucena = false;
                                    @Override
                                    public void run() {
                                        labela.setText("" + vreme);
                                        neisporuceneTimerPanel.add(labela);
                                        revalidate();
                                        for (int i = vreme; i >= 0; i--) {
                                            try {
                                                labela.setText("" + i);
                                                Thread.sleep(1000);
                                                if (i == 0) {
                                                    isporucena = true;
                                                    neisporuceneTimerPanel.remove(labela);
                                                }
                                                if (isporucena) {
                                                    isporucenePanel.add(new Label(picapica));
                                                    neisporucenePanel.remove(picaLabela);
                                                    revalidate();
                                                    repaint();
                                                }
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }.start();
                            }
                            in.close();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

}



























/*
import java.awt.*;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;
        import java.io.*;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.Random;


public class Server extends Frame {
    private Label labelaNeisporucene = new Label("Neisporucene pice");
    private TextArea textAreaNeisporucene = new TextArea();
    private Label labelaIsporucene = new Label("Isporucene pice");
    private TextArea textAreaIsporucene = new TextArea();



    public Server(){
        setSize(1000,500);
        setBackground(new Color(239,200,186));
        setLayout(new GridLayout(2,2));
        setTitle("Server");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(1);
            }
        });



        Font font = new Font("Arial", Font.BOLD,22);
        labelaNeisporucene.setFont(font);
        labelaNeisporucene.setAlignment(Label.CENTER);
        labelaIsporucene.setAlignment(Label.CENTER);
        labelaIsporucene.setFont(font);
        labelaIsporucene.setBackground(Color.green);
        labelaNeisporucene.setBackground(Color.orange);
        textAreaNeisporucene.setColumns(100);



        add(labelaNeisporucene);
        add(labelaIsporucene);
        add(textAreaNeisporucene);
        add(textAreaIsporucene);


        setVisible(true);



        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            System.out.println("Server pokrenut...");
            //textAreaNeisporucene.append("Server pokrenut" + "\n");

            //ovo ti je bilo u while(true). Server treba da radi non-stop. Prema tome otvara se samo jednom (i nikada se ne zatvara),
            // radi samo sa jednim klijentom (jer ne radis preko thread-a).
            //Klijent sada moze poslati vise od jedne narudzbine

            Socket socket = serverSocket.accept();
            System.out.println("Uspostavljena veza izmedju klijenta i servera...");

            //textAreaNeisporucene.append("Uspostavljena konekcija" + "\n");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);



            while (true) {
                //Ubaceno od nas
                String picapica = in.readLine(); //citanje toStringa koji je klijent poslao
                //System.out.println("Picapica: "+picapica); // ispis procitanog

                //Do ovde

                /*a ovo je citanje iz datoteke
                FileInputStream fis = new FileInputStream("pica.bin");
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    Pica pica = (Pica) ois.readObject();
                    fis.close();
                    ois.close();
                    textAreaNeisporucene.append(picapica + "\n");
                    */

/*                textAreaNeisporucene.append(picapica + "\n");

                new Thread(){
                    Random random = new Random();
                    int vreme= random.nextInt(40)+10;

                    @Override
                    public void run() {
                        try {
                            boolean isporucena = false;
                            out.println(vreme);
                            textAreaNeisporucene.append("\n");
                            System.out.println("Vreme do isporuke: " + vreme);

                            for (int i = vreme; i > 0; i--) {
                                Thread.sleep(1000);
                                System.out.println(i);
                                textAreaNeisporucene.append("" + i);
                                char ch = ((char) i);



                            }

                            isporucena=true;
                            if (isporucena){
                                textAreaIsporucene.append(picapica+ "\n");
                                textAreaNeisporucene.setText("");
                            }




                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();


                //System.out.println("Pica iz datoteke");
                //System.out.println(pica);



                //in.close();
                //out.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public static void main(String[] args){
        Server server = new Server();

    }


}*/
