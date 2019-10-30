/**
 *
 *  @author Bondarenko Maksym S16748
 *
 */

package zad1;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

public class Client{
	Server server;
	String name=null;
	private SocketChannel client;
    private ByteBuffer buffer;
	Client(Server server){
		this.server=server;
		JFrame logowanie = new JFrame("Logowanie");
		logowanie.setBounds(600, 300, 500, 300);
		JLabel nameLabel = new JLabel("NickName:",JLabel.RIGHT);
		JLabel passLabel = new JLabel("Password:",JLabel.RIGHT);
		JTextField inputNick = new JTextField();
		inputNick.setPreferredSize(new Dimension(300,40));
		JTextField inputPass = new JTextField();
		inputPass.setPreferredSize(new Dimension(300,40));
		nameLabel.setFont(new Font("serif", Font.PLAIN, 20));
		passLabel.setFont(new Font("serif", Font.PLAIN, 20));  
		JButton okButton = new JButton("Ok");
		okButton.setPreferredSize(new Dimension(100,50)); 
		try {
			client = SocketChannel.open();
			client.connect(new InetSocketAddress("localhost", Server.PORT));
            client.configureBlocking(false);
            buffer = ByteBuffer.allocate(Server.BUFFER_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
		okButton.addActionListener( e->{
			if((inputNick.getText()!=null&&!inputNick.getText().equals(""))&&(inputPass.getText()!=null&&!inputPass.getText().equals(""))) {
					String nick = inputNick.getText();
					if((!(inputNick.getText()+inputPass.getText()).contains(" -Separator228- "))&&isUser(nick, inputPass.getText())) {
							name = inputNick.getText();
						  mainWindow();
						  logowanie.setVisible(false);
					}else 
						JOptionPane.showMessageDialog(null, "User name or password is incorrect or user is already logged in");
					}else
				JOptionPane.showMessageDialog(null, "Please, input user name and password");
		});
		logowanie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    logowanie.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    logowanie.add(nameLabel, gbc);
	    gbc.gridx++;
	    logowanie.add(inputNick, gbc);
	    gbc.gridx--;
	    gbc.gridy++;
	    logowanie.add(passLabel, gbc);
	    gbc.gridx++;
	    logowanie.add(inputPass, gbc);
	    gbc.gridy++;
	    logowanie.add(okButton,gbc);
	    logowanie.setVisible(true);
	}
	public void mainWindow() {
		JFrame mainWindow = new JFrame("MyChat");
		mainWindow.setBounds(400, 200, 800, 600);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea chat = new JTextArea();
		JTextArea input = new JTextArea();
		chat.setEditable(false);
		JScrollPane jpChat = new JScrollPane(chat);
		JScrollPane jpInp = new JScrollPane(input);
		chat.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		input.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		jpChat.setPreferredSize(new Dimension(500,400));
		jpInp.setPreferredSize(new Dimension ( 500,100));
		JButton sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(100,50)); 
		mainWindow.setLayout(new GridBagLayout());
		new Thread(()->{
       		 try {
       		 while(true) {
       			StringBuffer resultString = new StringBuffer();
       			resultString.setLength(0);
       			Charset charset  = Charset.forName("UTF-8");
       			ByteBuffer bbuf = ByteBuffer.allocate(1024);

       			bbuf.clear();
    				int n = client.read(bbuf);

    				if (n > 0) {

    					bbuf.flip();

    					CharBuffer cbuf = charset.decode(bbuf);

    					while(cbuf.hasRemaining()) {

    						char c = cbuf.get();

    						resultString.append(c);
    					}
    				}
    				if(resultString.length()>0)
    					chat.setText(chat.getText()+'\n'+resultString.toString());
       		 }
       		} catch (Exception e) {
				e.printStackTrace();
			}
       	 }
		).start();
		sendButton.addActionListener((e)->{
			String msg = input.getText();
			ByteBuffer b = ByteBuffer.wrap((name+":"+msg).getBytes());
			while(b.hasRemaining())
				try {
					client.write(b);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			input.setText("");
		});
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    mainWindow.add(jpChat, gbc);
	    gbc.gridx = 0;
	    gbc.gridy--;
	    mainWindow.add(jpInp, gbc);
	    gbc.gridx++;
	    gbc.gridy--;
	    mainWindow.add(sendButton, gbc);
	    JLabel nameLabel = new JLabel("You logged in as: "+name);
	    nameLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
	    gbc.gridx=0;
	    gbc.gridy=-1;
	    mainWindow.add(nameLabel, gbc);
	    mainWindow.setVisible(true);
	}
	public boolean isUser(String nick, String pass) {
		String msg = nick+"this#@!is123@valid"+pass;
		ByteBuffer b = ByteBuffer.wrap(msg.getBytes());
		String response = null;
		while(b.hasRemaining()) {
			try {
				client.write(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		b.clear();
		int bytes = -1;
        while(response==null)
        	response=czytaj();
	if(response.equals("nulltrue"))
		return true;
	else
		return false;
	}
	public String czytaj() {
		ByteBuffer b = ByteBuffer.allocate(1024);
		int bytes=-1;
		String response=null;
		try {
	          while (
	           (bytes = client.read(b)) > 0) 
	            {
	              b.flip(); 
	              while (b.hasRemaining()) {
	            	  byte[] byt = new byte[b.remaining()];
	            	  b.get(byt);
	            	String str =  new String(byt);
	            	response+=str;
	              }
	            }
	        }catch (IOException e) {
				e.printStackTrace();
			}
		return response;
	}
	public static void main(String[] args) {
	  Client client = new Client(new Server());
	}
}
