/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.selenium_automatizacia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

/**
 *
 *
 */
public class UserFrame extends JFrame {
    
    private static final String NewLine = "\n";
    
    private JTextField Text_meno, Text_priezvisko, Text_telcislo, Text_email;
    private JComboBox Combo_pobocky;
    private String Meno_text, Priezvisko_text, Telcislo_text, 
            Email_text, Combo_text;
    private JButton OK, Zrusit;
    private JTextArea Text_check;
    private WebDriver driver ;

    public UserFrame() {
        super("Automatizovane vyplnenie dotaznika");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();

//        Listener na uvolnenie drivera z pamate pri zatvoreni formulara.
        this.addWindowListener(new WindowAdapter() {            
            public void windowClosing(WindowEvent e) {
                if(UserFrame.this.driver != null) {
                    UserFrame.this.driver.quit();                
                }                
            }
        });    
        
//        Hlavny kontajner do kt. su postupne pridavane
//        jednotlive komponenty formulara.
        JPanel hlavny_kontajner = new JPanel();
        BoxLayout box = new BoxLayout(hlavny_kontajner, BoxLayout.Y_AXIS);
        hlavny_kontajner.setLayout(box);
        
//        Kontajner pre zahlavie
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER));        
        JTextArea Text_header = new JTextArea("Chystate sa odoslat nasledovne" +
                " udaje na URL https://www.otpbanka.sk/otp-hypo-uver:", 2, 30);
        Text_header.setLineWrap(true);
        Text_header.setWrapStyleWord(true);        
        Text_header.setEditable(false);
        Text_header.setFont(new Font("", Font.PLAIN, 16));
        header.add(Text_header);        
        hlavny_kontajner.add(header);        
        
//        Kontajner pre textove pole 'Meno'.
        JPanel r_1 = new JPanel();
        r_1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel Label_meno = new JLabel("Meno");
        this.Text_meno = new JTextField(22);
        
        this.Text_meno.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {                
                veifyMeno ();
            }
            
            public void removeUpdate(DocumentEvent e) {
                veifyMeno ();
            }
            
            public void insertUpdate(DocumentEvent e) {
                veifyMeno ();
            }
            
            private void veifyMeno () {
                String regex = "^[a-zA-Z]+$";;                
                if (UserFrame.this.Text_meno.getText().matches(regex)) {
                    UserFrame.this.Meno_text = "Hodnota pre 'Meno' je platna.";
                } else {
                    UserFrame.this.Meno_text = "- - - - V poli 'Meno' zadajte znak. - - - -";
                }
                Validuj_input();
            }
        });
        
        Label_meno.setLabelFor(this.Text_meno);        
        r_1.add(Label_meno);
        r_1.add(this.Text_meno);        
        hlavny_kontajner.add(r_1);
        
//        Kontajner pre textove pole 'Priezvisko'.
        JPanel r_2 = new JPanel();
        r_2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel Label_priezvisko = new JLabel("Priezvisko");
        this.Text_priezvisko = new JTextField(22);
        
        this.Text_priezvisko.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {                
                veifyPriezvisko ();
            }
            
            public void removeUpdate(DocumentEvent e) {
                veifyPriezvisko ();
            }
            
            public void insertUpdate(DocumentEvent e) {
                veifyPriezvisko ();
            }
            
            private void veifyPriezvisko () {
                String regex = "^[a-zA-Z]+$";;                
                if (UserFrame.this.Text_priezvisko.getText().matches(regex)) {
                    UserFrame.this.Priezvisko_text = "Hodnota pre 'Priezvisko' je platna.";
                } else {
                    UserFrame.this.Priezvisko_text = "- - - - V poli 'Priezvisko' zadajte znak. - - - -";
                }
                Validuj_input();
            }
        });
        
        Label_priezvisko.setLabelFor(this.Text_priezvisko);
        r_2.add(Label_priezvisko);
        r_2.add(this.Text_priezvisko);
        hlavny_kontajner.add(r_2);
        
