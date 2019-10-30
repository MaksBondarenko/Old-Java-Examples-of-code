/**
 *
 *  @author Bondarenko Maksym S16748
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server {
	private Map<String,String> uzytkowniki = new HashMap<String,String>();
	static int PORT = 9676;
    static int BUFFER_SIZE = 1024;
    ServerSocketChannel serverSocketChannel;
    Selector selector;
    ArrayList<String> logged = new ArrayList<String>();
	Server(){
		
		try (FileReader reader = new FileReader("Hasla.txt");
	             BufferedReader br = new BufferedReader(reader)) {
	            String line;
	            System.out.println("Juz istnieja uzytkowniki:");
	            while ((line = br.readLine()) != null) {
	            	String[] NickAndHaslo =line.split(" -Separator228- ");
	        		uzytkowniki.put(NickAndHaslo[0], NickAndHaslo[1]);
	        		System.out.println("UserName: \""+NickAndHaslo[0]+"\" Password: \""+ NickAndHaslo[1]+"\"");
	            }

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
		
		ByteBuffer sharedBuffer = 
		         ByteBuffer.allocateDirect(BUFFER_SIZE);
		       
		       try {
		         serverSocketChannel =
		           ServerSocketChannel.open();
		         serverSocketChannel.configureBlocking(false);
		         InetSocketAddress inetSocketAddress = 
		           new InetSocketAddress(PORT);
		         serverSocketChannel.bind(inetSocketAddress);
		         
		         new Thread( 
		        	 ()->{
		        		  selector = null;
				         try {
				        	 selector = Selector.open();
				        	 serverSocketChannel.register(
				        			 selector, SelectionKey.OP_ACCEPT);
						} catch (Exception e3) {
							e3.printStackTrace();
						}
				         
		        		 while (true) {
		        	            try {
									selector.select();
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
		        	            Set<SelectionKey> selectedKeys = selector.selectedKeys();
		        	            Iterator<SelectionKey> iter = selectedKeys.iterator();
		        	            
		        	            while (iter.hasNext()) {
		        	            	
		        	                SelectionKey key = iter.next();
		        	                if (key.isAcceptable()) {
		        	                	SocketChannel client;
										try {
											client = serverSocketChannel.accept();
											if (client != null){
												client.configureBlocking(false);
												client.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
											}
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		        	                }
										//System.out.println("Is read");
		        	                    if (key.isReadable()) {
		        	                        //System.out.println("Reading channel");
		        	                        SocketChannel socketChannel = 
		        	                          (SocketChannel) key.channel(); 
		        	                        sharedBuffer.clear();
		        	                        int bytes = -1;
		        	                        String mes = "";
		        	                        try {
		        	                          while (
		        	                           (bytes = socketChannel.read(sharedBuffer)) > 0) 
		        	                            {
		        	                              //System.out.println("Reading...");
		        	                              sharedBuffer.flip(); 
		        	                              while (sharedBuffer.hasRemaining()) {
		        	                            	  byte[] b = new byte[sharedBuffer.remaining()];
		        	                            	  sharedBuffer.get(b);
		        	                            	String str =  new String(b);
		        	                                mes+=str;
		        	                              }
		        	                                if(mes.contains("this#@!is123@valid")){
		        	                                	String[] msg = mes.split("this#@!is123@valid");
		        	                                	if(isIn(msg[0])) {
		        	                                		if(isUser(msg[0],msg[1])) {

			        	                                		if(!logged.contains(msg[0])) {
		        	                                			logged.add(msg[0]);
		        	                                			ByteBuffer b = ByteBuffer.wrap("true".getBytes());
		        	                                			socketChannel.write(b);
			        	                                		}else{
			        	                                			ByteBuffer b = ByteBuffer.wrap("false".getBytes());
			        	                                			socketChannel.write(b);
			        	                                		}
		        	                                			//and send msg
		        	                                		}else {
		        	                                			ByteBuffer b = ByteBuffer.wrap("false".getBytes());
		        	                                			socketChannel.write(b);
		        	                                		}
		        	                                	}else {
		        	                                		if(!logged.contains(msg[0])) {
		        	                                		addUser(msg[0],msg[1]);
		        	                                		logged.add(msg[0]);
		        	                                		ByteBuffer b = ByteBuffer.wrap("true".getBytes());
		        	                                		socketChannel.write(b);
		        	                                		}else{
		        	                                			ByteBuffer b = ByteBuffer.wrap("false".getBytes());
		        	                                			socketChannel.write(b);
		        	                                		}
		        	                                		//and send msg
		        	                                	}
		        	                                }else
		        	                                	send(mes);
		        	                                //socketChannel.write(sharedBuffer);
		        	                              
		        	                              sharedBuffer.clear();
		        	                            }
		        	                        } catch (IOException e) {
		        	                          System.err.println("Error writing back bytes");
		        	                          e.printStackTrace();
		        	                          key.cancel();
		        	                        }
		        	                    }
		        	                
		        	                //iter.remove();
		        	            }
		        	        }
		        	 }
		         ).start();
		         
		       } catch (IOException e) {
		         System.err.println("Unable to setup environment");
		         System.exit(-1);
		       }
		       
	}
	public void addUser(String nick, String pass) {
		uzytkowniki.put(nick, pass);
		try (FileWriter writer = new FileWriter("Hasla.txt",true);
				BufferedWriter bw = new BufferedWriter(writer)) {
				bw.newLine();
				bw.write(nick+" -Separator228- "+pass);
			} catch (IOException ex) {
				System.err.format("IOException: ", ex);
			}
	}
	public boolean isIn(String nick) {
		//System.out.println("Is in?");
		if(uzytkowniki.containsKey(nick))
				return true;
			return false;
	}
	public boolean isUser(String nick, String pass) {
		//System.out.println("Is user?");
		if(uzytkowniki.containsKey(nick))
			if(uzytkowniki.get(nick).equals(pass))
				return true;
			return false;
	}
	private void send(String mes) {
		try {

			selector.select();

			Set keys = selector.selectedKeys();

			Iterator iter = keys.iterator();

			while(iter.hasNext()) {
				//System.out.println("Sending 2");
				SelectionKey key = (SelectionKey) iter.next();
				if (key.isWritable()) {
					//System.out.println("Sending 23");
					SocketChannel cc = (SocketChannel) key.channel();

					// System.out.println("Channel is writable");
					ByteBuffer b = ByteBuffer.wrap(mes.getBytes());
					while(b.hasRemaining()) {
						try {
							cc.write(b);
							//System.out.println("Sending 3");
						} catch (IOException e) {
							e.printStackTrace();
						}

				}

			}

		}} catch(Exception exc) {

			exc.printStackTrace();

		}
		}
  public static void main(String[] args) {
	  new Server();
  }
}
