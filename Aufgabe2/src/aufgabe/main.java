package aufgabe;


import aufgabe.FileImport;

public class main {
	public static void main(String[] args) {
	FileImport fileReader = new FileImport();
	Information infos = fileReader.readFile("DeutschlandMitStaedten.svg");
	LogicA2 logic = new LogicA2();
	
	
	for (int i = 0; i < infos.getStates().size(); i++) {
		double result = logic.calcAcreage(infos.getStates().get(i));
		System.out.println(result);
	}
	
	
	for(int i = 0; i < infos.getStates().size(); i++) {
		
		for(int circle = 0; circle < infos.getStates().get(i).getCircles().size(); circle++) {
			logic.pointInPolygone(infos.getCities().get(0).getPoint(), infos.getStates().get(i).getCircles().get(circle));
		}
		
	}
	
	
	
}
}
