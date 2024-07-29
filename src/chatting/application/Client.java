package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*; //bg color ke liye kiye.
import java.awt.event.*; // action ke liye actionlistener iske andr ataa hai.
import java.util.*; // time dikhane ke liye calendar iske andr rhta hai.
import java.text.*; // time ke format ke liye.
import java.net.*;
import java.io.*;

public class Client extends JFrame implements ActionListener {
    JTextField text;
    static JPanel a1; 
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    
    Client()   //Constructer
    {
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1); //frame mai set krta hai panel ko.
        
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);    // back button dbane se exit hojayega iss se.
            }
        });
        //Image set krne ke liye.
        //Profile image set.
        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        
        //video image set.
        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        
        //Phone image set.
        ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);
        
        //more image set.
        ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(415, 20, 10, 25);
        p1.add(more);
        
        //adding profile name.
        JLabel name = new JLabel("Rahul");
        name.setBounds(110, 15, 100, 20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        
        //Adding status active or not.
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 20); // height neeche chahiye tho bdhaana hota hai number.
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        a1 = new JPanel();
        a1.setBounds(5,75, 440, 570);
        f.add(a1);
        //Text field krne wala bnayee iss se.
        text= new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);
        // Send button bnayee.
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        f.add(send);
        
        f.setSize(450,700); // Frame ka size set kiye.
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.setVisible(true); // by default visiblity false rhti hai dikhne ke liye true krna rhata hai.
        
    }
    public void actionPerformed(ActionEvent ae){
        try{
        String out = text.getText();
        
        JPanel p2 = formatLabel(out);
      
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15)); // Space between two words.
        
        a1.add(vertical, BorderLayout.PAGE_START);
        
        dout.writeUTF(out);
        
        text.setText("");
        f.repaint();
        f.invalidate();
        f.validate();
    }catch(Exception e){
        e.printStackTrace();
    }    
    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance(); // msg ke neeche time ke liye.
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    public static void main(String[] args){
        new Client();   //anonymus object.
        // socket programming to connect server.
        try{
            Socket s = new Socket("127.0.0.1", 6001);
             DataInputStream din = new DataInputStream(s.getInputStream()); // msg recive krne ke liye.
             dout = new DataOutputStream(s.getOutputStream()); // msg bhejne ke liye.
             
             while(true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();//msg read krke "msg" ke andr daal diya.
                JPanel panel = formatLabel(msg);//panel mai daalne ke liye.
                    
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);// left side dikhana hai tho start mai kiye.
                vertical.add(left);
                
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                
                f.validate(); // frame ko refresh krne ke liye.
            }
                
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