//        Kontajner pre textove pole 'Telefonne cislo'.
        JPanel r_3 = new JPanel();
        r_3.setLayout(new FlowLayout(FlowLayout.RIGHT));        
        JLabel Label_telcislo = new JLabel("Telefonne cislo");
        this.Text_telcislo = new JTextField(22);
        
        this.Text_telcislo.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {                
                veifyPhone ();
            }
            
            public void removeUpdate(DocumentEvent e) {
                veifyPhone ();
            }
            
            public void insertUpdate(DocumentEvent e) {
                veifyPhone ();
            }
            
            private void veifyPhone () {
                String regex = "^\\d{9}$";                
                if (UserFrame.this.Text_telcislo.getText().matches(regex)) {
                    UserFrame.this.Telcislo_text = "Hodnota pre 'Telefonne cislo' je platna.";
                }
                else {
                    UserFrame.this.Telcislo_text = "- - - - V poli 'Telefonne cislo' zadajte 9 cislic. - - - -";
                }                
                Validuj_input();
            }
        });
        
        Label_telcislo.setLabelFor(this.Text_telcislo);
        r_3.add(Label_telcislo);
        r_3.add(this.Text_telcislo);
        hlavny_kontajner.add(r_3);
        
//        Kontajner pre textove pole 'Email'.
        JPanel r_4 = new JPanel();
        r_4.setLayout(new FlowLayout(FlowLayout.RIGHT));        
        JLabel Label_email = new JLabel("Email");
        this.Text_email = new JTextField(22);
        
        this.Text_email.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {                
                veifyEmail ();
            }
            
            public void removeUpdate(DocumentEvent e) {
                veifyEmail ();
            }
            
            public void insertUpdate(DocumentEvent e) {
                veifyEmail ();
            }
            
            private void veifyEmail () {
                String email = UserFrame.this.Text_email.getText();
                email = email.trim();                
                if(email == null | email.equals("")) {
                    UserFrame.this.Email_text = "V poli 'Email' zadajte email v tvare meno@domena";
                }
                if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$")) {
                    UserFrame.this.Email_text = "- - - - V poli 'Email' zadajte email v tvare meno@domena - - - -";
                } else {
                    UserFrame.this.Email_text = "Hodnota pre 'Email' je platna.";
                }
                Validuj_input();
            }
        });
        
        Label_email.setLabelFor(this.Text_email);
        r_4.add(Label_email);
        r_4.add(this.Text_email);
        hlavny_kontajner.add(r_4);
        
//        Kontajner pre combobox 'Vyberte pobocku'. Pre
//        ilustraciu je k dispozicii vyber zo 4 pobociek.
        JPanel r_5 = new JPanel();
        r_5.setLayout(new FlowLayout(FlowLayout.RIGHT));

        String[] pobocky = { "(_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _)", "Bratislava - Polus", 
            "Žilina", "Banská Bystrica", "Košice - Alžbetina" };
        
        this.Combo_pobocky = new JComboBox(pobocky);        
        this.Combo_pobocky.setBackground(Color.white);
        
        this.Combo_pobocky.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {           
                if (UserFrame.this.Combo_pobocky.getSelectedItem().equals("(_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _)")) {
                    UserFrame.this.Combo_text= "- - - - Vyberte pobocku - - - -";
                } else {
                    UserFrame.this.Combo_text= "Hodnota pre 'Pobocku' je platna.";
                }                
                Validuj_input();
            }
        });
        
        JLabel Label_pobocky = new JLabel("Kontaktna pobocka");
        Label_pobocky.setLabelFor(this.Combo_pobocky);
        r_5.add(Label_pobocky);
        r_5.add(this.Combo_pobocky);
        hlavny_kontajner.add(r_5);                
        
//        Kontajner pre navestie k prikazovym tlacitkam.
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));        
        JTextArea Text_footer = new JTextArea("Prajete si odoslat uvedene udaje a"
                + " dalej pokracovat na stranke OTP Banky Slovensko, a.s.?", 2, 30);
        Text_footer.setLineWrap(true);
        Text_footer.setWrapStyleWord(true);        
        Text_footer.setEditable(false);
        Text_footer.setFont(new Font("", Font.PLAIN, 16));
        footer.add(Text_footer);        
        hlavny_kontajner.add(footer);
        
