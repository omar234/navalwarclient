package navalwar.client.network;

import java.io.*;
import java.net.Socket;
import java.util.List;

import navalwar.client.gui.IGUIModule;
import navalwar.client.gui.NavalWarGUI;
import navalwar.client.gui.UnitAndPlace;
import navalwar.server.gameengine.info.IArmyInfo;
import navalwar.server.gameengine.info.IWarInfo;

public class ClientNetworkModule implements IClientNetworkModule {

	IGUIModule gui = null;
	
	//--------------------------------------------
	// Constructors & singleton pattern
	//--------------------------------------------

    private Socket client = null;
    private BufferedReader inServer = null;
    private DataOutputStream outToServer = null;
    private int WarID = 0;

    private ClientNetworkModule() {
		// TODO complete this constructor
	}

	private static ClientNetworkModule instance = null;
	public static ClientNetworkModule getInstance() {
		if (instance == null) instance = new ClientNetworkModule();
		return instance;
	}


	//--------------------------------------------
	// IClientNetworkModule methods
	//--------------------------------------------

	public void bindGUIModule(IGUIModule gui) {
		this.gui = gui;
	}

	public int connect(String ip, int port) {
        try {
            client = new Socket(ip, port);
            outToServer = new DataOutputStream(client.getOutputStream());
            inServer = new BufferedReader(new
                            InputStreamReader(client.getInputStream()));

            return 1;
        }catch (Exception e){
            System.out.println("Imposible conectar");
            return 0;
        }


	}

	public int disconnect() {
		try {
            client.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

		return 0;
	}

	public int createWar(String name, String desc) {
        String message = "";
		try {
            message = "CRT_WAR:"+ name + ":" + desc+ ":" + "EOM"+"\n";
            outToServer.writeBytes(message);
     
        }catch (Exception e){
            System.out.println("No se pudo crear la guerra " + e.getMessage());
        }
        return WarID++;
	}

	
	
	
	
	
	
	
	
	
	
	public int regArmy(int warID, String name, List<UnitAndPlace> unitsAndPlaces) {
   	 String message = "";
		try {
            message = "REG_AR:" + name + ":" +warID+ ":"+unitsAndPlaces+ ":EOM"+"\n";
            outToServer.writeBytes(message);
//            outToServer.flush(); 
        }catch (Exception e){
            System.out.println("No se pudo registrar la armada " + e.getMessage());
        }
        return WarID; //To change body of implemented methods use File | Settings | File Templates.
   }


    public int shot(int warID, int attackArmyID, int targetArmyID, int row,
			int col) {
    	 String message = "";
 		try {
             message = "SHOT:" + warID + ":" + attackArmyID+ ":" + targetArmyID + ":" +row + ":"+col+"\n";
             outToServer.writeBytes(message);
//             outToServer.flush(); 
         }catch (Exception e){
             System.out.println("No se pudo efectuar el disparo " + e.getMessage());
         }
		return 0;
	}

	public IWarInfo getWarInfo(int warID) {
		   String message = "";
			try {
	            message = "GWInfo:" + warID + "\n";
	            outToServer.writeBytes(message);
//	            outToServer.flush(); 
	        }catch (Exception e){
	            System.out.println("No se pudo obtener informacion sobre la guerra " + e.getMessage());
	        }
		return null;
	}

	public IArmyInfo getArmyInfo(int warID, int armyID) {
		   String message = "";
			try {
	            message = "GAInfo:" + warID + ":" + armyID+ ":"+"\n";
	            outToServer.writeBytes(message);
//	            outToServer.flush(); 
	        }catch (Exception e){
	            System.out.println("No se pudo obtener informacion sobre el ejercito " + e.getMessage());
	        }
		return null;
	}
	public int startWar(int warID) {
		 String message = "";
			try {
	            message = "SRT_WAR:" +"\n";
	            outToServer.writeBytes(message);
//	            outToServer.flush();
	        }catch (Exception e){
	            System.out.println("No se pudo iniciar la guerra " + e.getMessage());
	        }
		return 0;
	}

	public List<IWarInfo> getWarsList() {
		 String message = "";
			try {
	            message = "GWList:" +"\n";
	            outToServer.writeBytes(message);
//	            outToServer.flush();
	        }catch (Exception e){
	            System.out.println("No se pudo obtener la lista de guerras " + e.getMessage());
	        }
		return null;
	}



}