//        Kontajner pre prikazove tlacitka.
        JPanel Panel_exe = new JPanel();
        Panel_exe.setLayout(new FlowLayout());        
        this.OK = new JButton("OK");
        this.Zrusit = new JButton("Zrusit");        
        this.OK.setEnabled(false);
        
        this.OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    Spustit_skript();
                    Aktivuj_okno();
                } catch (InterruptedException ex) { }
            }
        });
        
        this.Zrusit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(UserFrame.this.driver != null){
                    UserFrame.this.driver.quit();
                } 
                System.exit(0);
            }
        });        
        
        Panel_exe.add(this.OK);
        Panel_exe.add(this.Zrusit);        
        hlavny_kontajner.add(Panel_exe);        
        
//        Kontajner pre navestie k validacnym kriteriam.
        JPanel navestie = new JPanel();
        navestie.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel Navestie_text = new JLabel("Validacne kriteria:");
        navestie.add(Navestie_text);
        hlavny_kontajner.add(navestie);
        
//        Kontajner pre validacne kriteria.
        JPanel Panel_check = new JPanel();
        Panel_check.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.Meno_text = "- - - - V poli 'Meno' zadajte znak. - - - -";
        this.Priezvisko_text = "- - - - V poli 'Priezvisko' zadajte znak. - - - -";
        this.Telcislo_text = "- - - - V poli 'Telefonne cislo' zadajte 9 cislic. - - - -";
        this.Email_text = "- - - - V poli 'Email' zadajte email v tvare meno@domena - - - -";
        this.Combo_text = "- - - - Vyberte pobocku - - - -";
        this.Text_check = new JTextArea(this.Meno_text + NewLine +  
                this.Priezvisko_text + NewLine +
                this.Telcislo_text + NewLine +
                this.Email_text +NewLine +
                this.Combo_text, 5, 30);
        this.Text_check.setEditable(false);                
        Panel_check.add(this.Text_check);        
        hlavny_kontajner.add(Panel_check);
        
        add(hlavny_kontajner);
        pack();        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setVisible(true);
    }

//        Metoda sluzi na vyhodnotenie toho ci su splnene vsetky validacne kriteria.
    private void Validuj_input() {
        this.Text_check.setText(this.Meno_text + NewLine +                
                this.Priezvisko_text + NewLine +
                this.Telcislo_text + NewLine +
                this.Email_text + NewLine +
                this.Combo_text);
        if ((this.Meno_text.contains("platna")) &&
                (this.Priezvisko_text.contains("platna")) &&
                (this.Telcislo_text.contains("platna")) &&
                (this.Email_text.contains("platna")) &&
                (this.Combo_text).contains("platna")) {            
            this.Text_check.setText(NewLine + NewLine + "                " + 
                    "               Udaje uz mozete odoslat.");            
            OK.setEnabled(true);
        } else {
            OK.setEnabled(false);
        }
    }

//        Metoda sluzi na poskytnutie udajov do dotaznika.
    private void Spustit_skript() throws InterruptedException {
        Nadviazat_spojenie();
        JavascriptExecutor je = (JavascriptExecutor) this.driver;
        WebElement element;
        Thread.sleep(2000);

//        Vyplnenie udajov v poli 'Meno'.
        element = this.driver.findElement(By.xpath("//*[contains(@id,'fieldId_2704_name')]"));
        element.clear();
        je.executeScript("arguments[0].scrollIntoView(true);",element);        
        element.sendKeys(this.Text_meno.getText());
        Thread.sleep(1000);

//        Vyplnenie udajov v poli 'Priezvisko'.
        element = this.driver.findElement(By.xpath("//*[contains(@id,'fieldId_2707_surname')]"));
        element.clear();
        element.sendKeys(this.Text_priezvisko.getText());
        Thread.sleep(1000);

//        Vyplnenie udajov v poli 'Telefonne cislo'.
        element = this.driver.findElement(By.xpath("//*[contains(@id,'fieldId_2710_phone')]"));
        element.clear();
        element.sendKeys(this.Text_telcislo.getText());
        Thread.sleep(1000);

//        Vyplnenie udajov v poli 'Email'.
        element = this.driver.findElement(By.xpath("//*[contains(@id,'fieldId_2713_email')]"));
        element.clear();
        element.sendKeys(this.Text_email.getText());
        Thread.sleep(1000);
        
//        Vyplnenie udajov v poli 'Kontaktna pobocka'.
        String pobocka;
        switch ((String)this.Combo_pobocky.getSelectedItem()) {
            case "Bratislava - Polus":
                pobocka = "2015";
                break;
            case "Žilina":
                pobocka = "4013";
                break;
            case "Banská Bystrica":
                pobocka = "4001";
                break;
            case "Košice - Alžbetina":
                pobocka = "5001";
                break;
            default:
                pobocka = "2015";
        }        
        
//        Prve kliknutie zobrazi list s pobockami. Nasledne sa
//        naskroluje vybrana pobocka a kliknutim sa vyberie.
        element = this.driver.findElement(By.xpath("//div[@class='selectize-control tx-widget tx-field tx-combobox single']"));
        element.click();
        element = this.driver.findElement(By.xpath("//div[@data-value='" + pobocka + "']"));
        je.executeScript("arguments[0].scrollIntoView(true);",element);        
        element.click();

//        Naskroluje obrazovku na uvodne pole, t.j. 'Vyberte pobocku'.
        element = this.driver.findElement(By.xpath("//*[contains(@id,'fieldId_2704_name')]"));
        je.executeScript("arguments[0].scrollIntoView(true);",element);        
        
//        Potvrdenie suhlasu so spracovanim osobnych udajov.
        element = this.driver.findElement(By.xpath("//input[@class='tx-widget tx-field tx-checkbox']"));
        je.executeScript("arguments[0].click();", element);

//        Zmena polohy slidera za ucelom Odomknutia formulara. Try - Catch blok
//        sluzi pre pripad opatovneho zadavania dat.
        try {
            element = this.driver.findElement(By.xpath("//div[@class='Slider ui-draggable ui-draggable-handle']"));            
            Actions move = new Actions(this.driver);
            Action action = move.dragAndDropBy(element, 250, 0).build();        
            action.perform();
        } catch (NoSuchElementException ex) { }
        Thread.sleep(1000);
        
        element = null;
        je = null;        
        
        String[] options = { "Nie" };
        int n = JOptionPane.showOptionDialog(null,
                   "Udaje v dotazniku boli vyplnene." +
                    NewLine + "Prajete si odoslat udaje banke?", 
                   "Hotovo", 0, JOptionPane.QUESTION_MESSAGE, null,
                   options, options[0]);
    }
    
//        Metoda sluzi na inicializaciu spojenia.
    private void Nadviazat_spojenie() {
        if (this.driver == null) {
            Path path = FileSystems.getDefault().getPath("src\\resources\\geckodriver.exe");
            System.setProperty("webdriver.gecko.driver",path.toString());        
            this.driver = new FirefoxDriver();
            driver.manage().window().maximize();
            this.driver.get("https://www.otpbanka.sk/otp-hypo-uver");            
        }
    }
    
//        Metoda sluzi na zobrazanie vstupneho formulara po vyplneni dotaznika.
    private void Aktivuj_okno(){
        this.toFront();
        this.setLocation(25, 25);        
    }

//        Metoda sluzi na vybranie dizajnu v, kt. budu
//        vyobrazene komponenty na formulari.
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
            );
        } catch (Exception exc) {
            System.err.println("Couldn't use the system "
                + "look and feel: " + exc);
        }
    }

    public static void main(String[] arguments) {
        UserFrame frame = new UserFrame();
    }    
}
